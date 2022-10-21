package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.I18n;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.GamePlayer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

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
                                    resultMessage = I18n.tl("arenaSeekersWorld");
                                    break;
                                }
                            } catch (Exception e) {
                                resultMessage = I18n.tl("arenaLocNotSet");
                                break;
                            }

                            arena.setSeekersLobbyLocation(playerLoc);
                            resultMessage = I18n.tl("seekersLobbyLocSet");
                        } else resultMessage = "Usage: /<command> arena set [seekers]";

                    } else {
                        arena.setLocation(playerLoc);
                        resultMessage = I18n.tl("arenaLocSet");
                    }

                    break;
                case ("members"):
                    List<String> members = arena.getGamePlayers().stream().map(GamePlayer::getName).collect(Collectors.toList());
                    resultMessage = I18n.tl("arenaMembers", members.size()) + StringUtils.join(members, ", ");

                    break;
                case ("seekers"):
                    List<String> seekers = arena.getSeekers().stream().map(GamePlayer::getName).collect(Collectors.toList());
                    resultMessage = I18n.tl("arenaSeekers", seekers.size()) + StringUtils.join(seekers, ", ");

                    break;
                case ("hiders"):
                    List<String> hiders = arena.getHiders().stream().map(GamePlayer::getName).collect(Collectors.toList());
                    resultMessage = I18n.tl("arenaHiders", hiders.size()) + StringUtils.join(hiders, ", ");

                    break;
                default:
                    resultMessage = I18n.tl("commandNotFound");
                    break;
            }
        } else resultMessage = I18n.tl("commandNotFound");

    }
}
