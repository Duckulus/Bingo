package de.amin.bingo.game.board;

import de.amin.bingo.utils.Constants;

public class BingoBoard {

    private BingoItem[] bingoItems = new BingoItem[Constants.BOARD_SIZE];

    public BingoBoard(BingoMaterial[] items) {
        for (int i = 0; i < items.length; i++) {
            bingoItems[i] = new BingoItem(items[i]);
        }
    }

    public BingoItem[] getItems() {
        return bingoItems;
    }

    public int getFoundItems() {
        int count = 0;
        for (BingoItem bingoItem : bingoItems) {
            if(bingoItem.isFound()) count++;
        }
        return count;
    }
}
