package me.Bright.commands;

import me.Bright.main.MainClass;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class DevCommands extends CommandCore {

    private final Plugin plugin = MainClass.getProvidingPlugin(MainClass.class);

    public DevCommands() {
        super("bright");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        if (sender.hasPermission("soulshard.dev")) {
            if (args.length == 1) {
                if (args[0].equals("addtoconfig")) {
                    ItemStack holding = sender.getInventory().getItemInMainHand();
                    plugin.getConfig().set("Item Debug", holding.toString());
                    plugin.saveConfig();
                    sender.sendMessage("Added to config");
                }
            }
        }
    }

    @Override
    public void commandSentAsConsole(String[] args) {

    }
}
