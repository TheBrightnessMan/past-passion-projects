package me.Bright.npc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.Bright.main.Utils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class NPCBright implements Listener {

    private NPC npc;
    private final String NAME = Utils.colorCodes("&4&l[BOSS] BrightnessMan");

    public NPCBright() {
        npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, NAME);
        npc.data().setPersistent(NPC.DEFAULT_PROTECTED_METADATA, false);
        npc.setUseMinecraftAI(true);
        npc.getOrAddTrait(SkinTrait.class).setSkinName("BrightnessMan");
    }

    public String getNAME() {
        return NAME;
    }

    public NPC getNpc() {
        return npc;
    }

    @EventHandler
    public void onNPCDeath(NPCDeathEvent event) {
        event.getNPC().destroy();
    }

    @EventHandler
    public void onEntityDeath(PlayerDeathEvent event) {
        if (event.getEntity().getName().contains("CIT-")) {
            event.setDeathMessage("");
        }
    }
}
