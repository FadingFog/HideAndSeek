package me.fadingfog.hideandseek.commands;

import org.bukkit.entity.Player;

public class LobbyCommand extends SubCommand {
    private String resultMessage;

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

    }
}
