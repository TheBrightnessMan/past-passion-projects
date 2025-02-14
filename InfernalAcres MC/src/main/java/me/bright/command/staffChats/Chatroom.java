package me.bright.command.staffChats;

import me.bright.command.CommandCore;
import me.bright.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Chatroom extends CommandCore {

    private final String prefix;

    public Chatroom(String mainCommand, String permission, String prefix) {
        super(mainCommand, permission);
        this.prefix = prefix;
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        StringBuilder message = new StringBuilder();
        for (String messageFragment : args) {
            message.append(messageFragment);
        }
        Bukkit.broadcast(Utils.colorCodes(prefix + "&8» " + sender.getName() + ": &6" + message), super.permission);
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        StringBuilder message = new StringBuilder();
        for (String messageFragment : args) {
            message.append(messageFragment);
        }
        Bukkit.broadcast(Utils.colorCodes(prefix + "Console: &6" + message), super.permission);

    }

}
