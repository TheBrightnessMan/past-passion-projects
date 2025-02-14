package me.bright.command.punishments;

import me.bright.utils.BanUnit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TempPunishmentTabComplete implements TabCompleter {

    private final List<String> DATES = BanUnit.getUnitsAsString();

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("tempban") || command.getName().equalsIgnoreCase("tempmute")) {
            if (args.length == 3) {
                final List<String> completions1 = new ArrayList<>();
                StringUtil.copyPartialMatches(args[2], DATES, completions1);
                return completions1;
            }
        }
        return null;
    }
}
