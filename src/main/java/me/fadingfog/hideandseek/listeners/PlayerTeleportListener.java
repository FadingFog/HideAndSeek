package me.fadingfog.hideandseek.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import static org.bukkit.Bukkit.getServer;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void onUserTeleport(PlayerTeleportEvent ev) {
//        Essentials ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
//
//        Player player = ev.getPlayer();
//        User user = ess.getUser(player);
//
//        final boolean backListener = ess.getSettings().registerBackInListener();
//
//        System.out.println("backListener: " + backListener);
//        System.out.println("Cause: " + ev.getCause().toString());
//        System.out.println("Last loc: " + user.getLastLocation().toString());
    }

}
