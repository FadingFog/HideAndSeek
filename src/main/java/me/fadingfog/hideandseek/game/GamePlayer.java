package me.fadingfog.hideandseek.game;

import me.fadingfog.hideandseek.I18n;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GamePlayer {
    public enum Role {
        HIDER(ChatColor.DARK_GREEN + I18n.tl("hider"), ChatColor.DARK_GREEN + "[" + I18n.tl("hider") + "] " + ChatColor.WHITE),
        SEEKER(ChatColor.RED + I18n.tl("seeker"), ChatColor.RED + "[" + I18n.tl("seeker") + "] " + ChatColor.WHITE);

        private final String name;
        private final String prefix;

        Role(String name, String prefix) {
            this.name = name;
            this.prefix = prefix;
        }

        public String getName() {
            return name;
        }

        public String getPrefix() {
            return prefix;
        }
    }

    private Role role;
    private Player player;
    private int score = 0;
    private long initTimeAsHider;
    private long initTimeAsSeeker;
    private int totalTimeAsHider = 0;
    private int totalTimeAsSeeker = 0;

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

    public void setInitTimeAsHider() {
        this.initTimeAsHider = System.currentTimeMillis();
    }

    public void setInitTimeAsSeeker() {
        this.initTimeAsSeeker = System.currentTimeMillis();
    }

    public int getTotalTimeAsHider() {
        return totalTimeAsHider;
    }

    public void setTotalTimeAsHider() {
        this.totalTimeAsHider = (int) ((System.currentTimeMillis() - this.initTimeAsHider) / 1000);
    }

    public int getTotalTimeAsSeeker() {
        return totalTimeAsSeeker;
    }

    public void setTotalTimeAsSeeker() {
        this.totalTimeAsSeeker = (int) ((System.currentTimeMillis() - this.initTimeAsSeeker) / 1000);
    }
}
