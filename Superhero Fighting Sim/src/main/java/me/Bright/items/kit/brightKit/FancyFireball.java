package me.Bright.items.kit.brightKit;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.utils.CountDown;
import me.Bright.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FancyFireball extends ItemBuilder {

    public FancyFireball() {
        super(Material.FIRE_CHARGE,
                "Fancy Fireball",
                MouseClick.RIGHT_CLICK,
                "Fireball!!!");
        setCooldown(0);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        new BukkitRunnable() {
            double y = 0;
            double radius = 1;

            @Override
            public void run() {
                Location location = player.getLocation();
                if (y >= 4) {
                    spawnFireballAndLaunch(player);
                    cancel();
                }
                if (radius < 0) {
                    radius = 0.1;
                }
                if (y >= 3.5) {
                    radius -= 0.5;
                }
                double x = radius * Math.cos(y);
                double z = radius * Math.sin(y);
                location.getWorld().spawnParticle(Particle.FLAME, location.clone().add(x, y, z), 0);
                location.getWorld().spawnParticle(Particle.FLAME, location.clone().add(-x, y, -z), 0);
                y += 0.125;
            }
        }.runTaskTimer(Utils.plugin, 0, 1);
    }

    private void spawnFireballAndLaunch(Player player) {
        Fireball fireball = player.getWorld().spawn(player.getLocation().clone().add(0, 3, 0), Fireball.class);
        fireball.setYield(0);
        fireball.setIsIncendiary(false);
        fireball.setCustomName("Fancy Fireball");
        fireball.setCustomNameVisible(false);
        Vector velocity = player.getTargetBlock(null, 200).getLocation().toVector().subtract(fireball.getLocation().toVector());
        new CountDown(2) {
            @Override
            public void run() {
                fireball.setVelocity(velocity);
            }
        };
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        try {
            if (event.getDamager().getCustomName().equals("Fancy Fireball")) {
                event.setDamage(40);
            }
        } catch (NullPointerException ignored) {
        }
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
