package me.bright.game;

import me.bright.main.BrightPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Game {

    protected final Plugin plugin = BrightPlugin.getPlugin(BrightPlugin.class);
    protected boolean ended = true;
    public final String name;

    protected final Component broadcastPrefix =
            Component.text("[").color(TextColor.color(255, 255, 85))
                    .append(Component.text("Broadcast").color(TextColor.color(170, 0, 0)))
                    .append(Component.text("] ").color(TextColor.color(255, 255, 85)));

    public Game(String name) {
        this.name = name;
    }

    /**
     * Starts the game
     */
    public boolean start() {
        if (!ended) {
            return false;
        }
        onStart();
        ended = false;
        broadcast(Component.text(this.name).color(TextColor.fromHexString("#b200ed"))
                .append(Component.text(" is starting now!").color(TextColor.color(0, 170, 0)))
        );

        new BukkitRunnable() {
            @Override
            public void run() {
                if (ended) {
                    cancel();
                    onEnd();
                }
                tick();
            }
        }.runTaskTimer(plugin, 0, 1);
        return true;
    }

    /**
     * Ends the game
     */
    public void end() {
        this.ended = true;
    }

    /**
     * Start game logic, runs when the game starts.
     */
    protected abstract void onStart();

    /**
     * End game logic, runs when the game ends.
     */
    protected abstract void onEnd();

    /**
     * Game Tick logic, runs every tick when started.
     */
    protected abstract void tick();

    public boolean hasEnded() {
        return this.ended;
    }

    public void broadcast(Component text) {
        Bukkit.broadcast(this.broadcastPrefix.append(text));
    }

}
