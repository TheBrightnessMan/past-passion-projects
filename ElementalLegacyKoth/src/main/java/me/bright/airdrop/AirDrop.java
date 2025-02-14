package me.bright.airdrop;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class AirDrop implements Listener {

    public static final String TIME_PATH = "airdropTime";
    public static final String REWARDS_PATH = "airdropRewards";
    private static AirDrop instance;
    private final Component name = Component.text("Airdrop");

    private AirDrop() {
        instance = this;
    }

    public static AirDrop getInstance() {
        return instance == null ? new AirDrop() : instance;
    }

    public void dropRandom() {
        // TODO: Implement airdrops
    }

    public void dropRandom(int n) {
        for (int i = 0; i < n; i++) {
            dropRandom();
        }
    }

    public void dropOnPlayer(Player player) {
        dropAtLocation(player.getLocation());
    }

    public void dropAtLocation(Location location) {
        FallingBlock fallingChest =
                location.getWorld().spawnFallingBlock(
                        location.clone().add(0, 20, 0),
                        Material.CHEST.createBlockData()
                );
        fallingChest.setHurtEntities(false);
        fallingChest.setDropItem(false);
        fallingChest.customName(this.name);
    }

    @EventHandler
    public void onFallingBlockLand(EntityChangeBlockEvent event) {
        if (event.getEntity().customName() == null) {
            return;
        }
        if (!event.getEntity().customName().equals(this.name)) {
            return;
        }
        if (!(event.getBlock() instanceof Chest chest)) {
            return;
        }
        chest.customName(this.name);
    }

}