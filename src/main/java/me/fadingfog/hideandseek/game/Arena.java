package me.fadingfog.hideandseek.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    private List<Player> playersOnArena = new ArrayList<>();

    public void sendArenaMessage(String msg) {
        for (Player player : playersOnArena) {
            player.sendMessage(msg);
        }
    }

    public void sendArenaTitle() {
//        for (Player player : playersOnArena) {
//            player.sendTitle
//        }
    }
}
