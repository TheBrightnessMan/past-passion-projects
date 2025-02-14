package me.Bright.items.kit.ironMan;

import me.Bright.utils.Utils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class IronManArmor {

    public static void equipArmor(Player player) {
        ItemStack[] armor = {boots(), leggings(), chestplate(), helmet()};
        player.getInventory().setArmorContents(armor);
        player.setAllowFlight(true);
        player.setFlying(true);
    }

    public static ItemStack[] getItemArray() {
        ItemStack[] items = {helmet(), chestplate(), leggings(), boots()};
        return items;
    }

    public static ItemStack helmet() {
        ItemStack item = new ItemStack(Material.IRON_HELMET);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.colorCodes("&f&lMetal Helmet"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 80);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack chestplate() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.colorCodes("&f&lMetal Chestpltae"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 175);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack leggings() {
        ItemStack item = new ItemStack(Material.IRON_LEGGINGS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.colorCodes("&f&lMetal Leggings"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 125);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack boots() {
        ItemStack item = new ItemStack(Material.IRON_BOOTS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.colorCodes("&f&lMetal Boots"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 50);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }
}