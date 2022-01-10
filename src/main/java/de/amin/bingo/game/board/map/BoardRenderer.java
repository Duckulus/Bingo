package de.amin.bingo.game.board.map;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.game.BingoGame;
import de.amin.bingo.team.TeamManager;
import de.amin.bingo.utils.Constants;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class BoardRenderer extends MapRenderer {

    private final BingoPlugin plugin;
    private final TeamManager teamManager;
    private final BingoGame game;
    private Image[] images;
    private Image checkmark;

    public BoardRenderer(BingoPlugin plugin, TeamManager teamManager, BingoGame game) {
        this.teamManager = teamManager;
        this.game = game;
        this.plugin = plugin;
        images = new Image[Constants.BOARD_SIZE];
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        //make background transparent
        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
                canvas.setPixel(i, j, MapPalette.TRANSPARENT);
            }
        }

        drawGrid(canvas);
        drawImages(canvas, player);
    }

    private void drawImages(MapCanvas canvas, Player player) {
        int offset = 8;
        //the x and y coordniates of the positions on the grid
        int[][] imagePositions = new int[][]{
                //row 1
                {offset, offset},
                {32 + offset, offset},
                {64 + offset, offset},
                {96 + offset, offset},
                //row2
                {offset, 32 + offset},
                {32 + offset, 32 + offset},
                {64 + offset, 32 + offset},
                {96 + offset, 32 + offset},
                //row3
                {offset, 64 + offset},
                {32 + offset, 64 + offset},
                {64 + offset, 64 + offset},
                {96 + offset, 64 + offset},
                //row4
                {offset, 96 + offset},
                {32 + offset, 96 + offset},
                {64 + offset, 96 + offset},
                {96 + offset, 96 + offset},
        };

        for (int i = 0; i < images.length; i++) {
            canvas.drawImage(imagePositions[i][0], imagePositions[i][1], images[i]);

            //draw checkmark if item is already found
            if (game.getBoard(teamManager.getTeam(player)).getItems()[i].isFound()) {
                canvas.drawImage(imagePositions[i][0], imagePositions[i][1], checkmark);
            }
        }
    }

    private void drawGrid(MapCanvas canvas) {
        int width = 2;
        byte color = MapPalette.DARK_GRAY;

        //vertical lines
        drawRect(canvas, color, 32, 32 + width, 0, 128);
        drawRect(canvas, color, 64, 64 + width, 0, 128);
        drawRect(canvas, color, 96, 96 + width, 0, 128);

        //horizontal lines
        drawRect(canvas, color, 0, 128, 32, 32 + width);
        drawRect(canvas, color, 0, 128, 64, 64 + width);
        drawRect(canvas, color, 0, 128, 96, 96 + width);
    }

    private void drawRect(MapCanvas canvas, byte color, int fromX, int toX, int fromY, int toY) {
        for (int x = fromX; x < toX; x++) {
            for (int y = fromY; y < toY; y++) {
                canvas.setPixel(x, y, color);
            }
        }
    }


    public void updateImages() {
        try {
            for (int i = 0; i < game.getItems().length; i++) {
                images[i] = ImageIO.read(plugin.getResource("assets/textures/" + game.getItems()[i].getAsset()));
            }
            checkmark = ImageIO.read(plugin.getResource("assets/checkmark.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
