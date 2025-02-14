package me.Bright.items.kit.thor;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.utils.ProjectileMotion;
import me.Bright.utils.Utils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Bifrost extends ItemBuilder {

    public Bifrost() {
        super(Material.WHITE_WOOL, "&f&lBifrost", MouseClick.RIGHT_CLICK, "Summon Bifrost");
        setCooldown(0);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        if (player.getVehicle() != null) {
            return;
        }
        final Location target = player.getTargetBlock(null, 200).getLocation();

        new ProjectileMotion(player, target) {
            @Override
            public void executeAfterLanding(Location landing) {

            }
        };

        new BukkitRunnable() {
            private int r, g, b = 20;
            private int time = 59;

            //Spawn Rainbow Particles
            @Override
            public void run() {
                if (player.getVehicle().isDead()) {
                    time = 59;
                    cancel();
                }
                switch (time) {
                    case 59:
                        r = 255;
                        g = 0;
                        b = 0;
                        time -= 1;
                        break;
                    case 58:
                        r = 255;
                        g = 68;
                        b = 0;
                        time -= 1;
                        break;
                    case 57:
                        r = 255;
                        g = 111;
                        b = 0;
                        time -= 1;
                        break;
                    case 56:
                        r = 255;
                        g = 171;
                        b = 0;
                        time -= 1;
                        break;
                    case 55:
                        r = 255;
                        g = 255;
                        b = 0;
                        time -= 1;
                        break;
                    case 54:
                        r = 188;
                        g = 255;
                        b = 0;
                        time -= 1;
                        break;
                    case 53:
                        r = 128;
                        g = 255;
                        b = 0;
                        time -= 1;
                        break;
                    case 52:
                        r = 43;
                        g = 255;
                        b = 0;
                        time -= 1;
                        break;
                    case 51:
                        r = 0;
                        g = 255;
                        b = 9;
                        time -= 1;
                        break;
                    case 50:
                        r = 0;
                        g = 255;
                        b = 51;
                        time -= 1;
                        break;
                    case 49:
                        r = 0;
                        g = 255;
                        b = 111;
                        time -= 1;
                        break;
                    case 48:
                        r = 0;
                        g = 255;
                        b = 162;
                        time -= 1;
                        break;
                    case 47:
                        r = 0;
                        g = 255;
                        b = 230;
                        time -= 1;
                        break;
                    case 46:
                        r = 0;
                        g = 239;
                        b = 255;
                        time -= 1;
                        break;
                    case 45:
                        r = 0;
                        g = 196;
                        b = 255;
                        time -= 1;
                        break;
                    case 44:
                        r = 0;
                        g = 173;
                        b = 255;
                        time -= 1;
                        break;
                    case 43:
                        r = 0;
                        g = 162;
                        b = 255;
                        time -= 1;
                        break;
                    case 42:
                        r = 0;
                        g = 137;
                        b = 255;
                        time -= 1;
                        break;
                    case 41:
                        r = 0;
                        g = 100;
                        b = 255;
                        time -= 1;
                        break;
                    case 40:
                        r = 0;
                        g = 77;
                        b = 255;
                        time -= 1;
                        break;
                    case 39:
                        r = 0;
                        g = 34;
                        b = 255;
                        time -= 1;
                        break;
                    case 38:
                        r = 17;
                        g = 0;
                        b = 255;
                        time -= 1;
                        break;
                    case 37:
                        r = 37;
                        g = 0;
                        b = 255;
                        time -= 1;
                        break;
                    case 36:
                        r = 68;
                        g = 0;
                        b = 255;
                        time -= 1;
                        break;
                    case 35:
                        r = 89;
                        g = 0;
                        b = 255;
                        time -= 1;
                        break;
                    case 34:
                        r = 102;
                        g = 0;
                        b = 255;
                        time -= 1;
                        break;
                    case 33:
                        r = 124;
                        g = 0;
                        b = 255;
                        time -= 1;
                        break;
                    case 32:
                        r = 154;
                        g = 0;
                        b = 255;
                        time -= 1;
                        break;
                    case 31:
                        r = 222;
                        g = 0;
                        b = 255;
                        time -= 1;
                        break;
                    case 30:
                        r = 255;
                        g = 0;
                        b = 247;
                        time -= 1;
                        break;
                    case 29:
                        r = 255;
                        g = 0;
                        b = 179;
                        time -= 1;
                        break;
                    case 28:
                        r = 255;
                        g = 0;
                        b = 128;
                        time = 59;
                        break;
                }
                Color color = Color.fromRGB(r, g, b);
                Particle.DustOptions dust = new Particle.DustOptions(color, 5);
                player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(),
                        0, 0, 0, 0, dust);
            }
        }.runTaskTimer(Utils.plugin, 0, 1);

    }

    @Override
    public void onLeftClick(Player player, PlayerInteractEvent event) {

    }

    @Override
    public void onPlayerLeftClickLivingEntity(Player player, LivingEntity livingEntity, EntityDamageByEntityEvent
            event) {

    }

    @Override
    public void onPlayerRightClickLivingEntity(Player player, LivingEntity livingEntity, PlayerInteractEntityEvent
            event) {

    }
}
