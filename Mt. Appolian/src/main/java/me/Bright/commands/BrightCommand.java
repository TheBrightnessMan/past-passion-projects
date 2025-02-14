package me.Bright.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BrightCommand implements CommandExecutor {

    private final String mainCommand;

    public BrightCommand(String mainCommand) {
        this.mainCommand = mainCommand;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase(mainCommand)) {
            return true;
        }
        if (sender instanceof Player) {
            commandSentAsPlayer((Player) sender, args);
        } else {
            commandSentAsConsole(args);
        }
        return true;
    }

    public abstract void commandSentAsPlayer(Player sender, String[] args);

    public abstract void commandSentAsConsole(String[] args);
}
