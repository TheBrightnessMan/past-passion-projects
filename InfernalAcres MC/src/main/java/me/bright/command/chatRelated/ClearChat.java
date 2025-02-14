package me.bright.command.chatRelated;

import me.bright.command.CommandCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClearChat extends CommandCore {

    private final String clearChatBypass = "bright.clearchatbypass";

    public ClearChat() {
        super("clearchat", "bright.clearchat");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission(clearChatBypass)) {
                for (int i = 0; i < 100; i++) {
                    player.sendMessage("");
                }
            }
        }
    }

    @Override
    public void commandSentAsConsole(String[] args) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission(clearChatBypass)) {
                for (int i = 0; i < 100; i++) {
                    player.sendMessage("");
                }
            }
        }
    }
}
