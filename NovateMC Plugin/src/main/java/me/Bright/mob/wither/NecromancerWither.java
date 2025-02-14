package me.Bright.mob.wither;

import me.Bright.main.MainClass;
import me.Bright.utils.Utils;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class NecromancerWither extends BrightWither {

    public static HashMap<Player, NecromancerWither> playerList = new HashMap<>();
    private Plugin plugin = MainClass.getProvidingPlugin(MainClass.class);
    private static Player owner;
    static EntityLiving target = null;

    public NecromancerWither(Player player) {
        super(EntityTypes.WITHER, ((CraftWorld) player.getWorld()).getHandle());
        Location location = player.getLocation();
        this.setPosition(location.getX(), location.getY(), location.getZ());
        setInvulnerable(true);
        setOwner(player);
    }

    @Override
    protected void initPathfinder() {
        goalSelector.a(1, new WitherPathfinderGoal(this, 1.0, 5));
        goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
    }

    private void setOwner(Player player) {
        setGoalTarget(((CraftPlayer) player).getHandle(), EntityTargetEvent.TargetReason.FOLLOW_LEADER, false);
        setName("" + ChatColor.BLACK + ChatColor.BOLD + player.getName() + "'s Wither");
        owner = player;
        playerList.put(player, this);
    }

    public void setName(String name) {
        setCustomName(Utils.stringToIChatBaseComponent(name));
        setCustomNameVisible(true);
    }

    public void attack(org.bukkit.entity.Entity bukkitTarget) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (bukkitTarget.isDead() || NecromancerWither.super.dead) {
                    setGoalTarget(((CraftPlayer) owner).getHandle(), EntityTargetEvent.TargetReason.FOLLOW_LEADER, false);
                    target = null;
                    cancel();
                } else {
                    target = ((CraftLivingEntity) bukkitTarget).getHandle();
                    setGoalTarget(target, EntityTargetEvent.TargetReason.CLOSEST_ENTITY, false);
                }
            }
        }.runTaskTimer(plugin, 0, 25);
    }

}