package me.Bright.items.kit.scarletWitch;

import me.Bright.utils.Utils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ScarletWitchArmor {

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
        ItemStack item = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.RED);
        meta.setDisplayName(Utils.colorCodes("&c&lSokovia Helmet"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 30);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack chestplate() {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.RED);
        meta.setDisplayName(Utils.colorCodes("&c&lSokovia Chestpltae"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 100);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack leggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.RED);
        meta.setDisplayName(Utils.colorCodes("&c&lSokovia Leggings"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 75);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack boots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.RED);
        meta.setDisplayName(Utils.colorCodes("&c&lSokovia Boots"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 50);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }
}
