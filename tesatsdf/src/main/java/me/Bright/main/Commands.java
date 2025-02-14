package me.Bright.main;

import me.Bright.items.*;
import me.Bright.items.powerstone.PowerStone;
import me.Bright.npc.NPCBright;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.getName().equals("BrightnessMan")) {
                switch (command.getName()) {
                    case "thestones":
                        player.getInventory().addItem(new PowerStone());
                        player.getInventory().addItem(new MindStone());
                        player.getInventory().addItem(new SpaceStone());
                        player.getInventory().addItem(new SoulStone());
                        player.getInventory().addItem(new RealityStone());
                        player.getInventory().addItem(new TimeStone());
                        player.getInventory().addItem(new EmptyGauntlet());
                        player.getInventory().addItem(new InfinityGauntlet());
                        break;
                    case "spawnnpc":
                        NPCBright npcBright = new NPCBright();
                        NPC npc = npcBright.getNpc();
                        npc.spawn(player.getLocation());
                        npc.getNavigator().setTarget(player, true);
                }
            }
        }
        return true;
    }
}
