package me.bright.command.punishments;

import me.bright.command.CommandCore;
import me.bright.playerData.PlayerData;
import me.bright.utils.PunishmentType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Mute extends CommandCore implements Listener {

    private final String muteExemptPermission = "bright.muteexempt";
    private final String notifySilentMute = "bright.notifysilentmute";

    public Mute() {
        super("mute", "bright.mute");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        mutePlayer(sender, args);
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        mutePlayer(null, args);
    }

    private void mutePlayer(Player moderator, String[] args) {
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
                if (PlayerData.isMuted(target.getUniqueId())) {
                    if (moderator != null) {
                        sendMessage(moderator, "This player is already muted!");
                    } else {
                        sendConsoleMessage("This player is already muted!");
                    }
                }
                if (moderator != null) {
                    if (target.hasPermission(muteExemptPermission)) {
                        sendMessage(moderator, "You can not mute this player!");
                        return;
                    }
                }

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

                if (silent) {
                    Bukkit.broadcast(ChatColor.GRAY + "[SILENT] " + ChatColor.GOLD + moderatorName + " muted " + target.getName() +
                            " for" + ChatColor.RED + reason, notifySilentMute);
                } else {
                    Bukkit.broadcastMessage(ChatColor.GOLD + moderatorName + " muted " + target.getName() +
                            " for" + ChatColor.RED + reason);
                    target.sendMessage(ChatColor.RED + "You have been muted by " + moderatorName + " for " + reason);
                }
                PlayerData.logPunishment(target.getUniqueId(), moderator, PunishmentType.MUTE, reason.toString(), null, null);
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
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You have been muted. You may not talk.");
        }
    }
}
