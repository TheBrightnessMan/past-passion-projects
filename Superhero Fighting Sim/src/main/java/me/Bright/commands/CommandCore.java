package me.Bright.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CommandCore implements CommandExecutor {

    protected final String noPerm = ChatColor.RED + "You do not have permission to execute this command!";
    protected final String namePrefix = ChatColor.WHITE + "BrightPlugin " + ChatColor.DARK_GRAY + "» ";
    protected final String unknownCommand = ChatColor.AQUA + "===============" + "\n" +
            ChatColor.GOLD + "kits" + ChatColor.WHITE + ": " + ChatColor.GREEN + "Opens the kits gui" + "\n" +

            ChatColor.AQUA + "===============";
    private final String mainCommand;

    public CommandCore(String mainCommand) {
        this.mainCommand = mainCommand;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase(mainCommand)) {
            return true;
        }
        if (sender instanceof Player) {
            commandSentAsPlayer((Player) sender, command.getName(), args);
        } else {
            commandSentAsConsole(command.getName(), args);
        }
        return true;
    }

    public abstract void commandSentAsPlayer(Player sender, String mainCommand, String[] args);

    public abstract void commandSentAsConsole(String mainCommand, String[] args);
}