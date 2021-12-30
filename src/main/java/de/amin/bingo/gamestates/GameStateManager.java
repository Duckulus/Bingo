package de.amin.bingo.gamestates;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.game.BingoGame;
import de.amin.bingo.game.board.map.BoardRenderer;
import de.amin.bingo.gamestates.impl.EndState;
import de.amin.bingo.gamestates.impl.MainState;
import de.amin.bingo.gamestates.impl.PreState;

public class GameStateManager {

    private GameState currentGameState;
    private final GameState[] gameStates;
    private final BoardRenderer renderer;

    public GameStateManager(BingoPlugin plugin, BingoGame game, BoardRenderer renderer) {
        currentGameState = null;
        this.renderer = renderer;

        //initialization of all GameStates insinde an Array
        gameStates = new GameState[3];
        gameStates[GameState.PRE_STATE] = new PreState(plugin, this);
        gameStates[GameState.MAIN_STATE] = new MainState(plugin, this, game, renderer);
        gameStates[GameState.END_STATE] = new EndState(plugin);
    }

    /**
     * Ends the current GameState and starts the one that has been provided
     *
     * @param gameStateID The ID of the new GameState. Use the static Integers of {@link GameState}
     */
    public void setGameState(int gameStateID) {
        if (currentGameState != null) {
            currentGameState.end();
        }

        currentGameState = gameStates[gameStateID];
        currentGameState.start();
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}
