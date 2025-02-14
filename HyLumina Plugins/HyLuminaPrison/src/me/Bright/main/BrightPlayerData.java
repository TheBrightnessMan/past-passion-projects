package me.Bright.main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

public class BrightPlayerData {

    private static final BrightPlugin plugin = BrightPlugin.getPlugin(BrightPlugin.class);
    public static final String filePath = plugin.getDataFolder() + File.separator + "playerData";
    public static final String TOKENS_PATH = "Currency.Tokens";
    public static final String GEMS_PATH = "Currency.Gems";

    protected static void registerPlayer(UUID uuid) {
        File file = new File(filePath, uuid + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    config.set("Player Name", Bukkit.getPlayer(uuid).getName());
                    config.set("First Login", new Date());
                    config.set(TOKENS_PATH, 0);
                    config.set(GEMS_PATH, 0);
                    saveFile(config, file);
                    Bukkit.getLogger().log(Level.INFO, "File " + uuid + ".yml created!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static @Nullable File getFileFromUUID(UUID uuid) {
        BrightPlugin plugin = BrightPlugin.getPlugin(BrightPlugin.class);
        File[] files = new File(filePath).listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        for (File file : files) {
            if (file.getName().equals(uuid.toString() + ".yml")) {
                return file;
            }
        }
        return null;
    }

    public static @Nullable FileConfiguration getFileConfigFromUUID(UUID uuid) {
        if (getFileFromUUID(uuid) == null) {
            return null;
        }
        return YamlConfiguration.loadConfiguration(getFileFromUUID(uuid));
    }

    public static void saveFile(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
