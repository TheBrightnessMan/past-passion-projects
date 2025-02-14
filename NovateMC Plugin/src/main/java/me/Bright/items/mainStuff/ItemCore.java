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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public abstract class ItemCore extends ItemStack implements Listener {

    ItemMeta itemMeta = getItemMeta();
    private final String name;
    private static ArrayList<Player> playerOnCD = new ArrayList<>();
    int cooldown = 0;

    public ItemCore(Material material, String name, ItemRarity rarity, String itemLore, MouseClick mouseClick,
                    String itemAbilityName, String itemAbility) {
        super(material);
        this.name = Utils.colorCodes(name);

        ArrayList<String> lore = new ArrayList<String>() {{
            add(Utils.colorCodes("&f" + itemLore));
            add("");
            switch (mouseClick) {
                case LEFT_CLICK:
                    add(Utils.colorCodes("&e&lLeft Click Ability: &6&l" + itemAbilityName));
                    break;
                case RIGHT_CLICK:
                    add(Utils.colorCodes("&e&lRight Click Ability: &6&l" + itemAbilityName));
                    break;
            }
            add(Utils.colorCodes("&f" + itemAbility));
            add(ChatColor.DARK_GRAY + "Cool Down: " + cooldown);
            add("");
            add(rarity.getRarity());
        }};

        itemMeta.setDisplayName(this.name);
        itemMeta.setLore(lore);
        setItemMeta(itemMeta);
    }

    public ItemCore(Material material, String name, ItemRarity rarity, String itemLore, String leftClickItemAbilityName,
                    String leftClickItemAbility, String rightCLickItemAbilityName, String rightClickItemAbility) {
        super(material);
        this.name = Utils.colorCodes(name);

        ArrayList<String> lore = new ArrayList<String>() {{
            add(Utils.colorCodes("&7" + itemLore));
            add("");
            add(Utils.colorCodes("&e&lLeft Click Ability: &6&l" + leftClickItemAbilityName));
            add(Utils.colorCodes("&7" + leftClickItemAbility));
            add("");
            add(Utils.colorCodes("&e&lRight Click Ability: &6&l" + rightCLickItemAbilityName));
            add(Utils.colorCodes("&7" + rightClickItemAbility));
            add("");
            add(rarity.getRarity());
        }};

        itemMeta.setDisplayName(this.name);
        itemMeta.setLore(lore);
        setItemMeta(itemMeta);
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public boolean onCoolDown(Player player) {
        return playerOnCD.contains(player);
    }

    public void callCooldown(Player player) {
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
        Player player = event.getPlayer();
        if (onCoolDown(player)) {
            return;
        }
        if (event.getItem() == null) {
            return;
        }
        ItemStack holding = event.getItem();
        if (holding.getItemMeta().getDisplayName() == null) {
            return;
        }
        if (!holding.getItemMeta().getDisplayName().equals(this.name)) {
            return;
        }
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            onRightClick(player, event);
            callCooldown(player);
        }
    }

    public abstract void onRightClick(Player player, PlayerInteractEvent event);

    @EventHandler
    public void onPlayerInteractByLeftClick(PlayerInteractEvent event) throws NullPointerException {
        Player player = event.getPlayer();
        if (onCoolDown(player)) {
            return;
        }
        if (event.getItem() == null) {
            return;
        }
        ItemStack holding = event.getItem();
        if (holding.getItemMeta().getDisplayName() == null) {
            return;
        }
        if (!holding.getItemMeta().getDisplayName().equals(this.name)) {
            return;
        }
        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            onLeftClick(player, event);
            callCooldown(player);
        }
    }

    public abstract void onLeftClick(Player player, PlayerInteractEvent event);

    @EventHandler
    public void onPlayerLeftClickLivingEntity(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        Entity gotHit = event.getEntity();
        if (gotHit instanceof LivingEntity && attacker instanceof Player) {
            try {
                Player player = (Player) attacker;
                if (player.getInventory().getItemInMainHand() == null) {
                    return;
                }
                ItemStack holding = player.getInventory().getItemInMainHand();
                if (holding.getItemMeta().getDisplayName() == null) {
                    return;
                }
                if (!holding.getItemMeta().getDisplayName().equals(this.name)) {
                    return;
                }
                onPlayerLeftClickLivingEntity(player, (LivingEntity) gotHit, event);
            } catch (NullPointerException ignored) {
            }
        }
    }

    public abstract void onPlayerLeftClickLivingEntity(Player player, LivingEntity livingEntity, EntityDamageByEntityEvent event);

    @EventHandler
    public void onPlayerRightClickLivingEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (onCoolDown(player)) {
            return;
        }
        if (player.getInventory().getItemInMainHand() == null) {
            return;
        }
        ItemStack holding = player.getInventory().getItemInMainHand();
        if (holding.getItemMeta().getDisplayName() == null) {
            return;
        }
        if (!holding.getItemMeta().getDisplayName().equals(this.name)) {
            return;
        }
        Entity entity = event.getRightClicked();
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        onPlayerRightClickLivingEntity(player, (LivingEntity) entity, event);
        callCooldown(player);
    }

    public abstract void onPlayerRightClickLivingEntity(Player player, LivingEntity livingEntity, PlayerInteractEntityEvent event);
}