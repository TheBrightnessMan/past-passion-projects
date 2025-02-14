package me.Bright.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class DoubleJump implements Listener {

    public static ArrayList<Player> allowDoubleJump = new ArrayList<>();
    public static ArrayList<Player> onCD = new ArrayList<>();

    @EventHandler
    public void onFlightAttempt(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (onCD.contains(player)) {
            return;
        }
        if (allowDoubleJump.contains(player)) {
            event.setCancelled(true);
            Vector vector = player.getLocation().getDirection().normalize().setY(0.5);
            player.setVelocity(vector);
            callCooldown(player);
        }
    }

    public void callCooldown(Player player) {
        onCD.add(player);
        new CountDown(2) {
            @Override
            public void run() {
                onCD.remove(player);
            }
        };
    }
}
