package me.Bright.items;

import me.Bright.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MindStone extends ItemStack implements Listener {

    ItemMeta itemMeta = this.getItemMeta();
    HashMap<Player, Boolean> active = new HashMap<>();

    public MindStone() {
        super(Material.YELLOW_DYE);
        itemMeta.setDisplayName("" + ChatColor.YELLOW + ChatColor.BOLD + "Mind Stone");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "Item Ability: Forcefield " + ChatColor.GOLD + ChatColor.BOLD + "RIGHT CLICK TOGGLE");
        lore.add(ChatColor.GRAY + "Creates a forcefield around you, \n of which any entity in a radius of " + ChatColor.RED + "5");
        lore.add(ChatColor.GRAY + "blocks will be repelled.");
        lore.add("");
        lore.add("The Mind Stone is a remnant of a singularity that predated");
        lore.add("the universe, which governed over the fabric of mind.");
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
    }

    @EventHandler
    public void itemUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            try {
                ItemStack mainHand = player.getInventory().getItemInMainHand();
                String mainHandName = mainHand.getItemMeta().getDisplayName();
                if (mainHandName.equals("" + ChatColor.YELLOW + ChatColor.BOLD + "Mind Stone")) {
                    if (!active.containsKey(player)) {
                        active.put(player, false);
                    }

                    active.replace(player, !active.get(player));

                    if (active.get(player)) {
                        player.sendMessage("Your " + ChatColor.YELLOW + ChatColor.BOLD + "Mind Stone" + ChatColor.RESET + " has been " + ChatColor.GREEN + "activated!");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                try {
                                    List<Entity> entities = player.getNearbyEntities(5, 5, 5);
                                    for (Entity entity : entities) {
                                        if (entity instanceof Item) {
                                            continue;
                                        }
                                        Vector velocity = entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                                        entity.setVelocity(velocity);
                                    }
                                    if (!active.get(player)) {
                                        cancel();
                                        player.sendMessage("Your " + ChatColor.YELLOW + ChatColor.BOLD + "Mind Stone" + ChatColor.RESET + " has been " + ChatColor.RED + "deactivated!");

                                    }
                                } catch (IllegalArgumentException e) {
                                }
                            }
                        }.runTaskTimer(Main.getProvidingPlugin(Main.class), 0, 5);
                    }
                }
            } catch (NullPointerException e) {
            }

        }
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent event) {
        if (active.containsKey(event.getEntity())) {
            active.replace(event.getEntity(), false);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (active.containsKey(event.getPlayer())) {
            active.replace(event.getPlayer(), false);
        }
    }
}
