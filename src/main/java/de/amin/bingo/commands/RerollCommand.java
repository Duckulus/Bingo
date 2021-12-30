package de.amin.bingo.commands;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.game.BingoGame;
import de.amin.bingo.game.board.map.BoardRenderer;
import de.amin.bingo.gamestates.GameState;
import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.gamestates.impl.PreState;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
        if(gameStateManager.getCurrentGameState() instanceof PreState) {
            sender.sendMessage(ChatColor.RED + "The board has not been generated yet!");
            return false;
        }

        plugin.getServer().broadcastMessage(ChatColor.DARK_GRAY + "The board has been rerolled!");
        plugin.getServer().getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_BREAK,1,1));
        game.createBoards();
        renderer.updateImages();
        return false;
    }
}
