package me.fadingfog.hideandseek.tasks;

import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.Lobby;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class PrepareCountdown extends BukkitRunnable {
    private final ConfigStorage config = ConfigStorage.getInstance();
    private final Lobby lobby = Lobby.getInstance();
    private final Game game = Game.getInstance();
    int timer = (int) config.getTimeToStart();

    Location lobbyLoc = lobby.getLocation();
    float pitch = 0.5F;

    @Override
    public void run() {
        lobby.sendLobbyMessage("Game will start in " + timer);
        if (timer == 0) {
            lobbyLoc.getWorld().playSound(lobbyLoc, Sound.LEVEL_UP, 2, 1);
            game.setGameState(Game.GameState.Hiding);
            cancel();
        } else if (timer <= 5) {
            lobbyLoc.getWorld().playSound(lobbyLoc, Sound.NOTE_PIANO, 2, pitch);
            pitch += 0.33F;
            System.out.println(timer);
        }
        timer--;
    }
}
