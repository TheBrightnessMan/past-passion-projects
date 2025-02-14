package me.Bright.items.kit.brightKit;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.utils.Laser;
import me.Bright.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class FocusedLaser extends ItemBuilder {

    private static ArrayList<Player> playerList = new ArrayList<>();

    public FocusedLaser() {
        super(Material.ENDER_EYE,
                "Focused Laser",
                MouseClick.RIGHT_CLICK,
                "Focus Laser");
        setCooldown(0);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        if (playerList.contains(player)) {
            playerList.remove(player);
        } else {
            playerList.add(player);
        }
        try {
            Laser laser1 = new Laser(player.getEyeLocation(), player.getTargetBlock(null, 250).getLocation(), 100000, 250);
            laser1.start(Utils.plugin);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!playerList.contains(player)) {
                        laser1.stop();
                        cancel();
                    }
                    Location location = player.getTargetBlock(null, 250).getLocation();
                    try {
                        laser1.moveStart(player.getEyeLocation());
                        laser1.moveEnd(location);
                        for (Entity entity : location.getWorld().getNearbyEntities(location, 2, 2, 2)) {
                            if (entity instanceof LivingEntity) {
                                ((LivingEntity) entity).damage(50, player);
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
            }.runTaskTimer(Utils.plugin, 0, 1);
        } catch (Exception ignored) {
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
