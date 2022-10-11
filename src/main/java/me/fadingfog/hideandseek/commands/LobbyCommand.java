package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.HideAndSeek;
import me.fadingfog.hideandseek.game.Lobby;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class LobbyCommand extends SubCommand {
    private String resultMessage;
    private final Lobby lobby = Lobby.getInstance();
    private final HideAndSeek plugin = HideAndSeek.getInstance();


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
                    resultMessage = "New lobby location set";

                    break;
                case ("open"):
                    if (lobby.getLocation() != null) {
                        if (lobby.openLobby()) {
                            resultMessage = "Lobby opened";
                        } else resultMessage = "Lobby is already open";
                    } else resultMessage = "At first set the lobby location - /hns lobby set";

                    break;
                case ("close"):
                    if (lobby.closeLobby()) {
                        resultMessage = "Lobby closed";
                    } else resultMessage = "Lobby is already closed";

                    break;
                case ("join"):
                    PlayerInventory playerInv = player.getInventory();
                    if (!(isInventoryEmpty(playerInv) && isArmorInventoryEmpty(playerInv))) {
                        resultMessage = "Sorry, but your inventory is not empty";
                        break;
                    }
                    if (lobby.addMember(player)) {
                        resultMessage = "You are joined lobby";
                    } else resultMessage = "Sorry, but lobby is closed or you're already in it";

                    break;
                case ("exit"):
                    if (lobby.removeMember(player)) {
                        resultMessage = "You are leaved lobby";
                    } else resultMessage = "Sorry, but you're not in lobby";

                    break;
                case ("members"):
                    List<String> members = lobby.getMembers().stream().map(Player::getDisplayName).collect(Collectors.toList());
                    resultMessage = "Lobby members: " + StringUtils.join(members, ',');

                    break;
                case ("test"):

                    break;
                default:
                    resultMessage = "Command not found";
                    break;
            }
        } else resultMessage = "Command not found";

    }

    public boolean isInventoryEmpty(final Inventory inv) {
        return Arrays.stream(inv.getContents()).noneMatch(Objects::nonNull);
    }

    public boolean isArmorInventoryEmpty(final PlayerInventory inv) {
        return Arrays.stream(inv.getArmorContents()).allMatch(item -> item.getType().equals(Material.AIR));
    }

}
