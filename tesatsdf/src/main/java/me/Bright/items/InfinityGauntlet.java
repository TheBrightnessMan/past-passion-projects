package me.Bright.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InfinityGauntlet extends ItemStack implements Listener {

    ItemMeta itemMeta = this.getItemMeta();
    String name = "" + ChatColor.BLACK + ChatColor.BOLD + ChatColor.MAGIC + "|" + ChatColor.WHITE + ChatColor.BOLD +
            "I" + ChatColor.GOLD + ChatColor.BOLD + "n" + ChatColor.RED + ChatColor.BOLD + "f" + ChatColor.YELLOW + ChatColor.BOLD + "i" +
            ChatColor.DARK_BLUE + ChatColor.BOLD + "n" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "i" + ChatColor.GREEN + ChatColor.BOLD + "t" +
            ChatColor.WHITE + ChatColor.BOLD + "y " + ChatColor.GRAY + ChatColor.BOLD + "Gauntlet" + ChatColor.BLACK + ChatColor.BOLD + ChatColor.MAGIC + "|";

    public InfinityGauntlet() {
        super(Material.WITHER_ROSE);
        itemMeta.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "Item Ability: Infinity Snap " + ChatColor.GOLD + ChatColor.BOLD + "RIGHT CLICK");
        lore.add(ChatColor.GRAY + "Destroy " + ChatColor.RED + ChatColor.BOLD + "ALL " + ChatColor.GRAY + "entities in a " + ChatColor.RED + "50");
        lore.add(ChatColor.GRAY + "block radius, snapping them away from existence");
        lore.add("");
        lore.add("The Infinity Gauntlet was a powerful");
        lore.add("Dwarven-made Uru glove");
        lore.add("that was designed to ");
        lore.add("channel the power of");
        lore.add("all six Infinity Stones.");
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
    }

    @EventHandler
    public void itemUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack holding = player.getInventory().getItemInMainHand();
        try {
            if (holding.getItemMeta().getDisplayName().equals(name) && event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                List<Entity> entities = player.getNearbyEntities(50, 50, 50);
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.damage(10000000, player);
                    } else {
                        entity.remove();
                    }
                }
                player.sendMessage("You have snapped away " + entities.size() + " entities!");
            }
        } catch (NullPointerException e) {}
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(name)) {
            event.setCancelled(true);
        }
    }

}
