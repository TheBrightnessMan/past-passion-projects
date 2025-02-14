package me.bright.command.punishments;

import me.bright.command.CommandCore;
import me.bright.playerData.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Unmute extends CommandCore {

    public Unmute() {
        super("unmute", "bright.unmute");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        unmutePlayer(sender, args);
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        unmutePlayer(null, args);
    }

    private void unmutePlayer(Player moderator, String[] args) {
        boolean silent = false;
        if (args.length >= 2) {
            silent = args[1].equalsIgnoreCase("-s");
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            if (moderator == null) {
                sendConsoleMessage("Unable to find player " + args[0]);
            } else {
                sendMessage(moderator, "Unable to find player " + args[0]);
            }
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        assert target != null;
        PlayerData.unMute(moderator, target, silent);

    }
}
