package me.Bright.main.gui;

import me.Bright.main.MainClass;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class GuiCore implements Listener {

    private Inventory inventory;
    private final String NAME;
    private final Plugin plugin = MainClass.getPlugin(MainClass.class);

    /**
     * @param name         Name of the GUI
     * @param numberOfRows Number of rows in the gui
     */

    public GuiCore(String name, int numberOfRows) {
        inventory = plugin.getServer().createInventory(null, numberOfRows * 9, name);
        this.NAME = name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        InventoryView clickedInvView = event.getView();
        if (!clickedInvView.getTitle().equals(NAME)) {
            return;
        }

        InventoryAction action = event.getAction();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }
        onInventoryClick(player, action, clickedItem, event);
    }

    public abstract void onInventoryClick(Player player, InventoryAction action, ItemStack clickedItem, InventoryClickEvent event);
}
