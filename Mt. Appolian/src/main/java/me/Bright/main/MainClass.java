package me.Bright.main;

import me.Bright.commands.GetItemCommand;
import me.Bright.commands.GetItemCommandCompleter;
import me.Bright.items.BrightItem;
import me.Bright.items.ZeusBow;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {

    @Override
    public void onEnable() {
        regItem(new ZeusBow());
        regCommand("getitem", new GetItemCommand(), new GetItemCommandCompleter());
    }


    private void regItem(BrightItem brightItem) {
        regEvents(brightItem);
    }

    private void regEvents(Listener... listener) {
        for (Listener register : listener) {
            getServer().getPluginManager().registerEvents(register, this);
        }
    }

    private void regCommand(String mainCommand, CommandExecutor executor) {
        getCommand(mainCommand).setExecutor(executor);
    }

    private void regCommand(String mainCommand, CommandExecutor executor, TabCompleter completer) {
        getCommand(mainCommand).setExecutor(executor);
        getCommand(mainCommand).setTabCompleter(completer);
    }

}
