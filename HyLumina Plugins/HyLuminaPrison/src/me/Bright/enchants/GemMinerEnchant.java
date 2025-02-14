package me.Bright.enchants;

import me.Bright.main.BrightEnchant;
import me.Bright.main.BrightPlayer;
import me.Bright.main.BrightPlayerService;
import me.Bright.utils.BrightUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnegative;
import java.util.Random;
import java.util.logging.Level;

public class GemMinerEnchant extends BrightEnchant {

    public GemMinerEnchant() {
        super("Gem Miner", 5000, EnchantmentTarget.TOOL);
    }

    @EventHandler
    public void onBreak(@NotNull BlockBreakEvent event) {
        Player player = event.getPlayer();
        BrightPlayer brightPlayer = BrightPlayerService.getBrightPlayer(player);
        ItemStack holding = player.getInventory().getItemInMainHand();
        if (!BrightUtils.itemContainsCustomEnchant(holding, this)) {
            return;
        }

        if (brightPlayer == null) {
            logError("attempted to give gems to an offline player");
            return;
        }
        int level = BrightUtils.getEnchantmentLevel(holding, this);
        int amount = BrightUtils.calculateFortune(level, 1);
        brightPlayer.setGem(brightPlayer.getToken() + amount);
        brightPlayer.sendMessage("You found &6" + amount + " &rgems!");
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
                ChatColor.GRAY + "Gain " + multiplier + " gem(s) whilst mining",
                ChatColor.GRAY + "and grant a " + chance + "% chance to",
                ChatColor.GRAY + "find 1 extra gem.",
        };
    }
}
