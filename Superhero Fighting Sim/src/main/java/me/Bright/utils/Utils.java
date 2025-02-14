package me.Bright.utils;

import me.Bright.main.MainClass;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Utils {

    public static Plugin plugin = MainClass.getProvidingPlugin(MainClass.class);

    public static boolean chanceYes(int chance) {
        double a = chance * 0.01;
        return Math.random() < a;
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
                    for (Block nearbyBlock : new ArrayList<Block>(Arrays.asList(up, down, north, east, south, west))) {
                        if (new ArrayList<>(Arrays.asList(materialsToEat)).contains(nearbyBlock.getType())) {
                            nearbyBlock.setType(Material.AIR);
                            blocksToCheck.add(nearbyBlock);
                        }
                    }
                }
            }, i * 2);
        }
    }

    public static ItemMeta setAttribute(ItemMeta itemMeta, Attribute attribute, double value) {
        AttributeModifier modifier = new AttributeModifier(attribute.getKey().getKey(), value, AttributeModifier.Operation.ADD_NUMBER);
        if (itemMeta.addAttributeModifier(attribute, modifier)) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            return itemMeta;
        }
        return null;
    }

    public static void shootBeam(Player player, Location from, Location to, Color color, double range, double damageDealt) {
        Vector moveDistance = to.subtract(from).toVector().normalize().multiply(0.5);
        Particle.DustOptions dust = new Particle.DustOptions(color, 1.5f);
        World world = from.getWorld();
        world.spawnParticle(Particle.REDSTONE, from, 0, 0, 0, 0, dust);
        Location newLocation = from;
        for (int i = 0; i <= range; i++) {
            world.spawnParticle(Particle.REDSTONE, newLocation.add(moveDistance), 0, 0, 0, 0, dust);
            newLocation = newLocation.clone().add(moveDistance);
            for (Entity entity : world.getNearbyEntities(newLocation, 0.5, 0.5, 0.5)) {
                if (entity instanceof LivingEntity) {
                    if (!(entity.getName().equals(player.getName()))) {
                        ((LivingEntity) entity).damage(damageDealt, player);
                        break;
                    }
                }
            }
        }
    }

    public static LivingEntity getNearestLivingEntity(Location location, EntityType[] ignoreType, Entity[] ignore) {
        Collection<Entity> nearbyEntites = location.getWorld().getNearbyEntities(location, 20, 20, 20);
        List<LivingEntity> nearbyLivingEntites = new ArrayList<>();
        for (Entity entity : nearbyEntites) {
            if (Arrays.asList(ignore).contains(entity)) {
                continue;
            }
            if (Arrays.asList(ignoreType).contains(entity.getType())) {
                continue;
            }
            if (entity instanceof LivingEntity) {
                nearbyLivingEntites.add((LivingEntity) entity);
            }

        }
        if (nearbyLivingEntites.isEmpty()) {
            return null;
        }
        LivingEntity nearestLivingEntity = nearbyLivingEntites.get(0);
        for (LivingEntity a : nearbyLivingEntites) {
            if (location.distance(a.getLocation()) < location.distance(nearestLivingEntity.getLocation())) {
                nearestLivingEntity = a;
            }
        }
        return nearestLivingEntity;
    }
}