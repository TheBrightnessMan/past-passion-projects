package me.Bright.main;

import me.Bright.commands.HyluminaCommandTabCompleter;
import me.Bright.commands.HyluminaCommands;
import me.Bright.generalListeners.DragDropEnchants;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrightPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        createPlayerDataFolder();
        regCommand("hylumina", new HyluminaCommands(), (sender, args) -> {

        });
        regEvents(new SetupPlayerListener(), new DragDropEnchants());

        regCustomEnchants(BrightEnchant.values);
    }

    @Override
    public void onDisable() {
        unregCustomEnchants(BrightEnchant.values);
    }

    private void createPlayerDataFolder() {
        if (!(new File(this.getDataFolder() + File.separator + "playerData").exists())) {
            new File(this.getDataFolder() + File.separator + "playerData").mkdirs();
        }
    }

    private void regEvents(Listener @NotNull ... listeners) {
        for (Listener listener : listeners) {
            this.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void regCommand(String command, BrightCommand commandExecutor, BrightCommandInterface commandInterface) {
        Logger logger = Bukkit.getLogger();
        commandExecutor.passLambda(commandInterface);
        this.getCommand(command).setExecutor(commandExecutor);
        logger.log(Level.INFO, command + " Command Registered");
    }

    private void regCommand(String command, CommandExecutor commandExecutor,
                            TabCompleter tabCompleter) {
        Logger logger = Bukkit.getLogger();
        this.getCommand(command).setExecutor(commandExecutor);
        this.getCommand(command).setTabCompleter(tabCompleter);
        logger.log(Level.INFO, command + " command Registered with Tab Complete");
    }

    private void regCustomEnchant(BrightEnchant enchant) {
        try {
            Field acceptingNewField = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNewField.setAccessible(true);
            acceptingNewField.set(null, true);
            acceptingNewField.setAccessible(false);
            Enchantment.registerEnchantment(enchant);

            regEvents(enchant);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void regCustomEnchants(BrightEnchant @NotNull ... enchants) {
        for (BrightEnchant enchant : enchants) {
            regCustomEnchant(enchant);
        }
    }

    private void unregCustomEnchant(BrightEnchant enchant) {
        try {
            Field byIdField = Enchantment.class.getDeclaredField("byKey");
            Field byNameField = Enchantment.class.getDeclaredField("byName");

            byIdField.setAccessible(true);
            byNameField.setAccessible(true);

            Map<NamespacedKey, Enchantment> byId = (Map<NamespacedKey, Enchantment>) byIdField.get(null);
            Map<String, Enchantment> byName = (Map<String, Enchantment>) byNameField.get(null);

            byId.remove(enchant.getKey());
            byName.remove(enchant.getName());

            byIdField.setAccessible(false);
            byNameField.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unregCustomEnchants(BrightEnchant @NotNull ... enchants) {
        for (BrightEnchant enchant : enchants) {
            unregCustomEnchant(enchant);
        }
    }
}
