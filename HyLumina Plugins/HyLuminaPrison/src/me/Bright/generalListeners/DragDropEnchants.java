package me.Bright.generalListeners;

import me.Bright.main.BrightEnchant;
import me.Bright.main.BrightItem;
import me.Bright.utils.BrightUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DragDropEnchants implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        ItemStack onCursor = event.getCursor();
        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        PlayerInventory inventory = player.getInventory();
        if (onCursor == null || clickedItem == null) {
            return;
        }
        if (!onCursor.getType().equals(Material.ENCHANTED_BOOK) || !BrightUtils.isPickaxe(clickedItem)) {
            return;
        }

        Set<Map.Entry<Enchantment, Integer>> custom = onCursor.getEnchantments().entrySet();
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) onCursor.getItemMeta();
        if (meta == null) {
            return;
        }
        Set<Map.Entry<Enchantment, Integer>> vanilla = meta.getStoredEnchants().entrySet();

        Set<Map.Entry<Enchantment, Integer>> everything = new HashSet<>(vanilla);
        everything.addAll(custom);

        for (Map.Entry<Enchantment, Integer> entry : everything) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();
        }
    }
}
