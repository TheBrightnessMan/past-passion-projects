package me.Bright.main;

import org.bukkit.ChatColor;

public class Utils {

    public static String colorCodesSpecial(String string) {
        return colorCodes(string.replaceAll("&", "§"));
    }

    public static String colorCodes(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
