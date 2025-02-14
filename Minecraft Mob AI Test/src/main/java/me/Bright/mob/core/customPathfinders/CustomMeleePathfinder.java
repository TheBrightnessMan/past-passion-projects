package me.Bright.mob.core.customPathfinders;

import me.Bright.mob.core.brightMob.BrightMob;
import net.minecraft.server.v1_14_R1.*;

public class CustomMeleePathfinder extends PathfinderGoalMeleeAttack {

    private final EntityCreature summon;
    private final Double moveSpeed;
    private PathEntity pathEntity;

    public CustomMeleePathfinder(BrightMob summon, double moveSpeed) {
        super(summon, moveSpeed, true);
        this.summon = summon;
        this.moveSpeed = moveSpeed;
    }

    @Override
    public boolean a() {
        EntityLiving target = (!(this.summon.getGoalTarget() instanceof EntityPlayer) && (this.summon.getGoalTarget().isAlive())) ?
                this.summon.getGoalTarget() : null;
        if (target == null) {
            return false;
        }
        this.pathEntity = this.summon.getNavigation().a(target, 0);
        return pathEntity != null;
    }

    @Override
    public boolean b() {
        EntityLiving target = (!(this.summon.getGoalTarget() instanceof EntityPlayer) && (this.summon.getGoalTarget().isAlive())) ?
                this.summon.getGoalTarget() : null;
        if (target == null) {
            return false;
        }
        return target.isAlive();
    }

    @Override
    public void c() {
        this.summon.getNavigation().a(this.pathEntity, moveSpeed);
        this.summon.q(true);
    }

    @Override
    public void d() {

    }
}
