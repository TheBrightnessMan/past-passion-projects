package me.Bright.items.kit.brightKit;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ShockWave extends ItemBuilder {

    public ShockWave() {
        super(Material.NETHERITE_AXE,
                "Shock Wave",
                MouseClick.RIGHT_CLICK,
                "Shock Wave");
        setCooldown(1);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        final Vector direction = player.getLocation().getDirection().normalize().setY(0);

        double angle = player.getLocation().getYaw() * Math.PI / 180;

        new BukkitRunnable() {
            int timer = 0;

            @Override
            public void run() {
                Location location1 = player.getLocation().clone().add(direction.clone().multiply(timer + 2));
                Location location2 = player.getLocation().clone().add(direction.clone().multiply(timer + 2)).add(-Math.cos(angle), 0, -Math.sin(angle));
                Location location3 = player.getLocation().clone().add(direction.clone().multiply(timer + 2)).add(Math.cos(angle), 0, Math.sin(angle));

                World world = player.getWorld();

                Block block1 = location1.getBlock();
                while (block1.isPassable()) {
                    block1 = block1.getRelative(BlockFace.DOWN);
                }
                FallingBlock fallingBlock1 = world.spawnFallingBlock(location1.clone().add(0, player.getEyeHeight(), 0), block1.getBlockData());
                fallingBlock1.setCustomName("ShockwaveBlock");
                fallingBlock1.setDropItem(false);
                for (Entity entity : location1.getWorld().getNearbyEntities(location1, 3, 2, 3)) {
                    if (entity.getName().equals(player.getName())) {
                        continue;
                    }
                    if (entity instanceof LivingEntity) {
                        ((LivingEntity) entity).damage(5, player);
                    }
                }

                Block block2 = location1.getBlock();
                while (block2.isPassable()) {
                    block2 = block2.getRelative(BlockFace.DOWN);
                }
                FallingBlock fallingBlock2 = world.spawnFallingBlock(location2.clone().add(0, player.getEyeHeight(), 0), block2.getBlockData());
                fallingBlock2.setCustomName("ShockwaveBlock");
                fallingBlock2.setDropItem(false);

                Block block3 = location1.getBlock();
                while (block3.isPassable()) {
                    block3 = block3.getRelative(BlockFace.DOWN);
                }
                FallingBlock fallingBlock3 = world.spawnFallingBlock(location3.clone().add(0, player.getEyeHeight(), 0), block3.getBlockData());
                fallingBlock3.setCustomName("ShockwaveBlock");
                fallingBlock3.setDropItem(false);

                timer++;
                if (timer >= 5) {
                    cancel();
                }
            }
        }.runTaskTimer(Utils.plugin, 0, 5);
    }

    @EventHandler
    public void onBlockFall(EntityChangeBlockEvent event) {
        if ((event.getEntityType() == EntityType.FALLING_BLOCK)) {
            FallingBlock fallingBlock = (FallingBlock) event.getEntity();
            if (fallingBlock.getName().equals("ShockwaveBlock")) {
                fallingBlock.remove();
                event.setCancelled(true);
            }
        }

    }

    public void spawnParticles(Player player) {
        new BukkitRunnable() {
            double t = Math.PI / 4;
            Location loc = player.getLocation();
            World world = loc.getWorld();

            public void run() {
                t = t + 0.1 * Math.PI;
                for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
                    double x = t * Math.cos(theta);
                    double y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
                    double z = t * Math.sin(theta);
                    loc.add(x, y, z);
                    world.spawnParticle(Particle.FIREWORKS_SPARK, loc, 1);
                    for (Entity nearbyEntity : world.getNearbyEntities(loc, 2, 2, 2)) {
                        if (!(nearbyEntity instanceof LivingEntity) || nearbyEntity.getName().equals(player.getName())) {
                            continue;
                        }
                        ((LivingEntity) nearbyEntity).damage(30, player);
                    }
                    loc.subtract(x, y, z);

                    theta = theta + Math.PI / 64;

                    x = t * Math.cos(theta);
                    y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
                    z = t * Math.sin(theta);
                    world.spawnParticle(Particle.SPELL_WITCH, loc.clone().add(x, y, z), 1);

                }
                if (t > 10) {
                    this.cancel();
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
