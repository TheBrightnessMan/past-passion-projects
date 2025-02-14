package me.Bright.items;

import me.Bright.main.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ZeusBow extends BrightItem {

    public ZeusBow() {
        super(Material.BOW, "&6Zeus &7Bow", true);
    }

    @EventHandler
    public void onBowShot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (!event.getBow().getItemMeta().hasDisplayName()) {
            return;
        }
        if (!event.getBow().getItemMeta().getDisplayName().equals(this.getItemMeta().getDisplayName())) {
            return;
        }
        Arrow arrow = (Arrow) event.getProjectile();
        arrow.setCustomName("Zeus Arrow");
        arrow.setCustomNameVisible(false);
        Vector vector = event.getEntity().getLocation().getDirection();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (arrow.isInBlock()) {
                    arrow.getWorld().strikeLightning(arrow.getAttachedBlock().getLocation());
                    cancel();
                } else {
                    arrow.setVelocity(vector);
                }
            }
        }.runTaskTimer(MainClass.getProvidingPlugin(MainClass.class), 0, 1);
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntity().getCustomName().equals("Zeus Arrow")) {
            event.getHitEntity().getWorld().strikeLightning(event.getHitEntity().getLocation());
        }
    }


    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {

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
