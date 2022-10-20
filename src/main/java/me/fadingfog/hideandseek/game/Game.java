package me.fadingfog.hideandseek.game;

import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.HideAndSeek;
import me.fadingfog.hideandseek.tasks.TaskManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Random;

public class Game {
    public enum GameState {
        Closed("Closed"),
        Preparing("Preparing"),
        Hiding("Hiding"),
        Seeking("Seeking"),
        End("End");

        private final String name;

        GameState(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    private static Game instance;
    private final HideAndSeek plugin = HideAndSeek.getInstance();
    private final ConfigStorage config = ConfigStorage.getInstance();
    private final Lobby lobby = Lobby.getInstance();
    private final Arena arena = Arena.getInstance();

    private GameState gameState = GameState.Closed;


    public static Game getInstance() {
        return instance;
    }

    public void setup() {
        instance = this;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void start() {
        lobby.closeLobby();
        List<Player> members = lobby.getMembers();
        preparePlayers(members);
        randomizeRoles(members);
        lobby.clearMembers();

        setGameState(GameState.Preparing);

        TaskManager taskManager = new TaskManager();
        taskManager.runTaskTimer(plugin, 20L, 10L);
    }

    public boolean stop() {
        if (getGameState() != Game.GameState.Closed) {
            setGameState(GameState.End);
            return true;
        } else {
            return false;
        }
    }


    private void preparePlayers(List<Player> players){
        for (Player player : players) {
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
        }
    }

    public void randomizeRoles(List<Player> hiders) {
        final int numberOfSeekers = config.getNumberOfSeekers();
        Random random = new Random();


        for (int i = 0; i < numberOfSeekers; i++){
            int randomIndex = random.nextInt(hiders.size());
            Player seeker = hiders.get(randomIndex);
            GamePlayer gPlayer = new GamePlayer(GamePlayer.Role.SEEKER, seeker);
            arena.addGamePlayer(gPlayer);
            hiders.remove(randomIndex);
        }

        for (Player hider : hiders) {
            GamePlayer gPlayer = new GamePlayer(GamePlayer.Role.HIDER, hider);
            arena.addGamePlayer(gPlayer);
        }
    }

    public void teleportPlayers(List<GamePlayer> gamePlayers, Location location) {
        for (GamePlayer gp : gamePlayers) {
            Player player = gp.getPlayer();
            player.teleport(location);
        }
    }

    public void returnPlayersToLobby() {
        lobby.addMembers(arena.getPlayers());
        teleportPlayers(arena.getGamePlayers(), lobby.getLocation());
        arena.clearGamePlayers();
    }
}
