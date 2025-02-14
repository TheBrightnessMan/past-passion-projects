package me.Bright.items.kit.ironMan;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.main.MainClass;
import me.Bright.utils.Utils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class IronManBeam extends ItemBuilder {

    private static ArrayList<Player> unibeamOn = new ArrayList<>();

    public IronManBeam() {
        super(Material.YELLOW_DYE,
                "Particle Weapon",
                MouseClick.RIGHT_CLICK,
                "Unibeam");
        setCooldown(0);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        if (unibeamOn.contains(player)) {
            unibeamOn.remove(player);
        } else {
            unibeamOn.add(player);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (unibeamOn.contains(player)) {
                    Location playerLocation = player.getEyeLocation();
                    Location target = player.getTargetBlock(null, 25).getLocation();
                    Utils.shootBeam(player, playerLocation, target, Color.YELLOW, 100, 3);
                } else {
                    cancel();
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
}
