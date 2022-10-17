package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.HideAndSeek;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.Lobby;
import me.fadingfog.hideandseek.tasks.PrepareCountdown;
import me.fadingfog.hideandseek.utils.TeleportUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class LobbyCommand extends SubCommand {
    private String resultMessage;
    private final HideAndSeek plugin = HideAndSeek.getInstance();
    private final Lobby lobby = Lobby.getInstance();
    private final Arena arena = Arena.getInstance();
    private final Game game = Game.getInstance();
    PrepareCountdown prepareCountdown = null; // DEBUG DELETE AFTER TEST


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
                    } else if (game.getGameState() != Game.GameState.Closed) {
                        resultMessage = "Sorry, but the game has already started";
                    } else if (lobby.getLobbyState() == Lobby.LobbyState.Closed) {
                        resultMessage = "Sorry, but lobby is closed";
                    } else {
                        resultMessage = "You are already in lobby";
                    }

                    break;
                case ("exit"):
                    if (lobby.removeMember(player)) {
                        resultMessage = "You are leaved lobby";
                    } else if (arena.removeGamePlayer(player)) {
                        resultMessage = "You are leaved game";
                    } else {
                        resultMessage = "Sorry, but you're not in game";
                        break;
                    }
                    TeleportUtil.teleportPlayerBack(player);

                    break;
                case ("members"):
                    List<String> members = lobby.getMembers().stream().map(Player::getDisplayName).collect(Collectors.toList());
                    resultMessage = "Lobby members: " + StringUtils.join(members, ',');

                    break;
                case ("test"):
                    if (args.length > 1 && args[1].equals("cancel")) {
                        prepareCountdown.cancel();
                    } else {
                        prepareCountdown = new PrepareCountdown();
                        prepareCountdown.runTaskTimer(plugin, 0L, 20L);
                    }

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
