package me.Bright.main;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static me.Bright.main.Utils.colorCodes;

public class SoulShardItem extends ItemStack {

    public SoulShardItem() {
        super(Material.QUARTZ);
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(colorCodes("&dSoul Shard"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(colorCodes("&fA fragment of the soul"));
        lore.add(colorCodes("&fof the people killed"));
        lore.add("");
        lore.add(colorCodes("&7Can be used to exchange items"));
        lore.add(colorCodes("&7in the Soul Shop"));
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
    }
}
