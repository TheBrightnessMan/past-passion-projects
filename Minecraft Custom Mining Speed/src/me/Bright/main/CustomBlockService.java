package me.Bright.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public class CustomBlockService {

    private static final Map<Location, CustomBlock> brokenBlocks = new HashMap<>();

    public void createBrokenBlock(Block block) {
        createBrokenBlock(block, -1);
    }

    public void createBrokenBlock(Block block, int time) {
        if (isCustomBlock(block.getLocation())) {
            return;
        }
        CustomBlock brokenBlock = new CustomBlock(block, time);
        brokenBlocks.put(block.getLocation(), brokenBlock);
    }

    public void removeCustomBlock(Location location) {
        brokenBlocks.remove(location);
    }

    public CustomBlock getCustomBlock(Location location) {
        createBrokenBlock(location.getBlock());
        return brokenBlocks.get(location);
    }

    public boolean isCustomBlock(Location location) {
        return brokenBlocks.containsKey(location);
    }
}
