package me.fadingfog.hideandseek.tasks;

import me.fadingfog.hideandseek.HideAndSeek;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.Lobby;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskManager extends BukkitRunnable {
    private final HideAndSeek plugin = HideAndSeek.getInstance();
    private final Lobby lobby = Lobby.getInstance();
    private final Game game;
    private PreparingCountdown preparingCountdown = null;
    private HidingCountdown hidingCountdown = null;
    private SeekingCountdown seekingCountdown = null;

    public TaskManager(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        switch (game.getGameState()) {
            case Closed:
            case End:
                cancelAllCountdowns();
                game.resetPlayersPrefix();
                game.returnPlayersToLobby();
                lobby.getLocation().getWorld().playSound(lobby.getLocation(), Sound.LEVEL_UP, 2, 1);
                game.setGameState(Game.GameState.Closed);
                cancel();
                break;
            case Preparing:
                if (preparingCountdown == null) {
                    preparingCountdown = new PreparingCountdown();
                    preparingCountdown.runTaskTimer(plugin, 0L, 20L);
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

    public void cancelAllCountdowns() {
        if (preparingCountdown != null) preparingCountdown.cancel();
        if (hidingCountdown != null) hidingCountdown.cancel();
        if (seekingCountdown != null) seekingCountdown.cancel();
    }
}
