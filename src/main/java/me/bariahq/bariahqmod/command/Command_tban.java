package me.bariahq.bariahqmod.command;

import me.bariahq.bariahqmod.banning.Ban;
import me.bariahq.bariahqmod.config.ConfigEntry;
import me.bariahq.bariahqmod.punishment.Punishment;
import me.bariahq.bariahqmod.punishment.PunishmentType;
import me.bariahq.bariahqmod.rank.Rank;
import me.bariahq.bariahqmod.util.FUtil;
import net.pravian.aero.util.Ips;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.MOD, source = SourceType.BOTH)
@CommandParameters(description = "Temporarily bans a player for five minutes.", usage = "/<command> <player> [reason]", aliases = "noob")
public class Command_tban extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length < 1)
        {
            return false;
        }

        final Player player = getPlayer(args[0]);

        if (player == null)
        {
            msg(FreedomCommand.PLAYER_NOT_FOUND, ChatColor.RED);
            return true;
        }

        String reason;
        if (args.length > 1)
        {
            reason = StringUtils.join(args, " ", 1, args.length);
        }
        else
        {
            reason = "You have been temporarily banned for 5 minutes.";
        }

        // strike with lightning effect:
        final Location targetPos = player.getLocation();
        for (int x = -1; x <= 1; x++)
        {
            for (int z = -1; z <= 1; z++)
            {
                final Location strike_pos = new Location(targetPos.getWorld(), targetPos.getBlockX() + x, targetPos.getBlockY(), targetPos.getBlockZ() + z);
                targetPos.getWorld().strikeLightning(strike_pos);
            }
        }

        FUtil.staffAction(sender.getName(), "Tempbanning: " + player.getName() + " for 5 minutes.", true);
        plugin.bm.addBan(Ban.forPlayer(player, sender, FUtil.parseDateOffset("5m"), reason));

        player.kickPlayer(ChatColor.RED + "You have been temporarily banned for five minutes. Please read " + ConfigEntry.SERVER_BAN_URL.getString() + " for more info.");
        plugin.pul.logPunishment(new Punishment(player.getName(), Ips.getIp(player), sender.getName(), PunishmentType.TEMPBAN, reason));

        return true;
    }
}
