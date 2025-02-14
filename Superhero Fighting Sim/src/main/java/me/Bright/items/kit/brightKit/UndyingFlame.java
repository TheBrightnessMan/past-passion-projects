package me.Bright.items.kit.brightKit;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class UndyingFlame extends ItemBuilder {
    public UndyingFlame() {
        super(Material.BLAZE_POWDER,
                "&c&lFlame of &4&lUndying",
                MouseClick.RIGHT_CLICK,
                "Undying Flame");
        setCooldown(0);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        flameHelix(player, player.getEyeLocation(), player.getTargetBlock(null, 200).getLocation(), 1.5);
    }

    private void flameHelix(Player caster, final Location start, final Location end, final double R) {
        final Vector vector = end.toVector().subtract(start.toVector()).normalize();
        new BukkitRunnable() {
            double t = 0;
            double k1 = vector.getX();
            double k2 = vector.getY();
            double k3 = vector.getZ();

            @Override
            public void run() {
                double x = k1 * t;
                double y = k2 * (t + R * Math.sin(t));
                double z = k3 * (t + R * Math.cos(t));
                start.getWorld().spawnParticle(Particle.FLAME, start.clone().add(x, y, z), 0);
                for (Entity entity : start.getWorld().getNearbyEntities(start.clone().add(x, y, z), 1, 1, 1)) {
                    if (entity.getName().equals(caster.getName())) {
                        continue;
                    }
                    if (entity instanceof LivingEntity) {
                        entity.setFireTicks(500);
                    }
                    cancel();
                }

                t++;
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
