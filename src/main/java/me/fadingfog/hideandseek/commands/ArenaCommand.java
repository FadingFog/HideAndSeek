package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.game.Arena;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ArenaCommand extends SubCommand {
    private String resultMessage;
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
                    if (args.length > 1) {
                        if (args[1].equalsIgnoreCase("seekers")) {
                            try {
                                if (playerLoc.getWorld() != arena.getLocation().getWorld()) {
                                    resultMessage = "Seekers lobby location should be the same as arena location";
                                    break;
                                }
                            } catch (Exception e) {
                                resultMessage = "At first set arena location - /hns arena set";
                                break;
                            }

                            arena.setSeekersLobbyLocation(playerLoc);
                            resultMessage = "New seekers lobby location set";
                        } else resultMessage = "Usage: /<command> arena set [seekers]";

                    } else {
                        arena.setLocation(playerLoc);
                        resultMessage = "New arena location set";
                    }

                    break;
                default:
                    resultMessage = "Command not found";
                    break;
            }
        } else resultMessage = "Command not found";

    }
}
