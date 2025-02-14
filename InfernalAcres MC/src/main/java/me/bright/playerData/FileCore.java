package me.bright.playerData;

import me.bright.main.BrightPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public abstract class FileCore {

    protected static final String pluginFolderPath = BrightPlugin.getPlugin(BrightPlugin.class).getDataFolder() + File.separator;

    private final String fileName;
    private final File file;
    private final FileConfiguration fileConfig;

    public FileCore(String path, String fileName) {
        this.fileName = fileName;
        if (!(new File(path).exists())) {
            new File(path).mkdirs();
        }

        this.file = new File(path, fileName);
        this.fileConfig = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    Bukkit.getLogger().log(Level.INFO, "File " + this.fileName + " created!");
                    onFileCreation();
                    save();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void onFileCreation();

    public void save() {
        try {
            fileConfig.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getFileConfig() {
        return this.fileConfig;
    }

    public File getFile() {
        return this.file;
    }
}
