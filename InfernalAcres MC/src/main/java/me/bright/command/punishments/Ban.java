package me.bright.command.punishments;

import me.bright.command.CommandCore;
import me.bright.playerData.PlayerData;
import me.bright.utils.PunishmentType;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Ban extends CommandCore {

    private final String banExemptPermission = "bright.banexempt";
    private final String notifysilentban = "bright.notifysilentban";

    public Ban() {
        super("ban", "bright.ban");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        banPlayer(sender, args);
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        banPlayer(null, args);
    }

    private void banPlayer(Player moderator, String[] args) {
        try {
            if (args.length >= 1) {
                if (Bukkit.getPlayer(args[0]) == null) {
                    if (moderator == null) {
                        sendConsoleMessage("Unable to find player " + args[0]);
                    } else {
                        sendMessage(moderator, "Unable to find player " + args[0]);
                    }
                    return;
                }
                Player target = Bukkit.getPlayer(args[0]);
                assert target != null;
                if (moderator != null) {
                    if (target.hasPermission(banExemptPermission)) {
                        sendMessage(moderator, "You can not ban this player!");
                        return;
                    }
                }

                BanList banList = Bukkit.getBanList(BanList.Type.NAME);

                String moderatorName = "Console";
                if (moderator != null) {
                    moderatorName = moderator.getName();
                }

                StringBuilder reason = new StringBuilder();
                boolean silent = false;

                if (args[1].equalsIgnoreCase("-s")) {
                    for (int i = 2; i <= (args.length - 1); i++) {
                        reason.append(" ").append(args[i]);
                    }
                    silent = true;
                } else {
                    for (int i = 1; i <= (args.length - 1); i++) {
                        reason.append(" ").append(args[i]);
                    }
                }

                String banScreen = reason + "\n" +
                        ChatColor.WHITE + "Moderator: " + moderatorName;


                banList.addBan(target.getName(), banScreen, null, null);
                target.kickPlayer("You are banned from this server. Reason:" + banScreen);

                if (silent) {
                    Bukkit.broadcast(ChatColor.GRAY + "[SILENT] " + ChatColor.GOLD + moderatorName + " banned " + target.getName() +
                            " for" + ChatColor.RED + reason, notifysilentban);
                } else {
                    Bukkit.broadcastMessage(ChatColor.GOLD + moderatorName + " banned " + target.getName() +
                            " for" + ChatColor.RED + reason);
                }
                PlayerData.logPunishment(target.getUniqueId(), moderator, PunishmentType.BAN, reason.toString(), null, null);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (moderator != null) {
                sendMessage(moderator, "Please provide a reason.");
            } else {
                sendConsoleMessage("Please provide a reason.");
            }
        }
    }
}
