package me.bright.commands;

import me.bright.airdrop.AirDrop;
import me.bright.game.KothGame;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AirdropCommand extends BrightCommand {

    private final String[] subCommands =
            {"dropHere", "testPapiHook", "setRewards"};
    private final Component REWARDS_GUI_NAME = Component.text("Airdrop Rewards");
    private final Component WEIGHT_GUI_NAME = Component.text("Airdrop Reward Weight Settings");

    private final Inventory rewardsInventory =
            plugin.getServer()
                    .createInventory(null, 27, REWARDS_GUI_NAME);

    private final Inventory weightInventory =
            plugin.getServer().createInventory(null, 45, WEIGHT_GUI_NAME);

    public AirdropCommand() {
        super("airdrop");
    }

    @Override
    public List<String> completerLogic(String[] args) {
        if (args.length > 1) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], List.of(subCommands), list);
        return list;
    }

    @Override
    public void runCommandAsPlayer(Player sender, String[] args) {
        if (args.length != 1) {
            sender.sendPlainMessage(unknownCommand);
            return;
        }
        switch (args[0]) {
            case "testPapiHook" -> {
                if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    sender.sendPlainMessage(setPrefix() + "Papi not enabled!");
                    return;
                }
                sender.sendPlainMessage(PlaceholderAPI.setPlaceholders(sender, "%airdrop_info%"));
            }
            case "setRewards" -> {
                List<ItemStack> savedRewards =
                        plugin.getConfig().getList(AirDrop.REWARDS_PATH, new ArrayList<>()).stream()
                                .map(x -> (ItemStack) x).toList();

                for (int i = 0; i < savedRewards.size(); i++) {
                    ItemStack reward = savedRewards.get(i);
                    if (reward == null) {
                        continue;
                    }
                    rewardsInventory.setItem(i, reward);
                }
                sender.openInventory(rewardsInventory);
            }
            case "dropHere" -> {
                AirDrop.getInstance().dropOnPlayer(sender);
                sender.sendPlainMessage("Look up!");
            }
            default -> sender.sendPlainMessage(unknownCommand);
        }
    }

    @Override
    public void runCommandAsConsole(String[] args) {

    }


    @EventHandler
    public void onRewardsGuiClose(@NotNull InventoryCloseEvent event) {
        if (!event.getView().title().equals(REWARDS_GUI_NAME)) {
            return;
        }

        ItemStack[] rewards = event.getInventory().getContents();
        event.getInventory().clear();
        ArrayList<ItemStack> rewardsList = new ArrayList<>();
        for (ItemStack reward : rewards) {
            if (reward == null) {
                continue;
            }
            var data = reward.getItemMeta().getPersistentDataContainer();
            NamespacedKey weightKey = new NamespacedKey(plugin, "weight");
            data.set(weightKey, PersistentDataType.INTEGER, data.getOrDefault(weightKey, PersistentDataType.INTEGER, 5));
            rewardsList.add(reward);
        }
        plugin.getConfig().set(AirDrop.REWARDS_PATH, rewardsList);
        plugin.saveConfig();
    }

    @EventHandler
    public void onClickRewardsGui(InventoryClickEvent event) {
//        Inventory gui = event.getClickedInventory();
//        if (gui == null) {
//            return;
//        }
//        event.setCancelled(true);
//        ItemStack clicked = event.getCurrentItem();
//        if (clicked == null || clicked.getType().equals(Material.AIR)) {
//            return;
//        }
//        if (gui instanceof PlayerInventory) {
//            gui.remove(clicked);
//            return;
//        }
        Player player = (Player) event.getWhoClicked();
        player.sendPlainMessage(event.getClickedInventory().getClass().getName());
    }

    @Override
    public String setPrefix() {
        return "Airdrop » ";
    }
}
