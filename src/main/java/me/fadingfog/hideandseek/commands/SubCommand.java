package me.fadingfog.hideandseek.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract String getName();

    public abstract String getResultMessage();

    public abstract void perform(Player player, String[] args);

}
