package com.sarinsa.domchat.command;

import com.sarinsa.domchat.core.DomChat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DomCommandExecutor extends BaseCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String s, String[] args) {
        final int argLength = args.length;

        if (argLength == 0) {
            commandSender.sendMessage(ChatColor.GREEN + "Whaddup! Looking for instructions? None needed really, there is only the plugin config reload command for now: " + ChatColor.AQUA + "\"/domchat reload\"");
        }
        else {
            if (argLength == 1 && args[0].equals("reload")) {
                reloadConfig(commandSender);
            }
            else {
                commandSender.sendMessage(ChatColor.RED + "Not a valid command my dude; there is only the plugin config reload command for now :(");
            }
        }
        return true;
    }

    private static void reloadConfig(CommandSender commandSender) {
        DomChat.INSTANCE.getPluginConfig().reload();
        commandSender.sendMessage(ChatColor.GREEN + "Reloaded plugin config");
    }
}
