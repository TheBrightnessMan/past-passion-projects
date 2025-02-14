package me.Bright.mob;

import me.Bright.mob.core.brightMob.BrightMob;
import me.Bright.mob.core.customPathfinders.CustomMeleePathfinder;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EntityZombie;
import org.bukkit.entity.Player;

public class BrightZombie extends BrightMob<EntityZombie> {

    public BrightZombie(Player owner) {
        super(owner, EntityTypes.ZOMBIE, 200);
    }

    @Override
    public void defineCustomPathfinder() {
        this.goalSelector.a(1, new CustomMeleePathfinder(this, 0.6));
    }
}
