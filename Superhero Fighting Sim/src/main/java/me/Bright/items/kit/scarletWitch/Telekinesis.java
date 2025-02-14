package me.Bright.items.kit.scarletWitch;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.main.MainClass;
import me.Bright.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Telekinesis extends ItemBuilder {

    ItemMeta itemMeta = getItemMeta();
    HashMap<Player, Boolean> playerList = new HashMap<>();
    private static Location target;
    private final double DAMAGE = 6;

    private final Plugin plugin = MainClass.getProvidingPlugin(MainClass.class);

    public Telekinesis() {
        super(Material.RED_DYE,
                "&c&lTelekinetic Powers",
                MouseClick.RIGHT_CLICK,
                "Lift");
        List<String> lore = itemMeta.getLore();
        lore.add(Utils.colorCodes("&e&lRight Click Entity Ability: &6&lLift"));
        itemMeta.setLore(lore);
        setItemMeta(itemMeta);
        setCooldown(0);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1, false, false, false));
        Block block = player.getLocation().clone().add(0, 1, 0).getBlock();

        //Keep looping through the blocks below the player until
        //a solid block is found
        while (block.isPassable()) {
            block = block.getRelative(BlockFace.DOWN);
        }

        //Initialize the armor stand
        final ArmorStand armorStand1 = player.getWorld().spawn(block.getLocation().clone().add(0.5, 0, 0.5), ArmorStand.class);
        final ArmorStand armorStand2 = player.getWorld().spawn(block.getLocation().clone().add(0.5, 0, 0.5), ArmorStand.class);

        armorStand1.setVisible(false);
        armorStand1.getEquipment().setHelmet(new ItemStack(block.getType()));

        armorStand2.setVisible(false);
        armorStand2.getEquipment().setHelmet(new ItemStack(block.getType()));

        if (playerList.containsKey(player)) {
            playerList.replace(player, false);
        } else {
            playerList.put(player, false);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                //If the player has launched their projectile
                if (playerList.containsKey(player)) {
                    if (playerList.get(player)) {

                        Vector vector1 = target.toVector().subtract(armorStand1.getLocation().toVector()).normalize();
                        armorStand1.setVelocity(vector1);

                        if (!armorStand1.getLocation().clone().add(0, -1, 0).getBlock().isPassable()) {
                            armorStand1.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, armorStand1.getLocation(), 5);
                            armorStand1.getWorld().playSound(armorStand1.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
                            armorStand1.remove();

                            Collection<Entity> nearbyEntities = armorStand1.getWorld().getNearbyEntities(armorStand1.getLocation(), 5, 5, 5);
                            for (Entity entity : nearbyEntities) {
                                if (entity instanceof LivingEntity) {
                                    if (!(entity.getName().equals(player.getName()))) {
                                        LivingEntity livingEntity = (LivingEntity) entity;
                                        livingEntity.damage(DAMAGE, player);
                                    }
                                }
                            }

                            playerList.remove(player);
                        }

                        Vector vector2 = target.toVector().subtract(armorStand2.getLocation().toVector()).normalize();
                        armorStand2.setVelocity(vector2);

                        if (!armorStand2.getLocation().clone().add(0, -1, 0).getBlock().isPassable()) {
                            armorStand2.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, armorStand2.getLocation(), 5);
                            armorStand2.getWorld().playSound(armorStand2.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
                            armorStand2.remove();

                            Collection<Entity> nearbyEntities = armorStand2.getWorld().getNearbyEntities(armorStand2.getLocation(), 5, 5, 5);
                            for (Entity entity : nearbyEntities) {
                                if (entity instanceof LivingEntity) {
                                    if (!(entity.getName().equals(player.getName()))) {
                                        LivingEntity livingEntity = (LivingEntity) entity;
                                        livingEntity.damage(DAMAGE, player);
                                    }
                                }
                            }

                            playerList.remove(player);
                        }
                    } else {
                        //Get the vector to launch the armor stand
                        double angle = player.getLocation().getYaw() * Math.PI / 180;
                        Location to1 = player.getLocation().clone().add(-1.5 * Math.cos(angle), 0, -1.5 * Math.sin(angle));
                        Location armorStandLocation1 = armorStand1.getLocation();
                        Vector vector1 = to1.subtract(armorStandLocation1).toVector().normalize();
                        armorStand1.setVelocity(vector1);

                        Location to2 = player.getLocation().clone().add(1.5 * Math.cos(angle), 0, 1.5 * Math.sin(angle));
                        Location armorStandLocation2 = armorStand2.getLocation();
                        Vector vector2 = to2.subtract(armorStandLocation2).toVector().normalize();
                        armorStand2.setVelocity(vector2);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1, false, false, false));
                    }
                } else {
                    Vector vector1 = target.toVector().subtract(armorStand1.getLocation().toVector());
                    if (vector1.length() > 1) {
                        vector1.normalize();
                    }
                    armorStand1.setVelocity(vector1);

                    Vector vector2 = target.toVector().subtract(armorStand2.getLocation().toVector());
                    if (vector2.length() > 1) {
                        vector2.normalize();
                    }
                    armorStand2.setVelocity(vector2);

                    if (!armorStand1.getLocation().clone().add(0, -1, 0).getBlock().isPassable()) {
                        armorStand1.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, armorStand1.getLocation(), 5);
                        armorStand1.getWorld().playSound(armorStand1.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
                        armorStand1.remove();

                        Collection<Entity> nearbyEntities = armorStand1.getWorld().getNearbyEntities(armorStand1.getLocation(), 5, 5, 5);
                        for (Entity entity : nearbyEntities) {
                            if (entity instanceof LivingEntity) {
                                if (!(entity.getName().equals(player.getName()))) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    livingEntity.damage(DAMAGE, player);
                                }
                            }
                        }
                        playerList.remove(player);
                    }

                    if (!armorStand2.getLocation().clone().add(0, -1, 0).getBlock().isPassable()) {
                        armorStand2.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, armorStand2.getLocation(), 5);
                        armorStand2.getWorld().playSound(armorStand2.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
                        armorStand2.remove();

                        Collection<Entity> nearbyEntities = armorStand2.getWorld().getNearbyEntities(armorStand2.getLocation(), 5, 5, 5);
                        for (Entity entity : nearbyEntities) {
                            if (entity instanceof LivingEntity) {
                                if (!(entity.getName().equals(player.getName()))) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    livingEntity.damage(DAMAGE, player);
                                }
                            }
                        }
                    }
                }
                if (armorStand1.isDead() && armorStand2.isDead()) {
                    player.removePotionEffect(PotionEffectType.GLOWING);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    @Override
    public void onLeftClick(Player player, PlayerInteractEvent event) {
        if (playerList.containsKey(player)) {
            playerList.replace(player, true);
            target = player.getTargetBlock(null, 200).getLocation();
        }
    }

    @Override
    public void onPlayerLeftClickLivingEntity(Player player, LivingEntity livingEntity, EntityDamageByEntityEvent event) {

    }

    @Override
    public void onPlayerRightClickLivingEntity(Player player, LivingEntity livingEntity, PlayerInteractEntityEvent event) {
        //Initialize the armor stand
        final ArmorStand armorStand1 = player.getWorld().spawn(livingEntity.getLocation().clone().add(0.5, 0, 0.5), ArmorStand.class);

        armorStand1.setVisible(false);
        armorStand1.addPassenger(livingEntity);

        if (playerList.containsKey(player)) {
            playerList.replace(player, false);
        } else {
            playerList.put(player, false);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                //If the player has launched their projectile
                if (playerList.containsKey(player)) {
                    if (playerList.get(player)) {

                        Vector vector1 = target.toVector().subtract(armorStand1.getLocation().toVector()).normalize();
                        armorStand1.setVelocity(vector1);

                        if (!armorStand1.getLocation().clone().add(0, -1, 0).getBlock().isPassable()) {
                            armorStand1.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, armorStand1.getLocation(), 5);
                            armorStand1.getWorld().playSound(armorStand1.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
                            armorStand1.remove();

                            livingEntity.damage(DAMAGE, player);

                            playerList.remove(player);
                        }

                    } else {
                        //Get the vector to launch the armor stand
                        double angle = player.getLocation().getYaw() * Math.PI / 180;
                        Location to1 = player.getLocation().clone().add(-3 * Math.cos(angle), 0, -3 * Math.sin(angle));
                        Location armorStandLocation1 = armorStand1.getLocation();
                        Vector vector1 = to1.subtract(armorStandLocation1).toVector().normalize();
                        armorStand1.setVelocity(vector1);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1, false, false, false));
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1, false, false, false));
                    }
                } else {
                    Vector vector1 = target.toVector().subtract(armorStand1.getLocation().toVector());
                    if (vector1.length() > 1) {
                        vector1.normalize();
                    }
                    armorStand1.setVelocity(vector1);

                    if (!armorStand1.getLocation().clone().add(0, -1, 0).getBlock().isPassable()) {
                        armorStand1.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, armorStand1.getLocation(), 5);
                        armorStand1.getWorld().playSound(armorStand1.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
                        armorStand1.remove();

                        Collection<Entity> nearbyEntities = armorStand1.getWorld().getNearbyEntities(armorStand1.getLocation(), 5, 5, 5);
                        for (Entity entity : nearbyEntities) {
                            if (entity instanceof LivingEntity) {
                                if (!(entity.getName().equals(player.getName()))) {
                                    LivingEntity livingEntity = (LivingEntity) entity;
                                    livingEntity.damage(DAMAGE, player);
                                }
                            }
                        }
                        playerList.remove(player);
                    }

                }
                if (armorStand1.isDead()) {
                    player.removePotionEffect(PotionEffectType.GLOWING);
                    livingEntity.removePotionEffect(PotionEffectType.GLOWING);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 5);
    }
}