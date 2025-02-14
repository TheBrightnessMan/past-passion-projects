package me.Bright.utils;

public enum ItemRarity {
    COMMON("Common",BrightUtils.colorCodes("&7&lCOMMON")),
    UNCOMMON("Uncommon", BrightUtils.colorCodes("&a&lUNCOMMON")),
    RARE("Rare",BrightUtils.colorCodes("&3&lRARE")),
    EPIC("Epic", BrightUtils.colorCodes("&5&lEPIC")),
    LEGENDARY("Legendary", BrightUtils.colorCodes("&6&lLEGENDARY")),
    MYTHIC("Mythic", BrightUtils.colorCodes("&d&lMYTHIC")),
    ENCHANTED("Enchanted", BrightUtils.colorCodes("&d&lE&5&lN&d&lC&5&lH&d&lA&5&lN&d&lT&5&lE&d&lD")),
    SUPERSTITIOUS("Superstitious", BrightUtils.colorCodes("&4&lS&8&lU&4&lP&8&lE&4&lR&8&lS&4&lT&8&lI&4&lT&8&lI&4&lO&8&lU&4&lS")),
    DIVINE("Divine", BrightUtils.colorCodes("&b&lD&9&lI&b&lV&9&lI&b&lN&9&lE")),
    BRIGHT("Bright", BrightUtils.colorCodes("&e&k&lBright"));

    private final String coloredName;
    private final String name;

    ItemRarity(String name, String coloredName) {
        this.coloredName = coloredName;
        this.name = name;
    }

    public String getRarity() {
        return this.coloredName;
    }

    public String getName() {
        return this.name;
    }
}
