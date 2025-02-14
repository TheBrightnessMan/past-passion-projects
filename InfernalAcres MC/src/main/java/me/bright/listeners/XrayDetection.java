package me.bright.listeners;

import me.bright.main.BrightPlugin;
import me.bright.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XrayDetection implements Listener {

    private final BrightPlugin plugin = BrightPlugin.getPlugin(BrightPlugin.class);

    private final ArrayList<Material> BLOCKS_TO_DETECT = new ArrayList<Material>() {{
        add(Material.DIAMOND_ORE);
        add(Material.EMERALD_ORE);
        add(Material.GOLD_ORE);
        add(Material.LAPIS_ORE);
    }};
    private final String notifyPermission = "bright.xraydetect";
    private final Map<Player, Integer> minedCount = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material material = block.getType();

        if (!BLOCKS_TO_DETECT.contains(material)) {
            return;
        }
        if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
            return;
        }

        if (minedCount.containsKey(player)) {
            minedCount.replace(player, minedCount.get(player) + 1);
        } else {
            minedCount.put(player, 1);
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Bukkit.broadcast(Utils.colorCodes(Utils.pluginPrefix + ChatColor.GOLD + player.getName() + " mined " + ChatColor.RED + minedCount.get(player) + " " + material.name()), this.notifyPermission);
                minedCount.remove(player);
            }, 100);
        }
    }
}