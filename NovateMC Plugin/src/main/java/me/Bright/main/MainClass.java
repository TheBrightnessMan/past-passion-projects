package me.Bright.main;

import me.Bright.commands.member.KitCommand;
import me.Bright.commands.staff.BrightCommand;
import me.Bright.commands.staff.CommandSpy;
import me.Bright.commands.staff.MuteChat;
import me.Bright.commands.staff.adminChat.AdminChat;
import me.Bright.commands.staff.adminChat.ToggleAdminChat;
import me.Bright.commands.staff.staffChat.StaffChat;
import me.Bright.commands.staff.staffChat.ToggleStaffChat;
import me.Bright.items.HomingMissile;
import me.Bright.items.Hyperion;
import me.Bright.items.InstaKillStick;
import me.Bright.items.WorldEater;
import me.Bright.items.kit.naturalGod.NaturalGodShield;
import me.Bright.items.kit.naturalGod.NaturalGodSword;
import me.Bright.items.scrolls.HellFire;
import me.Bright.items.scrolls.MasterNecromancer;
import me.Bright.items.scrolls.Telekinesis;
import me.Bright.items.scrolls.ThunderLord;
import me.Bright.main.gui.CustomItemGUI;
import me.Bright.main.gui.KitGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClass extends JavaPlugin implements Listener {

    public void onEnable() {
        regEvents(new Events());
        regEvents(new CustomItemGUI());
        regEvents(new KitGUI());

        regCommands();
        regItemListeners();
    }

    private void regCommands() {
        //Chats
        getCommand("sc").setExecutor(new StaffChat());
        getCommand("ac").setExecutor(new AdminChat());

        //Toggling chats
        getCommand("sctoggle").setExecutor(new ToggleStaffChat());
        regEvents(new ToggleStaffChat());
        getCommand("actoggle").setExecutor(new ToggleAdminChat());
        regEvents(new ToggleAdminChat());

        getCommand("mutechat").setExecutor(new MuteChat());
        regEvents(new MuteChat());

        getCommand("cmdspy").setExecutor(new CommandSpy());
        regEvents(new CommandSpy());

        getCommand("bright").setExecutor(new BrightCommand());

        getCommand("kits").setExecutor(new KitCommand());
    }

    private void regItemListeners() {
        regEvents(new HomingMissile());
        regEvents(new InstaKillStick());
        regEvents(new Telekinesis());
        regEvents(new HellFire());
        regEvents(new MasterNecromancer());
        regEvents(new ThunderLord());
        regEvents(new NaturalGodSword());
        regEvents(new NaturalGodShield());
        regEvents(new WorldEater());
        regEvents(new Hyperion());

//        regEvents(new ThreeSlotBackPack());
    }

    private void regEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void regCommand(String command, CommandExecutor commandExecutor) {
        Logger logger = Bukkit.getLogger();
        getCommand(command).setExecutor(commandExecutor);
        logger.log(Level.INFO, command + " Command Registered");
    }

    private void regCommand(String command, CommandExecutor commandExecutor, TabCompleter tabCompleter) {
        Logger logger = Bukkit.getLogger();
        getCommand(command).setExecutor(commandExecutor);
        getCommand(command).setTabCompleter(tabCompleter);
        logger.log(Level.INFO, command + " Command Registered with Tab Complete");
    }
}
