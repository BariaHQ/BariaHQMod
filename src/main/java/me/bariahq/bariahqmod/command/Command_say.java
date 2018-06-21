package me.bariahq.bariahqmod.command;

import me.bariahq.bariahqmod.rank.Rank;
import me.bariahq.bariahqmod.staff.StaffMember;
import me.bariahq.bariahqmod.util.FUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.MOD, source = SourceType.BOTH)
@CommandParameters(description = "Broadcasts the given message as the console, includes sender name.", usage = "/<command> <message>", aliases = "broadcast,bc")
public class Command_say extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            return false;
        }

        String message = StringUtils.join(args, " ");

        if (senderIsConsole && FUtil.isFromHostConsole(sender.getName()))
        {
            if (message.equalsIgnoreCase("WARNING: Server is restarting, you will be kicked"))
            {
                FUtil.bcastMsg("Server is going offline.", ChatColor.GRAY);

                for (Player player : server.getOnlinePlayers())
                {
                    player.kickPlayer("Server is going offline, come back in about 20 seconds.");
                }

                server.shutdown();

                return true;
            }
        }
        
        String color = "&d";
        
        if (!senderIsConsole)
        {
            StaffMember staffMember = plugin.al.getStaffMember(playerSender);
            if (staffMember.hasCustomShoutColor())
            {
                color = staffMember.getShoutColor();
            }
        }
        
        FUtil.bcastMsg(String.format("%s[Staff:%s] %s", FUtil.colorize(color), sender.getName(), message));

        return true;
    }
}
