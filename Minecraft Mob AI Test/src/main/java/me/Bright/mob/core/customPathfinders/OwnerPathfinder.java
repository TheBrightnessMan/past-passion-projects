package me.Bright.mob.core.customPathfinders;

import me.Bright.mob.core.brightMob.BrightMob;
import net.minecraft.server.v1_14_R1.EntityInsentient;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.PathEntity;
import net.minecraft.server.v1_14_R1.PathfinderGoal;
import org.bukkit.Bukkit;

import java.util.EnumSet;

public class OwnerPathfinder extends PathfinderGoal {

    private final EntityInsentient summon;
    private PathEntity pathEntity;

    private final double moveSpeed;
    private final float maxDistance = 10;

    public OwnerPathfinder(BrightMob<?> summon, double moveSpeed) {
        this.summon = summon;
        this.moveSpeed = moveSpeed;
        this.a(EnumSet.of(Type.MOVE));
    }

    @Override
    public boolean a() {
        EntityPlayer owner = (summon.getGoalTarget() instanceof EntityPlayer && summon.getGoalTarget().isAlive()) ?
                ((EntityPlayer) summon.getGoalTarget()) : null;
        if (owner == null) {
            return false;
        }

        double distanceFromOwnerToSummon = Math.sqrt(owner.h(summon));
        this.pathEntity = this.summon.getNavigation().a(owner, 3);

        if (distanceFromOwnerToSummon > maxDistance) {
            this.summon.setPosition(owner.locX, owner.locY, owner.locZ);
            return false;
        } else {
            return pathEntity != null;
        }
    }

    @Override
    public boolean b() {
        EntityPlayer owner = (summon.getGoalTarget() instanceof EntityPlayer && summon.getGoalTarget().isAlive()) ?
                ((EntityPlayer) summon.getGoalTarget()) : null;
        if (owner == null) {
            return false;
        }
        double distanceFromOwnerToSummon = Math.sqrt(owner.h(summon));
        return distanceFromOwnerToSummon > maxDistance;
    }

    @Override
    public void c() {
        summon.getNavigation().a(this.pathEntity, moveSpeed);
        this.summon.q(false);
    }

    @Override
    public void d() {
        EntityPlayer owner = (summon.getGoalTarget() instanceof EntityPlayer && summon.getGoalTarget().isAlive()) ?
                ((EntityPlayer) summon.getGoalTarget()) : null;
        if (owner == null) {
            return;
        }
        if (Math.sqrt(owner.h(summon)) > maxDistance) {
            this.summon.setPosition(owner.locX, owner.locY, owner.locZ);
        }
    }
}
