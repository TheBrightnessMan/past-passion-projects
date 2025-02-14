package me.Bright.items.kit.drStrange;

import me.Bright.items.kit.scarletWitch.Telekinesis;
import me.Bright.items.mainStuff.ItemBuilder;
import me.Bright.items.mainStuff.MouseClick;
import me.Bright.utils.Bar;
import me.Bright.utils.CountDown;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class AstralProjection extends ItemBuilder {

    public static HashMap<Player, NPC> map = new HashMap<>();

    public AstralProjection() {
        super(Material.ARMOR_STAND,
                "&f&lAstral Projection",
                MouseClick.RIGHT_CLICK,
                "Enter the Astral Plane");
    }

    @Override
    public void onRightClick(Player player, PlayerInteractEvent event) {
        if (!map.containsKey(player)) {
            NPC playerNPC = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, player.getName());
            map.put(player, playerNPC);
            playerNPC.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);
            playerNPC.spawn(player.getLocation());
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 10, false, false, false));
            player.getInventory().clear();
        }
        final double TIME = 10;
        new Bar(player, "AstralProjection Time Left", 100 / TIME);
        new CountDown((int) (TIME)) {
            @Override
            public void run() {
                try {
                    player.setGameMode(GameMode.SURVIVAL);
                    map.get(player).destroy();
                    player.teleport(map.get(player).getEntity().getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    map.remove(player);
                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    DrStrangeArmor.equipArmor(player);
                    player.getInventory().addItem(new DSEnergyBlast(), new Telekinesis(), new AstralProjection());
                } catch (NullPointerException e) {
                    map.remove(player);
                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    DrStrangeArmor.equipArmor(player);
                    player.getInventory().addItem(new DSEnergyBlast(), new Telekinesis(), new AstralProjection());
                }
            }
        };
    }

    @EventHandler
    public void onNPCDeath(NPCDeathEvent event) {
        event.getNPC().destroy();
    }

    @EventHandler
    public void entityHurt(EntityDamageByEntityEvent event) {
        if (CitizensAPI.getNPCRegistry().isNPC(event.getEntity())) {
            NPC damagedNPC = CitizensAPI.getNPCRegistry().getNPC(event.getEntity());
            ((LivingEntity) damagedNPC.getEntity()).setHealth(20);
            Player player = Bukkit.getPlayer(damagedNPC.getFullName());
            player.damage(event.getDamage());
        }
    }

    @EventHandler
    public void onEntityDeath(PlayerDeathEvent event) {
        if (event.getEntity().getName().contains("CIT-")) {
            event.setDeathMessage("");
        }
        if (map.containsKey(event.getEntity())) {
            map.get(event.getEntity()).destroy();
        }
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
