package me.Bright.items;

import me.Bright.items.mainStuff.ItemCore;
import me.Bright.items.mainStuff.ItemRarity;
import me.Bright.items.mainStuff.MouseClick;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class InstaKillStick extends ItemCore {

    public InstaKillStick() {
        super(Material.STICK,
                "&4&lInsta Kill Stick",
                ItemRarity.DIVINE,
                "It does what it says.",
                MouseClick.LEFT_CLICK,
                "One Tap",
                "One taps anything!");
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {

    }

    @Override
    public void onLeftClick(Player player, PlayerInteractEvent event) {

    }

    @Override
    public void onPlayerLeftClickLivingEntity(Player player, LivingEntity livingEntity, EntityDamageByEntityEvent event) {
        livingEntity.setHealth(0);
    }

    @Override
    public void onPlayerRightClickLivingEntity(Player player, LivingEntity livingEntity, PlayerInteractEntityEvent event) {

    }
}