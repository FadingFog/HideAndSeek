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
                case ("create"):
                    Location playerLoc = player.getLocation();
                    arena.setLocation(playerLoc);
                    resultMessage = "Arena location successfully set";
                    break;
                default:
                    resultMessage = "Command not found";
                    break;
            }
        } else resultMessage = "Command not found";

    }
}
