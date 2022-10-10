package me.fadingfog.hideandseek.game;

import me.fadingfog.hideandseek.ConfigStorage;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Random;

public class Game {
    public enum GameState {
        Closed, WarmUp, Ingame, End;
    }
    private static Game instance;
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
        setGameState(GameState.WarmUp);
        lobby.closeLobby();
        preparePlayers(lobby.getMembers());
        randomizeRoles();

    }


    private void preparePlayers(List<Player> players){
        for (Player player : players) {
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
        }
    }

    public void randomizeRoles() {
        int numberOfSeekers = config.getNumberOfSeekers();
        List<Player> hiders = lobby.getMembers();

        Random random = new Random();


        for (int i = 0; i < numberOfSeekers; i++){
            int randomIndex = random.nextInt(hiders.size());
            Player seeker = hiders.get(randomIndex);
            GamePlayer player = new GamePlayer(GamePlayer.Role.SEEKER, seeker);
            arena.addPlayer(player);
            hiders.remove(randomIndex);
            lobby.removeMember(seeker);
        }

        for (Player hider : hiders) {
            GamePlayer player = new GamePlayer(GamePlayer.Role.HIDER, hider);
            arena.addPlayer(player);
            lobby.removeMember(hider);

        }

    }

}
