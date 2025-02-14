package me.Bright.main;

import me.Bright.mob.wither.NecromancerWither;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (NecromancerWither.playerList.containsKey(player)) {
            NecromancerWither.playerList.get(player).killEntity();
            NecromancerWither.playerList.remove(player);
        }
    }

}
