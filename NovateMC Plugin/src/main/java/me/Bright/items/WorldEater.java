package me.Bright.items;

import me.Bright.items.mainStuff.ItemCore;
import me.Bright.items.mainStuff.ItemRarity;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WorldEater extends ItemCore {
    public WorldEater() {
        super(Material.TNT,
                "World Eater",
                ItemRarity.ENCHANTED,
                "Eats world",
                MouseClick.RIGHT_CLICK,
                "Eat World",
                "Eats world, what more can you ask?");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack holding = player.getInventory().getItemInMainHand();
        if (!holding.getItemMeta().getDisplayName().equals(this.getItemMeta().getDisplayName())) {
            return;
        }
        Block block = event.getBlock().getRelative(BlockFace.DOWN);
        Material[] materials = {
                Material.WATER
        };
        Utils.eatBlock(block,materials ,35);
        event.setCancelled(true);
    }



    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {

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
