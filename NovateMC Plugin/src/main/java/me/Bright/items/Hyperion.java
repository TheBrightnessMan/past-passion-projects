package me.Bright.items;

import me.Bright.items.mainStuff.ItemCore;
import me.Bright.items.mainStuff.ItemRarity;
import me.Bright.items.mainStuff.MouseClick;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Collection;

public class Hyperion extends ItemCore {
    public Hyperion() {
        super(Material.IRON_SWORD,
                "Hyperion",
                ItemRarity.MYTHIC,
                "Hyperion, what more do you want?",
                MouseClick.RIGHT_CLICK,
                "Wither Impact",
                "You know what this does");
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        Location location = player.getTargetBlock(null, 10).getLocation().add(0, 1, 0);
        Location destination = new Location(player.getWorld(), location.getX() + 0.5, location.getY(), location.getZ() + 0.5, player.getLocation().getYaw(), player.getLocation().getPitch());
        player.teleport(destination);
        Collection<Entity> nearbyEntities = destination.getWorld().getNearbyEntities(destination, 10, 10, 10);
        player.setAbsorptionAmount(50);
        for (Entity entity : nearbyEntities) {
            if (!(entity instanceof LivingEntity)) {
                continue;
            }
            if (entity instanceof Player) {
                if (entity.getName().equals(player.getName())) {
                    continue;
                }
            }
            ((LivingEntity) entity).damage(50, player);
        }
        destination.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, destination.add(0, 0.5, 0), 5);
        destination.getWorld().playSound(destination, Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
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
