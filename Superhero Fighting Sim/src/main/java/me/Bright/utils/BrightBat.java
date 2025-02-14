package me.Bright.utils;

import org.bukkit.Location;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BrightBat {

    public BrightBat(Player player) {
        Bat bat = player.getWorld().spawn(player.getEyeLocation(), Bat.class);
        bat.setAI(false);
        bat.setInvulnerable(true);
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count >= 20 || bat.isDead()) {
                    bat.remove();
                    cancel();
                }
                Location eyeLocation = player.getEyeLocation().clone();
                Location playerLocation = player.getLocation().clone();
                Vector direction = player.getLocation().getDirection().normalize();
                bat.teleport(new Location(
                        player.getWorld(),
                        eyeLocation.getX() + direction.getX(),
                        eyeLocation.getY() + direction.getY(),
                        eyeLocation.getZ() + direction.getZ() + 1));
//                count++;
            }
        }.runTaskTimer(Utils.plugin, 0, 5);
    }

}
