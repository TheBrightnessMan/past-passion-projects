package me.Bright.utils;

import me.Bright.main.BrightEnchant;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BrightUtils {

    public static ArrayList<Material> whitelistedBlocks = new ArrayList<Material>() {{
        add(Material.STONE);
    }};

    public static IChatBaseComponent stringToIChatBaseComponent(String string) {
        return IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + colorCodesSpecial(string) + "\"}");
    }

    public static @NotNull String colorCodesSpecial(@NotNull String string) {
        return colorCodes(string.replaceAll("&", "§"));
    }

    public static @NotNull String colorCodes(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static final HashMap<Integer, String> suffixMap = new HashMap<Integer, String>() {{
        put(0, "");
        put(1, "k");
        put(2, "M");
        put(3, "B");
        put(4, "T");
        put(5, "qd");
        put(6, "Qn");
        put(7, "sx");
        put(8, "Sp");
        put(9, "o");
        put(10, "N");
        put(11, "de");
        put(12, "Ud");
        put(13, "DD");
        put(14, "TdD");
        put(15, "qdD");
        put(16, "QnD");
        put(17, "sxD");
        put(18, "SpD");
        put(19, "OcD");
        put(20, "NvD");
    }};

    public static @NotNull String addSuffix(double x) {
        int suffixNumber = 0;
        double num = x;
        while (num > 1) {
            num /= 1000;
            suffixNumber++;
            if (num < 1000) {
                break;
            }
        }
        int sciNotation = (int) Math.pow(10, 3 * suffixNumber);
        double wanted = x / sciNotation;
        String str = String.valueOf(wanted);
        String output;
        if (str.toCharArray()[1] == '.' || str.toCharArray()[2] == '.') { //1.23 or 12.3
            output = str.substring(0, 4);
        } else if (str.toCharArray()[3] == '.') { //123.
            output = str.substring(0, 3);
        } else {
            output = str; //Should not get to this point
        }
        return output + suffixMap.get(suffixNumber);
    }

    public static @NotNull String enchantmentLevelDisplay(int level) {
        if (level == 1) {
            return "I";
        } else if (level == 2) {
            return "II";
        } else if (level == 3) {
            return "III";
        } else if (level == 4) {
            return "IV";
        } else if (level == 5) {
            return "V";
        } else if (level == 6) {
            return "VI";
        } else if (level == 7) {
            return "VII";
        } else if (level == 8) {
            return "VIII";
        } else if (level == 9) {
            return "IX";
        } else if (level == 10) {
            return "X";
        } else {
            return "enchantment.level." + level;
        }
    }

    public static int calculateFortune(int level, int increment) {
        return calculateFortune(level, increment, 100);
    }

    public static int calculateFortune(int level, int increment, int bound) {
        int amount = 0;
        while (level > 0) {
            int chance = level % bound;
            if (chance == 0 || Math.floorDiv(level, bound) >= 1) {
                amount += increment;
            } else {
                if (new Random().nextInt(bound + 1) <= chance) {
                    amount += increment;
                }
            }
            level -= bound;
        }
        return amount;
    }

    public static List<String> onlinePlayers = new ArrayList<String>() {{
        for (Player player : Bukkit.getOnlinePlayers()) {
            add(player.getName());
        }
    }};

    public static boolean itemContainsCustomEnchant(@NotNull ItemStack itemStack, BrightEnchant brightEnchant) {
        return itemStack.getEnchantments().containsKey(brightEnchant);
    }

    public static int getEnchantmentLevel(@NotNull ItemStack itemStack, BrightEnchant brightEnchant) {
        return itemStack.getEnchantments().get(brightEnchant);
    }

    public static void callFortune(@NotNull Player player, @NotNull Block block) {
        ItemStack holding = player.getInventory().getItemInMainHand();
        Collection<ItemStack> drops = block.getDrops(holding);
        BrightEnchant fortune = BrightEnchant.FORTUNE;
        if (BrightUtils.itemContainsCustomEnchant(holding, fortune)) {
            int level = BrightUtils.getEnchantmentLevel(holding, fortune);
            int amount = BrightUtils.calculateFortune(level, 2);
            if (drops.isEmpty()) {
                return;
            }
            ItemStack firstDrop = (ItemStack) drops.toArray()[0];
            while (amount > 0) {
                drops.add(firstDrop);
                amount--;
            }
        }
        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            for (ItemStack itemStack : drops) {
                player.getInventory().addItem(itemStack);
            }
        }
    }

    public static void addDropsToInv(@NotNull Player player, @NotNull Block block) {
        ItemStack holding = player.getInventory().getItemInMainHand();
        Collection<ItemStack> drops = block.getDrops(holding);
        for (ItemStack drop : drops) {
            player.getInventory().addItem(drop);
        }
    }

    public static boolean isBright(@NotNull Player player) {
        return player.getUniqueId().toString().equals("2608d7bc-babf-48dc-92c4-668b6695172d");
    }

    public static boolean isPickaxe(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case DIAMOND_PICKAXE:
            case GOLDEN_PICKAXE:
            case IRON_PICKAXE:
            case STONE_PICKAXE:
            case WOODEN_PICKAXE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isAxe(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case DIAMOND_AXE:
            case GOLDEN_AXE:
            case IRON_AXE:
            case STONE_AXE:
            case WOODEN_AXE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isShovel(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case DIAMOND_SHOVEL:
            case GOLDEN_SHOVEL:
            case IRON_SHOVEL:
            case STONE_SHOVEL:
            case WOODEN_SHOVEL:
                return true;
            default:
                return false;
        }
    }

    public static boolean isTool(ItemStack itemStack) {
        return isPickaxe(itemStack) || isAxe(itemStack) || isShovel(itemStack);
    }
}
