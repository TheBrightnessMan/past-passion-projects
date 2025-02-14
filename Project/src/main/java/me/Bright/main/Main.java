package me.Bright.main;

import items.sword.TelekineticSword;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

    public void onEnable() {
        regEvents(new TelekineticSword());
//        regEvents(this);
    }

    private void regEvents(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
