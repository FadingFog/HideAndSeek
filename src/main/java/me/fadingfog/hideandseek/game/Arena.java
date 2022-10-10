package me.fadingfog.hideandseek.game;

import me.fadingfog.hideandseek.ConfigStorage;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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

    public void sendArenaMessage(String msg, List<Player> members) {
        for (Player player : members) {
            player.sendMessage(msg);
        }
    }

}
