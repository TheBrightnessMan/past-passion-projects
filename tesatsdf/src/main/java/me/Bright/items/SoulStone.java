package me.Bright.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SoulStone extends ItemStack implements Listener {

    ItemMeta itemMeta = this.getItemMeta();

    public SoulStone() {
        super(Material.ORANGE_DYE);
        itemMeta.setDisplayName("" + ChatColor.GOLD + ChatColor.BOLD + "Soul Stone");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "Item Ability: Soul Drain " + ChatColor.GOLD + ChatColor.BOLD + "RIGHT CLICK");
        lore.add(ChatColor.GRAY + "Drains the soul from your opponent, killing them.");
        lore.add("");
        lore.add("The Soul Stone represented the soul.");
        lore.add("It would later go on to cultivate");
        lore.add("a reputation of mystery");
        lore.add("throughout the galaxy.");
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
    }

    @EventHandler
    public void itemUse(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        try {
            ItemStack holding = player.getInventory().getItemInMainHand();
            if (holding.getItemMeta().getDisplayName().equals("" + ChatColor.GOLD + ChatColor.BOLD + "Soul Stone")) {
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.setHealth(0);
                    livingEntity.damage(1, player);
                }
            }
        } catch (NullPointerException e) {
        }
    }
}
