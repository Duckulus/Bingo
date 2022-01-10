package de.amin.bingo;

import de.amin.bingo.commands.BoardCommand;
import de.amin.bingo.commands.ForceStart;
import de.amin.bingo.commands.RerollCommand;
import de.amin.bingo.game.BingoGame;
import de.amin.bingo.game.board.map.BoardRenderer;
import de.amin.bingo.gamestates.GameState;
import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.listeners.*;
import de.amin.bingo.team.TeamManager;
import de.amin.bingo.utils.Localization;
import de.amin.bingo.team.TeamGuiListener;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class BingoPlugin extends JavaPlugin {

    public static BingoPlugin INSTANCE;

    @Override
    public void onLoad() {
        resetWorld();
    }

    @Override
    public void onEnable() {
        INSTANCE = this;

        Localization.load();
        getLogger().info(ChatColor.GREEN + "Plugin has been initialized");

        saveDefaultConfig();

        TeamManager  teamManager= new TeamManager();

        BingoGame game = new BingoGame(this, teamManager);
        BoardRenderer renderer = new BoardRenderer(this, teamManager, game);

        GameStateManager gameStateManager = new GameStateManager(this, game, renderer, teamManager);
        gameStateManager.setGameState(GameState.PRE_STATE);

        registerListeners(getServer().getPluginManager(), gameStateManager, teamManager);
        registerCommands(gameStateManager, game, renderer, teamManager);
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners(PluginManager pluginManager, GameStateManager gameStateManager, TeamManager teamManager) {
        pluginManager.registerEvents(new DamageListener(gameStateManager), this);
        pluginManager.registerEvents(new ConnectionListener(gameStateManager, this), this);
        pluginManager.registerEvents(new DeathListener(), this);
        pluginManager.registerEvents(new DropListener(gameStateManager), this);
        pluginManager.registerEvents(new PlayerInteractListener(gameStateManager, teamManager),this);
        pluginManager.registerEvents(new TeamGuiListener(teamManager), this);
    }

    private void registerCommands(GameStateManager gameStateManager, BingoGame game, BoardRenderer renderer, TeamManager teamManager) {
        getCommand("forcestart").setExecutor(new ForceStart(this, gameStateManager));
        getCommand("board").setExecutor(new BoardCommand(game, gameStateManager, teamManager));
        getCommand("reroll").setExecutor(new RerollCommand(this, game, renderer, gameStateManager));
    }

    private void resetWorld() {
        File propertiesFile = new File(Bukkit.getWorldContainer(), "server.properties");
        try (FileInputStream stream = new FileInputStream(propertiesFile)) {
            Properties properties = new Properties();
            properties.load(stream);

            // Getting and deleting the main world
            File world = new File(Bukkit.getWorldContainer(), properties.getProperty("level-name"));
            FileUtils.deleteDirectory(world);

            // Creating needed directories
            world.mkdirs();
            new File(world, "data").mkdirs();
            new File(world, "datapacks").mkdirs();
            new File(world, "entities").mkdirs();
            new File(world, "playerdata").mkdirs();
            new File(world, "poi").mkdirs();
            new File(world, "region").mkdirs();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
