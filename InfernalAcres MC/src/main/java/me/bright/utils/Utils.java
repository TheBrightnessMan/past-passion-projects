package me.bright.utils;

import org.bukkit.ChatColor;

public class Utils {

    public static String pluginPrefix = "&4Infernal&6Acres &8» ";

    public static String colorCodesSpecial(String string) {
        return colorCodes(string.replaceAll("&", "§"));
    }

    public static String colorCodes(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
