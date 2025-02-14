package me.Bright.utils;

import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagString;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemNbtEditor {

    public static @NotNull ItemStack edit(ItemStack item, String tagName, String tagValue) {
        net.minecraft.server.v1_14_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tagCompound = (nmsItem.hasTag()) ?
                nmsItem.getTag() : new NBTTagCompound();
        assert tagCompound != null;
        tagCompound.set(tagName, new NBTTagString(tagValue));
        nmsItem.setTag(tagCompound);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static @Nullable
    String getTagValue(ItemStack item, String tagName) {
        net.minecraft.server.v1_14_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (!nmsItem.hasTag()) {
            return null;
        }
        NBTTagCompound tagCompound = nmsItem.getTag();
        assert tagCompound != null;
        return tagCompound.getString(tagName);
    }
}
