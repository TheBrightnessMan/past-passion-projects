package me.Bright.utils;

import me.Bright.main.MainClass;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.Arrays;

public class Utils {

    public static boolean chanceYes(int chance) {
        double a = chance * 0.01;
        if (Math.random() < a) {
            return true;
        }
        return false;
    }

    public static IChatBaseComponent stringToIChatBaseComponent(String string) {
        return IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + colorCodesSpecial(string) + "\"}");
    }

    public static String colorCodesSpecial(String string) {
        return colorCodes(string.replaceAll("&", "§"));
    }

    public static String colorCodes(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void eatBlock(Block startingBlock, int range) {
        eatBlock(startingBlock, Material.values(), range);
    }

    public static void eatBlock(Block startingBlock, Material[] materialsToEat, int range) {
        ArrayList<Block> blocksToCheck = new ArrayList<Block>() {{
            add(startingBlock);
        }};
        for (int i = 0; i <= range; i++) {
            Bukkit.getScheduler().runTaskLater(MainClass.getProvidingPlugin(MainClass.class), () -> {
                ArrayList<Block> newList = new ArrayList<>(blocksToCheck);
                for (Block block : newList) {
                    blocksToCheck.remove(block);
                    Block up = block.getRelative(BlockFace.UP);
                    Block down = block.getRelative(BlockFace.DOWN);
                    Block north = block.getRelative(BlockFace.NORTH);
                    Block east = block.getRelative(BlockFace.EAST);
                    Block south = block.getRelative(BlockFace.SOUTH);
                    Block west = block.getRelative(BlockFace.WEST);
                    for (Block nearbyBlock : new ArrayList<>(Arrays.asList(up, down, north, east, south, west))) {
                        if (new ArrayList<>(Arrays.asList(materialsToEat)).contains(nearbyBlock.getType())) {
                            nearbyBlock.setType(Material.AIR);
                            blocksToCheck.add(nearbyBlock);
                        }
                    }
                }
            }, i * 2L);
        }
    }
}
