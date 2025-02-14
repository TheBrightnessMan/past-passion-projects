package me.Bright.utils;

import me.Bright.main.MainClass;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Forcefield {

    private static ArrayList<Player> list;
    private final Player player;
    private final double forcefieldSize;
    private final Color color;
    private static boolean active = false;

    /**
     * @param player         Player to start the forcefield
     * @param forcefieldSize Size of the forcefield
     * @param color          Color for forcefield particles, null for
     * @param list           List of players who has their forcefield toggled
     */

    public Forcefield(Player player, double forcefieldSize, @Nullable Color color, ArrayList<Player> list) {
        this.player = player;
        this.forcefieldSize = forcefieldSize;
        this.color = color;
        Forcefield.list = list;
    }

    public boolean setActive(boolean active) {
        Forcefield.active = active;
        return active;
    }

    public boolean toggle() {
        active = !active;
        return active;
    }

    public void run() {
        if (color != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (active) {
                        if (!list.contains(player)) {
                            cancel();
                        }
                        Particle.DustOptions dust = new Particle.DustOptions(color, 2);
                        Location cloneOfPlayerLocation = player.getLocation().clone();
                        for (double x = -forcefieldSize; x <= forcefieldSize; x += forcefieldSize) {
                            for (double y = -forcefieldSize; y <= forcefieldSize; y += forcefieldSize) {
                                for (double z = -forcefieldSize; z <= forcefieldSize; z += forcefieldSize) {
                                    cloneOfPlayerLocation.getWorld().spawnParticle(Particle.REDSTONE,
                                            cloneOfPlayerLocation.getX() + x,
                                            cloneOfPlayerLocation.getY() + y,
                                            cloneOfPlayerLocation.getZ() + z,
                                            0, 0, 0, 0, dust);
                                }
                            }
                        }

                        List<Entity> entities = player.getNearbyEntities(forcefieldSize, forcefieldSize, forcefieldSize);
                        for (Entity entity : entities) {
                            if (entity instanceof Item) {
                                continue;
                            }
                            Vector velocity = entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                            entity.setVelocity(velocity);
                        }
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(MainClass.getProvidingPlugin(MainClass.class), 0, 5);
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!list.contains(player)) {
                        cancel();
                    }
                    List<Entity> entities = player.getNearbyEntities(forcefieldSize, forcefieldSize, forcefieldSize);
                    for (Entity entity : entities) {
                        if (entity instanceof Item) {
                            continue;
                        }
                        Vector velocity = entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                        entity.setVelocity(velocity);
                    }
                }
            }.runTaskTimer(MainClass.getProvidingPlugin(MainClass.class), 0, 5);
        }
    }
}