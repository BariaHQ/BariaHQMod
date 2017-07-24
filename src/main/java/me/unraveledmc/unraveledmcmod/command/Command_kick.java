package me.unraveledmc.unraveledmcmod.command;

import me.unraveledmc.unraveledmcmod.rank.Rank;
import me.unraveledmc.unraveledmcmod.util.FUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.MOD, source = SourceType.BOTH)
@CommandParameters(description = "Kick a player.", usage = "/<command> <player> [reason]", aliases = "k")
public class Command_kick extends FreedomCommand
{

    @Override
    protected boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            return false;
        }

        Player player = getPlayer(args[0]);
        if (player == null)
        {
            msg(PLAYER_NOT_FOUND);
            return true;
        }

        String reason = null;
        if (args.length > 1)
        {
            reason = StringUtils.join(args, " ", 1, args.length);
        }

        StringBuilder builder = new StringBuilder()
                .append(ChatColor.RED).append("You have been kicked from the server.")
                .append("\n").append(ChatColor.RED).append("Kicked by: ").append(ChatColor.GOLD).append(sender.getName());

        if (reason != null)
        {
            builder.append("\n").append(ChatColor.RED).append("Reason: ").append(ChatColor.GOLD).append(reason);
            FUtil.adminAction(sender.getName(), "Kicking " + player.getName() + " - Reason: " + reason, true);
        }
        else
        {
            FUtil.adminAction(sender.getName(), "Kicking " + player.getName(), true);
        }

        player.kickPlayer(builder.toString());
        return true;
    }

}
