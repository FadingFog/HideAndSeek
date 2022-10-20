package me.fadingfog.hideandseek.tasks;

import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.placeholder.HnsExpansion;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class SeekingCountdown extends BukkitRunnable {
    private final ConfigStorage config = ConfigStorage.getInstance();
    private final Arena arena = Arena.getInstance();
    private final Game game = Game.getInstance();
    private final HnsExpansion hnsExpansion = HnsExpansion.getInstance();

    final int init_timer = (int) config.getTimeToSeek();
    int timer = init_timer;

    @Override
    public void run() {
        if (timer == init_timer) {
            game.teleportPlayers(arena.getSeekers(), arena.getLocation());
            arena.sendArenaMessage("It's time to " + ChatColor.RED  + " seek!");

            arena.sendArenaMessage(ConfigStorage.formatDuration(timer) + " left in the game");
        } else if (timer == 0) {
            arena.sendArenaMessage("Game over. Time's up");
            game.setGameState(Game.GameState.End);
            cancel();
        } else if (timer == init_timer / 2 || timer == 5 * 60 || timer == 3 * 60 || timer == 60 || timer == 5 || timer <= 5) {
            arena.sendArenaMessage(ConfigStorage.formatDuration(timer) + " left in the game");
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
        hnsExpansion.timer = timer;
    }
}
