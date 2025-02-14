package me.Bright.commands;

import me.Bright.main.SoulShardShop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ShopCommand extends CommandCore{

    private final SoulShardShop soulShardShop = new SoulShardShop();

    public ShopCommand() {
        super("soulshop");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        if (sender.hasPermission("soulshard.shop")) {
            if (args.length == 0) {
                soulShardShop.openInventory(sender);
            } else if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    soulShardShop.openInventory(Bukkit.getPlayer(args[0]));
                }
            }
        }
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        if (args.length == 1) {
            if (Bukkit.getPlayer(args[0]) != null) {
                soulShardShop.openInventory(Bukkit.getPlayer(args[0]));
            }
        }
    }
}
