package de.amin.bingo.game.board;

import org.bukkit.Material;

public class BingoItem {

    private final BingoMaterial item;
    private boolean found = false;

    public BingoItem(BingoMaterial item) {
        this.item = item;
    }

    public Material getMaterial() {
        return item.getMaterial();
    }

    public BingoMaterial getBingoMaterial() {return item;}

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }
}
