package me.Bright.mob;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class RemoveDrops implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity().getCustomName() == null) {
            return;
        }
        if (event.getEntity().getCustomName().contains("'s")) {
            event.getDrops().clear();
        }
    }
}
