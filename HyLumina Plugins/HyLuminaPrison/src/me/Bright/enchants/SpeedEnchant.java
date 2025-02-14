package me.Bright.enchants;

import me.Bright.main.BrightEnchant;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SpeedEnchant extends BrightEnchant {

    private final double percentSpeedPerLevel = 2.5;

    public SpeedEnchant() {
        super("Speed", 100, EnchantmentTarget.TOOL);
    }

    @Override
    public ItemMeta onEnchant(@NotNull ItemMeta itemMeta, int level) {
        itemMeta.removeAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED);
        itemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED,
                new AttributeModifier(UUID.randomUUID(), "generic_movementSpeed",
                        level * 0.01 * percentSpeedPerLevel,
                        AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND));
        return itemMeta;
    }

    @Override
    public ItemMeta onDisenchant(ItemMeta itemMeta) {
        itemMeta.removeAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED);
        return itemMeta;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment enchantment) {
        return false;
    }

    @Override
    public String[] getDescription(int level) {
        return new String[]{
                ChatColor.GRAY + "Gain +" + (level * percentSpeedPerLevel) + "% movement speed."
        };
    }
}