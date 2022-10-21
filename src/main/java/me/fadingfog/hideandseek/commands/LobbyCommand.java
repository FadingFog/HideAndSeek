package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.I18n;
import me.fadingfog.hideandseek.game.Lobby;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.*;

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
                    resultMessage = I18n.tl("lobbyLocSet");

                    break;
                case ("open"):
                    if (lobby.getLocation() != null) {
                        if (lobby.openLobby()) {
                            resultMessage = I18n.tl("lobbyOpened");
                        } else resultMessage = I18n.tl("lobbyAlreadyOpened");
                    } else resultMessage = I18n.tl("lobbyLocNotSet");

                    break;
                case ("close"):
                    if (lobby.closeLobby()) {
                        resultMessage = I18n.tl("lobbyClosed");
                    } else resultMessage = I18n.tl("lobbyAlreadyClosed");

                    break;
                case ("members"):
                    List<String> members = lobby.getMembers().stream().map(Player::getDisplayName).collect(Collectors.toList());
                    resultMessage = I18n.tl("lobbyMembers", members.size()) + StringUtils.join(members, ", ");

                    break;
                default:
                    resultMessage = I18n.tl("commandNotFound");
                    break;
            }
        } else resultMessage = I18n.tl("commandNotFound");

    }



}
