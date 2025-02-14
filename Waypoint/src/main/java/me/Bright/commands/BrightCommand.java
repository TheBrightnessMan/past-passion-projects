package me.Bright.commands;

import me.Bright.waypoint.Waypoint;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BrightCommand extends CommandCore {

    public BrightCommand() {
        super("bright");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String mainCommand, String[] args) {
        Waypoint waypoint = new Waypoint(new Location(sender.getWorld(), 0, 64, 0));
        waypoint.enable(sender);
    }

    @Override
    public void commandSentAsConsole(String mainCommand, String[] args) {

    }
}
