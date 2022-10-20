package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.game.Lobby;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;


public class LobbyCommand extends SubCommand {
    private String resultMessage;
    private final Lobby lobby = Lobby.getInstance();

    @Override
    public String getName() {
        return "lobby";
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
                    lobby.setLocation(playerLoc);
                    resultMessage = "New lobby location set";

                    break;
                case ("open"):
                    if (lobby.getLocation() != null) {
                        if (lobby.openLobby()) {
                            resultMessage = "Lobby opened";
                        } else resultMessage = "Lobby is already open";
                    } else resultMessage = "At first set the lobby location - /hns lobby set";

                    break;
                case ("close"):
                    if (lobby.closeLobby()) {
                        resultMessage = "Lobby closed";
                    } else resultMessage = "Lobby is already closed";

                    break;
                case ("members"):
                    List<String> members = lobby.getMembers().stream().map(Player::getDisplayName).collect(Collectors.toList());
                    resultMessage = MessageFormat.format("Lobby members [{0}]:", members.size()) + StringUtils.join(members, ", ");

                    break;
                default:
                    resultMessage = "Command not found";
                    break;
            }
        } else resultMessage = "Command not found";

    }



}
