package me.Bright.items.kit.thor;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.main.MainClass;
import me.Bright.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Stormbreaker extends ItemBuilder {
    public Stormbreaker() {
        super(Material.IRON_AXE,
                "&f&lStormbreaker",
                MouseClick.RIGHT_CLICK,
                "Axe Throw");
        setCooldown(0);
    }

    private ItemStack thrown() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.colorCodes("&7&lThrown Stormbreaker"));
        itemMeta.setLore(new ArrayList<String>() {{
            add(Utils.colorCodes("&fYour &7&lStormbreaker &fis thrown!"));
            add(Utils.colorCodes("&fWait for it to come back!"));
        }});
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        player.getInventory().setItemInMainHand(thrown());
        Item axe = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.IRON_AXE));
        axe.setPickupDelay(100);
        final Vector velocity = player.getEyeLocation().getDirection();
        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                if (i >= 2) {
                    Vector axeLocation = axe.getLocation().toVector();
                    Vector playerLocation = player.getEyeLocation().toVector();
                    Vector finalVelocity = playerLocation.subtract(axeLocation).normalize();
                    axe.setVelocity(finalVelocity);
                }
                if (i >= 5) {
                    player.getInventory().setItemInMainHand(Stormbreaker.super.clone());
                    player.getInventory().remove(thrown());
                    axe.remove();
                    cancel();
                }
                if (i <= 2) {
                    axe.setVelocity(velocity);
                }

                for (
                        Entity entity : axe.getNearbyEntities(1, 1, 1)) {
                    if (entity instanceof LivingEntity) {
                        if (!entity.getName().equals(player.getName())) {
                            ((LivingEntity) entity).damage(4, player);
                        }
                    }
                }

                i++;
            }
        }.runTaskTimer(MainClass.getProvidingPlugin(MainClass.class), 0, 5);
    }

    @Override
    public void onLeftClick(Player player, PlayerInteractEvent event) {
        Location target = player.getTargetBlock(null, 100).getLocation().add(0, 1, 0);
        target.getWorld().strikeLightning(target);
    }

    @Override
    public void onPlayerLeftClickLivingEntity(Player player, LivingEntity livingEntity, EntityDamageByEntityEvent event) {

    }

    @Override
    public void onPlayerRightClickLivingEntity(Player player, LivingEntity livingEntity, PlayerInteractEntityEvent event) {

    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING)) {
            event.setDamage(50);
        }
    }
}
