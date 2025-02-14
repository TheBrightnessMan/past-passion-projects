package me.Bright.main;

import me.Bright.utils.BrightUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class BrightPlayer {

    private final Player player;
    private final File file;
    private final FileConfiguration config;

    protected BrightPlayer(@NotNull Player player) {
        this.player = player;
        this.file = BrightPlayerData.getFileFromUUID(player.getUniqueId());
        this.config = BrightPlayerData.getFileConfigFromUUID(player.getUniqueId());
    }

    public void setToken(double token) {
        if (file == null || config == null) {
            return;
        }
        config.set(BrightPlayerData.TOKENS_PATH, token);
        BrightPlayerData.saveFile(config, file);
    }

    public double getToken() {
        if (file == null || config == null) {
            return -1;
        }
        return config.getDouble(BrightPlayerData.TOKENS_PATH);
    }

    public void setGem(double gem) {
        if (file == null || config == null) {
            return;
        }
        config.set(BrightPlayerData.GEMS_PATH, gem);
        BrightPlayerData.saveFile(config, file);
    }

    public double getGem() {
        if (file == null || config == null) {
            return -1;
        }
        return config.getDouble(BrightPlayerData.GEMS_PATH);
    }

    public void sendMessage(String message) {
        player.sendMessage(BrightUtils.colorCodes(BrightCommand.namePrefix + " » &r" + message));
    }


    public Player toBukkitPlayer() {
        return this.player;
    }
}
