package me.Bright.items;

import me.Bright.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class RealityStone extends ItemStack implements Listener {

    ItemMeta itemMeta = this.getItemMeta();
    HashMap<Player, Boolean> active = new HashMap<>();

    public RealityStone() {
        super(Material.RED_DYE);
        itemMeta.setDisplayName("" + ChatColor.RED + ChatColor.BOLD + "Reality Stone");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "Item Ability: Reality Warp " + ChatColor.GOLD + ChatColor.BOLD + "RIGHT CLICK");
        lore.add(ChatColor.GRAY + "Makes you invincible for " + ChatColor.RED + "10" + ChatColor.GRAY + " seconds.");
        lore.add("");
        lore.add("This stone represents reality, as it");
        lore.add("possesses the power to bend");
        lore.add("the very laws of reality");
        lore.add("and physics, converting");
        lore.add("matter into dark matter.");
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
    }

    @EventHandler
    public void itemUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!active.containsKey(player)) {
            active.put(player, false);
        }

        if (active.get(player) == false) {
            ItemStack holding = player.getInventory().getItemInMainHand();
            try {
                if (holding.getItemMeta().getDisplayName().equals("" + ChatColor.RED + ChatColor.BOLD + "Reality Stone")) {
                    active.replace(player, true);
                    if (active.get(player)) {
                        player.sendMessage("Your " + ChatColor.RED + ChatColor.BOLD + "Reality Stone" + ChatColor.RESET + " has been " + ChatColor.GREEN + "activated!");
                        new BukkitRunnable() {
                            int counter = 0;
                            @Override
                            public void run() {
                                counter++;
                                if (counter >=5 && counter < 10) {
                                    player.sendMessage("Your " + ChatColor.RED + ChatColor.BOLD + "Reality Stone" + ChatColor.RESET + " will be " + ChatColor.RED + "deactivated " +
                                            ChatColor.RESET + "in " + ChatColor.GOLD + (10 - counter) + ChatColor.RESET + " seconds!");
                                }

                                if (counter >= 10) {
                                    cancel();
                                    active.replace(player, false);
                                    player.sendMessage("Your " + ChatColor.RED + ChatColor.BOLD + "Reality Stone" + ChatColor.RESET + " has been " + ChatColor.RED + "deactivated!");
                                }
                            }
                        }.runTaskTimerAsynchronously(Main.getProvidingPlugin(Main.class), 0, 20);
                    }
                }
            } catch (NullPointerException e) {}
        }

    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (active.containsKey(player)) {
                if (active.get(player)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}