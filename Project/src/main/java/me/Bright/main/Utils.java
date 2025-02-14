package me.Bright.main;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Utils {

    public static Block getBlockLooking(Player player, int range) {
        Block block = player.getTargetBlock(null, range);
        return block;
    }

    public static Location getLocationLooking(Player player, int range) {
        Block block = player.getTargetBlock(null, range);
        return block.getLocation();
    }
}
