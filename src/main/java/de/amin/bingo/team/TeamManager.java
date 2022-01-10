package de.amin.bingo.team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class TeamManager {

    private final Scoreboard scoreboard;
    private final ArrayList<Team> teams;

    public TeamManager() {
        teams = new ArrayList<>();
         scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
         //unregister all existing teams
         scoreboard.getTeams().forEach(Team::unregister);
        for (BingoTeam team : BingoTeam.values()) {
            Team t = scoreboard.registerNewTeam(team.getNameKey());
            t.setColor(team.getColor());
            teams.add(t);
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

    public BingoTeam addToTeam(Player player, BingoTeam team) {
        Team t = scoreboard.getTeam(team.getNameKey());
        t.addEntry(player.getName());
        return team;
    }

    public BingoTeam addToSmallest(Player player) {
        return addToTeam(player, getSmallest());
    }

    private BingoTeam getSmallest() {
        BingoTeam team = null;
        int size = Integer.MAX_VALUE;
        for (BingoTeam t : BingoTeam.values()) {
            if(scoreboard.getTeam(t.getNameKey()).getSize()<size) {
                team = t;
                size = scoreboard.getTeam(t.getNameKey()).getSize();
            }
        }
        return team;
    }

    public Team getTeam(Material material) {
        BingoTeam team = null;
        //find team by material
        for (BingoTeam t : BingoTeam.values()) {
            if(t.getItem().equals(material)) {
                team = t;
            }
        }
        if(team==null)return null;
        return scoreboard.getTeam(team.getNameKey());
    }

    public Team getTeam(Player player) {
        if(teams.stream().filter(team -> team.hasPlayer(player)).findAny().isPresent()) {
            return teams.stream().filter(team -> team.hasPlayer(player)).findAny().get();
        }
        return null;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }
}
