package me.Bright.items.powerstone;

import me.Bright.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class PowerStone extends ItemStack implements Listener {

    ItemMeta itemMeta = this.getItemMeta();

    public PowerStone() {
        super(Material.PURPLE_DYE);
        itemMeta.setDisplayName("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Power Stone");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "Huge Knockback");
        lore.add("");
        lore.add(ChatColor.YELLOW + "Item Ability: Terrain Toss " + ChatColor.GOLD + ChatColor.BOLD + "RIGHT CLICK");
        lore.add(ChatColor.GRAY + "Launches the block underneath you, causing an");
        lore.add(ChatColor.GRAY + "explosion at the landing site,");
        lore.add(ChatColor.GRAY + "dealing " + ChatColor.RED + "∞" + ChatColor.GRAY + " damage to all nearby entities.");
        lore.add(ChatColor.DARK_GRAY + "Yes you read that right, ∞ damage.");
        lore.add("");
        lore.add("The Power Stone was a powerful weapon");
        lore.add("capable of granting a person with great,");
        lore.add("cosmic power,but was highly likely to");
        lore.add("kill any organic beings that touch it.");
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        final Location target = player.getTargetBlock(null, 200).getLocation();

    }

    /*@EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        this.player = event.getPlayer();
        Action action = event.getAction();
        try {
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Power Stone")) {
                    this.blockType = Material.STONE;

                    ArmorStand armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
                    armorStand.setGravity(false);

                    fallingBlock = player.getLocation().getWorld().spawnFallingBlock(player.getLocation(), blockType, (byte) 0);
                    Snowball snowball = player.getWorld().spawn(player.getEyeLocation().clone().add(0.5, 0.5, 0.5), Snowball.class);
                    snowball.addPassenger(fallingBlock);
                    snowball.setVelocity(player.getLocation().getDirection().multiply(1.5));

                    snowball.setShooter(player);
                }
            }
        } catch (NullPointerException e) {
        }
    }

    @EventHandler
    public void fallingBlockLand(EntityChangeBlockEvent event) {
        if ((event.getEntityType() == EntityType.FALLING_BLOCK)) {
            if (((FallingBlock) event.getEntity()).getBlockData().getMaterial().equals(Material.STONE)) {
                event.setCancelled(true);
                event.getEntity().remove();
            }
        }
    }

    @EventHandler
    public void projectileHitEvent(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (projectile.getPassengers().isEmpty()) {
            return;
        }
        BlockIterator iterator = new BlockIterator(event.getEntity().getWorld(), event.getEntity().getLocation().toVector(), event.getEntity().getVelocity().normalize(), 0.0D, 4);
        Block hitBlock = null;

        while (iterator.hasNext()) {
            hitBlock = iterator.next();

            if (!hitBlock.getType().equals(Material.AIR)) {
                break;
            }
        }

        hitBlock.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, hitBlock.getLocation(), 10);
        fallingBlock.remove();

        for (Entity entity : hitBlock.getWorld().getNearbyEntities(hitBlock.getLocation(), 5, 5, 5)) {
            if (entity instanceof LivingEntity) {
                if (entity instanceof Player) {
                    if (entity.getName().equals(player.getName())) {
                        continue;
                    }
                }
                ((LivingEntity) entity).setHealth(0);
            }
        }
    }*/

    @EventHandler
    public void entityDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            Entity ouch = event.getEntity();
            try {
                if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Power Stone")) {
                    Vector velocity = ouch.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(10);
                    ouch.setVelocity(velocity);
                }
            } catch (NullPointerException e) {
            }
        }
    }

}