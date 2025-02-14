package me.Bright.utils;

import me.Bright.main.MainClass;
import org.bukkit.Bukkit;

public abstract class CountDown {

    public CountDown(int countdownTime) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(MainClass.getProvidingPlugin(MainClass.class), () -> run(), countdownTime * 20);
    }

    public abstract void run();
}
