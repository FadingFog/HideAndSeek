package me.fadingfog.hideandseek.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GetItemNameCommand extends SubCommand {
    private String itemName;

    @Override
    public String getName() {
        return "item";
    }

    @Override
    public String getResultMessage() {
        return itemName;
    }

    @Override
    public void perform(Player player, String[] args) {
        ItemStack item = player.getItemInHand();
        Material material = item.getType();

        itemName = material.toString();
    }
}
