package me.Bright.items.kit.brightKit;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.utils.Forcefield;
import me.Bright.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class BrightForceField extends ItemBuilder {

    private ArrayList<Player> playerList = new ArrayList<>();

    public BrightForceField() {
        super(Material.SHIELD,
                "ForceField",
                MouseClick.RIGHT_CLICK,
                "Force Field Generation");
        setCooldown(0);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        double radius = 3;
        Forcefield forcefield = new Forcefield(player, radius, null, playerList);
        if (playerList.contains(player)) {
            playerList.remove(player);
        } else {
            playerList.add(player);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (playerList.contains(player)) {
                    spawnParticle(player, radius);
                }
            }
        }.runTaskTimer(Utils.plugin, 0, 5);

        forcefield.run();
    }

    private void spawnParticle(final Player player, double radius) {
        final Location location = player.getLocation();
        new BukkitRunnable() {
            double alpha = 0;

            @Override
            public void run() {
                alpha += Math.PI / 10;
                for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 40) {
                    double x = radius * Math.cos(theta) * Math.sin(alpha);
                    double y = radius * Math.cos(alpha) + 1.5;
                    double z = radius * Math.sin(theta) * Math.sin(alpha);
                    location.add(x, y, z);
                    location.getWorld().spawnParticle(Particle.DRIP_LAVA, location, 1);
                    location.subtract(x, y, z);
                }
                if (alpha > Math.PI) {
                    cancel();
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
