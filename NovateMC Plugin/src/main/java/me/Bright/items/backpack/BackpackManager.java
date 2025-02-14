package me.Bright.items.backpack;

import me.Bright.utils.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public abstract class BackpackManager extends ItemStack implements Listener {

    private ItemMeta itemMeta = getItemMeta();
    private Inventory inventory;
    private String name;
    private String backpackUUID;

    public BackpackManager(String name, BackpackSize backpackSize, String skinUrlId) {
        super(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/" + skinUrlId));
        itemMeta.setDisplayName(name);
        inventory = Bukkit.createInventory(null, backpackSize.getSize(), name);
        backpackUUID = UUID.randomUUID().toString();
        ArrayList<String> lore = new ArrayList<String>() {{
            add("A backpack with " + backpackSize.getSize() + " slots!");
            add(backpackUUID);
        }};
        itemMeta.setLore(lore);
        setItemMeta(itemMeta);
        this.name = name;
    }

    public Inventory getBackpack() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    @EventHandler
    private void onPlayerRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack holding = event.getItem();
        if (holding.getItemMeta().getDisplayName().equals(this.itemMeta.getDisplayName())) {
            onOpenBackpack(player, holding);
        }
    }
    public abstract void onOpenBackpack(Player player, ItemStack itemStack);
}
