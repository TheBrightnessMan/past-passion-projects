package me.Bright.main;

import me.Bright.enchants.*;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnegative;
import java.util.logging.Level;

public abstract class BrightEnchant extends Enchantment implements Listener {

    public static final BrightEnchant TOKEN_MINER = new TokenMinerEnchant();
    public static final BrightEnchant SPEED = new SpeedEnchant();
    public static final BrightEnchant FORTUNE = new FortuneEnchant();
    public static final BrightEnchant EFFICIENT_MINER = new EfficientMinerEnchant();
    public static final BrightEnchant GEM_MINER = new GemMinerEnchant();

    public static final BrightEnchant[] values = {
            TOKEN_MINER,
            SPEED,
            FORTUNE,
            EFFICIENT_MINER,
            GEM_MINER
    };

    public static @Nullable BrightEnchant getByKey(String key) {
        return (BrightEnchant) Enchantment.getByKey(new NamespacedKey(BrightPlugin.getProvidingPlugin(BrightPlugin.class),
                key));
    }

    private final String name;
    private final int maxLevel;
    private final EnchantmentTarget target;

    public BrightEnchant(@NotNull String name, @Nonnegative int maxLevel, EnchantmentTarget target) {
        super(new NamespacedKey(BrightPlugin.getProvidingPlugin(BrightPlugin.class),
                name.toLowerCase().replaceAll(" ", "_")));
        this.name = name;
        this.maxLevel = maxLevel;
        this.target = target;
    }

    public abstract ItemMeta onEnchant(ItemMeta itemMeta, int level);

    public abstract ItemMeta onDisenchant(ItemMeta itemMeta);

    public void logError(String errorMsg) {
        Bukkit.getLogger().log(Level.WARNING, "Enchant " + this.getKey() + " " + errorMsg + "!");
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Nonnegative
    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @NotNull
    @Override
    public EnchantmentTarget getItemTarget() {
        return this.target;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public abstract boolean conflictsWith(@NotNull Enchantment enchantment);

    @Override
    public boolean canEnchantItem(@NotNull ItemStack itemStack) {
        return this.target.includes(itemStack);
    }

    public abstract String[] getDescription(@Nonnegative int level);
}
