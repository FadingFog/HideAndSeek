package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.ConfigStorage;
import org.bukkit.entity.Player;

public class SetupCommand extends SubCommand {
    private String resultMessage;
    private final ConfigStorage config = ConfigStorage.getInstance();

    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public String getResultMessage() {
        return resultMessage;
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "seekers":
                    if (args.length > 1) {
                        try {
                            config.setNumberOfSeekers(Integer.parseInt(args[1]));
                        } catch (Exception e) {
                            resultMessage = "Usage: /<command> setup seekers <count>";
                        }
                    } else resultMessage = "Usage: /<command> setup seekers <count>";

                    break;
                case "min-players":
                    if (args.length > 1) {
                        try {
                            config.setMinNumberOfPlayers(Integer.parseInt(args[1]));
                        } catch (Exception e) {
                            resultMessage = "Usage: /<command> setup min-players <count>";
                        }
                    } else resultMessage = "Usage: /<command> setup min-players <count>";
                case "tts":
                case "timetostart":

                    resultMessage = "";

                    break;
                case "tth":
                case "timetohide":

                    resultMessage = "";

                    break;
                case "gd":
                case "gameduration":

                    resultMessage = "";

                    break;
                default:
                    resultMessage = "Command not found";
                    break;
            }
        }
    }
}
