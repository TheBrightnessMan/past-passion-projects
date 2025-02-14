package me.Bright.main;

import me.Bright.utils.BrightUtils;
import me.Bright.utils.ItemRarity;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnegative;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BrightItem extends ItemStack implements Listener {

    private final String name;

    public BrightItem(Material material, String name) {
        super(material);
        this.name = name;
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        itemMeta.setDisplayName(BrightUtils.colorCodes(this.name));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.setItemMeta(itemMeta);
    }
    /*public BrightItem(ItemStack itemStack) {
        super(itemStack.getType());
        this.name = itemStack.getItemMeta().getDisplayName();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.setItemMeta(itemMeta);
    }

    public List<String> getLore() {
        assert this.getItemMeta() != null;
        return (this.getItemMeta().hasLore()) ? this.getItemMeta().getLore() : new ArrayList<>();
    }

    public void addRarity(@NotNull ItemRarity rarity) {
        editNbt("rarity", rarity.getName());
    }

    public ItemRarity getRarity() {
        return ItemRarity.valueOf(getNbtData("rarity"));
    }

    public void editNbt(String tagName, String tagValue) {
        net.minecraft.server.v1_14_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
        NBTTagCompound tagCompound = (nmsItem.hasTag()) ?
                nmsItem.getTag() : new NBTTagCompound();
        assert tagCompound != null;
        tagCompound.set(tagName, new NBTTagString(tagValue));
        nmsItem.setTag(tagCompound);
    }

    public String getNbtData(String tagName) {
        net.minecraft.server.v1_14_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
        if (!nmsItem.hasTag()) {
            return null;
        }
        NBTTagCompound tagCompound = nmsItem.getTag();
        assert tagCompound != null;
        return tagCompound.getString(tagName);
    }

    public void setCustomEnchant(@NotNull BrightEnchant brightEnchant,
                                 @Nonnegative int level, boolean allowIllegal) {
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        List<String> lore = getLore();
        lore.removeIf(str -> str.contains(brightEnchant.getName()));

        final int actualLevel = allowIllegal ? level : Math.min(level, brightEnchant.getMaxLevel());

        itemMeta.removeEnchant(brightEnchant);
        itemMeta.addEnchant(brightEnchant, actualLevel, true);
        lore.add(0, ChatColor.GRAY + brightEnchant.getName() + " " +
                BrightUtils.enchantmentLevelDisplay(actualLevel));
        itemMeta.setLore(lore);

        ItemMeta edited = brightEnchant.onEnchant(itemMeta, actualLevel);
        if (edited == null) {
            this.setItemMeta(itemMeta);
        } else {
            this.setItemMeta(edited);
        }
    }

    public void enchantBook(@NotNull BrightEnchant brightEnchant, @Nonnegative int level, boolean allowIllegal) {
        ItemMeta itemMeta = this.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        final int actualLevel = allowIllegal ? level : Math.min(level, brightEnchant.getMaxLevel());
        itemMeta.removeEnchant(brightEnchant);
        itemMeta.addEnchant(brightEnchant, actualLevel, true);

        List<String> lore = this.getLore();
        lore.add(ChatColor.DARK_BLUE + brightEnchant.getName() + " " +
                BrightUtils.enchantmentLevelDisplay(actualLevel));
        lore.addAll(Arrays.asList(brightEnchant.getDescription(level)));
        lore.add("");
        itemMeta.setLore(lore);
        this.setType(Material.ENCHANTED_BOOK);
        this.setItemMeta(itemMeta);
    }

    public void removeEnchant(BrightEnchant brightEnchant) {
        ItemMeta itemMeta = this.getItemMeta();
        if (brightEnchant == null) {
            return;
        }
        assert itemMeta != null;

        List<String> lore = getLore();
        lore.removeIf(str -> str.startsWith(brightEnchant.getName()));
        itemMeta.setLore(lore);
        itemMeta.removeEnchant(brightEnchant);
        ItemMeta edited = brightEnchant.onDisenchant(itemMeta);
        if (edited == null) {
            this.setItemMeta(itemMeta);
        } else {
            this.setItemMeta(edited);
        }
    }*/
    @EventHandler
    public void onPlayerInteractByRightClick(@NotNull PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null) {
            return;
        }
        ItemStack holding = event.getItem();
        if (holding.getItemMeta() == null) {
            return;
        }
        if (!holding.getItemMeta().getDisplayName().equals(this.name)) {
            return;
        }
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            this.onRightClick(player, event);
        }
    }

    public abstract void onRightClick(Player player, PlayerInteractEvent event);

    @EventHandler
    public void onPlayerRightClickLivingEntity(@NotNull PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack holding = player.getInventory().getItemInMainHand();
        if (holding.getItemMeta() == null) {
            return;
        }
        assert holding.getItemMeta() != null;
        holding.getItemMeta().getDisplayName();
        if (holding.getItemMeta().getDisplayName().equals(this.name)) {
            return;
        }
        Entity entity = event.getRightClicked();
        this.onRightClickEntity(player, entity, event);
    }
    public abstract void onRightClickEntity(Player player, Entity clickedEntity, PlayerInteractEntityEvent event);
}
