package me.bright.game;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;

import java.util.*;

public class KothGame extends Game {

    private List<ItemStack> rewards;
    private BoundingBox region;

    private Collection<? extends Player> onlinePlayers;
    private HashMap<Player, Integer> playerScore;
    private int count = 0;

    public static final String DURATION_PATH = "durationTicks";
    public static final String REWARDS_PATH = "kothRewards";
    public static final String CORNER1_PATH = "region.corner1";
    public static final String CORNER2_PATH = "region.corner2";
    public static final String TIME_PATH = "kothTime";
    private static final boolean RANDOM_REWARDS = true;
    private static KothGame instance;

    private KothGame() {
        super("KOTH");
        instance = this;
    }

    public static KothGame getInstance() {
        return instance == null ? new KothGame() : instance;
    }

    @Override
    protected void onStart() {
        count = plugin.getConfig().getInt(DURATION_PATH, 200);

        rewards = plugin.getConfig().getList(KothGame.REWARDS_PATH, new ArrayList<>()).stream()
                .map(str -> (ItemStack) str).toList();

        Location corner1 = plugin.getConfig().getLocation(CORNER1_PATH);
        Location corner2 = plugin.getConfig().getLocation(CORNER2_PATH);

        if (corner1 == null || corner2 == null) {
            throw new NullPointerException("KOTH Region not set!");
        }

        if (!corner1.getWorld().equals(corner2.getWorld())) {
            throw new IllegalArgumentException("The corners are in different worlds!");
        }

        region = new BoundingBox(
                corner1.x(), corner1.y(), corner1.z(),
                corner2.x(), corner2.y(), corner2.z()
        );

        onlinePlayers = new HashSet<>();
        onlinePlayers = plugin.getServer().getOnlinePlayers();

        playerScore = new HashMap<>(onlinePlayers.size());
    }

    @Override
    protected void onEnd() {
        var scores = getPlayerScore();
        final TextColor yellow = TextColor.fromHexString("#ffd700");
        final TextColor violet = TextColor.fromHexString("#b200ed");

        if (scores.size() == 0) {
            broadcast(Component.text("No one took part in KOTH!").color(violet));
            return;
        }

        var entry = scores.get(0);
        Player winner = entry.getKey();

        if (RANDOM_REWARDS && rewards.size() > 0) {
            winner.getInventory().addItem(rewards.get(new Random().nextInt(rewards.size())));
        } else if (!RANDOM_REWARDS) {
            winner.getInventory().addItem(rewards.toArray(ItemStack[]::new));
        }

        Component gameEndMessage;
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            String factionName = PlaceholderAPI.setPlaceholders(winner, "%factionsuuid_faction_name%");

            gameEndMessage = factionName.contains("no-faction") ?
                    Component.text("Player ").color(violet)
                            .append(Component.text(winner.getName()).color(yellow))
                            .append(Component.text(" has won the KOTH! They were not in any faction.")).color(violet)
                    :
                    Component.text("The faction ").color(violet)
                            .append(Component.text(factionName).color(yellow))
                            .append(Component.text(" has won the KOTH because of ").color(violet))
                            .append(Component.text(winner.getName()).color(yellow));
        } else {
            gameEndMessage = Component.text("Player ").color(violet)
                    .append(Component.text(winner.getName()).color(yellow))
                    .append(Component.text(" has won the KOTH!")).color(violet);
        }
        broadcast(gameEndMessage);
    }

    @Override
    protected void tick() {
        if (count < 0) {
            end();
            return;
        }

        for (Player player : onlinePlayers) {
            if (player.isDead()) {
                playerScore.put(player, 0);
                continue;
            }
            BoundingBox playerBoundingBox = player.getBoundingBox();
            if (!region.contains(playerBoundingBox)) {
                continue;
            }
            playerScore.put(player, playerScore.getOrDefault(player, 0) + 1);
        }

        count--;
    }

    public List<Map.Entry<Player, Integer>> getPlayerScore() {
        return playerScore.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).toList();
    }

    public int getRemainingTicks() {
        return count;
    }
}
