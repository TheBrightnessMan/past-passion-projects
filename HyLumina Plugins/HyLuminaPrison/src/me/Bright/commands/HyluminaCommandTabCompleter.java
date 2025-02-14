package me.Bright.commands;

import me.Bright.main.BrightEnchant;
import me.Bright.utils.BrightUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HyluminaCommandTabCompleter implements TabCompleter {

    private final List<String> SUB_COMMANDS = Arrays.asList(
            "enchantCustom", "token", "gem", "items"
    );
    private final List<String> ENCHANT_SUB_COMMANDS = new ArrayList<String>() {{
        for (BrightEnchant brightEnchant : BrightEnchant.values) {
            add(brightEnchant.getKey().getKey());
        }
    }};
    private final List<String> CURRENCY_SUB_COMMANDS = Arrays.asList(
            "set"
    );

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command,
                                      @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player) ||
                !command.getName().equalsIgnoreCase(HyluminaCommands.COMMAND)) {
            return null;
        }
        if (!commandSender.hasPermission("bright.hylumina")) {
            return null;
        }
        final List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], SUB_COMMANDS, completions);
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("enchantCustom")) {
                StringUtil.copyPartialMatches(args[1], ENCHANT_SUB_COMMANDS, completions);
            } else if (args[0].equalsIgnoreCase("token") ||
                    args[0].equalsIgnoreCase("gem")) {
                StringUtil.copyPartialMatches(args[1], CURRENCY_SUB_COMMANDS, completions);
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("enchantCustom")) {
                completions.add(String.valueOf(BrightEnchant.getByKey(args[1]).getMaxLevel()));
            } else if (args[0].equalsIgnoreCase("token") ||
                    args[0].equalsIgnoreCase("gem")) {
                StringUtil.copyPartialMatches(args[2], BrightUtils.onlinePlayers, completions);
            }
        }

        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("token") ||
                    args[0].equalsIgnoreCase("gem")) {
                completions.add("<Value>");
            }
        }

        return completions;
    }
}
