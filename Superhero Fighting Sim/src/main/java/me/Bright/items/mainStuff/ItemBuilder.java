package me.Bright.items.mainStuff;

import me.Bright.utils.CountDown;
import me.Bright.utils.Utils;
import org.bukkit.ChatColor;
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

import java.util.ArrayList;

public abstract class ItemBuilder extends ItemStack implements Listener {

    ItemMeta itemMeta = getItemMeta();
    private final String name;
    private static ArrayList<Player> playerOnCD = new ArrayList<>();
    int cooldown = 5;

    /**
     * @param material        Material of the item
     * @param name            Name of the item
     * @param mouseClick      LEFT or RIGHT click to start the ability
     * @param itemAbilityName Name of the ability
     */

    public ItemBuilder(Material material, String name, MouseClick mouseClick, String itemAbilityName) {
        super(material);
        this.name = Utils.colorCodes(name);

        ArrayList<String> lore = new ArrayList<String>() {{
            switch (mouseClick) {
                case LEFT_CLICK:
                    add(Utils.colorCodes("&e&lLeft Click Ability: &6&l" + itemAbilityName));
                    break;
                case RIGHT_CLICK:
                    add(Utils.colorCodes("&e&lRight Click Ability: &6&l" + itemAbilityName));
                    break;
            }
            add(ChatColor.DARK_GRAY + "Cool Down: " + cooldown);
        }};

        itemMeta.setDisplayName(this.name);
        itemMeta.setLore(lore);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        setItemMeta(itemMeta);
    }


    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    private void callCooldown(Player player) {
        playerOnCD.add(player);
        new CountDown(cooldown) {
            @Override
            public void run() {
                playerOnCD.remove(player);
            }
        };
    }

    @EventHandler
    public void onPlayerInteractByRightClick(PlayerInteractEvent event) throws NullPointerException {
        try {
            Player player = event.getPlayer();
            if (playerOnCD.contains(player)) {
                return;
            }
            ItemStack holding = event.getItem();
            assert holding != null;
            if (!holding.getItemMeta().getDisplayName().equals(this.name)) {
                return;
            }
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                onRightClick(player, event);
                callCooldown(player);
            }
        } catch (NullPointerException ignored) {
        }
    }

    public abstract void onRightClick(Player player, PlayerInteractEvent event);

    @EventHandler
    public void onPlayerInteractByLeftClick(PlayerInteractEvent event) throws NullPointerException {
        Player player = event.getPlayer();
        if (playerOnCD.contains(player)) {
            return;
        }
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
        if (playerOnCD.contains(player)) {
            return;
        }

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
        callCooldown(player);
    }

    public abstract void onPlayerRightClickLivingEntity(Player player, LivingEntity livingEntity, PlayerInteractEntityEvent event);

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(this.name)) {
            event.setCancelled(true);
        }
    }
}