package me.Bright.items.kit.naturalGod;

import me.Bright.items.mainStuff.ItemCore;
import me.Bright.items.mainStuff.ItemRarity;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.main.MainClass;
import me.Bright.utils.CountDown;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class NaturalGodShield extends ItemCore {

    private static ArrayList<Player> forcefieldActive = new ArrayList<>();
    private static ArrayList<Player> onCD = new ArrayList<>();

    public NaturalGodShield() {
        super(Material.SHIELD,
                "&aShield of the Natural God",
                ItemRarity.COMMON,
                "",
                MouseClick.RIGHT_CLICK,
                "Forcefield",
                "Spawns a forcefield around you that would only be on for 5 seconds.");
        setCooldown(0);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        if (onCD.contains(player)) {
            return;
        }
        if (forcefieldActive.contains(player)) {
            deactivateForceField(player);
        } else {
            forcefieldActive.add(player);
        }
        activateForceField(player);
        new CountDown(5) {
            @Override
            public void run() {
                deactivateForceField(player);
            }
        };
    }

    private void deactivateForceField(Player player) {
        forcefieldActive.remove(player);
        onCD.add(player);
        new CountDown(40) {
            @Override
            public void run() {
                onCD.remove(player);
            }
        };
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

    private void activateForceField(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!forcefieldActive.contains(player)) {
                    cancel();
                }
                Particle.DustOptions dust = new Particle.DustOptions(
                        Color.fromRGB(50, 168, 82), 2);

                Location cloneOfPlayerLocation = player.getLocation().clone();
                for (int x = -5; x <= 5; x += 5) {
                    for (int y = -5; y <= 5; y += 5) {
                        for (int z = -5; z <= 5; z += 5) {
                            cloneOfPlayerLocation.getWorld().spawnParticle(Particle.REDSTONE,
                                    cloneOfPlayerLocation.getX() + x,
                                    cloneOfPlayerLocation.getY() + y,
                                    cloneOfPlayerLocation.getZ() + z,
                                    0, 0, 0, 0, dust);
                        }
                    }
                }

                List<Entity> entities = player.getNearbyEntities(5, 5, 5);
                for (Entity entity : entities) {
                    if (entity instanceof Item) {
                        continue;
                    }
                    Vector velocity = entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                    entity.setVelocity(velocity);
                }
            }
        }.runTaskTimer(MainClass.getProvidingPlugin(MainClass.class), 0, 5);

    }
}
