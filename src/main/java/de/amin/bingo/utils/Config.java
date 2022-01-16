package de.amin.bingo.utils;

import de.amin.bingo.BingoPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;

public class Config {

    static FileConfiguration config = BingoPlugin.INSTANCE.getConfig();
    private static final double CURRENT_CONFIG_VERSION = 1.0;

    public static double CONFIG_VERSION = config.getDouble("configVersion");
    public static int PRESTATE_TIME = config.getInt("lobbyTime");
    public static int MIN_PLAYERS = config.getInt("minPlayers");
    public static int BORDER_SIZE = config.getInt("borderSize");
    public static int FORCESTART_TIME = config.getInt("forcestartTime");
    public static int GAME_DURATION = config.getInt("gameDuration");
    public static String DEFAULT_LOCALE = config.getString("defaultLocale");
    public static boolean TEAM_LIMIT = config.getBoolean("teamlimit");
    public static boolean PVP = config.getBoolean("pvp");
    public static boolean WORLD_RESET = config.getBoolean("worldReset");
    public static int BOARD_SIZE = 16;

    public static boolean isDeprecated() {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new File("plugins\\Bingo\\config.yml"));
        return CURRENT_CONFIG_VERSION>yamlConfiguration.getDouble("configVersion");
    }

}
