package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.HideAndSeek;
import me.fadingfog.hideandseek.game.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ArenaCommand extends SubCommand {
    private String resultMessage;
    private final HideAndSeek plugin = HideAndSeek.getInstance();
    private final Arena arena = Arena.getInstance();


    @Override
    public String getName() {
        return "arena";
    }

    @Override
    public String getResultMessage() {
        return resultMessage;
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case ("set"):
                    Location playerLoc = player.getLocation();
                    if (args.length > 1 && args[1].toLowerCase().equals("seekers")) {
                        arena.setSeekersLobbyLocation(playerLoc);
                        resultMessage = "New seekers lobby location set";
                    }
                    arena.setLocation(playerLoc);
                    resultMessage = "New arena location set";

                    break;
                default:
                    resultMessage = "Command not found";
                    break;
            }
        } else resultMessage = "Command not found";

    }
}
