package de.amin.bingo.gamestates;

public abstract class GameState {

    public static final int PRE_STATE = 0;
    public static final int MAIN_STATE = 1;
    public static final int END_STATE = 2;

    public abstract void start();
    public abstract void end();

}
