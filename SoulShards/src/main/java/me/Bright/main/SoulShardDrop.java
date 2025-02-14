package me.Bright.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class SoulShardDrop implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        ItemStack soulShard = new SoulShardItem();
        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), soulShard);
    }
}
