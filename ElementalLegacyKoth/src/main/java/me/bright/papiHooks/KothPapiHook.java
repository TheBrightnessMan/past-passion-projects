package me.bright.papiHooks;

import me.bright.game.KothGame;
import me.bright.main.BrightPlugin;
import me.bright.main.BrightUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KothPapiHook extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "koth";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Bright";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        try {
            switch (params) {
                case "info" -> {
                    if (KothGame.getInstance().getRemainingTicks() > 0) {
                        return "KOTH ending in "
                                .concat(BrightUtils.formatSeconds(KothGame.getInstance().getRemainingTicks() / 20));
                    }

                    Plugin plugin = BrightPlugin.getProvidingPlugin(BrightPlugin.class);
                    long difference = plugin.getConfig().getLong(KothGame.TIME_PATH) - System.currentTimeMillis();
                    return "KOTH starting in "
                            .concat(BrightUtils.formatSeconds(difference / 1000L));
                }
                case "first" -> {
                    return KothGame.getInstance().getPlayerScore().get(0).getKey().getName();
                }
                case "second" -> {
                    return KothGame.getInstance().getPlayerScore().get(1).getKey().getName();
                }
                case "third" -> {
                    return KothGame.getInstance().getPlayerScore().get(2).getKey().getName();
                }
                default -> {
                    return getIdentifier().concat(" null result: Unknown parameter: ").concat(params);
                }
            }
        } catch (NullPointerException e) {
            return getIdentifier().concat(" null result: PAPI query returned null: ").concat(params);
        }
    }
}
