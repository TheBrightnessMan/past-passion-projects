package me.bright.command.vanish;

import me.bright.command.CommandCore;
import me.bright.main.BrightPlugin;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class VanishCore extends CommandCore implements Listener {

    private final ArrayList<Player> vanishedPlayers = new ArrayList<>();
    protected static final ArrayList<Player> allowItemPickUp = new ArrayList<>();

    private final BrightPlugin plugin = BrightPlugin.getPlugin(BrightPlugin.class);

    public VanishCore() {
        super("v", "bright.vanish");
    }

    @Override
    protected void commandSentAsPlayer(Player sender, String[] args) {
        VanishLevel vanishLevel = null;
        for (VanishLevel level : VanishLevel.values()) {
            if (sender.hasPermission(level.getPermission())) {
                vanishLevel = level;
                break;
            }
        }

        if (vanishLevel == null) {
            return;
        }

        if (vanishedPlayers.contains(sender)) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.showPlayer(plugin, sender);
            }
            vanishedPlayers.remove(sender);
            allowItemPickUp.remove(sender);
            Bukkit.broadcast(ChatColor.GREEN + sender.getName() + " has unvanished.", vanishLevel.getPermission());
        } else {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!online.hasPermission(vanishLevel.getPermission())) {
                    online.hidePlayer(plugin, sender);
                }
            }
            vanishedPlayers.add(sender);
            Bukkit.broadcast(ChatColor.RED + sender.getName() + " has vanished.", vanishLevel.getPermission());
            showActionBar(sender);
        }
    }

    private void showActionBar(Player sender) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (vanishedPlayers.contains(sender)) {
                    sender.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + "Vanished"));
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        sendConsoleMessage("Only players can access this command.");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (vanishedPlayers.contains(player)) {
            sendMessage(player, "You are still vanished.");
        } else {
            VanishLevel vanishLevel = null;
            for (VanishLevel level : VanishLevel.values()) {
                if (player.hasPermission(level.getPermission())) {
                    vanishLevel = level;
                    break;
                }
            }

            if (vanishLevel == null) {
                return;
            }


            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!online.hasPermission(vanishLevel.getPermission())) {
                    online.hidePlayer(plugin, player);
                }
            }
            vanishedPlayers.add(player);
            Bukkit.broadcast(ChatColor.RED + player.getName() + " has vanished.", vanishLevel.getPermission());
            sendMessage(player, "You have been auto vanished.");
            showActionBar(player);
        }
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onItemPickUp(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (!allowItemPickUp.contains(player)) {
            event.setCancelled(true);
        }
    }
}
