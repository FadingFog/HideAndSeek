package me.fadingfog.hideandseek.commands;

import com.earth2me.essentials.User;
import me.fadingfog.hideandseek.I18n;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.Objects;

import static me.fadingfog.hideandseek.commands.CommandManager.ess;
import static me.fadingfog.hideandseek.commands.CommandManager.prefix;


public class LobbyJoinCommand implements CommandExecutor {
    private final Lobby lobby = Lobby.getInstance();
    private final Game game = Game.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            String resultMessage;
            Player player = (Player) sender;
            User user = ess.getUser(player);

            if (user.isAuthorized("hns.lobby.join")) {
                PlayerInventory playerInv = player.getInventory();
                if (!(isInventoryEmpty(playerInv) && isArmorInventoryEmpty(playerInv))) {
                    resultMessage = I18n.tl("inventoryNotEmpty");
                } else {
                    if (lobby.addMember(player)) {
                        resultMessage = I18n.tl("lobbyJoined");
                    } else if (game.getGameState() != Game.GameState.Closed) {
                        resultMessage = I18n.tl("gameAlreadyStarted");
                    } else if (lobby.getLobbyState() == Lobby.LobbyState.Closed) {
                        resultMessage = I18n.tl("lobbyClosed");
                    } else {
                        resultMessage = I18n.tl("lobbyAlreadyJoined");
                    }
                }

            } else {
                resultMessage = ChatColor.RED + I18n.tl("notEnoughPermissions");
            }

            player.sendMessage(prefix + resultMessage);
            return true;
        }

        return false;
    }

    public boolean isInventoryEmpty(final Inventory inv) {
        return Arrays.stream(inv.getContents()).noneMatch(Objects::nonNull);
    }

    public boolean isArmorInventoryEmpty(final PlayerInventory inv) {
        return Arrays.stream(inv.getArmorContents()).allMatch(item -> item.getType().equals(Material.AIR));
    }
}
