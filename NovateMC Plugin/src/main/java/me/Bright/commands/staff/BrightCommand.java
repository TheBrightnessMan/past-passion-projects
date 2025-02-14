package me.Bright.commands.staff;

import me.Bright.commands.CommandCore;
import me.Bright.main.gui.CustomItemGUI;
import org.bukkit.entity.Player;

public class BrightCommand extends CommandCore {

    public BrightCommand() {
        super("bright");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String mainCommand, String[] args) {
        if (!sender.getName().equals("BrightnessMan")) {
            return;
        }
        if (args[0].equalsIgnoreCase("gui")) {
            CustomItemGUI customItemGUI = new CustomItemGUI();
            customItemGUI.openInventory(sender);
        } else {
            sender.sendMessage(unknownCommand);
        }
    }

    @Override
    public void commandSentAsConsole(String mainCommand, String[] args) {

    }
}