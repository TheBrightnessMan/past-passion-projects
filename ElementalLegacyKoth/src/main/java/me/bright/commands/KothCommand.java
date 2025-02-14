package me.bright.commands;

import me.bright.game.KothGame;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class KothCommand extends BrightCommand {

    private static final Component REWARDS_GUI_NAME = Component.text("KOTH Rewards");
    private final String[] subCommands =
            {"corner1", "corner2", "setRewards", "start", "testPapiHook"};

    private final Inventory rewardsInventory =
            plugin.getServer()
                    .createInventory(null, 27, REWARDS_GUI_NAME);

    public KothCommand() {
        super("koth");
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
            case "corner1" -> {
                plugin.getConfig().set(KothGame.CORNER1_PATH, sender.getLocation());
                plugin.saveConfig();
                sender.sendPlainMessage(setPrefix() + "Corner 1 set!");
            }
            case "corner2" -> {
                plugin.getConfig().set(KothGame.CORNER2_PATH, sender.getLocation());
                plugin.saveConfig();
                sender.sendPlainMessage(setPrefix() + "Corner 2 set!");
            }
            case "start" -> {
                if (KothGame.getInstance().start()) {
                    sender.sendPlainMessage(setPrefix() + "Starting King of the Hill!");
                } else {
                    sender.sendPlainMessage(setPrefix() + "Event is already under way!");
                }

            }
            case "setRewards" -> {
                List<ItemStack> savedRewards =
                        plugin.getConfig().getList(KothGame.REWARDS_PATH, new ArrayList<>()).stream()
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
            case "testPapiHook" -> {
                if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    sender.sendPlainMessage(setPrefix() + "Papi not enabled!");
                    return;
                }
                sender.sendPlainMessage(PlaceholderAPI.setPlaceholders(sender, "%koth_info%"));
            }
            default -> sender.sendPlainMessage(unknownCommand);
        }
    }

    @Override
    public void runCommandAsConsole(String[] args) {
        if (args[0].equalsIgnoreCase("start")) {
            KothGame.getInstance().start();
            plugin.getLogger().log(Level.INFO, "Starting King of the Hill!");
        }
    }

    @Override
    public String setPrefix() {
        return "KOTH » ";
    }

    @EventHandler
    public void onInventoryClose(@NotNull InventoryCloseEvent event) {
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
            rewardsList.add(reward);
        }
        plugin.getConfig().set(KothGame.REWARDS_PATH, rewardsList);
        plugin.saveConfig();
    }
}
