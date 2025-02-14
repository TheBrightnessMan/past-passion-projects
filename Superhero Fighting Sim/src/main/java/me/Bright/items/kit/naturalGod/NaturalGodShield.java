package me.Bright.items.kit.naturalGod;

import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.utils.Forcefield;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class NaturalGodShield extends ItemBuilder {

    private static ArrayList<Player> forcefieldActive = new ArrayList<>();
    private static ArrayList<Player> onCD = new ArrayList<>();
    private static Forcefield forcefield;

    public NaturalGodShield() {
        super(Material.SHIELD,
                "&aShield of the Natural God",
                MouseClick.RIGHT_CLICK,
                "Forcefield");
        setCooldown(0);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        forcefield = new Forcefield(player, 5, Color.GREEN, onCD);
        if (onCD.contains(player)) {
            return;
        }
        forcefield.setActive(forcefieldActive.contains(player));
        forcefield.run();

    }

    @Override
    public void onLeftClick(Player player, PlayerInteractEvent event) {

    }

    @Override
    public void onPlayerLeftClickLivingEntity(Player player, LivingEntity livingEntity, EntityDamageByEntityEvent event) {

    }

    @Override
    public void onPlayerRightClickLivingEntity(Player player, LivingEntity livingEntity, PlayerInteractEntityEvent event) {

    }
}
