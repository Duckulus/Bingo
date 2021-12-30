package de.amin.bingo.game;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.game.board.BingoBoard;
import de.amin.bingo.game.board.BingoItem;
import de.amin.bingo.game.board.BingoMaterial;
import de.amin.bingo.game.board.map.BoardRenderer;
import de.amin.bingo.utils.Constants;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class BingoGame {

    private BingoPlugin plugin;
    private HashMap<UUID, BingoBoard> boards;
    BingoMaterial[] items = new BingoMaterial[Constants.BOARD_SIZE];
    private BoardRenderer renderer;

    public BingoGame(BingoPlugin plugin) {
        this.plugin = plugin;
        boards = new HashMap<>();
    }

    public void createBoards() {

        //generation of random items
        for (int i = 0; i < items.length; i++) {
            BingoMaterial bingoMaterial = getRandomMaterial();
            while (Arrays.asList(items).contains(bingoMaterial)) {
                bingoMaterial = getRandomMaterial();
            }
            items[i] = bingoMaterial;
        }

        plugin.getServer().getOnlinePlayers().forEach(player -> {
            BingoBoard board = new BingoBoard(items);
            boards.put(player.getUniqueId(), board);
        });
    }

    public boolean checkWin(Player player) {
        int[][] winSituations = new int[][]{
                //horizonzal
                {0,1,2,3},
                {4,5,6,7},
                {8,9,10,11},
                {12,13,14,15},
                //vertical
                {0,4,8,12},
                {1,5,9,13},
                {2,6,10,14},
                {3,7,11,15},
                //diagonal
                {0,5,10,15},
                {3,6,9,12}
        };
        for (int[] winSituation : winSituations) {
            boolean win = true;
            for (int i : winSituation) {
                if(!getBoard(player).getItems()[i].isFound()) {
                    win = false;
                }
            }
            if(win) {
                return true;
            }
        }
        return false;
    }

    public BingoBoard getBoard(Player player) {
        return boards.get(player.getUniqueId());
    }

    public BingoMaterial[] getItems() {
        return items;
    }

    public BingoMaterial getRandomMaterial() {
        return BingoMaterial.values()[new Random().nextInt(BingoMaterial.values().length)];
    }

}
