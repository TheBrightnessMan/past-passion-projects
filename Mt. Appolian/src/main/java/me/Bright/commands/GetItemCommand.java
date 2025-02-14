package me.Bright.commands;

import me.Bright.items.ZeusBow;
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
            case "ZeusBow":
                ZeusBow zeusBow = new ZeusBow();
                sender.getInventory().addItem(zeusBow);
                break;
        }
    }

    @Override
    public void commandSentAsConsole(String[] args) {

    }
}