package me.Bright.item;

import me.Bright.main.MainClass;
import me.Bright.main.SkullCreator;
import me.Bright.main.Utils;
import me.Bright.mob.BrightSkeleton;
import me.Bright.mob.BrightZombie;
import me.Bright.mob.core.brightMob.BrightMob;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReaperScythe extends BrightItem implements Listener {

    private static HashMap<Player, List<BrightMob<?>>> playerSpawnedMap = new HashMap<>();

    public ReaperScythe() {
        super(Material.DIAMOND_HOE, "&4&lReaper's Scythe");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Utils.colorCodes("&6Collected Souls:"));
        lore.add(Utils.colorCodes("0"));
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setLore(lore);
        setItemMeta(itemMeta);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        if (playerSpawnedMap.containsKey(player)) {
            return;
        }
        ItemStack holding = event.getItem();
        if (player.isSneaking()) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(Utils.colorCodes("&6Collected Souls: &8(Max 3 souls)"));
            lore.add(Utils.colorCodes("0"));
            ItemMeta holdingMeta = holding.getItemMeta();
            holdingMeta.setLore(lore);
            holding.setItemMeta(holdingMeta);
        } else {
            List<BrightMob<?>> minions = new ArrayList<>();
            try {
                for (int i = 1; i <= Integer.parseInt(holding.getItemMeta().getLore().get(1)); i++) {
                    BrightMob<?> zombie = new BrightZombie(player);
                    minions.add(zombie);
                }
                playerSpawnedMap.put(player, minions);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLeftClick(Player player, PlayerInteractEvent event) {
        if (player.isSneaking()) {
            if (playerSpawnedMap.containsKey(player)) {
                for (BrightMob mob : playerSpawnedMap.get(player)) {
                    mob.killEntity();
                }
                playerSpawnedMap.remove(player);
            }
        }
    }

    @Override
    public void onPlayerLeftClickLivingEntity(Player player, LivingEntity livingEntity, EntityDamageByEntityEvent event) {
        livingEntity.setHealth(0);
        if (!(livingEntity instanceof Monster)) {
            return;
        }
        ItemStack soul = SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/596a4e285dc7da11dacb87f745951a79c9fe27661351e9c6f67452f4a51e7495");
        ArmorStand armorStand = livingEntity.getWorld().spawn(livingEntity.getLocation().add(0, -1.25, 0), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.getEquipment().setHelmet(soul);
        armorStand.setCustomName(livingEntity.getType().name());
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if (!(damager instanceof Player)) {
            return;
        }
        if (!(entity instanceof LivingEntity)) {
            return;
        }

        Player player = (Player) damager;

        if (playerSpawnedMap.containsKey(player)) {
            for (BrightMob mob : playerSpawnedMap.get(player)) {
                mob.setTarget((LivingEntity) entity);
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (entity.isDead()) {
                        for (BrightMob mob : playerSpawnedMap.get(player)) {
                            mob.setTarget(player);
                        }
                        cancel();
                    }
                }
            }.runTaskTimer(MainClass.getProvidingPlugin(MainClass.class), 0, 5);
        }

    }

    @EventHandler
    public void onRightClickEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (!(entity instanceof ArmorStand)) {
            return;
        }
        if (entity.getCustomName() == null) {
            return;
        }
        try {
            ItemStack holding = player.getInventory().getItemInMainHand();
            if (!holding.getItemMeta().getDisplayName().contains("Reaper's Scythe")) {
                return;
            }
            List<String> lore = holding.getItemMeta().getLore();
            int count = Integer.parseInt(lore.get(1));
            if (count < 3) {
                lore.set(1, String.valueOf(count + 1));
            }
            ItemMeta holdingMeta = holding.getItemMeta();
            holdingMeta.setLore(lore);
            holding.setItemMeta(holdingMeta);
            entity.remove();
        } catch (Exception ignored) {
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (playerSpawnedMap.containsKey(player)) {
            for (BrightMob mob : playerSpawnedMap.get(player)) {
                mob.killEntity();
            }
            playerSpawnedMap.remove(player);
        }
    }

    @Override
    public void onPlayerRightClickLivingEntity(Player player, LivingEntity livingEntity, PlayerInteractEntityEvent event) {

    }
}
