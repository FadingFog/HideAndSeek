package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.HideAndSeek;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;

public class GameCommand extends SubCommand {
    private String resultMessage;
    private final HideAndSeek plugin = HideAndSeek.getInstance();

    @Override
    public String getName() {
        return "game";
    }

    @Override
    public String getResultMessage() {
        return resultMessage;
    }

    @Override
    public void perform(Player player, String[] args) {
        TextComponent message = Component.text("Test");
        plugin.adventure().sender(player).sendMessage(message);
    }
}
