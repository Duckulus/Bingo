package de.amin.bingo.commands;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.game.BingoGame;
import de.amin.bingo.game.board.map.BoardRenderer;
import de.amin.bingo.gamestates.GameState;
import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.gamestates.impl.MainState;
import de.amin.bingo.gamestates.impl.PreState;
import de.amin.bingo.utils.Constants;
import de.amin.bingo.utils.Localization;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RerollCommand implements CommandExecutor {

    private BingoPlugin plugin;
    private BingoGame game;
    private BoardRenderer renderer;
    private GameStateManager gameStateManager;

    public RerollCommand(BingoPlugin plugin, BingoGame game, BoardRenderer renderer, GameStateManager gameStateManager) {
        this.plugin = plugin;
        this.game = game;
        this.renderer = renderer;
        this.gameStateManager = gameStateManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))return false;
        Player player = (Player) sender;

        if(gameStateManager.getCurrentGameState() instanceof PreState) {
            player.sendMessage(Localization.get(player, "command.not_generated"));
            return false;
        }

        plugin.getServer().getOnlinePlayers().forEach(p -> {
            p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_BREAK,1,1);
            p.sendMessage(Localization.get(p, "command.reroll.message"));
        });

        if(gameStateManager.getCurrentGameState() instanceof MainState) {
            ((MainState) gameStateManager.getCurrentGameState()).setTime(Constants.GAME_DURATION);
        }

        game.createBoards();
        renderer.updateImages();
        return false;
    }
}
