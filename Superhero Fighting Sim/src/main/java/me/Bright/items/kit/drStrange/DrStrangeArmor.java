package me.Bright.items.kit.drStrange;

import me.Bright.utils.Utils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class DrStrangeArmor {

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
        meta.setColor(Color.fromBGR(186, 151, 37));
        meta.setDisplayName(Utils.colorCodes("&7&lStrange Helmet"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 75);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack chestplate() {
        ItemStack item = new ItemStack(Material.ELYTRA);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.colorCodes("&c&lCloak of Levitation"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 125);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack leggings() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.fromBGR(186, 151, 37));
        meta.setDisplayName(Utils.colorCodes("&7&lStrange Leggings"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 80);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack boots() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.fromBGR(186, 151, 37));
        meta.setDisplayName(Utils.colorCodes("&7&lStrange Boots"));
        Utils.setAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 50);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }
}
