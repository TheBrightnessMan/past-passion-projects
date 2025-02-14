package me.Bright.utils;

import me.Bright.main.MainClass;
import org.bukkit.Bukkit;

public abstract class CountDown {

    public CountDown(int countdownTimeInSeconds) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(MainClass.getProvidingPlugin(MainClass.class), this::run,
                countdownTimeInSeconds * 20L - 20);
    }

    public abstract void run();
}
