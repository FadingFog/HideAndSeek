package me.fadingfog.hideandseek.tasks;

import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

public class HidingCountdown extends BukkitRunnable {
    private final ConfigStorage config = ConfigStorage.getInstance();
    private final Arena arena = Arena.getInstance();
    private final Game game = Game.getInstance();

    final int init_timer = (int) config.getTimeToHide();
    int timer = init_timer;

    @Override
    public void run() {
        Duration timerDur;

        if (timer == init_timer) {
            game.teleportPlayers(arena.getSeekers(), arena.getSeekersLobbyLocation());
            arena.sendSeekersMessage("You are " + ChatColor.RED + "Seeker");
            game.teleportPlayers(arena.getHiders(), arena.getLocation());
            arena.sendHidersMessage("You are " + ChatColor.DARK_GREEN + "Hider");

            timerDur = ConfigStorage.parseToDuration(timer);
            arena.sendArenaMessage("Hiders have " + ConfigStorage.formatDuration(timerDur) + " to hide");

        } else if (timer == init_timer / 2) {
            timerDur = ConfigStorage.parseToDuration(timer);
            arena.sendArenaMessage("Hiders have " + ConfigStorage.formatDuration(timerDur) + " left to hide");

        } else if (timer == 30) {
            timerDur = ConfigStorage.parseToDuration(timer);
            arena.sendArenaMessage("Hiders have " + ConfigStorage.formatDuration(timerDur) + " left to hide");

        } else if (timer == 0) {
            game.setGameState(Game.GameState.Seeking);
            cancel();
        }

        timer--;
    }
}
