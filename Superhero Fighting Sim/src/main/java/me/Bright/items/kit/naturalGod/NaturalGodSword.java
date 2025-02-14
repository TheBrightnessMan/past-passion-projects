package me.Bright.items.kit.naturalGod;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.main.MainClass;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class NaturalGodSword extends ItemBuilder {

    public NaturalGodSword() {
        super(Material.DIAMOND_SWORD,
                "&aSword of the Natural God",
                MouseClick.RIGHT_CLICK,
                "Homing Fire Blast");
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        Fireball fireball = player.getWorld().spawn(player.getLocation().clone().add(0, 1, 0), Fireball.class);
        Vector from = fireball.getLocation().toVector();
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Vector to = player.getTargetBlock(null, 1000).getLocation().toVector();
                    Vector finalVector = to.subtract(from).normalize().multiply(2);
                    fireball.setVelocity(finalVector);
                    if (fireball.isDead()) {
                        cancel();
                    }
                } catch (NullPointerException e) {
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
