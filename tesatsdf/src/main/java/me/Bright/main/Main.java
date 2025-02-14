package me.Bright.main;

import me.Bright.items.*;
import me.Bright.items.powerstone.PowerStone;
import me.Bright.npc.NPCBright;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

    public void onEnable() {
//        regEvents(new PowerStone());
//        regEvents(new MindStone());
//        regEvents(new SpaceStone());
//        regEvents(new SoulStone());
//        regEvents(new RealityStone());
//        regEvents(new TimeStone());
//        regEvents(new InfinityGauntlet());
//        regEvents(new EmptyGauntlet());

        getServer().getPluginManager().registerEvents(this, this);

//        getCommand("thestones").setExecutor(new Commands());
        getCommand("spawnnpc").setExecutor(new Commands());

        regEvents(new NPCBright());
    }

    private void regEvents(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}