package me.bright.main;

import me.bright.airdrop.AirDrop;
import me.bright.commands.AirdropCommand;
import me.bright.commands.BrightCommand;
import me.bright.commands.KothCommand;
import me.bright.game.KothGame;
import me.bright.papiHooks.AirdropPapiHook;
import me.bright.papiHooks.KothPapiHook;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class BrightPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        initConfig();
        initKoth();
        initAirdrops();
        this.getLogger().log(Level.INFO, "Plugin loaded successfully!");
    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.INFO, "Plugin disabled successfully!");
    }

    private void initKoth() {
        registerCommand(new KothCommand());
        registerPapiHook(new KothPapiHook());
        startKothTimer();
    }

    private void startKothTimer() {
        this.getConfig().set(KothGame.TIME_PATH, System.currentTimeMillis() + 1.44e7);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            this.getConfig().set(KothGame.TIME_PATH, System.currentTimeMillis() + 1.44e7);
            KothGame.getInstance().start();
        }, 288_000, 288_000);
    }

    private void initAirdrops() {
        registerEvent(AirDrop.getInstance());
        registerPapiHook(new AirdropPapiHook());
        registerCommand(new AirdropCommand());
        // startAirdropTimer();
    }

    private void startAirdropTimer() {
        this.getConfig().set(AirDrop.TIME_PATH, System.currentTimeMillis() + 1.44e7);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            this.getConfig().set(AirDrop.TIME_PATH, System.currentTimeMillis() + 3.6e6);
            AirDrop.getInstance().dropRandom();
        }, 72_000, 72_000);
    }

    private void registerPapiHook(PlaceholderExpansion expansion) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            this.getLogger().log(Level.WARNING, "PlaceholderAPI not detected! KOTH will not announce winner's faction!");
            return;
        }

        if (expansion.register()) {
            this.getLogger().log(
                    Level.INFO,
                    "Successfully hooked "
                            .concat(expansion.getIdentifier())
                            .concat(" into PAPI!")
            );
        } else {
            this.getLogger().log(
                    Level.WARNING,
                    expansion.getIdentifier()
                            .concat(" failed to hook into PAPI!")
            );
        }
    }

    private void registerEvent(Listener... listeners) {
        for (Listener l : listeners) {
            this.getServer().getPluginManager().registerEvents(l, this);
        }
    }

    private void registerCommand(BrightCommand brightCommand) {
        String commandString = brightCommand.getMainCommand();
        PluginCommand commandObject = this.getCommand(commandString);
        if (commandObject == null) {
            this.getLogger().log(
                    Level.WARNING,
                    "Command \""
                            .concat(commandString)
                            .concat("\" is not listed in plugin.yml!")
            );
            return;
        }
        commandObject.setExecutor(brightCommand);
        commandObject.setTabCompleter(brightCommand);
        registerEvent(brightCommand);
        this.getLogger().log(
                Level.INFO,
                "Command \""
                        .concat(commandString)
                        .concat("\" registered successfully!")
        );
    }

    private void initConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
