package me.bright.playerData;

import me.bright.main.BrightPlugin;
import me.bright.utils.PunishmentType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class PlayerData {

    private final File file;
    private final FileConfiguration config;
    private final BrightPlugin plugin = BrightPlugin.getPlugin(BrightPlugin.class);

    public PlayerData(UUID uuid) {
        if (!(new File(plugin.getDataFolder() + File.separator + "playerdata").exists())) {
            new File(plugin.getDataFolder() + File.separator + "playerdata").mkdirs();
        }
        file = new File(plugin.getDataFolder() + File.separator + "playerdata", uuid.toString() + ".yml");
        this.config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    config.set("Player Name", Bukkit.getPlayer(uuid).getName());
                    config.set("Money", 0);
                    config.createSection("Punishment History");
                    save();
                    Bukkit.getLogger().log(Level.INFO, "File " + uuid + ".yml created!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getFileFromUUID(UUID uuid) {
        BrightPlugin plugin = BrightPlugin.getPlugin(BrightPlugin.class);
        File[] files = new File(plugin.getDataFolder() + File.separator + "playerdata").listFiles();
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

    public static FileConfiguration getFileConfigFromUUID(UUID uuid) {
        if (getFileFromUUID(uuid) == null) {
            return null;
        }
        return YamlConfiguration.loadConfiguration(getFileFromUUID(uuid));
    }

    public static void logPunishment(UUID targetUUID, Player moderator, PunishmentType punishmentType, String reason, String lengthOfTime, Date endDate) {
        File file = getFileFromUUID(targetUUID);
        FileConfiguration fileConfig = getFileConfigFromUUID(targetUUID);
        if (file == null || fileConfig == null) {
            return;
        }

        ConfigurationSection punishmentHistory = fileConfig.getConfigurationSection("Punishment History");
        int count = punishmentHistory.getKeys(false).size();

        fileConfig.set("Punishment History." + punishmentType.getType() + count + ".Reason", reason);

        String moderatorName = "Console";
        if (moderator != null) {
            moderatorName = moderator.getName();
        }

        fileConfig.set("Punishment History." + punishmentType.getType() + count + ".Moderator", moderatorName);
        fileConfig.set("Punishment History." + punishmentType.getType() + count + ".Punishment Length", lengthOfTime);
        fileConfig.set("Punishment History." + punishmentType.getType() + count + ".End Date", endDate);
        fileConfig.set("Punishment History." + punishmentType.getType() + count + ".Active", true);

        try {
            fileConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> loadPunishmentHistory(UUID targetUUID) {
        File file = getFileFromUUID(targetUUID);
        FileConfiguration fileConfig = getFileConfigFromUUID(targetUUID);
        if (file == null || fileConfig == null) {
            return null;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetUUID);
        ArrayList<String> history = new ArrayList<>();
        history.add("------- History of " + offlinePlayer.getName() +
                " -------");

        Set<String> punishmentHistoryShallowKeys = fileConfig.getConfigurationSection("Punishment History").getKeys(false);

        for (String punishment : punishmentHistoryShallowKeys) {
            String info1 = fileConfig.getString("Punishment History." + punishment + ".Reason");
            String info2 = fileConfig.getString("Punishment History." + punishment + ".Moderator");
            String info3 = fileConfig.getString("Punishment History." + punishment + ".Punishment Length");
            String info4 = fileConfig.getString("Punishment History." + punishment + ".End Date");
            String info5 = fileConfig.getString("Punishment History." + punishment + ".Active");
            history.add(punishment);
            history.add("    Reason: " + info1);
            history.add("    Moderator: " + info2);
            history.add("    Punishment Length: " + info3);
            history.add("    End Date: " + info4);
            history.add("    Active: " + info5);
            history.add("---------------");
        }
        return history;
    }

    public static boolean isMuted(UUID targetUUID) {
        File file = getFileFromUUID(targetUUID);
        FileConfiguration fileConfig = getFileConfigFromUUID(targetUUID);
        if (file == null || fileConfig == null) {
            return false;
        }
        Set<String> punishmentHistory = fileConfig.getConfigurationSection("Punishment History").getKeys(false);
        Pattern regex = Pattern.compile("mute", Pattern.CASE_INSENSITIVE);
        for (String punishment : punishmentHistory) {
            if (regex.matcher(punishment).find()) {
                if (fileConfig.getBoolean("Punishment History." + punishment + ".Active")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Date getTempMuteEndDate(UUID targetUUID) {
        File file = getFileFromUUID(targetUUID);
        FileConfiguration fileConfig = getFileConfigFromUUID(targetUUID);
        if (file == null || fileConfig == null) {
            return null;
        }
        Set<String> punishmentHistory = fileConfig.getConfigurationSection("Punishment History").getKeys(false);
        Pattern regex = Pattern.compile("temp mute", Pattern.CASE_INSENSITIVE);
        for (String punishment : punishmentHistory) {
            if (regex.matcher(punishment).find()) {
                if (fileConfig.getBoolean("Punishment History." + punishment + ".Active")) {
                    return Date.from(Instant.parse(fileConfig.getString("Punishment History." + punishment + ".End Date")));
                }
            }
        }
        return null;
    }

    public static void unMute(Player moderator, Player target, boolean silent) {
        UUID targetUUID = target.getUniqueId();
        File file = getFileFromUUID(targetUUID);
        FileConfiguration fileConfig = getFileConfigFromUUID(targetUUID);
        if (file == null || fileConfig == null) {
            return;
        }
        Set<String> punishmentHistory = fileConfig.getConfigurationSection("Punishment History").getKeys(false);
        Pattern regex = Pattern.compile("mute", Pattern.CASE_INSENSITIVE);
        for (String punishment : punishmentHistory) {
            if (regex.matcher(punishment).find()) {
                if (fileConfig.getBoolean("Punishment History." + punishment + ".Active")) {
                    fileConfig.set("Punishment History." + punishment + ".Active", false);
                    try {
                        fileConfig.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String moderatorName = "Console";
                    if (moderator != null) {
                        moderatorName = moderator.getName();
                    }
                    if (silent) {
                        Bukkit.broadcast(ChatColor.GRAY + "[SILENT] " + ChatColor.WHITE + moderatorName + " unmuted " + target.getName(), "bright.notifysilentunmute");
                    } else {
                        Bukkit.broadcastMessage(ChatColor.WHITE + moderatorName + " unmuted " + target.getName());
                    }
                }
            }
        }
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
