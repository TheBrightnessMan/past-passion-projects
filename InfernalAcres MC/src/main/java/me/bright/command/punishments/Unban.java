package me.bright.command.punishments;

import me.bright.command.CommandCore;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Unban extends CommandCore {

    public Unban() {
        super("unban", "bright.unban");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        unbanPlayer(sender, args);
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        unbanPlayer(null, args);
    }

    private void unbanPlayer(Player moderator, String[] args) {
        boolean silent = false;
        if (args.length >= 2) {
            silent = args[1].equalsIgnoreCase("-s");
        }
        Bukkit.getBanList(BanList.Type.NAME).pardon(args[0]);

        String moderatorName = "Console";
        if (moderator != null) {
            moderatorName = moderator.getName();
        }
        if (silent) {
            Bukkit.broadcast(ChatColor.GRAY + "[SILENT] " + ChatColor.GOLD + moderatorName + " unbanned " + args[0], "bright.notifysilentunban");
        } else {
            Bukkit.broadcastMessage(ChatColor.GOLD + moderatorName + " unbanned " + args[0]);
        }

    }
}
