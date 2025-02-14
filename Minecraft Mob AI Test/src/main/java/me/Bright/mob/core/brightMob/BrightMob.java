package me.Bright.mob.core.brightMob;

import me.Bright.main.Utils;
import me.Bright.mob.core.customPathfinders.OwnerPathfinder;
import net.minecraft.server.v1_14_R1.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;

public abstract class BrightMob<T extends EntityMonster> extends EntityMonster {

    public BrightMob(Player owner, EntityTypes<T> entityType, double damage) {
        super(entityType, ((CraftWorld) owner.getWorld()).getHandle());

        Location location = owner.getLocation();

        this.getWorld().addEntity(this);
        this.setPosition(location.getX(), location.getY(), location.getZ());

        String mobName = EntityTypes.getName(entityType).getKey();
        this.setCustomName(Utils.stringToIChatBaseComponent("&c" + owner.getName() + "'s " + StringUtils.capitalize(mobName)));
        this.setInvulnerable(true);

        ((CraftLivingEntity) this.getBukkitEntity()).getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);

        this.setGoalTarget(((CraftPlayer) owner).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true);
    }

    public abstract void defineCustomPathfinder();

    @Override
    public void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        defineCustomPathfinder();
        this.goalSelector.a(2, new OwnerPathfinder(this, 0.6));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
    }

    public void setTarget(LivingEntity target) {
        this.setGoalTarget(((CraftLivingEntity) target).getHandle(), EntityTargetEvent.TargetReason.CUSTOM, true);
    }

}
