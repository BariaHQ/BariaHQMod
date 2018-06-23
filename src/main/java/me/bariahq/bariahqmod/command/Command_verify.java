package me.bariahq.bariahqmod.command;

import me.bariahq.bariahqmod.config.ConfigEntry;
import me.bariahq.bariahqmod.player.FPlayer;
import me.bariahq.bariahqmod.rank.Rank;
import me.bariahq.bariahqmod.staff.StaffMember;
import me.bariahq.bariahqmod.util.FUtil;
import net.pravian.aero.util.Ips;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Random;

@CommandPermissions(level = Rank.IMPOSTOR, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Sends a verification code to the player, or the player can input the sent code.", usage = "/<command> [code]")
public class Command_verify extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!plugin.dc.enabled)
        {
            msg("The discord verification system is currently disabled", ChatColor.RED);
            return true;
        }

        if (!plugin.al.isStaffImposter(playerSender))
        {
            msg("You are not an imposter, therefore you do not need to verify", ChatColor.RED);
            return true;
        }

        StaffMember staffMember = plugin.al.getEntryByName(playerSender.getName());

        if (staffMember.getDiscordID() == null)
        {
            msg("You do not have a discord account linked to your minecraft account, please verify the manual way.", ChatColor.RED);
            return true;
        }

        if (args.length < 1)
        {
            String code = "";
            Random random = new Random();
            for (int i = 0; i < 10; i++)
            {
                code += random.nextInt(10);
            }
            plugin.dc.VERIFY_CODES.add(code);
            plugin.dc.bot.getUserById(staffMember.getDiscordID()).openPrivateChannel().complete().sendMessage("A user with the ip `" + Ips.getIp(playerSender) + "` has sent a verification request. Please run the following in-game command: `/verify " + code + "`");
            msg("A verification code has been sent to your account, please copy the code and do /verify <code>", ChatColor.GREEN);
        }
        else
        {
            String code = args[0];
            if (!plugin.dc.VERIFY_CODES.contains(code))
            {
                msg("You have entered an invalid verification code", ChatColor.RED);
                return true;
            }
            else
            {
                plugin.dc.VERIFY_CODES.remove(code);
                FUtil.bcastMsg(playerSender.getName() + " has verified themself!", ChatColor.GOLD);
                FUtil.staffAction(ConfigEntry.SERVER_NAME.getString(), "Readding " + staffMember.getName() + " to the staff list", true);
                staffMember.setName(playerSender.getName());
                staffMember.addIp(Ips.getIp(playerSender));
                staffMember.setActive(true);
                staffMember.setLastLogin(new Date());
                plugin.al.save();
                plugin.al.updateTables();
                plugin.rm.updateDisplay(playerSender);
                final FPlayer fPlayer = plugin.pl.getPlayer(playerSender);
                if (fPlayer.getFreezeData().isFrozen())
                {
                    fPlayer.getFreezeData().setFrozen(false);
                    msg("You have been unfrozen.");
                }
            }
        }
        return true;
    }
}