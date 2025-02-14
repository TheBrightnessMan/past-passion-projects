package me.Bright.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Set;

import static me.Bright.main.Utils.colorCodes;

public class SoulShardShop implements Listener {

    private Inventory GUI;
    private final String NAME;

    public SoulShardShop() {
        NAME = colorCodes("Soul Shard Shop");
        GUI = Bukkit.createInventory(null, 9, NAME);
        Plugin plugin = MainClass.getProvidingPlugin(MainClass.class);
        FileConfiguration config = plugin.getConfig();

        try {
            Set<String> set = config.getConfigurationSection("Shop Items").getKeys(false);
            Bukkit.broadcastMessage(set.toString());
            if (!set.isEmpty()) {
                GUI = Bukkit.createInventory(null, (int) Math.ceil(set.size() / 9), NAME);
                for (String s : set) {
                    ItemStack is = config.getItemStack("Shop Items." + s);
                    GUI.addItem(is);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        try {
            Player player = (Player) event.getWhoClicked();
            if (event.getView().getTitle().equals(this.NAME)) {
                event.setCancelled(true);
            }
            if (event.getCurrentItem() == null) {
                return;
            }
            if (event.getCurrentItem().getItemMeta().getLore().isEmpty()) {
                return;
            }
            ItemStack goods = event.getCurrentItem();
            String lastLine = ChatColor.stripColor(goods.getItemMeta().getLore().get(goods.getItemMeta().getLore().size() - 1));
            String line = lastLine.split(" ")[1];
            int cost = Integer.parseInt(line);

            ItemStack soulShard = new SoulShardItem();

            if (player.getInventory().containsAtLeast(new SoulShardItem(), cost)) {
                for (ItemStack item : player.getInventory()) {
                    if (item.equals(soulShard)) {
                        if (item.getAmount() == cost) {
                            player.getInventory().remove(soulShard);
                        } else {
                            item.setAmount(item.getAmount() - cost);
                        }
                    }
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    public Inventory getGui() {
        return GUI;
    }

    public String getName() {
        return NAME;
    }

    public void openInventory(Player player) {
        player.openInventory(GUI);
    }
}
