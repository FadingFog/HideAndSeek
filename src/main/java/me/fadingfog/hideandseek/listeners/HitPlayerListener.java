package me.fadingfog.hideandseek.listeners;

import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.GamePlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.text.MessageFormat;

import static me.fadingfog.hideandseek.commands.CommandManager.prefix;

public class HitPlayerListener implements Listener {
    private final Arena arena = Arena.getInstance();
    private final Game game = Game.getInstance();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent ev) {
        if (ev.getEntity() instanceof Player && ev.getDamager() instanceof Player) {
            Player seeker = (Player) ev.getDamager();
            Player hider = (Player) ev.getEntity();

            if (seeker.getWorld() == arena.getLocation().getWorld()) {
                ev.setCancelled(true);

                if (game.getGameState() == Game.GameState.Seeking) {

                    GamePlayer gSeeker = arena.getGamePlayer(seeker);
                    GamePlayer gHider = arena.getGamePlayer(hider);
                    if (gSeeker.getRole() == GamePlayer.Role.SEEKER && gHider.getRole() == GamePlayer.Role.HIDER) {

                        seeker.playSound(seeker.getLocation(), Sound.SUCCESSFUL_HIT, 1, 0);
                        Location hiderLoc = hider.getLocation().add(0, 1.5, 0);

                        for (int degree = 0; degree < 360; degree++) {
                            double radians = Math.toRadians(degree);
                            double nx = Math.cos(radians);
                            double nz = Math.sin(radians);
                            hiderLoc.add(nx, 0, nz);
                            hider.getWorld().playEffect(hiderLoc, Effect.PORTAL, 1);
                            hiderLoc.subtract(nx, 0, nz);
                        }

                        String message = ChatColor.AQUA + MessageFormat.format("{0} поймал {1}", seeker.getDisplayName(), hider.getDisplayName());

                        seeker.sendMessage(prefix + ChatColor.AQUA + "Вы поймали " + hider.getDisplayName());
                        arena.sendArenaMessage(prefix + message);

                        gSeeker.setScore(gSeeker.getScore() + 1);

                        hider.teleport(arena.getLocation());
                        gHider.setRole(GamePlayer.Role.SEEKER);
                        hider.sendMessage(prefix + ChatColor.AQUA + "Вас поймали. Теперь вы охотник");
                    }
                }
            }
        }

    }

}
