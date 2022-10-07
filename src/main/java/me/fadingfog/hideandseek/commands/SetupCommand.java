package me.fadingfog.hideandseek.commands;

import org.bukkit.entity.Player;

public class SetupCommand extends SubCommand {
    private String resultMessage;

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
            if (args[0].equalsIgnoreCase("create")) {

            } else if (args[0].equalsIgnoreCase("timeToStart")) {

            } else if (args[0].equalsIgnoreCase("timeToHide")) {

            } else if (args[0].equalsIgnoreCase("gameDuration")) {

            }
        }
    }
}
