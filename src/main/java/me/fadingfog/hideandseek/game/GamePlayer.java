package me.fadingfog.hideandseek.game;

import org.bukkit.entity.Player;

public class GamePlayer {
    public enum Role {
        HIDER("Hider"),
        SEEKER("Seeker");

        private final String name;

        Role(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private Role role;
    private Player player;
    private int score = 0;

    public GamePlayer(Role role, Player player) {
        this.role = role;
        this.player = player;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return player.getDisplayName();
    }
}
