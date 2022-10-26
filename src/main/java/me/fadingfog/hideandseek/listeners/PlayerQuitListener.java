package me.fadingfog.hideandseek.listeners;

import me.fadingfog.hideandseek.I18n;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Lobby;
import me.fadingfog.hideandseek.utils.TeleportUtil;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.fadingfog.hideandseek.commands.CommandManager.prefix;

public class PlayerQuitListener implements Listener {
    private final Lobby lobby = Lobby.getInstance();
    private final Arena arena = Arena.getInstance();

    @EventHandler
    public void onUserQuit(PlayerQuitEvent ev) {
        Player player = ev.getPlayer();
        World world = player.getWorld();

        if (world == lobby.getLocation().getWorld() || world == arena.getLocation().getWorld()) {
            if (lobby.removeMember(player)) {
                for (Player p : world.getPlayers()) {
                    p.sendMessage(prefix + ChatColor.DARK_AQUA + I18n.tl("leftTheLobby", player.getDisplayName()));
                }
                TeleportUtil.teleportPlayerBack(player);
            }
            if (arena.removeGamePlayer(player)) {
                for (Player p : world.getPlayers()) {
                    p.sendMessage(prefix + ChatColor.DARK_AQUA + I18n.tl("leftTheGame", player.getDisplayName()));
                }
                TeleportUtil.teleportPlayerBack(player);
            }
        }

    }
}
