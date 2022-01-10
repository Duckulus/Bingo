package de.amin.bingo.commands;

import de.amin.bingo.team.TeamManager;
import de.amin.bingo.utils.Localization;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.Collections;
import java.util.List;

public class TeamChatCommand implements CommandExecutor, TabCompleter {

    private final TeamManager teamManager;
    private final String PREFIX = "§8[§cTeamChat§8]§r ";

    public TeamChatCommand(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))return false;
        Player player = (Player) sender;

        Team team = teamManager.getTeam(player);

        if(team==null) {
            player.sendMessage(Localization.get(player,"team.no_team"));
            return true;
        }

        Bukkit.getOnlinePlayers().forEach(all -> {
            if(teamManager.getTeam(all)!=null && teamManager.getTeam(all).equals(team)) {
                all.sendMessage(PREFIX + player.getDisplayName() + ": §r" + String.join(" ", args));
            }
        });

        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
