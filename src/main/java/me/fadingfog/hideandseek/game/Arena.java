package me.fadingfog.hideandseek.game;

import me.fadingfog.hideandseek.ConfigStorage;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Arena {
    private static Arena instance;
    private final ConfigStorage config = ConfigStorage.getInstance();

    private final String configPath = "arena-location";
    private final String configSeekersPath = "seekers-lobby-location";
    private Location location = config.getLocation(configPath);
    private Location seekersLobbyLocation = config.getLocation(configSeekersPath);

    private final List<GamePlayer> players = new ArrayList<>();


    public static Arena getInstance() {
        return instance;
    }

    public void setup() {
        instance = this;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        config.setLocation(configPath, location);
        this.location = config.getLocation(configPath);
    }

    public Location getSeekersLobbyLocation() {
        return this.seekersLobbyLocation;
    }

    public void setSeekersLobbyLocation(Location location) {
        config.setLocation(configSeekersPath, location);
        this.seekersLobbyLocation = config.getLocation(configSeekersPath);
    }

    public List<GamePlayer> getPlayers() {
        return this.players;
    }

    public void addPlayer(GamePlayer player) {
        this.players.add(player);
    }

    public GamePlayer getPlayer(Player player) {
        List<GamePlayer> gamePlayers = this.players.stream().filter(p -> p.getPlayer() == player).collect(Collectors.toList());
        if (gamePlayers.size() > 0) {
            return gamePlayers.get(0);
        }
        return null;
    }

    public boolean removePlayer(GamePlayer gPlayer) {
        // TODO teleport player to /back
        return this.players.remove(gPlayer);
    }

    public boolean removePlayer(Player player) {
        GamePlayer gPlayer = getPlayer(player);
        if (gPlayer == null) {
            return false;
        }
        // TODO teleport player to /back
        return this.players.remove(gPlayer);
    }

    public List<GamePlayer> getSeekers() {
        return this.players.stream().filter(p -> p.getRole() == GamePlayer.Role.SEEKER).collect(Collectors.toList());
    }

    public List<GamePlayer> getHiders() {
        return this.players.stream().filter(p -> p.getRole() == GamePlayer.Role.HIDER).collect(Collectors.toList());
    }

    public void sendArenaMessage(String msg) {
        for (GamePlayer p : players) {
            Player player = p.getPlayer();
            player.sendMessage(msg);
        }
    }

    public void sendSeekersMessage(String msg) {
        for (GamePlayer seeker : getSeekers()) {
            Player player = seeker.getPlayer();
            player.sendMessage(msg);
        }
    }

    public void sendHidersMessage(String msg) {
        for (GamePlayer hider : getHiders()) {
            Player player = hider.getPlayer();
            player.sendMessage(msg);
        }
    }

}
