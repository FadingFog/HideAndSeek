package me.fadingfog.hideandseek.tasks;

import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.I18n;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.placeholder.HnsExpansion;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import static me.fadingfog.hideandseek.commands.CommandManager.MAIN_COLOR;
import static me.fadingfog.hideandseek.commands.CommandManager.SECONDARY_COLOR;

public class HidingCountdown extends BukkitRunnable {
    private final ConfigStorage config = ConfigStorage.getInstance();
    private final Arena arena = Arena.getInstance();
    private final Game game = Game.getInstance();
    private final HnsExpansion hnsExpansion = HnsExpansion.getInstance();

    final int initTimer = (int) config.getTimeToHide();
    int timer = initTimer;

    @Override
    public void run() {
        if (timer == initTimer) {
            game.teleportPlayers(arena.getSeekers(), arena.getSeekersLobbyLocation());
            arena.sendSeekersMessage(I18n.tl("youAreSeeker", ChatColor.RED));
            game.teleportPlayers(arena.getHiders(), arena.getLocation());
            arena.sendHidersMessage(I18n.tl("youAreHider", ChatColor.DARK_GREEN));

            arena.sendArenaMessage(I18n.tl("timerHide", SECONDARY_COLOR + ConfigStorage.formatDuration(timer) + MAIN_COLOR));

        } else if (timer == initTimer / 2 || timer == 30) {
            arena.sendArenaMessage(I18n.tl("timerHideLeft", SECONDARY_COLOR + ConfigStorage.formatDuration(timer) + MAIN_COLOR));

        } else if (timer == 0) {
            game.setGameState(Game.GameState.Seeking);
            cancel();
        }

        hnsExpansion.timer = timer;
        timer--;
    }
}
