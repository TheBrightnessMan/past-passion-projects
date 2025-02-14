package me.Bright.commands.staff.adminChat;

import me.Bright.commands.CommandCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getServer;

public class AdminChat extends CommandCore implements Listener {

    public AdminChat() {
        super("ac");
    }

    @Override
    public void commandSentAsPlayer(Player playerSender, String mainCommand, String[] args) {
        if (playerSender.hasPermission("bright.adminchat")) {
            StringBuilder message = new StringBuilder();
            for (String word : args) {
                message.append(" ").append(word);
            }
            String format = "" + ChatColor.RED + ChatColor.BOLD + "Admin Chat " + ChatColor.GRAY + playerSender.getName()
                    + ChatColor.DARK_GRAY + " »" + ChatColor.GOLD + message;
            for (Player receiver : Bukkit.getOnlinePlayers()) {
                if (receiver.hasPermission("bright.adminchat")) {
                    receiver.sendMessage(format);
                }
            }
            getServer().getConsoleSender().sendMessage(format);
        } else {
            playerSender.sendMessage(noPerm);
        }
    }

    @Override
    public void commandSentAsConsole(String mainCommand, String[] args) {
        StringBuilder message = new StringBuilder();
        for (String word : args) {
            message.append(" ").append(word);
        }
        String format = "" + ChatColor.RED + ChatColor.BOLD + "Admin Chat " + ChatColor.GRAY + "Console"
                + ChatColor.DARK_GRAY + " »" + ChatColor.GOLD + message;
        for (Player receiver : Bukkit.getOnlinePlayers()) {
            if (receiver.hasPermission("bright.adminchat")) {
                receiver.sendMessage(format);
            }
        }
        getServer().getConsoleSender().sendMessage(format);
    }
}
