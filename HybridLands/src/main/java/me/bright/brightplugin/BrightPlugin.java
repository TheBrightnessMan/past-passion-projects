package me.bright.brightplugin;

import me.bright.commands.staff.StaffChat;
import me.bright.events.RemoveAiFromSpawnerMobs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class BrightPlugin extends JavaPlugin {

    Logger logger;
    final String prefix = "BrightPlugin";

    @Override
    public void onEnable() {
        logger = getServer().getLogger();
        regEvents(new RemoveAiFromSpawnerMobs());
        regCommand("sc", new StaffChat());
        log(Level.INFO, "Plugin startup successful");
    }

    @Override
    public void onDisable() {
        log(Level.INFO, "Disabling, see ya");
    }

    private void regEvents(Listener... listener) {
        for (Listener registerMe : listener) {
            getServer().getPluginManager().registerEvents(registerMe, this);
            log(Level.INFO, "Registered listener class " + registerMe.getClass().getName());
        }
        log(Level.INFO, "Registered " + listener.length + " listeners");
    }

    private void regCommand(String command, CommandExecutor commandExecutor) {
        Logger logger = Bukkit.getLogger();
        this.getCommand(command).setExecutor(commandExecutor);
        log(Level.INFO, command + " command Registered");
    }

    private void log(Level level, String message) {
        logger.log(level, "[" + prefix + "] " + message);
    }

}
