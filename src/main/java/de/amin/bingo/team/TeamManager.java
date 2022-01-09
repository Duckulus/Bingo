package de.amin.bingo.team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamManager {

    private final Scoreboard scoreboard;

    public TeamManager() {
         scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
         //unregister all existing teams
         scoreboard.getTeams().forEach(Team::unregister);
        for (BingoTeam team : BingoTeam.values()) {
            Team t = scoreboard.registerNewTeam(team.getNameKey());
            t.setColor(team.getColor());
        }
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setScoreboard(scoreboard);
        });
    }

    public BingoTeam addToTeam(Player player, Material material) {
        BingoTeam team = null;
        //find team by material
        for (BingoTeam t : BingoTeam.values()) {
            if(t.getItem().equals(material)) {
                team = t;
            }
        }
        if(team==null)return null;
        scoreboard.getTeam(team.getNameKey()).addEntry(player.getName());
        player.setDisplayName(team.getColor() + player.getName() + ChatColor.RESET);

        return team;
    }

}
