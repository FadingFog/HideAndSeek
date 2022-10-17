package me.fadingfog.hideandseek.game;

import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.utils.TeleportUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    public enum LobbyState {
        Closed, Open;
    }
    private static Lobby instance;
    private LobbyState lobbyState = LobbyState.Closed;
    private final ConfigStorage config = ConfigStorage.getInstance();

    private final String configPath = "lobby-location";
    private Location location = config.getLocation(configPath);
    private final List<Player> members = new ArrayList<>();


    public static Lobby getInstance() {
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

    public LobbyState getLobbyState() {
        return lobbyState;
    }

    public void setLobbyState(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }

    public boolean openLobby() {
        if (this.lobbyState == LobbyState.Closed) {
            setLobbyState(LobbyState.Open);
            return true;
        }
        return false;
    }

    public boolean closeLobby() {
        if (this.lobbyState == LobbyState.Open) {
            setLobbyState(LobbyState.Closed);
            return true;
        }
        return false;
    }

    public List<Player> getMembers() {
        return this.members;
    }

    public boolean addMember(Player player) {
        if (!(this.members.contains(player)) && this.lobbyState == LobbyState.Open) {
            this.members.add(player);
            TeleportUtil.teleportPlayer(player, this.location);
            return true;
        }
        return false;
    }

    public void addMembers(List<Player> players) {
        this.members.addAll(players);
    }

    public boolean removeMember(Player player) {
        return this.members.remove(player);
    }

    public void clearMembers() {
        this.members.clear();
    }

    public void sendLobbyMessage(String msg) {
        for (Player player : members) {
            player.sendMessage(msg);
        }
    }

}
