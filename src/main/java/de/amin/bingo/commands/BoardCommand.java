package de.amin.bingo.commands;

import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.game.board.BingoBoard;
import de.amin.bingo.game.BingoGame;
import de.amin.bingo.game.board.BingoItem;
import de.amin.bingo.gamestates.impl.PreState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoardCommand implements CommandExecutor {

    private final BingoGame game;
    private final GameStateManager gameStateManager;

    public BoardCommand(BingoGame game, GameStateManager gameStateManager) {
        this.game = game;
        this.gameStateManager = gameStateManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player))return false;

        if(gameStateManager.getCurrentGameState() instanceof PreState) {
            player.sendMessage(ChatColor.RED + "The board has not been generated yet!");
        }

        BingoBoard board = game.getBoard(player);
        if(board==null)return false;

        player.sendMessage(ChatColor.DARK_GRAY + "Your board:");
        for (int i = 0; i < board.getItems().length; i++) {
            //Material appears green if found, red if not
            BingoItem item = board.getItems()[i];
            player.sendMessage(ChatColor.GRAY + "" + (i+1) + ". " + (item.isFound() ? ChatColor.GREEN : ChatColor.RED) + item.getBingoMaterial().getName());
        }

        return false;
    }
}
