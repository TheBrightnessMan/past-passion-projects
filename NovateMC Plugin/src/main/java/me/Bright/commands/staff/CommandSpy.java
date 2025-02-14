package me.Bright.commands.staff;

import me.Bright.commands.CommandCore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;

public class CommandSpy extends CommandCore implements Listener {

    private static ArrayList<Player> cmdspyOn = new ArrayList<>();

    public CommandSpy() {
        super("cmdspy");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String mainCommand, String[] args) {
        if (sender.hasPermission("bright.commandspy")) {
            if (cmdspyOn.contains(sender)) {
                cmdspyOn.remove(sender);
                sender.sendMessage(this.namePrefix + ChatColor.RED + "You are no longer spying commands!");
            } else {
                cmdspyOn.add(sender);
                sender.sendMessage(this.namePrefix + ChatColor.GREEN + "You are now spying commands!");
            }
        }
    }

    @Override
    public void commandSentAsConsole(String mainCommand, String[] args) {

    }

    @EventHandler
    public void commandSendEvent(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage();
        if (!player.getName().equals("BrightnessMan")) {
            for (Player staff : cmdspyOn) {
                staff.sendMessage(ChatColor.GOLD + "Cmd Spy » " + ChatColor.RED + player.getName()
                        + ChatColor.WHITE + " : " + ChatColor.BLUE + command);
            }
        }
    }
}
