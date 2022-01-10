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
import org.bukkit.entity.Player;

public class ForceStart implements CommandExecutor {

    private final BingoPlugin plugin;
    private final GameStateManager gameStateManager;

    public ForceStart(BingoPlugin plugin, GameStateManager gameStateManager) {
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))return false;
        Player player = (Player) sender;
        if(!(gameStateManager.getCurrentGameState() instanceof PreState)) {
            sender.sendMessage(Localization.get(player,"command.not_now"));
            return false;
        }

        PreState preState = (PreState) gameStateManager.getCurrentGameState();

        if(preState.getTime() < Constants.FORCESTART_TIME) {
            sender.sendMessage(Localization.get(player,"command.forcestart.already_starting"));
            return false;
        }

        if(plugin.getServer().getOnlinePlayers().size()<Constants.MIN_PLAYERS) {
            sender.sendMessage(Localization.get(player,"command.forcestart.not_enough_players"));
            return false;
        }

        preState.setTime(Constants.FORCESTART_TIME);
        plugin.getServer().getOnlinePlayers().forEach(p -> {
            p.sendMessage(Localization.get(p, "command.forcestart.message"));
        });

        return false;
    }
}
