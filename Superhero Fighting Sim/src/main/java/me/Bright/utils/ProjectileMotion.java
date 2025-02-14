package me.Bright.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public abstract class ProjectileMotion {

    /**
     * @param entity The entity to send into projectile motion
     * @param to     Where to land
     */

    public ProjectileMotion(Entity entity, Location to) {
        final Vector entityLocationToTargetVector = to.toVector().subtract(entity.getLocation().toVector());
        final Vector divideBy5 = entityLocationToTargetVector.normalize().multiply(entity.getLocation().distance(to) / 17);
        final int[] theYs = {2, 1, -1, -2, entityLocationToTargetVector.getBlockY()};

        final ArmorStand armorStand = entity.getWorld().spawn(entity.getLocation(), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.addPassenger(entity);

        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                if (count >= 4) {
                    armorStand.remove();
                    executeAfterLanding(entity.getLocation());
                    cancel();
                }
                armorStand.setVelocity(divideBy5.setY(theYs[count]));
                count++;
            }
        }.runTaskTimer(Utils.plugin, 0, 5);

    }

    public abstract void executeAfterLanding(Location landing);
}
