package me.Bright.mob;

import me.Bright.mob.core.brightMob.BrightMob;
import me.Bright.mob.core.customPathfinders.CustomRangedPathfinder;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

public class BrightSkeleton extends BrightMob<EntitySkeleton> implements IRangedEntity {

    public BrightSkeleton(Player owner) {
        super(owner, EntityTypes.SKELETON, 100);
    }

    @Override
    public void a(EntityLiving entityLiving, float v) {
        ItemStack itemstack = this.f(this.b(ProjectileHelper.a(this, Items.BOW)));
        EntityArrow entityarrow = new EntityArrow(EntityTypes.ARROW, this.getWorld()) {
            @Override
            protected ItemStack getItemStack() {
                return itemstack;
            }
        };
        double d0 = entityLiving.locX - this.locX;
        double d1 = entityLiving.getBoundingBox().minY + (double) (entityLiving.getHeight() / 3.0F) - entityarrow.locY;
        double d2 = entityLiving.locZ - this.locZ;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
        entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float) (14 - this.world.getDifficulty().a() * 4));
        EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(this, this.getItemInMainHand(), entityarrow, 0.8F);
        if (event.isCancelled()) {
            event.getProjectile().remove();
        } else {
            if (event.getProjectile() == entityarrow.getBukkitEntity()) {
                this.world.addEntity(entityarrow);
            }

            this.a(SoundEffects.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        }
    }

    @Override
    public void defineCustomPathfinder() {

    }
}
