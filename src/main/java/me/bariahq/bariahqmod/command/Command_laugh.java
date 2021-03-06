package me.bariahq.bariahqmod.command;

import me.bariahq.bariahqmod.rank.Rank;
import me.bariahq.bariahqmod.util.FUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "When you just have to burst out laughing", usage = "/<command>", aliases = "lol")
public class Command_laugh extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        FUtil.bcastMsg(ChatColor.AQUA + plugin.esb.getDisplayName(playerSender.getName()) + ChatColor.AQUA + " has fell to the floor and began to laugh uncontrollably");
        return true;
    }
}
