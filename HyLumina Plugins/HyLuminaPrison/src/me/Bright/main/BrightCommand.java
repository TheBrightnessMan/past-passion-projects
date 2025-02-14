package me.Bright.main;

import me.Bright.utils.BrightUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public abstract class BrightCommand implements CommandExecutor {

    public static final String namePrefix = "&8[&b&lHY&f&lLumina&r&8]";
    protected final String noPerm = "&cYou do not have permission to execute this command!";
    protected final String invalidArgument = "&cInvalid Argument";
    protected final String notBrightError = "&cHold on, you're not Bright, &4get outta here";

    private final String mainCommand;
    private final String permission;
    private BrightCommandInterface brightCommandInterface;

    public BrightCommand(String mainCommand, String permission) {
        this.mainCommand = mainCommand;
        this.permission = permission;
    }

    public void passLambda(BrightCommandInterface brightCommandInterface) {
        this.brightCommandInterface = brightCommandInterface;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!command.getName().equalsIgnoreCase(mainCommand)) {
            return true;
        }
        if (sender instanceof Player) {
            if ((sender.hasPermission("bright." + this.permission))) {
                brightCommandInterface.runCommand((Player) sender, args);
            } else {
                sendMessage((Player) sender, this.noPerm);
            }
        } else {
            commandSentAsConsole(args);
        }
        return true;
    }

    public void sendMessage(@NotNull Player receiver, String message) {
        receiver.sendMessage(BrightUtils.colorCodes(namePrefix + " » &r" + message));
    }

    public void sendConsoleMessage(String message) {
        Bukkit.getLogger().log(Level.INFO, message);
    }

    public abstract void commandSentAsPlayer(Player sender, String[] args);

    public abstract void commandSentAsConsole(String[] args);
}
