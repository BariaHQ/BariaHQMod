package me.bariahq.bariahqmod.command;

import me.bariahq.bariahqmod.rank.Rank;
import me.bariahq.bariahqmod.util.FLog;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@CommandPermissions(level = Rank.MOD, source = SourceType.BOTH)
@CommandParameters(description = "Validates if a given account is premium.", usage = "/<command> <player>", aliases = "prem")
public class Command_premium extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length != 1)
        {
            return false;
        }

        final Player player = getPlayer(args[0]);
        final String name;

        if (player != null)
        {
            name = player.getName();
        }
        else
        {
            name = args[0];
        }

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    final URL getUrl = new URL("https://axis.iaero.me/accstatus?username=" + name + "&format=plain&cache=0");
                    final URLConnection urlConnection = getUrl.openConnection();
                    // Read the response
                    final BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    final String message = ("free".equalsIgnoreCase(in.readLine()) ? ChatColor.RED + "No" : ChatColor.DARK_GREEN + "Yes");
                    in.close();

                    if (!plugin.isEnabled())
                    {
                        return;
                    }

                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            msg("Player " + name + " is premium: " + message);
                        }
                    }.runTask(plugin);

                }
                catch (Exception ex)
                {
                    FLog.severe(ex);
                    msg("There was an error querying the mojang server.", ChatColor.RED);
                }
            }
        }.runTaskAsynchronously(plugin);

        return true;
    }
}
