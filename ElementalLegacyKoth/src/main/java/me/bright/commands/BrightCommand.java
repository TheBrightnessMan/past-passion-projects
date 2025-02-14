package me.bright.commands;

import me.bright.main.BrightPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public abstract class BrightCommand implements CommandExecutor, TabCompleter, Listener {

    protected final Plugin plugin = BrightPlugin.getPlugin(BrightPlugin.class);
    private final String prefix = setPrefix();
    protected final String mainCommand;
    protected final String permission;
    protected final String unknownCommand = setPrefix() + "Unknown command! Please refer to the tab complete!";

    public BrightCommand(String mainCommand) {
        this.mainCommand = mainCommand;
        this.permission = mainCommand.concat(".admin");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase(this.mainCommand)) {
            return true;
        }

        if (!(sender instanceof Player player)) {
            runCommandAsConsole(args);
            return true;
        }

        if (!player.hasPermission(this.permission) && !this.permission.isBlank()) {
            player.sendPlainMessage(prefix + "You do not have permission to run this command!");
            return true;
        }

        runCommandAsPlayer(player, args);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission(this.permission)) {
            return null;
        }
        List<String> list = completerLogic(args);
        Collections.sort(list);
        return list;
    }

    public abstract List<String> completerLogic(String[] args);

    public String getMainCommand() {
        return mainCommand;
    }

    public String getPermission() {
        return permission;
    }

    public abstract void runCommandAsPlayer(Player sender, String[] args);

    public abstract void runCommandAsConsole(String[] args);

    public abstract String setPrefix();

}
