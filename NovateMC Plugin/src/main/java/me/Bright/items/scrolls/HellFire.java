package me.Bright.items.scrolls;

import me.Bright.items.mainStuff.ItemCore;
import me.Bright.items.mainStuff.ItemRarity;
import me.Bright.items.mainStuff.MouseClick;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class HellFire extends ItemCore {

    public HellFire() {
        super(Material.PAPER,
                "&cScroll of Hell Fire",
                ItemRarity.RARE,
                "itemLore",
                MouseClick.RIGHT_CLICK,
                "Hell Fire",
                "Rains fireball upon your foes");
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        Block targetBlock = player.getTargetBlock(null, 200);
        if (targetBlock.isPassable()) {
            return;
        }
        Location targetLocation = targetBlock.getLocation().clone().add(0, 5, 0);
        for (int x = -1; x < 1; x++) {
            for (int z = -1; z < 1; z++) {
                targetLocation.add(x, 0, z);
                Fireball fireball = targetLocation.getWorld().spawn(targetLocation, Fireball.class);
                fireball.setVelocity(new Vector(0, -1, 0));
            }
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
