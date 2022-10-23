package me.fadingfog.hideandseek.tasks;

import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.I18n;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.GamePlayer;
import me.fadingfog.hideandseek.placeholder.HnsExpansion;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import static me.fadingfog.hideandseek.commands.CommandManager.SECONDARY_COLOR;

public class SeekingCountdown extends BukkitRunnable {
    private final ConfigStorage config = ConfigStorage.getInstance();
    private final Arena arena = Arena.getInstance();
    private final Game game = Game.getInstance();
    private final HnsExpansion hnsExpansion = HnsExpansion.getInstance();

    final int initTimer = (int) config.getTimeToSeek();
    int timer = initTimer;

    @Override
    public void run() {
        if (timer == initTimer) {
            game.teleportPlayers(arena.getSeekers(), arena.getLocation());
            arena.sendArenaMessage(I18n.tl("timeToSeek", ChatColor.RED));

            for (GamePlayer gPlayer : arena.getGamePlayers()) {
                if (gPlayer.getRole() == GamePlayer.Role.SEEKER) {
                    gPlayer.setInitTimeAsSeeker();
                } else if (gPlayer.getRole() == GamePlayer.Role.HIDER) {
                    gPlayer.setInitTimeAsHider();
                }
            }

            arena.sendArenaMessage(I18n.tl("timerSeekLeft", SECONDARY_COLOR + ConfigStorage.formatDuration(timer)));

        } else if (timer == 0) {
            arena.sendArenaMessage(I18n.tl("gameOverTimeIsUp"));
            stop();

        } else if (timer == initTimer / 2 || timer == 5 * 60 || timer == 3 * 60 || timer == 60 || timer <= 5) {
            arena.sendArenaMessage(I18n.tl("timerSeekLeft", SECONDARY_COLOR + ConfigStorage.formatDuration(timer)));
        }

        if (arena.getHiders().size() == 0) {
            arena.sendArenaMessage(I18n.tl("gameOverHidersFound"));
            stop();
        } else if (arena.getSeekers().size() == 0) {
            arena.sendArenaMessage(I18n.tl("gameOverNoSeekers"));
            stop();
        }

        timer--;
        hnsExpansion.timer = timer;
    }

    private void stop() {
        for (GamePlayer gPlayer : arena.getGamePlayers()) {
            if (gPlayer.getRole() == GamePlayer.Role.SEEKER) {
                gPlayer.setTotalTimeAsSeeker();
            } else if (gPlayer.getRole() == GamePlayer.Role.HIDER) {
                gPlayer.setTotalTimeAsHider();
            }
        }

        game.setGameState(Game.GameState.End);
        cancel();
    }

}
