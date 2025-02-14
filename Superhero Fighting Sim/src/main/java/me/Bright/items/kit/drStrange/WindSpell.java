package me.Bright.items.kit.drStrange;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WindSpell extends ItemBuilder {
    public WindSpell() {
        super(Material.PAPER,
                "&6&lWind Spell",
                MouseClick.RIGHT_CLICK,
                "Wind Conjuring");
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
            if (player.hasLineOfSight(entity)) {
                entity.setVelocity(player.getLocation().getDirection().normalize().multiply(2));
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
