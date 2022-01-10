package de.amin.bingo.commands;

import de.amin.bingo.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class BackPackCommand implements CommandExecutor {

    private TeamManager teamManager;
    private HashMap<Team, Inventory> backpacks;

    public BackPackCommand(TeamManager teamManager) {
        this.teamManager = teamManager;
        backpacks = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))return false;
        Player player = (Player) sender;

        Team team = teamManager.getTeam(player);

        Inventory inventory = null;

        if(backpacks.containsKey(team))  {
            inventory = backpacks.get(team);
        } else  {
            inventory = Bukkit.createInventory(null, 9, "Backpack");
            backpacks.put(team, inventory);
        }

        player.openInventory(inventory);
        return false;
    }
}
