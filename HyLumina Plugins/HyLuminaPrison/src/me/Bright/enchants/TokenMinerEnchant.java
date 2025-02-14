package me.Bright.enchants;

import me.Bright.main.BrightEnchant;
import me.Bright.main.BrightPlayer;
import me.Bright.main.BrightPlayerService;
import me.Bright.utils.BrightUtils;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TokenMinerEnchant extends BrightEnchant {

    public TokenMinerEnchant() {
        super("Token Miner", 5000, EnchantmentTarget.TOOL);
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
            logError("attempted to give token to an offline player");
            return;
        }
        int level = BrightUtils.getEnchantmentLevel(holding, this);
        //Token gain range: 2~5
        int amount = BrightUtils.calculateFortune(level, new Random().nextInt(4) + 2);
        brightPlayer.setToken(brightPlayer.getToken() + amount);
        brightPlayer.sendMessage("You found &6" + amount + " &rtokens!");
    }

    @Override
    public ItemMeta onEnchant(ItemMeta itemMeta, int level) {
        return itemMeta;
    }

    @Override
    public ItemMeta onDisenchant(ItemMeta itemMeta) {
        return itemMeta;
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

        String multiplier1 = ChatColor.GREEN +
                String.valueOf(Math.floorDiv(level, 100) * 2) +
                ChatColor.GRAY;

        String multiplier2 = ChatColor.GREEN +
                String.valueOf(Math.floorDiv(level, 100) * 5) +
                ChatColor.GRAY;

        return new String[]{
                ChatColor.GRAY + "Gain " + multiplier1 + " to " + multiplier2 + " token(s) whilst mining",
                ChatColor.GRAY + "and grant a " + chance + "% chance to",
                ChatColor.GRAY + "find extra tokens.",
        };
    }
}
