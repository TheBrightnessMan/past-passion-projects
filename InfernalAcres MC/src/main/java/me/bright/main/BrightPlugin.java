package me.bright.main;

import me.bright.command.Invsee;
import me.bright.command.chatRelated.ClearChat;
import me.bright.command.chatRelated.MuteChat;
import me.bright.command.punishments.*;
import me.bright.command.staffChats.AdminChat;
import me.bright.command.staffChats.BuilderChat;
import me.bright.command.staffChats.DevChat;
import me.bright.command.staffChats.StaffChat;
import me.bright.command.vanish.VanishCore;
import me.bright.command.vanish.VanishLevel;
import me.bright.command.vanish.VanishedItemPickUp;
import me.bright.listeners.GeneralListener;
import me.bright.listeners.XrayDetection;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BrightPlugin extends JavaPlugin {

    public void onEnable() {
        regCommand("mutechat", new MuteChat());
        regCommand("clearchat", new ClearChat());
        regCommand("invsee", new Invsee());
        regCommand("history", new History());
        regCommand("v", new VanishCore());
        regCommand("bp", new VanishedItemPickUp());
        regStaffChats();
        regPunishments();

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        regEvents(new GeneralListener(), new MuteChat(), new Mute(), new VanishCore(),
                new XrayDetection());
    }

    private void regPunishments() {
        regCommand("ban", new Ban());
        regCommand("tempban", new TempBan(), new TempPunishmentTabComplete());
        regCommand("unban", new Unban(), new UnbanTabComplete());

        regCommand("ipban", new IpBan());
        regCommand("unbanip", new UnIpBan(), new UnbanTabComplete());

        regCommand("mute", new Mute());
        regCommand("tempmute", new TempMute(), new TempPunishmentTabComplete());
        regCommand("unmute", new Unmute());
    }

    private void regStaffChats() {
        regCommand("sc", new StaffChat());
        regCommand("ac", new AdminChat());
        regCommand("bc", new BuilderChat());
        regCommand("dc", new DevChat());
    }

    private void regEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void regCommand(String command, CommandExecutor commandExecutor) {
        try {
            getCommand(command).setExecutor(commandExecutor);
        } catch (NullPointerException ignored) {
        }
    }

    private void regCommand(String command, CommandExecutor commandExecutor, TabCompleter tabCompleter) {
        try {
            getCommand(command).setExecutor(commandExecutor);
            getCommand(command).setTabCompleter(tabCompleter);
        } catch (NullPointerException ignored) {
        }
    }

}
