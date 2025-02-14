package me.Bright.main;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.PacketPlayOutBlockBreakAnimation;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CustomBlock {

    private final int time;
    private int oldAnimation = 0;
    private double damage = -1;
    private final Block block;

    public CustomBlock(Block block, int timeToBreakInTicks) {
        this.time = timeToBreakInTicks;
        this.block = block;
    }

    public void increaseDamage(Player player, double multiplier) {
        if (this.isBroken()) {
            return;
        }
        this.damage += multiplier;
        int animation = getAnimation();

        if (animation != oldAnimation) {
            if (!isBroken()) {
                sendBreakPacket(animation, this.block);
            } else {
                breakBlock(player);
                return;
            }
        }
        oldAnimation = animation;
    }

    public boolean isBroken() {
        return (getAnimation() >= 10);
    }

    public void breakBlock(Player player) {
        destroyBlockObject();
        if (player == null) {
            return;
        }
        ((CraftPlayer) player).getHandle().playerInteractManager.breakBlock(getBlockPosition(this.block));
    }

    public void destroyBlockObject() {
        sendBreakPacket(-1, this.block);
        CustomBlockService customBlockService = new CustomBlockService();
        customBlockService.removeCustomBlock(this.block.getLocation());
    }

    public int getAnimation() {
        return (int) (this.damage / this.time * 11) - 1;
    }

    public void sendBreakPacket(int animation, Block block) {
        ((CraftServer) Bukkit.getServer()).getHandle().sendPacketNearby(null, block.getX(), block.getY(), block.getZ(), 120, ((CraftWorld) block.getLocation().getWorld()).getHandle().getWorldProvider().getDimensionManager(),
                new PacketPlayOutBlockBreakAnimation(getBlockEntityId(block), getBlockPosition(block), animation));
    }

    private BlockPosition getBlockPosition(Block block) {
        return new BlockPosition(block.getX(), block.getY(), block.getZ());
    }

    private int getBlockEntityId(Block block) {
        return ((block.getX() & 0xFFF) << 20 | (block.getZ() & 0xFFF) << 8) | (block.getY() & 0xFF);
    }
}
