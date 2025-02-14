package me.Bright.items.kit.scarletWitch;

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

public class SWForcefield extends ItemBuilder {

    private static ArrayList<Player> forcefieldActive = new ArrayList<>();

    public SWForcefield() {
        super(Material.SHIELD,
                "Forcefield",
                MouseClick.RIGHT_CLICK,
                "Forcefield Generation");
        setCooldown(0);
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        if (forcefieldActive.contains(player)) {
            forcefieldActive.remove(player);
        } else {
            forcefieldActive.add(player);
        }
        Forcefield forcefield = new Forcefield(player, 5, Color.RED, forcefieldActive);
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