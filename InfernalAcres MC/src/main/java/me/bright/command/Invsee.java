package me.bright.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class Invsee extends CommandCore {

    private final String invseeBypass = "bright.invseebypass";

    public Invsee() {
        super("invsee", "bright.invsee");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        if (args.length == 1 && Bukkit.getPlayer(args[0]) != null) {
            Player player = Bukkit.getPlayer(args[0]);
            assert player != null;
//            if (player.hasPermission(invseeBypass)) {
//                return;
//            }
            PlayerInventory playerInventory = player.getInventory();

            sender.openInventory(playerInventory);
        }
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        sendConsoleMessage("&cThis command can only be ran as a player!");
    }
}
