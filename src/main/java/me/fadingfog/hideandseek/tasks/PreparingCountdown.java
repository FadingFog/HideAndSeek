package me.fadingfog.hideandseek.tasks;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.I18n;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.Lobby;
import me.fadingfog.hideandseek.placeholder.HnsExpansion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

import static me.fadingfog.hideandseek.commands.CommandManager.SECONDARY_COLOR;

public class PreparingCountdown extends BukkitRunnable {
    private final ConfigStorage config = ConfigStorage.getInstance();
    private final Lobby lobby = Lobby.getInstance();
    private final Arena arena = Arena.getInstance();
    private final Game game = Game.getInstance();

    private final HnsExpansion hnsExpansion = HnsExpansion.getInstance();
    final int initTimer = (int) config.getTimeToStart();
    int timer = initTimer;

    Location lobbyLoc = lobby.getLocation();
    private float pitch = 0.5F;

    @Override
    public void run() {
        arena.sendArenaMessage(I18n.tl("timerGameStart", SECONDARY_COLOR.toString() + timer));
        if (timer == initTimer) {
            Song song = NBSDecoder.parse(new File(Bukkit.getServer().getPluginManager().getPlugin("HideAndSeek").getDataFolder(), "We are number one 1.nbs"));
            RadioSongPlayer rsp = new RadioSongPlayer(song);
            for (Player player : arena.getPlayers()) {
                rsp.addPlayer(player);
            }
            rsp.setPlaying(true);
        }
        if (timer == 0) {
            lobbyLoc.getWorld().playSound(lobbyLoc, Sound.LEVEL_UP, 2, 1);
            cancel();
            game.setGameState(Game.GameState.Hiding);
        } else if (timer <= 3) {
            lobbyLoc.getWorld().playSound(lobbyLoc, Sound.NOTE_PIANO, 2, pitch);
            pitch += 0.33F;
        }

        timer--;
        hnsExpansion.timer = timer;
    }
}
