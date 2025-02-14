package me.Bright.main;

import me.Bright.commands.DevCommandCompleter;
import me.Bright.commands.DevCommands;
import me.Bright.commands.ShopCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public class MainClass extends JavaPlugin {

    @Override
    public void onEnable() {
        setupConfig();
        regEvents(new SoulShardDrop(), new SoulShardShop());
        regCommand("bright", new DevCommands(), new DevCommandCompleter());
        regCommand("soulshop", new ShopCommand(), null);
    }

    private void setupConfig() {
        FileConfiguration config = getConfig();
        config.set("Shop Items", "");
        config.set("Item Debug", "");
//        config.addDefault("Shop Items", null);
//        config.addDefault("Item Debug", null);
//        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void regCommand(String mainCommand, CommandExecutor executor, @Nullable TabCompleter tabCompleter) {
        getCommand(mainCommand).setExecutor(executor);
        if (tabCompleter != null) {
            getCommand(mainCommand).setTabCompleter(tabCompleter);
        }
    }

    private void regEvents(Listener... listener) {
        for (Listener register : listener) {
            this.getServer().getPluginManager().registerEvents(register, this);
        }
    }
}
