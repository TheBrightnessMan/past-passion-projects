package me.bright.events;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class RemoveAiFromSpawnerMobs implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason spawnReason = event.getSpawnReason();
        ArrayList<CreatureSpawnEvent.SpawnReason> spawnReasons = new ArrayList<CreatureSpawnEvent.SpawnReason>() {{
            add(CreatureSpawnEvent.SpawnReason.SPAWNER);
            add(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
            add(CreatureSpawnEvent.SpawnReason.CUSTOM);
            add(CreatureSpawnEvent.SpawnReason.DEFAULT);
            add(CreatureSpawnEvent.SpawnReason.SLIME_SPLIT);
        }};
        if (!spawnReasons.contains(spawnReason)) {
            return;
        }
        LivingEntity entity = event.getEntity();
        WorldServer world = ((CraftWorld) entity.getWorld()).getHandle();

        EntityInsentient nmsEntity;

        if (entity instanceof Creature) {
            nmsEntity = ((CraftCreature) entity).getHandle();
        } else {
            if (entity.getType().equals(EntityType.SLIME)) {
                nmsEntity = ((CraftSlime) entity).getHandle();
            } else if (entity.getType().equals(EntityType.MAGMA_CUBE)) {
                nmsEntity = ((CraftMagmaCube) entity).getHandle();
            } else {
                return;
            }
        }

        MethodProfiler methodprofiler = world != null && world.methodProfiler != null ? world.methodProfiler : null;
        nmsEntity.goalSelector = new PathfinderGoalSelector(methodprofiler);
        nmsEntity.targetSelector = new PathfinderGoalSelector(methodprofiler);
    }

    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event) {
        if (event.getTarget() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) event.getEntity();
        Entity damager = event.getDamager();
        Vector velocity = damager.getLocation().toVector().subtract(livingEntity.getLocation().toVector()).normalize();
        livingEntity.setVelocity(velocity.multiply(0.8).setY(-1));
    }
}
