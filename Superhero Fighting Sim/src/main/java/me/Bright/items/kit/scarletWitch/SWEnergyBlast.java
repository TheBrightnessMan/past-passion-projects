package me.Bright.items.kit.scarletWitch;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.main.MainClass;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SWEnergyBlast extends ItemBuilder {

    private static Player player;

    public SWEnergyBlast() {
        super(Material.RED_TULIP,
                "&c&lPsionic Energy Blast",
                MouseClick.RIGHT_CLICK,
                "Energy Blast");
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        Fireball fireball = player.launchProjectile(Fireball.class);
        fireball.setGlowing(true);
        fireball.setIsIncendiary(false);
        final Vector velocity = player.getLocation().getDirection().multiply(2);
        SWEnergyBlast.player = player;
        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                if (i <= 200) {
                    fireball.setVelocity(velocity);
                } else {
                    fireball.remove();
                }
            }
        }.runTaskTimer(MainClass.getProvidingPlugin(MainClass.class), 0, 5);
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

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        try {
            if (event.getEntity().getName().equals(SWEnergyBlast.player.getName())) {
                return;
            }
            if (event.getDamager() instanceof Fireball) {
                event.setDamage(5);
            }
        } catch (NullPointerException ignored) {
        }
    }
}
