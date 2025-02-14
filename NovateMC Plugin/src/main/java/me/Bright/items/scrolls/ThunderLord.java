package me.Bright.items.scrolls;

import me.Bright.items.mainStuff.ItemCore;
import me.Bright.items.mainStuff.ItemRarity;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.main.MainClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ThunderLord extends ItemCore {

    ItemMeta itemMeta = getItemMeta();
    ArrayList<Player> playerList = new ArrayList<>();
    private final int THUNDER_COUNT = 10;

    public ThunderLord() {
        super(Material.PAPER,
                "&6&lScroll of Thunderlord",
                ItemRarity.EPIC,
                "Be the controller of Lightning",
                MouseClick.RIGHT_CLICK,
                "Thunderlord",
                "Spawns a row of lightning in front of you");
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        double angle = player.getLocation().getYaw() * Math.PI / 180;

        Block blockToSmite = player.getLocation().clone().add(-3 * Math.sin(angle), 0, 3 * Math.cos(angle)).getBlock();
        while (blockToSmite.isPassable()) {
            blockToSmite = blockToSmite.getRelative(BlockFace.DOWN);
        }

        ArmorStand armorStand = player.getWorld().spawn(blockToSmite.getLocation(), ArmorStand.class);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        player.getWorld().strikeLightning(armorStand.getLocation());

        new BukkitRunnable() {
            int i = 1;

            @Override
            public void run() {
                if (i <= THUNDER_COUNT) {
                    Location teleport = armorStand.getLocation().add(-Math.sin(angle), 0, Math.cos(angle));
                    while (!teleport.clone().add(0, 1, 0).getBlock().isPassable()) {
                        teleport.add(0, 1, 0);
                        if (teleport.getY() >= 130) {
                            break;
                        }
                    }
                    while (teleport.clone().add(0, -1, 0).getBlock().isPassable()) {
                        teleport.add(0, -1, 0);
                    }
                    armorStand.teleport(teleport);
                    player.getWorld().strikeLightning(armorStand.getLocation());
                    i++;
                } else {
                    armorStand.remove();
                    playerList.remove(player);
                    cancel();
                }
            }
        }.runTaskTimer(MainClass.getProvidingPlugin(MainClass.class), 0, 1);
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
