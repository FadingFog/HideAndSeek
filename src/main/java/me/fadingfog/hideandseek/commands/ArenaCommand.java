package me.fadingfog.hideandseek.commands;

import org.bukkit.entity.Player;

public class ArenaCommand extends SubCommand {
    private String resultMessage;

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

    }
}
