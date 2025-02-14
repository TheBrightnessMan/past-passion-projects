package me.bright.utils;

import java.util.ArrayList;
import java.util.List;

public enum BanUnit {

    SECOND("Second(s)", 1, "second"),
    MINUTE("Minute(s)", 60, "minute"),
    HOUR("Hour(s)", 60 * 60, "hour"),
    DAY("Day(s)", 24 * 60 * 60, "day"),
    WEEK("Week(s)", 7 * 24 * 60 * 60, "week"),
    MONTH("Month(s)", 30 * 24 * 60 * 60, "month"),
    YEAR("Year(s)", 365 * 24 * 60 * 60, "year");

    private String name;
    private long toMillisecond;
    private String shortcut;

    BanUnit(String name, long toMillisecond, String shortcut) {
        this.name = name;
        this.toMillisecond = toMillisecond;
        this.shortcut = shortcut;
    }

    public long getToMillisecond() {
        return toMillisecond * 1000;
    }

    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public static List<String> getUnitsAsString() {
        List<String> units = new ArrayList<>();
        for (BanUnit unit : BanUnit.values()) {
            units.add(unit.getShortcut().toLowerCase());
        }
        return units;
    }

    public static BanUnit getUnit(String unit) {
        for (BanUnit units : BanUnit.values()) {
            if (units.getShortcut().equalsIgnoreCase(unit)) {
                return units;
            }
        }
        return null;
    }
}
