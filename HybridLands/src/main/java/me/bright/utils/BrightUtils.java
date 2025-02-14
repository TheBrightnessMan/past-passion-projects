package me.bright.utils;

import org.bukkit.ChatColor;

public class BrightUtils {

    public static String colorCodesSpecial(String string) {
        return colorCodes(string.replaceAll("&", "§"));
    }

    public static String colorCodes(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
