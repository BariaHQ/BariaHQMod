package me.bariahq.bariahqmod.command;

import me.bariahq.bariahqmod.rank.Rank;
import me.bariahq.bariahqmod.util.FUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.MOD, source = SourceType.BOTH)
@CommandParameters(description = "Essentials Interface Command - Remove the nickname of all non-staff or all players on the server.", usage = "/<command> [-a]")
public class Command_denick extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        Boolean includeStaff = false;
        if (args.length > 0 && args[0].equals("-a"))
        {
            includeStaff = true;
        }
    
        FUtil.staffAction(sender.getName(), "Removing nicknames for all " + (includeStaff ? "players" : "non-staff"), false);

        for (Player player : server.getOnlinePlayers())
        {
            if (plugin.al.isAdmin(player) && !includeStaff)
            {
                continue;
            }
            plugin.esb.setNickname(player.getName(), null);
        }

        return true;
    }
}
