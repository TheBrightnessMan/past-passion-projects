package me.Bright.main.gui;

import me.Bright.items.ShadowFury;
import me.Bright.items.kit.brightKit.*;
import me.Bright.items.kit.drStrange.AstralProjection;
import me.Bright.items.kit.drStrange.DSEnergyBlast;
import me.Bright.items.kit.drStrange.DrStrangeArmor;
import me.Bright.items.kit.hulk.HulkArmor;
import me.Bright.items.kit.ironMan.IronManArmor;
import me.Bright.items.kit.ironMan.IronManBeam;
import me.Bright.items.kit.naturalGod.NaturalGodShield;
import me.Bright.items.kit.naturalGod.NaturalGodSword;
import me.Bright.items.kit.scarletWitch.SWEnergyBlast;
import me.Bright.items.kit.scarletWitch.SWForcefield;
import me.Bright.items.kit.scarletWitch.ScarletWitchArmor;
import me.Bright.items.kit.scarletWitch.Telekinesis;
import me.Bright.items.kit.thor.Bifrost;
import me.Bright.items.kit.thor.Stormbreaker;
import me.Bright.items.kit.thor.ThorArmor;
import me.Bright.utils.DoubleJump;
import me.Bright.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class KitGUI extends GuiCore {

    public KitGUI() {
        super(ChatColor.GOLD + "Pick your kits!", 1);
    }

    public void createInventory(Player player) {
        Inventory inventory = super.getInventory();
        inventory.addItem(new GuiItemBuilder(Material.OAK_SAPLING, ChatColor.GREEN, "Natural God", false),
                new GuiItemBuilder(Material.IRON_HELMET, ChatColor.WHITE, "Iron Man", false),
                hulk(),
                new GuiItemBuilder(Material.IRON_AXE, ChatColor.WHITE, "Thor", true),
                new GuiItemBuilder(Material.RED_DYE, ChatColor.RED, "Scarlet Witch", false),
                new GuiItemBuilder(Material.GREEN_DYE, ChatColor.DARK_GREEN, "Dr Strange", false),
                new ShadowFury());
        if (player.getName().equals("BrightnessMan")) {
            inventory.addItem(new GuiItemBuilder(Material.NETHERITE_SWORD, ChatColor.WHITE, "Bright", false));
        }
    }

    @Override
    public void onInventoryClick(Player player, InventoryAction action, ItemStack clickedItem, InventoryClickEvent event) {
        PlayerInventory playerInventory = player.getInventory();
        player.setAllowFlight(false);
        player.setFlying(false);
        playerInventory.setArmorContents(null);
        player.getInventory().clear();
        if (DoubleJump.allowDoubleJump.contains(player)) {
            DoubleJump.allowDoubleJump.remove(player);
        }
        for (PotionEffectType effectType : PotionEffectType.values()) {
            player.removePotionEffect(effectType);
        }

        switch (clickedItem.getType()) {
            case OAK_SAPLING: //Natural God
                playerInventory.addItem(new NaturalGodSword(), new NaturalGodShield());
                break;
            case IRON_HELMET: //Iron Man
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 8, false, false, false));
                IronManArmor.equipArmor(player);
                playerInventory.addItem(new IronManBeam());
                player.setAllowFlight(true);
                break;
            case GREEN_BANNER: //Hulk
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 10, false, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 8, false, false, false));
                HulkArmor.equipArmor(player);
                break;
            case IRON_AXE: //Thor
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1000000, 10, false, false, false));
                ThorArmor.equipArmor(player);
                playerInventory.addItem(new Stormbreaker(), new Bifrost());
                break;
            case RED_DYE: //Scarlet Witch
                ScarletWitchArmor.equipArmor(player);
                playerInventory.addItem(new Telekinesis(), new SWEnergyBlast(), new SWForcefield());
                player.setAllowFlight(true);
                break;
            case GREEN_DYE: //Dr Strange
                DrStrangeArmor.equipArmor(player);
                playerInventory.addItem(new DSEnergyBlast(), new Telekinesis(), new AstralProjection());
                player.setAllowFlight(true);
                break;
            case NETHERITE_SWORD: //Bright aka Me
                BrightArmor.equipArmor(player);
                playerInventory.addItem(new FancyFireball(), new ShockWave(), new BrightForceField(), new FocusedLaser(), new UndyingFlame());
        }
        event.setCancelled(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1000000, 10, false, false, false));
        for (int i = 0; i <= 5000; i++) {
            try {
                player.setHealth(player.getHealth() + 1);
            } catch (Exception e) {
                break;
            }
        }
        player.closeInventory();
    }

    private ItemStack hulk() {
        ItemStack item = new GuiItemBuilder(Material.GREEN_BANNER, ChatColor.GREEN, "Hulk", true);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(Utils.colorCodes("Get it? I'm a green banner because Bruce &lBanner&r?"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}