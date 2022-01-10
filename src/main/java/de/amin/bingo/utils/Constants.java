package de.amin.bingo.utils;

import de.amin.bingo.BingoPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class Constants {

    static FileConfiguration config = BingoPlugin.INSTANCE.getConfig();

    public static int PRESTATE_TIME = config.getInt("lobbyTime");
    public static int MIN_PLAYERS = config.getInt("minPlayers");
    public static int BORDER_SIZE = config.getInt("borderSize");
    public static int FORCESTART_TIME = config.getInt("forcestartTime");
    public static int GAME_DURATION = config.getInt("gameDuration");
    public static String DEFAULT_LOCALE = config.getString("defaultLocale");
    public static boolean TEAM_LIMIT = config.getBoolean("teamlimit");
    public static int BOARD_SIZE = 16;
}
