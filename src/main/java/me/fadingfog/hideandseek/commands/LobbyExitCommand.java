package me.fadingfog.hideandseek.commands;

import com.earth2me.essentials.User;
import me.fadingfog.hideandseek.I18n;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Lobby;
import me.fadingfog.hideandseek.utils.TeleportUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.fadingfog.hideandseek.commands.CommandManager.ess;
import static me.fadingfog.hideandseek.commands.CommandManager.prefix;


public class LobbyExitCommand implements CommandExecutor {
    private final Lobby lobby = Lobby.getInstance();
    private final Arena arena = Arena.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            String resultMessage;
            Player player = (Player) sender;
            User user = ess.getUser(player);

            if (user.isAuthorized("hns.lobby.join")) {
                if (lobby.removeMember(player)) {
                    resultMessage = I18n.tl("lobbyLeaved");
                    TeleportUtil.teleportPlayerBack(player);
                } else if (arena.removeGamePlayer(player)) {
                    resultMessage = I18n.tl("gameLeaved");
                    TeleportUtil.teleportPlayerBack(player);
                } else {
                    resultMessage = I18n.tl("notInGame");
                }
            } else {
                resultMessage = ChatColor.RED + I18n.tl("notEnoughPermissions");
            }

            player.sendMessage(prefix + resultMessage);
            return true;
        }

        return false;
    }
}
