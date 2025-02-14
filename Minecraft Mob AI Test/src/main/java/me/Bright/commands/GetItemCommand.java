package me.Bright.commands;

import me.Bright.item.ReaperScythe;
import org.bukkit.entity.Player;

public class GetItemCommand extends BrightCommand {

    public GetItemCommand() {
        super("getitem");
    }

    @Override
    public void commandSentAsPlayer(Player sender, String[] args) {
        if (args.length != 1) {
            return;
        }
        switch (args[0]) {
            case "reaperScythe":
                ReaperScythe scythe = new ReaperScythe();
                sender.getInventory().addItem(scythe);
                break;
        }
    }

    @Override
    public void commandSentAsConsole(String[] args) {

    }
}