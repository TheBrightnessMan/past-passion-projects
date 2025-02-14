package me.bright.main;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BrightUtils {

    public static <T extends Number> String formatSeconds(T seconds) {
        long copy = seconds.longValue();
        long hour = copy / 3600;
        copy -= hour * 3600L;
        long minute = copy / 60;
        copy -= minute * 60L;
        long second = copy;

        StringBuilder result = new StringBuilder();
        if (hour > 0) {
            result.append(hour).append("h ");
        }
        if (minute > 0) {
            result.append(minute).append("m ");
        }
        if (second > 0) {
            result.append(second).append("s");
        }
        return result.toString();
    }

    public static ItemStack createGuiIcon(Material material, Component displayName, int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(displayName);
        item.setItemMeta(meta);
        return item;
    }
}
