package me.bright.command.punishments;

import me.bright.command.CommandCore;
import me.bright.playerData.PlayerData;
import me.bright.utils.BanUnit;
import me.bright.utils.PunishmentType;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Date;

public class TempBan extends CommandCore {

    private final String tempbanExemptPermission = "bright.tempbanexempt";
    private final String notifysilenttempban = "bright.notifysilenttempban";

    public TempBan() {
        super("tempban", "bright.tempban");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        tempbanPlayer(sender, args);
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        tempbanPlayer(null, args);
    }

    private void tempbanPlayer(Player moderator, String[] args) {
        try {
            if (args.length >= 3) {
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
                    if (target.hasPermission(tempbanExemptPermission)) {
                        sendMessage(moderator, "You can not temp ban this player!");
                        return;
                    }
                }
                BanList banList = Bukkit.getBanList(BanList.Type.NAME);

                boolean silent = false;

                StringBuilder reason = new StringBuilder();
                BanUnit unit;
                long value;

                if (args[1].equalsIgnoreCase("-s")) {
                    for (int i = 4; i <= (args.length - 1); i++) {
                        reason.append(" ").append(args[i]);
                    }
                    silent = true;
                    if (!BanUnit.getUnitsAsString().contains(args[3])) {
                        return;
                    }
                    unit = BanUnit.getUnit(args[3]);
                    try {
                        value = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        if (moderator != null) {
                            sendMessage(moderator, "Invalid Number");
                        } else {
                            sendConsoleMessage("Invalid Number");
                        }
                        return;
                    }
                } else {
                    for (int i = 3; i <= (args.length - 1); i++) {
                        reason.append(" ").append(args[i]);
                    }
                    if (!BanUnit.getUnitsAsString().contains(args[2])) {
                        return;
                    }
                    unit = BanUnit.getUnit(args[2]);
                    try {
                        value = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        if (moderator != null) {
                            sendMessage(moderator, "Invalid Number");
                        } else {
                            sendConsoleMessage("Invalid Number");
                        }
                        return;
                    }
                }

                assert unit != null;
                long lengthInMilliseconds = value * unit.getToMillisecond();

                Date date = new Date(System.currentTimeMillis() + lengthInMilliseconds);

                String banScreen;
                String moderatorName = "Console";
                if (moderator != null) {
                    moderatorName = moderator.getName();
                }
                banScreen = reason + "\n" +
                        ChatColor.WHITE + "Moderator: " + moderatorName;

                banList.addBan(target.getName(), banScreen, date, null);

                if (silent) {
                    target.kickPlayer("You are banned from this server for " + args[2] + " " + args[3] + "(s). Reason:" + banScreen);
                    Bukkit.broadcast(ChatColor.GRAY + "[SILENT] " + ChatColor.GOLD + moderatorName + " temporarily banned " + target.getName() + " for" +
                            ChatColor.RED + reason, notifysilenttempban);
                } else {
                    target.kickPlayer("You are banned from this server for " + args[1] + " " + args[2] + "(s). Reason:" + banScreen);
                    Bukkit.broadcastMessage(ChatColor.GOLD + moderatorName + " temporarily banned " + target.getName() + " for" +
                            ChatColor.RED + reason);
                }
                PlayerData.logPunishment(target.getUniqueId(), moderator, PunishmentType.TEMP_BAN, reason.toString(), args[1] + " " + args[2] + "(s)", date);

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
