package me.bright.command.punishments;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UnbanTabComplete implements TabCompleter {

    private final List<String> BANNED_LIST = new ArrayList<>();

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("unban") || command.getName().equalsIgnoreCase("unbanip")) {
            if (args.length == 1) {
                for (OfflinePlayer offlinePlayer : Bukkit.getBannedPlayers()) {
                    BANNED_LIST.add(offlinePlayer.getName());
                }
                final List<String> completions1 = new ArrayList<>();
                StringUtil.copyPartialMatches(args[0], BANNED_LIST, completions1);
                return completions1;
            }
        }
        return null;
    }
}
