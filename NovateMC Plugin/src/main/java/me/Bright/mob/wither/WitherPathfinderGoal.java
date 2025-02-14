package me.Bright.mob.wither;

import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.PathfinderGoal;

import java.util.EnumSet;

public class WitherPathfinderGoal extends PathfinderGoal {

    private final EntityInsentient a; // our pet
    private EntityLiving b; // owner

    private final double f; // pet's speed
    private final float g; // distance between owner & pet

    private double c; // x
    private double d; // y
    private double e; // z


    public WitherPathfinderGoal(EntityInsentient a, double speed, float distance) {
        this.a = a;
        this.f = speed;
        this.g = distance;
        this.a(EnumSet.of(Type.TARGET));
    }

    @Override
    public boolean a() {
        // Will start event if this is true
        // runs every tick
        this.b = this.a.getGoalTarget();
        if (this.b == null) {
            return false;
        } else if (this.a.getDisplayName() == null) {
            return false;
        } else if (!(this.a.getDisplayName().toString().contains(this.b.getName()))) {
            return false;
        } else if (this.b.h(this.a) > (double) (this.g * this.g)) {
            this.c = this.b.locX(); // x
            this.d = this.b.locY(); // y
            this.e = this.b.locZ(); // z
            return true;
            // call c()
        } else {
            return false;
        }
    }

    @Override
    public void c() {
        // runner :)                x      y        z    speed
        this.a.getNavigation().a(this.c, this.d, this.e, this.f);
//        Bukkit.broadcastMessage("c");
    }

    @Override
    public boolean b() {
        // run every tick as long as its true (repeats c)
//        Bukkit.broadcastMessage("b");
        return !this.a.getNavigation().m() && this.b.h(this.a) < (double) (this.g * this.g);

    }

    @Override
    public void d() {
        // runs when done (b is false)
//        Bukkit.broadcastMessage("d");
        this.b = null;
    }
}
