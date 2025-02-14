package me.Bright.items;

import me.Bright.main.MainClass;
import me.Bright.utils.CountDown;
import me.Bright.utils.SkullCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Objects;

import static me.Bright.utils.Utils.colorCodes;

public class HomingMissile extends ItemStack implements Listener {

    private final Plugin plugin = MainClass.getProvidingPlugin(MainClass.class);

    ItemMeta skullMeta;
    int radius = 5;

    public HomingMissile() {
        super(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/302f48f34d22ded7404f76e8a132af5d7919c8dcd51df6e7a85ddfac85ab"));
        skullMeta = getItemMeta();
        skullMeta.setDisplayName(colorCodes("&6Guided Missile"));
        setItemMeta(skullMeta);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) throws NullPointerException {
        try {
            final Player player = event.getPlayer();
            if (player.getInventory().getItemInMainHand() == null) {
                return;
            }
            final ItemStack holding = player.getInventory().getItemInMainHand();
            String holdingName = Objects.requireNonNull(holding.getItemMeta()).getDisplayName();

            if (player.getVehicle() != null) {
                return;
            }
            if (!holdingName.equals(skullMeta.getDisplayName())) {
                return;
            }
            if (!event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                return;
            }

            final Location playerLocation = player.getLocation();

            final ArmorStand armorStand = playerLocation.getWorld().spawn(playerLocation.clone().add(0, 1, 0), ArmorStand.class);
            armorStand.setVisible(false);
            armorStand.addPassenger(player);
            Objects.requireNonNull(armorStand.getEquipment()).setHelmet(new ItemStack(Material.TNT));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 1, false, false));

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (armorStand.getPassengers().isEmpty()) {
                        player.teleport(playerLocation);
                        player.removePotionEffect(PotionEffectType.INVISIBILITY);
                        armorStand.remove();
                        cancel();
                    }
                    Block block = armorStand.getLocation().clone().add(0, -1, 0).getBlock();
                    if (block.isPassable()) {
                        Vector lookingDirection = player.getLocation().getDirection().normalize();
                        armorStand.setVelocity(lookingDirection);
                    } else {
                        armorStand.setVelocity(new Vector(0, 0, 0));
                        armorStand.getEquipment().setHelmet(null);
                        armorStand.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, armorStand.getLocation(), 5);
                        armorStand.getWorld().playSound(armorStand.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
                        Collection<Entity> nearbyEntities = armorStand.getLocation().getWorld().getNearbyEntities(armorStand.getLocation(), radius, radius, radius);
                        for (Entity entity : nearbyEntities) {
                            if (entity instanceof LivingEntity) {
                                LivingEntity livingEntity = (LivingEntity) entity;
                                if (livingEntity instanceof Player) {
                                    if (livingEntity.getName().equals(player.getName())) {
                                        continue;
                                    }
                                }
                                ((LivingEntity) entity).damage(30);
                            }
                        }
                        waitForABit(armorStand, player, playerLocation);
                        cancel();
                    }

                }
            }.runTaskTimer(plugin, 0, 5);
        } catch (NullPointerException ignored) {
        }
    }

    private void waitForABit(final ArmorStand armorStand, final Player player, final Location startingLocation) {
        new CountDown(2) {
            @Override
            public void run() {
                armorStand.remove();
                player.teleport(startingLocation);
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
            }
        };
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(skullMeta.getDisplayName())) {
            event.setCancelled(true);
        }
    }
}