package me.Bright.items.kit.drStrange;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DSEnergyBlast extends ItemBuilder {
    public DSEnergyBlast() {
        super(Material.DANDELION, "Energy Blast", MouseClick.RIGHT_CLICK, "Eldritch Energy");
        setCooldown(0);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        final Location playerLocation = player.getEyeLocation();
        final Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(217, 217, 13), 2);
        final World world = player.getWorld();
        new BukkitRunnable() {
            Vector nextLocation = playerLocation.toVector().clone();
            int i = 0;

            @Override
            public void run() {
                if (i >= 500 || !world.getBlockAt(nextLocation.toLocation(world)).isPassable()) {
                    cancel();
                }
                world.spawnParticle(Particle.REDSTONE,
                        nextLocation.getX(),
                        nextLocation.getY(),
                        nextLocation.getZ(),
                        0, 0, 0, 0, dust);
                nextLocation = nextLocation.add(playerLocation.getDirection().normalize().multiply(0.75));
                i++;
                for (Entity entity : world.getNearbyEntities(nextLocation.toLocation(world), 1, 1, 1)) {
                    if (entity instanceof LivingEntity) {
                        if (!(entity.getName().equals(player.getName()))) {
                            ((LivingEntity) entity).damage(50, player);
                            cancel();
                            break;
                        }
                    }
                }
            }
        }.runTaskTimer(Utils.plugin, 0, 1);

    }

    @Override
    public void onLeftClick(Player player, PlayerInteractEvent event) {

    }

    @Override
    public void onPlayerLeftClickLivingEntity(Player player, LivingEntity livingEntity, EntityDamageByEntityEvent event) {

    }

    @Override
    public void onPlayerRightClickLivingEntity(Player player, LivingEntity livingEntity, PlayerInteractEntityEvent event) {

    }
}
