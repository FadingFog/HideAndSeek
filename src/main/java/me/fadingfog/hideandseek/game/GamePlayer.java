package me.fadingfog.hideandseek.game;

import org.bukkit.entity.Player;

public class GamePlayer {
    public enum Role {
        HIDER("Hider"),
        SEEKER("Seeker");

        final String name;

        Role(String name) {
            this.name = name;
        }
    }

    private Role role;
    private Player player;

    public GamePlayer(Role role, Player player) {
        this.role = role;
        this.player = player;

        player.sendMessage("You are a " + role.name);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
