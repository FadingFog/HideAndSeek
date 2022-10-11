package me.fadingfog.hideandseek.tasks;

import me.fadingfog.hideandseek.HideAndSeek;
import me.fadingfog.hideandseek.game.Game;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskManager extends BukkitRunnable {
    private final HideAndSeek plugin = HideAndSeek.getInstance();
    private final Game game = Game.getInstance();
    private PrepareCountdown prepareCountdown = null;
    private HidingCountdown hidingCountdown = null;
    private SeekingCountdown seekingCountdown = null;

    @Override
    public void run() {
        switch (game.getGameState()) {
            case Closed:
            case End:
                cancel();
                break;
            case Preparing:
                if (prepareCountdown == null) {
                    prepareCountdown = new PrepareCountdown();
                    prepareCountdown.runTaskTimer(plugin, 0L, 20L);
                }
                break;
            case Hiding:
                if (hidingCountdown == null) {
                    hidingCountdown = new HidingCountdown();
                    hidingCountdown.runTaskTimer(plugin, 0L, 20L);
                }
                break;
            case Seeking:
                if (seekingCountdown == null) {
                    seekingCountdown = new SeekingCountdown();
                    seekingCountdown.runTaskTimer(plugin, 0L, 20L);
                }
                break;
            default:
                plugin.getLogger().severe("[TaskManager] Unknown GameState");
                break;
        }
    }
}
