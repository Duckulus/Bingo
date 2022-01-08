package de.amin.bingo.commands;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.gamestates.impl.PreState;
import de.amin.bingo.utils.Constants;
import de.amin.bingo.utils.Localization;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ForceStart implements CommandExecutor {

    private final BingoPlugin plugin;
    private final GameStateManager gameStateManager;

    public ForceStart(BingoPlugin plugin, GameStateManager gameStateManager) {
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(gameStateManager.getCurrentGameState() instanceof PreState)) {
            sender.sendMessage(ChatColor.RED + "This Command cannot be used at this Stage of the game!");
            return false;
        }

        PreState preState = (PreState) gameStateManager.getCurrentGameState();

        if(preState.getTime() < Constants.FORCESTART_TIME) {
            sender.sendMessage(ChatColor.RED + "The Game is already starting!");
            return false;
        }

        if(plugin.getServer().getOnlinePlayers().size()<Constants.MIN_PLAYERS) {
            sender.sendMessage(ChatColor.RED + "Not enough Players!");
            return false;
        }

        preState.setTime(Constants.FORCESTART_TIME);
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            player.sendMessage(Localization.get(player, "command.forcestart.message"));
        });

        return false;
    }
}
