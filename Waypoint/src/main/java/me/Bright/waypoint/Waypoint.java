package me.Bright.waypoint;

import me.Bright.main.BrightPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class Waypoint {

    private final Plugin plugin = BrightPlugin.getProvidingPlugin(BrightPlugin.class);
    private final Location to;
    private static boolean enabled = false;
    private ArmorStand waypoint;

    public Waypoint(Location to) {
        this.to = to;
    }

    public void enable(Player player) {
        if (player.getWorld() != to.getWorld()) {
            return;
        }
        enabled = true;
        this.waypoint = player.getLocation().getWorld().spawn(player.getLocation(), ArmorStand.class);
        waypoint.setVisible(false);
        waypoint.setCustomNameVisible(true);
        waypoint.setMarker(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (enabled) {
                    Vector vector = to.toVector().clone().subtract(player.getLocation().toVector().clone()).normalize().multiply(2);
                    Location waypointLocation = player.getLocation().clone().add(vector);
                    waypoint.teleport(waypointLocation);
                    waypoint.setCustomName((int) player.getLocation().distance(to) + "m");
                } else {
                    cancel();
                }

                if (player.getLocation().distance(to) <= 2) {
                    disable();
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    public void disable() {
        enabled = false;
        this.waypoint.remove();
    }
}
