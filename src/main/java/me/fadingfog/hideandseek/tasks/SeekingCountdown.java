package me.fadingfog.hideandseek.tasks;

import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

public class SeekingCountdown extends BukkitRunnable {
    private final ConfigStorage config = ConfigStorage.getInstance();
    private final Arena arena = Arena.getInstance();
    private final Game game = Game.getInstance();

    final int init_timer = (int) config.getTimeToSeek();
    int timer = init_timer;

    @Override
    public void run() {
        Duration timerDur;

        if (timer == init_timer) {
            game.teleportPlayers(arena.getSeekers(), arena.getLocation());
            arena.sendArenaMessage("It's time to " + ChatColor.RED  + " seek!");

            timerDur = ConfigStorage.parseToDuration(timer);
            arena.sendArenaMessage(ConfigStorage.formatDuration(timerDur) + " left");
        } else if (timer == init_timer / 2) {
            timerDur = ConfigStorage.parseToDuration(timer);
            arena.sendArenaMessage(ConfigStorage.formatDuration(timerDur) + " left");
        } else if (timer == 0) {
            arena.sendArenaMessage("Game over. Time's up");
            game.setGameState(Game.GameState.End);
            cancel();
        }
        if (arena.getHiders().size() == 0) {
            arena.sendArenaMessage("Game over. All hiders have been found");
            game.setGameState(Game.GameState.End);
            cancel();
        } else if (arena.getSeekers().size() == 0) {
            arena.sendArenaMessage("Game over. There are no seekers left");
            game.setGameState(Game.GameState.End);
            cancel();
        }

        timer--;
    }
}
