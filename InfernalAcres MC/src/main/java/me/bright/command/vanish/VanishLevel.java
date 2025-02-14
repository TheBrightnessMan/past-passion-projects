package me.bright.command.vanish;

public enum VanishLevel {
    ADMIN("bright.vanish.admin"),
    MOD("bright.vanish.mod"),
    HELPER("bright.vanish.helper");

    private final String permission;

    VanishLevel(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
