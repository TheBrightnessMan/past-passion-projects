package me.Bright.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;


public class TimeStone extends ItemStack implements Listener {

    ItemMeta itemMeta = this.getItemMeta();
    HashMap<Material, Integer> swordPriority = new HashMap<Material, Integer>() {{
        put(Material.NETHERITE_SWORD, 0);
        put(Material.DIAMOND_SWORD, 1);
        put(Material.IRON_SWORD, 2);
        put(Material.GOLDEN_SWORD, 3);
        put(Material.STONE_SWORD, 4);
        put(Material.WOODEN_SWORD, 5);
    }};
    HashMap<Integer, Material> swordPriority2 = new HashMap<Integer, Material>() {{
        put(0, Material.NETHERITE_SWORD);
        put(1, Material.DIAMOND_SWORD);
        put(2, Material.IRON_SWORD);
        put(3, Material.GOLDEN_SWORD);
        put(4, Material.STONE_SWORD);
        put(5, Material.WOODEN_SWORD);
    }};
    boolean active = false;

    public TimeStone() {
        super(Material.LIME_DYE);
        itemMeta.setDisplayName("" + ChatColor.GREEN + ChatColor.BOLD + "Time Stone");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "Item Ability: Time Warp " + ChatColor.GOLD + ChatColor.BOLD + "RIGHT CLICK");
        lore.add(ChatColor.GRAY + "Downgrades the weapon of your opponent,");
        lore.add(ChatColor.GRAY + "removing enchantments in the process.");
        lore.add(ChatColor.DARK_GRAY + "Diamond -> Iron -> Gold -> Stone -> Wood -> Destroy");
        lore.add("");
        lore.add("As indicated by its name,");
        lore.add("the Time Stone held dominion");
        lore.add("over the forces of time.");
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
    }

    @EventHandler
    public void itemUse(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (entity instanceof Player) {
            Player target = (Player) entity;
            try {
                if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("" + ChatColor.GREEN + ChatColor.BOLD + "Time Stone")) {
                    ItemStack holding = target.getInventory().getItemInMainHand();

                }
            } catch (NullPointerException e) {
            }
        }
    }
}