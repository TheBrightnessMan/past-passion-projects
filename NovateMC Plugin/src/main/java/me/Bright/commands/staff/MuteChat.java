package me.Bright.commands.staff;

import me.Bright.commands.CommandCore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static org.bukkit.Bukkit.broadcastMessage;

public class MuteChat extends CommandCore implements Listener {

    private static boolean chatMuted = false;

    public MuteChat() {
        super("mutechat");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String mainCommand, String[] args) {
        if (sender.hasPermission("bright.mutechat")) {
            chatMuted = !chatMuted;
            if (chatMuted) {
                broadcastMessage(ChatColor.RED + "Chat has been muted!");
            } else {
                broadcastMessage(ChatColor.GREEN + "Chat has been unmuted!");
            }
        } else {
            sender.sendMessage(noPerm);
        }
    }

    @Override
    public void commandSentAsConsole(String mainCommand, String[] args) {
        chatMuted = !chatMuted;
        if (chatMuted) {
            broadcastMessage(ChatColor.RED + "Chat has been muted!");
        } else {
            broadcastMessage(ChatColor.GREEN + "Chat has been unmuted!");
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("bright.chatmutebypass")) {
            event.setCancelled(chatMuted);
        }
    }
}
