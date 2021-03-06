package me.bariahq.bariahqmod.command;

import com.earth2me.essentials.Essentials;
import me.bariahq.bariahqmod.rank.Rank;
import me.bariahq.bariahqmod.util.FUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

@CommandPermissions(level = Rank.SENIOR_ADMIN, source = SourceType.ONLY_CONSOLE, blockHostConsole = true)
@CommandParameters(description = "Removes essentials warps", usage = "/<command>")
public class Command_wipewarps extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!server.getPluginManager().isPluginEnabled("UMC-Essentials"))
        {
            msg("Essentials is not enabled on this server");
            return true;
        }

        Essentials essentials = plugin.esb.getEssentialsPlugin();
        File warps = new File(essentials.getDataFolder(), "warps");
        FUtil.staffAction(sender.getName(), "Wiping Essentials warps", true);
        FUtil.deleteFolder(warps);
        warps.mkdir();
        essentials.reload();
        msg("All warps deleted.");
        return true;
    }
}