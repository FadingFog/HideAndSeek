package me.fadingfog.hideandseek.utils;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Trade;
import com.earth2me.essentials.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import static org.bukkit.Bukkit.getServer;

public class TeleportUtil {
    final static Essentials ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
    final static Trade charge = new Trade("", ess);

    public static void teleportPlayer(Player player, Location location) {
        User user = ess.getUser(player);

        try {
            user.getTeleport().teleport(location, charge, PlayerTeleportEvent.TeleportCause.PLUGIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void teleportPlayerBack(Player player) {
        User user = ess.getUser(player);

        try {
            user.getTeleport().back(charge);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
