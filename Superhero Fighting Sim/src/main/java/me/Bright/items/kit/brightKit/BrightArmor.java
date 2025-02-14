package me.Bright.items.kit.brightKit;

import me.Bright.utils.Utils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BrightArmor {

    public static void equipArmor(Player player) {
        ItemStack[] armor = {boots(), leggings(), chestplate(), helmet()};
        player.getInventory().setArmorContents(armor);
        player.setAllowFlight(true);
    }

    public static ItemStack[] getItemArray() {
        ItemStack[] items = {helmet(), chestplate(), leggings(), boots()};
        return items;
    }

    public static ItemStack helmet() {
        ItemStack item = new ItemStack(Material.NETHERITE_HELMET);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.colorCodes("&f&lB&8&lr&f&li&8&lg&f&lh&8&lt&f&l's Helmet"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 125);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack chestplate() {
        ItemStack item = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.colorCodes("&f&lB&8&lr&f&li&8&lg&f&lh&8&lt&f&l's Chestpltae"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 200);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack leggings() {
        ItemStack item = new ItemStack(Material.NETHERITE_LEGGINGS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.colorCodes("&f&lB&8&lr&f&li&8&lg&f&lh&8&lt&f&l's Leggings"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 150);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack boots() {
        ItemStack item = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.colorCodes("&f&lB&8&lr&f&li&8&lg&f&lh&8&lt&f&l's Shoes"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 100);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }
}
