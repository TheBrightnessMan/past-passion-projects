package me.bright.papiHooks;

import me.bright.airdrop.AirDrop;
import me.bright.game.KothGame;
import me.bright.main.BrightPlugin;
import me.bright.main.BrightUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AirdropPapiHook extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "airdrop";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Bright";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0-DEV";
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
                    Plugin plugin = BrightPlugin.getProvidingPlugin(BrightPlugin.class);
                    long difference = plugin.getConfig().getLong(AirDrop.TIME_PATH) - System.currentTimeMillis();
                    return "Next airdrop in ".concat(BrightUtils.formatSeconds(difference / 1000L));
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
