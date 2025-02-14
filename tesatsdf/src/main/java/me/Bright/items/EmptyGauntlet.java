package me.Bright.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class EmptyGauntlet extends ItemStack implements Listener {

    ItemMeta itemMeta = this.getItemMeta();

    public EmptyGauntlet() {
        super(Material.POPPY);
        itemMeta.setDisplayName("" + ChatColor.GRAY + ChatColor.BOLD + "Gauntlet");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("The Infinity Gauntlet was a powerful");
        lore.add("Dwarven-made Uru glove");
        lore.add("that was designed to ");
        lore.add("channel the power of");
        lore.add("all six Infinity Stones.");
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("" + ChatColor.GRAY + ChatColor.BOLD + "Gauntlet")) {
            event.setCancelled(true);
        }
    }
}
