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
            commandSender.sendMessage(ChatColor.GREEN + "/domchat reload" + ChatColor.GRAY + " - " + ChatColor.AQUA + "Reloads the plugin config");
            commandSender.sendMessage(ChatColor.GREEN + "/domchat [enable / disable]" + ChatColor.GRAY + " - " + ChatColor.AQUA + "Toggles between active and dormant mode. When \"disabled\", DomChat will stop interacting with chat entirely.");
        }
        else {
            if (argLength == 1) {
                switch (args[0]) {
                    case "reload":
                        reloadConfig(commandSender);
                        break;

                    case "disable":
                        if (DomChat.INSTANCE.isDisabled()) {
                            commandSender.sendMessage(ChatColor.RED + "DomChat has already been disabled");
                        } else {
                            DomChat.INSTANCE.setDisabled(true);
                            commandSender.sendMessage(ChatColor.RED + "Disabled DomChat");
                        }
                        break;

                    case "enable":
                        if (DomChat.INSTANCE.isDisabled()) {
                            DomChat.INSTANCE.setDisabled(false);
                            commandSender.sendMessage(ChatColor.GREEN + "Enabled DomChat");
                        }
                        else {
                            commandSender.sendMessage(ChatColor.RED + "DomChat is already enabled");
                        }
                        break;

                    default:
                        commandSender.sendMessage(ChatColor.RED + "Not a valid command my dude; check out /domchat for a list of subcommands.");
                        break;
                }
            }
            else {
                commandSender.sendMessage(ChatColor.RED + "Too many command arguments. See /domchat for a list of subcommands.");
            }
        }
        return true;
    }

    private static void reloadConfig(CommandSender commandSender) {
        DomChat.INSTANCE.getPluginConfig().reload();
        commandSender.sendMessage(ChatColor.GREEN + "Reloaded plugin config");
    }
}
