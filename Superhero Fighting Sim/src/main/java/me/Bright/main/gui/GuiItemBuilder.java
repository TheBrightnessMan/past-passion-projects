package me.Bright.main.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiItemBuilder extends ItemStack {

    /**
     * @param material        Material of the item
     * @param color           Color of the item name
     * @param name            Name of the item
     * @param allowDoubleJump Toggle allow double jump
     */

    public GuiItemBuilder(Material material, ChatColor color, String name, boolean allowDoubleJump) {
        super(material);
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName("" + ChatColor.BOLD + color + name);
        ArrayList<String> lore = new ArrayList<String>() {{
            add(ChatColor.AQUA + "Click me to select " + color + name + "!");
            if (allowDoubleJump) {
                add(ChatColor.AQUA + "You can double jump while using this character!");
            }
        }};
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(lore);
        this.setItemMeta(meta);
    }
}
