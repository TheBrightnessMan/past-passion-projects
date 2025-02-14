package me.Bright.videoAssignment;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class LightningStaff extends CustomItem {

    public LightningStaff() {
        super(Material.BLAZE_ROD);
        this.setName("Lightning Staff");
        this.setDescription(Arrays.asList("Strike lightning 10 blocks away",
                "with right click"));
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        Location location = player.getTargetBlock(null, 10).getLocation();
        player.getWorld().strikeLightning(location);
    }
}
