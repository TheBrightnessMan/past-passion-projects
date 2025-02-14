package me.Bright.commands.staff;

import me.Bright.commands.CommandCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.broadcastMessage;

public class ClearChat extends CommandCore {

    public ClearChat() {
        super("clearchat");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String mainCommand, String[] args) {
        if (sender.hasPermission("bright.clearchat")) {
            clearTheChat();
            broadcastMessage(namePrefix + ChatColor.GRAY + "The chat has been cleared.");
        } else {
            sender.sendMessage(noPerm);
        }
    }

    @Override
    public void commandSentAsConsole(String mainCommand, String[] args) {
        clearTheChat();
    }

    private void clearTheChat() {
        for (Player online : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i <= 150; i++) {
                online.sendMessage("");
            }
        }
    }
}
