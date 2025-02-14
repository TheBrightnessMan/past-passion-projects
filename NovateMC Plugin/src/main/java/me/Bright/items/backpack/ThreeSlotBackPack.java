package me.Bright.items.backpack;

import me.Bright.main.MainClass;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ThreeSlotBackPack extends BackpackManager {

    Plugin plugin = MainClass.getProvidingPlugin(MainClass.class);
    FileConfiguration config = plugin.getConfig();
    Inventory backpack = getBackpack();
    String name = getName();

    public ThreeSlotBackPack() {
        super("Small Backpack", BackpackSize.SMALL, "8351e505989838e27287e7afbc7f97e796cab5f3598a76160c131c940d0c5");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            return;
        }
        if (player.getInventory().getItemInMainHand() == null) {
            return;
        }
        if (!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(getItemMeta().getDisplayName())) {
            return;
        }


    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        String name = event.getView().getTitle();
        if (!name.equals(this.name)) {
            return;
        }
        String backpackUUID = player.getInventory().getItemInMainHand().getItemMeta().getLore().get(1);
        config.set(backpackUUID, null);
        for (int i = 0; i <= inventory.getSize() - 1; i++) {
            ItemStack itemInBackpack = inventory.getItem(i);
            if (itemInBackpack == null) {
                continue;
            }
            if (itemInBackpack.getType().isAir()) {
                continue;
            }
            config.set(backpackUUID + "." + i, itemInBackpack.toString());
        }
        plugin.saveConfig();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand() == null) {
            return;
        }
        if (!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(getItemMeta().getDisplayName())) {
            return;
        }
        event.setCancelled(true);
    }

    @Override
    public void onOpenBackpack(Player player, ItemStack holding) {
        String backpackUUID = holding.getItemMeta().getLore().get(1);
        if (config.contains(backpackUUID)) {
            for (String id : config.getConfigurationSection(backpackUUID).getKeys(false)) {
                ItemStack item = config.getItemStack(holding.getItemMeta().getLore().get(1) + "." + id);
                if (item == null) {
                    continue;
                }
                backpack.setItem(Integer.parseInt(id), item);
            }
        }
        player.openInventory(backpack);
    }
}
