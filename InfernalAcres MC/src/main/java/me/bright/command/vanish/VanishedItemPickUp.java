package me.bright.command.vanish;

import me.bright.command.CommandCore;
import org.bukkit.entity.Player;

public class VanishedItemPickUp extends CommandCore {

    public VanishedItemPickUp() {
        super("bp", "bright.vanish.itempickup");
    }

    @Override
    protected void commandSentAsPlayer(Player sender, String[] args) {
        if (VanishCore.allowItemPickUp.contains(sender)) {
            VanishCore.allowItemPickUp.remove(sender);
            sendMessage(sender, "You have disabled block pick-up");
        } else {
            VanishCore.allowItemPickUp.add(sender);
            sendMessage(sender, "You have enabled block pick-up");
        }
    }

    @Override
    protected void commandSentAsConsole(String[] args) {

    }
}
