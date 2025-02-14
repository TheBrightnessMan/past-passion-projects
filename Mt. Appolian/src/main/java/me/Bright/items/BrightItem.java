package me.Bright.items;

import me.Bright.main.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class BrightItem extends ItemStack implements Listener {

    private ItemMeta itemMeta = getItemMeta();
    private final String name;

    public BrightItem(Material material, String name, boolean unbreakable) {
        super(material);
        this.name = Utils.colorCodes(name);
        itemMeta.setDisplayName(this.name);
        itemMeta.setUnbreakable(unbreakable);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        setItemMeta(itemMeta);
    }

    @EventHandler
    public void onPlayerInteractByRightClick(PlayerInteractEvent event) throws NullPointerException {
        try {
            Player player = event.getPlayer();
            ItemStack holding = event.getItem();
            if (!holding.getItemMeta().getDisplayName().equals(this.name)) {
                return;
            }
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                onRightClick(player, event);
            }
        } catch (NullPointerException ignored) {
        }
    }

    public abstract void onRightClick(Player player, PlayerInteractEvent event);

    @EventHandler
    public void onPlayerInteractByLeftClick(PlayerInteractEvent event) throws NullPointerException {
        Player player = event.getPlayer();
        try {
            ItemStack holding = event.getItem();
            assert holding != null;
            if (!holding.getItemMeta().getDisplayName().equals(this.name)) {
                return;
            }
            if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                onLeftClick(player, event);
            }
        } catch (NullPointerException ignored) {
        }
    }

    public abstract void onLeftClick(Player player, PlayerInteractEvent event);

    @EventHandler
    public void onPlayerLeftClickLivingEntity(EntityDamageByEntityEvent event) {
        try {
            Entity attacker = event.getDamager();
            Entity gotHit = event.getEntity();
            if (gotHit instanceof LivingEntity && attacker instanceof Player) {
                Player player = (Player) attacker;
                ItemStack holding = player.getInventory().getItemInMainHand();
                if (!holding.getItemMeta().getDisplayName().equals(this.name)) {
                    return;
                }
                onPlayerLeftClickLivingEntity(player, (LivingEntity) gotHit, event);
            }
        } catch (NullPointerException ignored) {
        }
    }

    public abstract void onPlayerLeftClickLivingEntity(Player player, LivingEntity livingEntity, EntityDamageByEntityEvent event);

    @EventHandler
    public void onPlayerRightClickLivingEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        ItemStack holding = player.getInventory().getItemInMainHand();
        if (!holding.getItemMeta().getDisplayName().equals(this.name)) {
            return;
        }
        Entity entity = event.getRightClicked();
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        try {
            onPlayerRightClickLivingEntity(player, (LivingEntity) entity, event);
        } catch (Exception ignored) {
        }
    }

    public abstract void onPlayerRightClickLivingEntity(Player player, LivingEntity livingEntity, PlayerInteractEntityEvent event);

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(this.name)) {
            event.setCancelled(true);
        }
    }
}
