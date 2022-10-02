package me.fadingfog.hideandseek.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager implements CommandExecutor {
    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new GetItemNameCommand());
        subCommands.add(new GameCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    SubCommand sCommand = getSubCommands().get(i);
                    if (args[0].equalsIgnoreCase(sCommand.getName())) {
                        sCommand.perform(player, Arrays.copyOfRange(args, 1, args.length));
                        player.sendMessage(sCommand.getResultMessage());
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
