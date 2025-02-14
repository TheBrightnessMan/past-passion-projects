package me.Bright.videoAssignment;

import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FireStaff extends CustomItem {

    public FireStaff() {
        super(Material.BLAZE_ROD);
        setName("Fire Staff");
        setDescription(Arrays.asList("Shoot fireball with right click"));
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        Fireball fireball = player.getWorld().spawn(player.getLocation(), Fireball.class);
        fireball.setVelocity(player.getLocation().getDirection());
    }
}
