package me.Bright.items;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class ShadowFury extends ItemBuilder {
    public ShadowFury() {
        super(Material.DIAMOND_SWORD, "&4&lShadow Fury", MouseClick.RIGHT_CLICK, "Shadow Step");
        setCooldown(0);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        try {
            LivingEntity target = Utils.getNearestLivingEntity(player.getLocation(), new EntityType[]{EntityType.BAT}, new Entity[]{player});
            player.teleport(target.getLocation().add(target.getLocation().getDirection().multiply(-1)), PlayerTeleportEvent.TeleportCause.PLUGIN);
        } catch (NullPointerException e) {
            player.sendMessage(ChatColor.RED + "No Target Found!");
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
