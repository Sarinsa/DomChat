package com.sarinsa.domchat.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseCommand implements TabCompleter {

    private static final List<String> SUB_COMMANDS = new ArrayList<>();

    static {
        SUB_COMMANDS.add("reload");
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            final List<String> list = new ArrayList<>();

            StringUtil.copyPartialMatches(args[0], SUB_COMMANDS, list);
            Collections.sort(list);
            return list;
        }
        else {
            return null;
        }
    }
}
