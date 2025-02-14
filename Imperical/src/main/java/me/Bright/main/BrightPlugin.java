package me.Bright.main;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BrightPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("WorldEdit") != null || Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) {
            Bukkit.getLogger().info("WorldEdit plugin not found! Disabling BrightPlugin!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void regEvent(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }
}
