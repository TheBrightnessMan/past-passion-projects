package me.Bright.enchants;

import me.Bright.main.BrightEnchant;
import me.Bright.utils.BrightUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnegative;
import java.util.ArrayList;
import java.util.Random;

public class EfficientMinerEnchant extends BrightEnchant {

    private final boolean PROC_FORTUNE = true;

    public EfficientMinerEnchant() {
        super("Efficient Miner", 2500, EnchantmentTarget.TOOL);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack holding = player.getInventory().getItemInMainHand();
        if (!BrightUtils.itemContainsCustomEnchant(holding, this)) {
            return;
        }
        int level = BrightUtils.getEnchantmentLevel(holding, this);
        int amount = BrightUtils.calculateFortune(level, 1);

        BlockFace[] toCheck = {
                BlockFace.NORTH,
                BlockFace.NORTH_EAST,
                BlockFace.EAST,
                BlockFace.SOUTH_EAST,
                BlockFace.SOUTH,
                BlockFace.SOUTH_WEST,
                BlockFace.WEST,
                BlockFace.NORTH_WEST
        };

        ArrayList<Block> blocks = new ArrayList<>();

        Block block = event.getBlock();
        Block upBlock = block.getRelative(BlockFace.UP);
        Block downBlock = block.getRelative(BlockFace.DOWN);

        if (BrightUtils.whitelistedBlocks.contains(upBlock.getType())) {
            blocks.add(upBlock);
        }
        if (BrightUtils.whitelistedBlocks.contains(downBlock.getType())) {
            blocks.add(downBlock);
        }

        //Check the relatives of the mined block
        for (BlockFace blockFace : toCheck) {
            Block checking = block.getRelative(blockFace);
            if (BrightUtils.whitelistedBlocks.contains(checking.getType())) {
                blocks.add(checking);
            }
        }

        //Check the relatives above the mined block
        for (BlockFace blockFace : toCheck) {
            Block checking = upBlock.getRelative(blockFace);
            if (BrightUtils.whitelistedBlocks.contains(checking.getType())) {
                blocks.add(checking);
            }
        }

        //Check the relatives below the mined block
        for (BlockFace blockFace : toCheck) {
            Block checking = downBlock.getRelative(blockFace);
            if (BrightUtils.whitelistedBlocks.contains(checking.getType())) {
                blocks.add(checking);
            }
        }

        while (amount > 0 && !blocks.isEmpty()) {
            Block pickedBlock = blocks.get(new Random().nextInt(blocks.size()));
            blocks.remove(pickedBlock);
            pickedBlock.setType(Material.AIR);
            if (PROC_FORTUNE) {
                BrightUtils.callFortune(player, pickedBlock);
            } else {
                BrightUtils.addDropsToInv(player, pickedBlock);
            }
            amount--;
        }
    }

    @Override
    public ItemMeta onEnchant(ItemMeta itemMeta, int level) {
        return null;
    }

    @Override
    public ItemMeta onDisenchant(ItemMeta itemMeta) {
        return null;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment enchantment) {
        return false;
    }

    @Override
    public String[] getDescription(int level) {
        String chance = ChatColor.GREEN +
                String.valueOf(Math.floorMod(level, 100)) +
                ChatColor.GRAY;

        String multiplier = ChatColor.GREEN +
                String.valueOf(Math.floorDiv(level, 100)) +
                ChatColor.GRAY;

        return new String[]{
                ChatColor.GRAY + "Mines " + multiplier + " block(s) nearby",
                ChatColor.GRAY + "and grant a " + chance + "% chance to",
                ChatColor.GRAY + "mine an extra random block.",
        };
    }
}
