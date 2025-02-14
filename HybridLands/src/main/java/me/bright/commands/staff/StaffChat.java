package me.bright.commands.staff;

import me.bright.commands.BrightCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class StaffChat extends BrightCommand {

    public StaffChat() {
        super("sc", "bright.sc");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        String message = Arrays.toString(args).replace("[", "")
                .replace("]", "")
                .replace(",", " ");
        Bukkit.broadcast(ChatColor.YELLOW + "[Staff Chat] " +
                ChatColor.RESET + sender.getName() + " » " +  message, this.permission);
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        String message = Arrays.toString(args).replace("[", "")
                .replace("]", "")
                .replace(",", " ");
        Bukkit.broadcast(ChatColor.YELLOW + "[Staff Chat] " +
                ChatColor.RESET + "Console » " +  message, this.permission);
    }
}
