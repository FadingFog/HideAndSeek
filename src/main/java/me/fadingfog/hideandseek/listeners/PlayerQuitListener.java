package me.fadingfog.hideandseek.listeners;

import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Lobby;
import me.fadingfog.hideandseek.utils.TeleportUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final Lobby lobby = Lobby.getInstance();
    private final Arena arena = Arena.getInstance();


    @EventHandler
    public void onUserQuit(PlayerQuitEvent ev) {
        Player player = ev.getPlayer();
        World world = player.getWorld();

        if (world == lobby.getLocation().getWorld() || world == arena.getLocation().getWorld()) {
            lobby.removeMember(player);
            arena.removeGamePlayer(player);
            for (Player p : world.getPlayers()) {
                p.sendMessage(player.getDisplayName() + " left the game");
            }
            TeleportUtil.teleportPlayerBack(player);
        }
    }

}
