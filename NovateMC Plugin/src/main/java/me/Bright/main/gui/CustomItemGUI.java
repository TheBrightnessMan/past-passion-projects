package me.Bright.main.gui;

import me.Bright.items.HomingMissile;
import me.Bright.items.Hyperion;
import me.Bright.items.InstaKillStick;
import me.Bright.items.WorldEater;
import me.Bright.items.backpack.ThreeSlotBackPack;
import me.Bright.items.kit.naturalGod.NaturalGodShield;
import me.Bright.items.kit.naturalGod.NaturalGodSword;
import me.Bright.items.scrolls.HellFire;
import me.Bright.items.scrolls.MasterNecromancer;
import me.Bright.items.scrolls.Telekinesis;
import me.Bright.items.scrolls.ThunderLord;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CustomItemGUI extends GuiCore {

    public CustomItemGUI() {
        super(ChatColor.GOLD + "Custom Items", 2);
        Inventory inventory = super.getInventory();
        inventory.addItem(new HomingMissile());
        inventory.addItem(new InstaKillStick());
        inventory.addItem(new Telekinesis());
        inventory.addItem(new HellFire());
        inventory.addItem(new MasterNecromancer());
        inventory.addItem(new ThunderLord());
        inventory.addItem(new NaturalGodSword());
        inventory.addItem(new NaturalGodShield());
//        inventory.addItem(new WorldEater());
//        inventory.addItem(new Hyperion());
    }

    @Override
    public void onInventoryClick(Player player, InventoryAction action, ItemStack clickedItem, InventoryClickEvent event) {
        PlayerInventory playerInventory = player.getInventory();
        if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            assert clickedItem != null;
            ItemStack stackOfItem = clickedItem.clone();
            stackOfItem.setAmount(64);
            playerInventory.addItem(stackOfItem);

        } else {
            playerInventory.addItem(clickedItem);
        }
        event.setCancelled(true);
    }
}