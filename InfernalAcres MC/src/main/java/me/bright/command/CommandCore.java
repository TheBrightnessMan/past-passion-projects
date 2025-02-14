package me.bright.command;

import me.bright.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CommandCore implements CommandExecutor {

    protected final String namePrefix = "&4Infernal&6Acres &8» ";
    protected final String noPerm = "&cYou do not have permission to execute this command!";
    protected final String error = "&cInvalid Argument";
    protected final String permission;

    private final String mainCommand;

    public CommandCore(String mainCommand, String permission) {
        this.mainCommand = mainCommand;
        this.permission = permission;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase(mainCommand)) {
            return true;
        }
        if (sender instanceof Player) {
            if (sender.hasPermission(this.permission)) {
                commandSentAsPlayer((Player) sender, args);
            } else {
                sendMessage((Player) sender, noPerm);
            }
        } else {
            commandSentAsConsole(args);
        }
        return true;
    }

    public void sendMessage(Player receiver, String message) {
        receiver.sendMessage(Utils.colorCodes(namePrefix + "&r" + message));
    }

    public void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(Utils.colorCodesSpecial(namePrefix + message));
    }

    protected abstract void commandSentAsPlayer(Player sender, String[] args);

    protected abstract void commandSentAsConsole(String[] args);
}