package me.Bright.videoAssignment;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class CustomItem extends ItemStack implements Listener {

    public CustomItem(Material material) {
        super(material);
    }

    public void setName(String name) {
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        itemMeta.setDisplayName(name);
        this.setItemMeta(itemMeta);
    }

    public void setDescription(List<String> desc) {
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        itemMeta.setLore(desc);
        this.setItemMeta(itemMeta);
    }

    @EventHandler
    public void rightClickEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null) {
            return;
        }
        ItemStack holding = event.getItem();
        if (holding.getItemMeta() == null) {
            return;
        }
        if (!holding.getItemMeta().getDisplayName().equals(this.getItemMeta().getDisplayName())) {
            return;
        }
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            return;
        }
        onRightClick(player, event);
    }

    public abstract void onRightClick(Player player, PlayerInteractEvent event);
}
