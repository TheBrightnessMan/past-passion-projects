package me.Bright.main;

import me.Bright.Events;
import me.Bright.commands.BrightCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BrightPlugin extends JavaPlugin {

    public void onEnable() {
        regCommand("bright", new BrightCommand());
    }

    private void regEvents(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    private void regCommand(String command, CommandExecutor commandExecutor) {
        getCommand(command).setExecutor(commandExecutor);
    }

    private void regCommand(String command, CommandExecutor commandExecutor, TabCompleter tabCompleter) {
        getCommand(command).setExecutor(commandExecutor);
        getCommand(command).setTabCompleter(tabCompleter);
    }

}
