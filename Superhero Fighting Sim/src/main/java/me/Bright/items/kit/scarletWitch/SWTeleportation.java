package me.Bright.items.kit.scarletWitch;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SWTeleportation extends ItemBuilder {
    public SWTeleportation() {
        super(Material.DIAMOND,
                "Teleportation",
                MouseClick.RIGHT_CLICK,
                "Instant Teleportation");
        setCooldown(10);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        Location playerLocation = player.getLocation();
        Location to = player.getTargetBlock(null, 10).getLocation();
        Location teleport = new Location(to.getWorld(), to.getX(), to.getY(), to.getZ(), playerLocation.getYaw(), playerLocation.getPitch());

        player.teleport(teleport, PlayerTeleportEvent.TeleportCause.PLUGIN);
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
