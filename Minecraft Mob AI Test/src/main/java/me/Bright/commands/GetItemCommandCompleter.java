package me.Bright.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetItemCommandCompleter implements TabCompleter {

    private final String[] SUB_COMMANDS = {"reaperScythe"};

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("getitem")) {
            if (args.length == 1) {
                final List<String> completions = new ArrayList<>();
                StringUtil.copyPartialMatches(args[0], Arrays.asList(SUB_COMMANDS), completions);
                Collections.sort(completions);
                return completions;
            }
        }
        return null;
    }
}
