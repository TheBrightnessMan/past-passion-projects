package me.Bright.enchants;

import me.Bright.main.BrightEnchant;
import me.Bright.utils.BrightUtils;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnegative;

public class FortuneEnchant extends BrightEnchant {

    public FortuneEnchant() {
        super("Fortune", 5000, EnchantmentTarget.TOOL);
    }

    @EventHandler
    public void onBreak(@NotNull BlockBreakEvent event) {
        BrightUtils.callFortune(event.getPlayer(), event.getBlock());
        event.setDropItems(false);
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
                String.valueOf(Math.floorDiv(level, 100) * 2) +
                ChatColor.GRAY;

        return new String[]{
                ChatColor.GRAY + "Gains " + multiplier + " extra block drops",
                ChatColor.GRAY + "and grant a " + chance + "% chance to",
                ChatColor.GRAY + "drop 2 extra items",
        };
    }
}
