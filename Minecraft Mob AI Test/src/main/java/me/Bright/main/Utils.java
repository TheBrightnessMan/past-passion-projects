package me.Bright.main;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;

public class Utils {

    public static IChatBaseComponent stringToIChatBaseComponent(String string) {
        return IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + colorCodesSpecial(string) + "\"}");
    }

    public static String colorCodesSpecial(String string) {
        return colorCodes(string.replaceAll("&", "§"));
    }

    public static String colorCodes(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
