package me.bright.command.punishments;

import me.bright.command.CommandCore;
import me.bright.playerData.PlayerData;
import me.bright.utils.BanUnit;
import me.bright.utils.PunishmentType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Date;

public class TempMute extends CommandCore {

    private final String notifySilentTempMute = "bright.notifysilenttempmute";
    private final String tempMuteExempt = "bright.tempmuteexempt";

    public TempMute() {
        super("tempmute", "bright.tempmute");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        tempBanPlayer(sender, args);
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        tempBanPlayer(null, args);
    }

    private void tempBanPlayer(Player moderator, String[] args) {
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
                    if (target.hasPermission(tempMuteExempt)) {
                        sendMessage(moderator, "You can not temp mute this player!");
                        return;
                    }
                }

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

                String moderatorName = "Console";
                if (moderator != null) {
                    moderatorName = moderator.getName();
                }

                if (silent) {
                    Bukkit.broadcast(ChatColor.GRAY + "[SILENT] " + ChatColor.GOLD + moderatorName + " temporarily muted " + target.getName() + " for" +
                            ChatColor.RED + reason, notifySilentTempMute);
                } else {
                    Bukkit.broadcastMessage(ChatColor.GOLD + moderatorName + " temporarily muted " + target.getName() + " for" +
                            ChatColor.RED + reason);
                }
                target.sendMessage(ChatColor.RED + "You have been temporarily muted by " + moderatorName + " for " + args[1] + " " + args[2] + "(s)" + " for the reason " + reason);
                PlayerData.logPunishment(target.getUniqueId(), moderator, PunishmentType.TEMP_MUTE, reason.toString(), args[1] + " " + args[2] + "(s)", date);

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (moderator != null) {
                sendMessage(moderator, "Please provide a reason.");
            } else {
                sendConsoleMessage("Please provide a reason.");
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (PlayerData.isMuted(player.getUniqueId())) {
            Date endDate = PlayerData.getTempMuteEndDate(player.getUniqueId());
            Date current = new Date(System.currentTimeMillis());

            assert endDate != null;
            if (endDate.after(current)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You have been muted. You may not talk.");
            } else {
                PlayerData.unMute(null, player, true);
            }
        }
    }
}
