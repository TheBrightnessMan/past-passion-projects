package me.Bright.commands;

import me.Bright.main.*;
import me.Bright.utils.BrightCurrency;
import me.Bright.utils.BrightUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HyluminaCommands extends BrightCommand {

    public static String COMMAND = "hylumina";

    public HyluminaCommands() {
        super(COMMAND, COMMAND);
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        if (args.length == 0) {
            sendMessage(sender, invalidArgument);
            return;
        }

        //hylumina enchantCustom <enchantName> <level>
        if (args[0].equalsIgnoreCase("enchantCustom")) {
            if (args.length != 3) {
                sendMessage(sender, invalidArgument);
                return;
            }
            BrightItem holding = BrightItemService.getBrightItem(sender.getInventory().getItemInMainHand());
            BrightEnchant enchant = BrightEnchant.getByKey(args[1]);
            if (enchant == null) {
                sendMessage(sender, "Unknown enchant!");
                return;
            }
            if (holding.getType().equals(Material.BOOK) || holding.getType().equals(Material.ENCHANTED_BOOK)) {
                holding.enchantBook(enchant, Integer.parseInt(args[2]), BrightUtils.isBright(sender));
            } else {
                holding.setCustomEnchant(enchant, Integer.parseInt(args[2]), BrightUtils.isBright(sender));
            }
            sender.getInventory().setItemInMainHand(holding);
            return;
        }

        //hylumina token [set] <playerName> <amount>
        if (args[0].equalsIgnoreCase("token")) {
            if (args.length != 4) {
                return;
            }
            if (args[1].equalsIgnoreCase("set")) {
                if (Bukkit.getPlayer(args[2]) != null) {
                    Player player = Bukkit.getPlayer(args[2]);
                    assert player != null;
                    double amount = Double.parseDouble(args[3]);
                    if (setCurrency(player, BrightCurrency.TOKEN, amount)) {
                        sendMessage(sender, player.getName() + " now has " + amount + " tokens!");
                    } else {
                        sendMessage(sender, "Specified player is not online.");
                    }
                }
            }
            return;
        }

        //hylumina gem [set] <playerName> <amount>
        if (args[0].equalsIgnoreCase("gem")) {
            if (args.length != 4) {
                return;
            }
            if (args[1].equalsIgnoreCase("set")) {
                if (Bukkit.getPlayer(args[2]) != null) {
                    Player player = Bukkit.getPlayer(args[2]);
                    assert player != null;
                    double amount = Double.parseDouble(args[3]);
                    if (setCurrency(player, BrightCurrency.GEM, amount)) {
                        sendMessage(sender, player.getName() + " now has " + amount + " gems!");
                    } else {
                        sendMessage(sender, "Specified player is not online.");
                    }
                }
            }
            return;
        }

        if (args[0].equalsIgnoreCase("items")) {
            if (!BrightUtils.isBright(sender)) {
                sendMessage(sender, notBrightError);
            }
            return;
        }
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        if (args.length == 0) {
            sendConsoleMessage(invalidArgument);
            return;
        }
        if (args[0].equalsIgnoreCase("token")) {
            if (args.length != 4) {
                sendConsoleMessage(invalidArgument);
                return;
            }
            if (args[1].equalsIgnoreCase("set")) {
                if (Bukkit.getPlayer(args[2]) != null) {
                    Player player = Bukkit.getPlayer(args[2]);
                    assert player != null;
                    double amount = Double.parseDouble(args[3]);
                    if (setCurrency(player, BrightCurrency.TOKEN, amount)) {
                        sendConsoleMessage(player.getName() + " now has " + amount + " tokens!");
                    } else {
                        sendConsoleMessage("Specified player is not online.");
                    }
                }
            }
            return;
        }
        if (args[0].equalsIgnoreCase("gem")) {
            if (args.length != 4) {
                sendConsoleMessage(invalidArgument);
                return;
            }
            if (args[1].equalsIgnoreCase("set")) {
                if (Bukkit.getPlayer(args[2]) != null) {
                    Player player = Bukkit.getPlayer(args[2]);
                    assert player != null;
                    double amount = Double.parseDouble(args[3]);
                    if (setCurrency(player, BrightCurrency.GEM, amount)) {
                        sendConsoleMessage(player.getName() + " now has " + amount + " gems!");
                    }
                } else {
                    sendConsoleMessage("Specified player is not online.");
                }
            }
            return;
        }
    }

    private boolean setCurrency(Player player, BrightCurrency currency, double amount) {
        BrightPlayer brightPlayer = BrightPlayerService.getBrightPlayer(player);
        if (brightPlayer == null) {
            return false;
        }
        switch (currency) {
            case TOKEN:
                brightPlayer.setToken(amount);
                break;
            case GEM:
                brightPlayer.setGem(amount);
                break;
        }
        return true;
    }
}
