package me.fadingfog.hideandseek.tasks;

import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.I18n;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.GamePlayer;
import me.fadingfog.hideandseek.placeholder.HnsExpansion;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

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

        LinkedHashMap<GamePlayer, Integer> topSeekers = arena.getTopSeekers(5);
        LinkedHashMap<GamePlayer, Integer> topHiders = arena.getTopHiders(5);

        arena.sendArenaMessage("----------------------");
        arena.sendArenaMessage(I18n.tl("topSeekers"));
        arena.sendArenaMessage("----------------------");
        int counter = 1;
        for (Map.Entry<GamePlayer, Integer> entry : topSeekers.entrySet()) {
            GamePlayer gPlayer = entry.getKey();
            int score = entry.getValue();
            arena.sendArenaMessage(MessageFormat.format("{0}. {1} - {2}", counter, gPlayer.getName(), score));
            counter++;
        }

        arena.sendArenaMessage("----------------------");
        arena.sendArenaMessage(I18n.tl("topHiders"));
        arena.sendArenaMessage("----------------------");
        counter = 1;
        for (Map.Entry<GamePlayer, Integer> entry : topHiders.entrySet()) {
            GamePlayer gPlayer = entry.getKey();
            int totalTimeAsHider = entry.getValue();

            arena.sendArenaMessage(MessageFormat.format("{0}. {1} - {2}",
                    counter,
                    gPlayer.getName(),
                    totalTimeAsHider > 0 ? ConfigStorage.formatDuration(totalTimeAsHider) : I18n.tl("seeker")
            ));

            counter++;
        }

        arena.sendArenaMessage("----------------------");


        game.setGameState(Game.GameState.End);
        cancel();
    }

}
