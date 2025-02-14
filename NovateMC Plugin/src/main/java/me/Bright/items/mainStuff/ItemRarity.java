package me.Bright.items.mainStuff;

import me.Bright.utils.Utils;

public enum ItemRarity {

    COMMON(Utils.colorCodes("&7&lCOMMON")),
    UNCOMMON(Utils.colorCodes("&a&lUNCOMMON")),
    RARE(Utils.colorCodes("&3&lRARE")),
    EPIC(Utils.colorCodes("&5&lEPIC")),
    LEGENDARY(Utils.colorCodes("&6&lLEGENDARY")),
    MYTHIC(Utils.colorCodes("&d&lMYTHIC")),
    ENCHANTED(Utils.colorCodes("&d&lE&5&lN&d&lC&5&lH&d&lA&5&lN&d&lT&5&lE&d&lD")),
    SUPERSTITIOUS(Utils.colorCodes("&4&lS&8&lU&4&lP&8&lE&4&lR&8&lS&4&lT&8&lI&4&lT&8&lI&4&lO&8&lU&4&lS")),
    DIVINE(Utils.colorCodes("&b&lD&9&lI&b&lV&9&lI&b&lN&9&lE"));

    private String rarity;
    ItemRarity(String rarity) {
        this.rarity = rarity;
    }
    public String getRarity() {
        return rarity;
    }
}
