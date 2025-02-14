package me.Bright.items.scrolls;

import me.Bright.items.mainStuff.ItemCore;
import me.Bright.items.mainStuff.ItemRarity;
import me.Bright.mob.wither.NecromancerWither;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MasterNecromancer extends ItemCore {

    ItemMeta itemMeta = getItemMeta();
    private static ArrayList<Player> playerList = new ArrayList<>();
    private static NecromancerWither wither;

    public MasterNecromancer() {
        super(Material.PAPER,
                "&4&lMaster Scroll of Necromancy",
                ItemRarity.SUPERSTITIOUS,
                "Only the masters can control him...",
                "Despawn Wither",
                "Despawns your wither friend",
                "Summon Wither",
                "Summons your wither friend");
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        if (playerList.contains(player)) {
            return;
        }
        wither = new NecromancerWither(player);
        WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
        world.addEntity(wither);
        playerList.add(player);
    }

    @Override
    public void onLeftClick(Player player, PlayerInteractEvent event) {
        if (playerList.contains(player)) {
            playerList.remove(player);
            NecromancerWither.playerList.remove(player, wither);
            wither.killEntity();
        }
    }

    @Override
    public void onPlayerLeftClickLivingEntity(Player player, LivingEntity livingEntity, EntityDamageByEntityEvent event) {

    }

    @Override
    public void onPlayerRightClickLivingEntity(Player player, LivingEntity livingEntity, PlayerInteractEntityEvent event) {

    }

    @EventHandler
    public void onGettingAttacked(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        if (NecromancerWither.playerList.containsKey(player)) {
            NecromancerWither.playerList.get(player).attack(damager);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if (!(damager instanceof Player)) {
            return;
        }
        Player player = (Player) damager;
        if (playerList.contains(player)) {
            NecromancerWither.playerList.get(player).attack(entity);
        }
    }
}