package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.I18n;
import org.bukkit.entity.Player;

public class SetupCommand extends SubCommand {
    private String resultMessage;
    private final ConfigStorage config = ConfigStorage.getInstance();
    private final String durationErrorMessage = I18n.tl("durationErrorMessage");

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
                            resultMessage = I18n.tl("setupSeekers");
                        } catch (Exception e) {
                            resultMessage = "Usage: /<command> setup seekers <count>";
                            e.printStackTrace();
                        }
                    } else resultMessage = "Usage: /<command> setup seekers <count>";

                    break;
                case "min-players":
                    if (args.length > 1) {
                        try {
                            config.setMinNumberOfPlayers(Integer.parseInt(args[1]));
                            resultMessage = I18n.tl("setupMinPlayers");
                        } catch (Exception e) {
                            resultMessage = "Usage: /<command> setup min-players <count>";
                            e.printStackTrace();
                        }
                    } else resultMessage = "Usage: /<command> setup min-players <count>";

                    break;
                case "tts":
                case "timetostart":
                    if (args.length > 1) {
                        String dur = args[1];
                        if (!config.isDuration(dur)) {
                            resultMessage = durationErrorMessage;
                        } else {
                            config.setTimeToStart(dur);
                            resultMessage = I18n.tl("setupTimeToStart");
                        }
                    } else resultMessage = "Usage: /<command> setup timetostart <duration [H/M/S]>";

                    break;
                case "tth":
                case "timetohide":
                    if (args.length > 1) {
                        String dur = args[1];
                        if (!config.isDuration(dur)) {
                            resultMessage = durationErrorMessage;
                        } else {
                            config.setTimeToHide(dur);
                            resultMessage = I18n.tl("setupTimeToHide");
                        }
                    } else resultMessage = "Usage: /<command> setup timetohide <duration [H/M/S]>";

                    break;
                case "gd":
                case "gameduration":
                    if (args.length > 1) {
                        String dur = args[1];
                        if (!config.isDuration(dur)) {
                            resultMessage = durationErrorMessage;
                        } else {
                            config.setTimeToSeek(dur);
                            resultMessage = I18n.tl("setupTimeToSeek");
                        }
                    } else resultMessage = "Usage: /<command> setup gameduration <duration [H/M/S]>";

                    break;
                default:
                    resultMessage = I18n.tl("commandNotFound");
                    break;
            }
        }
    }
}
