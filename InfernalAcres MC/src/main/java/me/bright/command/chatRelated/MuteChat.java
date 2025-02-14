package me.bright.command.chatRelated;

import me.bright.command.CommandCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MuteChat extends CommandCore implements Listener {

    private static boolean chatMuted = false;
    private final String bypassChatPermission = "bright.mutechatbypass";

    public MuteChat() {
        super("mutechat", "bright.mutechat");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        chatMuted = !chatMuted;
        if (chatMuted) {
            if (args.length == 1 && args[0].equalsIgnoreCase("-a")) {
                Bukkit.broadcastMessage(ChatColor.RED + "The chat has been muted.");
            } else {
                Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " has muted the chat.");
            }
        } else {
            if (args.length == 1 && args[0].equalsIgnoreCase("-a")) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "The chat has been unmuted.");
            } else {
                Bukkit.broadcastMessage(ChatColor.GREEN + sender.getName() + " has unmuted the chat.");
            }
        }
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        chatMuted = !chatMuted;
        if (chatMuted) {
            Bukkit.broadcastMessage(ChatColor.RED + "The chat has been muted.");
        } else {
            Bukkit.broadcastMessage(ChatColor.GREEN + "The chat has been unmuted.");
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (chatMuted) {
            if (!player.hasPermission(this.bypassChatPermission)) {
                event.setCancelled(true);
            }
        }
    }
}
