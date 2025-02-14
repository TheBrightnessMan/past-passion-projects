package me.Bright.main;

import me.Bright.commands.member.KitCommand;
import me.Bright.items.ShadowFury;
import me.Bright.items.kit.brightKit.*;
import me.Bright.items.kit.drStrange.AstralProjection;
import me.Bright.items.kit.drStrange.DSEnergyBlast;
import me.Bright.items.kit.ironMan.IronManBeam;
import me.Bright.items.kit.naturalGod.NaturalGodShield;
import me.Bright.items.kit.naturalGod.NaturalGodSword;
import me.Bright.items.kit.scarletWitch.SWEnergyBlast;
import me.Bright.items.kit.scarletWitch.SWForcefield;
import me.Bright.items.kit.scarletWitch.SWTeleportation;
import me.Bright.items.kit.scarletWitch.Telekinesis;
import me.Bright.items.kit.thor.Bifrost;
import me.Bright.items.kit.thor.Stormbreaker;
import me.Bright.main.gui.KitGUI;
import me.Bright.utils.DoubleJump;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {

    public void onEnable() {
        regEvents(new Events());
        regEvents(new KitGUI());
        regEvents(new DoubleJump());

        regCommands();
        regItemListeners();
    }

    private void regCommands() {
        getCommand("kits").setExecutor(new KitCommand());
    }

    private void regItemListeners() {
        //Natural God
        regEvents(new NaturalGodShield(), new NaturalGodSword());

        //Scarlet Witch
        regEvents(new Telekinesis(), new SWEnergyBlast(), new SWForcefield(), new SWTeleportation());

        //Iron Man
        regEvents(new IronManBeam());

        //Thor
        regEvents(new Stormbreaker(), new Bifrost());

        //Dr Strange
        regEvents(new DSEnergyBlast(), new AstralProjection());

        //Bright Kit
        regEvents(new FancyFireball(), new ShockWave(), new BrightForceField(), new FocusedLaser(), new UndyingFlame());

        regEvents(new ShadowFury());
    }

    private void regEvents(Listener... listener) {
        for (Listener registerMe : listener) {
            getServer().getPluginManager().registerEvents(registerMe, this);
        }
    }

}
