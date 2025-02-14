package me.Bright.commands.member;

import me.Bright.commands.CommandCore;
import me.Bright.main.gui.KitGUI;
import org.bukkit.entity.Player;

public class KitCommand extends CommandCore {

    public KitCommand() {
        super("kits");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String mainCommand, String[] args) {
        KitGUI kitGUI = new KitGUI();
        kitGUI.openInventory(sender);
    }

    @Override
    public void commandSentAsConsole(String mainCommand, String[] args) {
    }
}
