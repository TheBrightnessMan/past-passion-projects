package me.Bright.main.gui;

import me.Bright.items.kit.naturalGod.NaturalGodShield;
import me.Bright.items.kit.naturalGod.NaturalGodSword;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class KitGUI extends GuiCore {

    public KitGUI() {
        super(ChatColor.GOLD + "Pick your kits!", 1);
        Inventory inventory = super.getInventory();
        inventory.addItem(naturalGod());
    }

    @Override
    public void onInventoryClick(Player player, InventoryAction action, ItemStack clickedItem, InventoryClickEvent event) {
        PlayerInventory playerInventory = player.getInventory();
        switch (clickedItem.getType()) {
            case OAK_SAPLING:
                playerInventory.addItem(new NaturalGodSword());
                playerInventory.addItem(new NaturalGodShield());
                break;
        }
        event.setCancelled(true);
        player.closeInventory();
    }

    private ItemStack naturalGod() {
        ItemStack itemStack = new ItemStack(Material.OAK_SAPLING);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.GREEN + "God of Nature");
        itemMeta.setLore(new ArrayList<String>() {{
            add(ChatColor.AQUA + "Click me to select the" + ChatColor.GREEN + " God of Nature Kit");
        }});
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
