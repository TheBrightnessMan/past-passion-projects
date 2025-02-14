package me.bright.command.punishments;

import me.bright.command.CommandCore;
import me.bright.playerData.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class History extends CommandCore {

    public History() {
        super("history", "bright.history");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        viewHistory(sender, args);
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        viewHistory(null, args);
    }

    private void viewHistory(Player moderator, String[] args) {
        if (args.length != 1) {
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (moderator != null) {
            if (PlayerData.loadPunishmentHistory(target.getUniqueId()) == null || PlayerData.loadPunishmentHistory(target.getUniqueId()).isEmpty()) {
                sendMessage(moderator, "This player has no punishment history.");
                return;
            }
            for (String str : PlayerData.loadPunishmentHistory(target.getUniqueId())) {
                sendMessage(moderator, str);
            }
        } else {
            if (PlayerData.loadPunishmentHistory(target.getUniqueId()) == null || PlayerData.loadPunishmentHistory(target.getUniqueId()).isEmpty()) {
                sendConsoleMessage("This player has no punishment history.");
                return;
            }
            for (String str : PlayerData.loadPunishmentHistory(target.getUniqueId())) {
                sendConsoleMessage(str);
            }
        }
    }
}
