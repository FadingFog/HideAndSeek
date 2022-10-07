package me.fadingfog.hideandseek.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    private static Arena instance;
    private Location location = null; // TODO get from config by default
    private List<Player> members = new ArrayList<>();

    public static Arena getInstance() {
        return instance;
    }

    public void setup() {
        instance = this;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        // TODO set to config and then reload config
    }

    public List<Player> getMembers() {
        return members;
    }

    public boolean addMember(Player player) {
        if (!this.members.contains(player)) {
            this.members.add(player);
            player.teleport(location);  // TODO: Teleport via Essentials api (to /back support if false in config)
            return true;
        }
        return false;
    }

    public boolean removeMember(Player player) {
        return this.members.remove(player);
    }

    public void sendArenaMessage(String msg) {
        for (Player player : members) {
            player.sendMessage(msg);
        }
    }

}
