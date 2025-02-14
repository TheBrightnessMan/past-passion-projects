package me.Bright.commands.staff.staffChat;

import me.Bright.commands.CommandCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class ToggleStaffChat extends CommandCore implements Listener {

    private static ArrayList<Player> inStaffChat = new ArrayList<>();

    public ToggleStaffChat() {
        super("sctoggle");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String mainCommand, String[] args) {
        if (sender.hasPermission("bright.staffchat")) {
            if (inStaffChat.contains(sender)) {
                inStaffChat.remove(sender);
                sender.sendMessage(ChatColor.GREEN + "You are now no longer in staff chat");
            } else {
                inStaffChat.add(sender);
                sender.sendMessage(ChatColor.GREEN + "You are now in staff chat");
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
        if (inStaffChat.contains(player)) {
            event.setCancelled(true);
            String format = "" + ChatColor.GREEN + ChatColor.BOLD + "Staff Chat " + ChatColor.GRAY + player.getName()
                    + ChatColor.DARK_GRAY + " » " + ChatColor.GOLD + event.getMessage();
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("bright.staffchat")) {
                    online.sendMessage(format);
                }
            }
        }
    }
}
