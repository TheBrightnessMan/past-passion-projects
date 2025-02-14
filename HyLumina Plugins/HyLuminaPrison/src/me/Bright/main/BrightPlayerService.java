package me.Bright.main;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class BrightPlayerService {

    private static final HashMap<Player, BrightPlayer> map = new HashMap<>();

    public static @Nullable BrightPlayer getBrightPlayer(Player player) {
        if (map.containsKey(player)) {
            return map.get(player);
        }
        return null;
    }

    protected static void registerBrightPlayer(Player player) {
        map.put(player, new BrightPlayer(player));
        BrightPlayerData.registerPlayer(player.getUniqueId());
    }

    protected static void unregBrightPlayer(Player player) {
        map.remove(player);
    }
}
