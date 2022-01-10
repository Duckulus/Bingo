package de.amin.bingo.commands;

import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.gamestates.impl.PreState;
import de.amin.bingo.team.TeamManager;
import de.amin.bingo.utils.Localization;
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
    private GameStateManager gameStateManager;
    private HashMap<Team, Inventory> backpacks;

    public BackPackCommand(TeamManager teamManager, GameStateManager gameStateManager) {
        this.teamManager = teamManager;
        this.gameStateManager = gameStateManager;
        backpacks = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))return false;
        Player player = (Player) sender;

        if(gameStateManager.getCurrentGameState() instanceof PreState) {
            player.sendMessage(Localization.get(player, "command.not_now"));
            return false;
        }


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
