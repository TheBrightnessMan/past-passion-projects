package me.bright.utils;

public enum PunishmentType {
    BAN("Ban"),
    TEMP_BAN("Temp Ban"),
    IP_BAN("IP Ban"),
    MUTE("Mute"),
    TEMP_MUTE("Temp Mute");


    private String type;

    PunishmentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
