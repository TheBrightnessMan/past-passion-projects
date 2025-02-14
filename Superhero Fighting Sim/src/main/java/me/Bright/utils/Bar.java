package me.Bright.utils;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Bar {

    private int taskID = -1;
    private final Plugin plugin = Utils.plugin;
    private BossBar bossBar;

    public Bar(Player player, String title, double reductionPerSecond) {
        bossBar = Bukkit.createBossBar(Utils.colorCodes(title), BarColor.RED, BarStyle.SEGMENTED_10);
        bossBar.setVisible(true);
        bossBar.addPlayer(player);
        bossBar.setProgress(1.0);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (bossBar.getProgress() <= (reductionPerSecond / 100)) {
                    bossBar.removeAll();
                    cancel();
                } else {
                    bossBar.setProgress(bossBar.getProgress() - (reductionPerSecond / 100));
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
