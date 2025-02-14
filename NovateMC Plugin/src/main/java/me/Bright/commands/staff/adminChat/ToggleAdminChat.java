package me.Bright.commands.staff.adminChat;

import me.Bright.commands.CommandCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class ToggleAdminChat extends CommandCore implements Listener {

    private static ArrayList<Player> inAdminChat = new ArrayList<>();

    public ToggleAdminChat() {
        super("actoggle");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String mainCommand, String[] args) {
        if (sender.hasPermission("bright.adminchat")) {
            if (inAdminChat.contains(sender)) {
                inAdminChat.remove(sender);
                sender.sendMessage(ChatColor.RED + "You are now no longer in admin chat");
            } else {
                inAdminChat.add(sender);
                sender.sendMessage(ChatColor.RED + "You are now in admin chat");
            }
        } else {
            sender.sendMessage(noPerm);
        }
    }

    @Override
    public void commandSentAsConsole(String mainCommand, String[] args) {

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (inAdminChat.contains(player)) {
            event.setCancelled(true);
            String format = "" + ChatColor.RED + ChatColor.BOLD + "Admin Chat " + ChatColor.GRAY + player.getName()
                    + ChatColor.DARK_GRAY + " »" + ChatColor.GOLD + event.getMessage();
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("bright.adminchat")) {
                    online.sendMessage(format);
                }
            }
        }
    }
}
