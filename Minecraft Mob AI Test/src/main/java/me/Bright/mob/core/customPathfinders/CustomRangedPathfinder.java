package me.Bright.mob.core.customPathfinders;

import net.minecraft.server.v1_14_R1.*;

import java.lang.reflect.Field;

@Deprecated
public class CustomRangedPathfinder extends PathfinderGoalArrowAttack {

    private final EntityInsentient summon;

    public CustomRangedPathfinder(IRangedEntity summon, double moveSpeed,
                                  int attackSpeed, float attackRange) {
        super(summon, moveSpeed, attackSpeed, attackRange);
        this.summon = (EntityInsentient) summon;
    }

    @Override
    public boolean a() {
        EntityLiving target = (!(this.summon.getGoalTarget() instanceof EntityPlayer) && (this.summon.getGoalTarget().isAlive())) ?
                this.summon.getGoalTarget() : null;
        if (target != null && target.isAlive()) {
            try {
                Field targetField = PathfinderGoalArrowAttack.class.getDeclaredField("c");
                targetField.setAccessible(true);
                targetField.set(this, target);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}
