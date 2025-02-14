package me.Bright.items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SpaceStone extends ItemStack implements Listener {

    ItemMeta itemMeta = this.getItemMeta();

    public SpaceStone() {
        super(Material.BLUE_DYE);
        itemMeta.setDisplayName("" + ChatColor.DARK_BLUE + ChatColor.BOLD + "Space Stone");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "Item Ability: Instant Transportation " + ChatColor.GOLD + ChatColor.BOLD + "RIGHT CLICK");
        lore.add(ChatColor.GRAY + "Teleports you forward by " + ChatColor.RED + "20" + ChatColor.GRAY + " blocks.");
        lore.add("");
        lore.add("Befitting its name, the Space Stone held dominion");
        lore.add("over the fabric of space being able to");
        lore.add("teleport its users anywhere in the universe.");
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
    }

    @EventHandler
    public void itemUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        try {
            ItemStack holding = player.getInventory().getItemInMainHand();
            if (holding.getItemMeta().getDisplayName().equals("" + ChatColor.DARK_BLUE + ChatColor.BOLD + "Space Stone") && event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                Location location = player.getTargetBlock(null, 20).getLocation().add(0.5, 1, 0.5);
                Location destination = new Location(player.getWorld(), location.getX(), location.getY(), location.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                player.teleport(destination);
            }
        } catch (NullPointerException e) {
        }
    }
}
