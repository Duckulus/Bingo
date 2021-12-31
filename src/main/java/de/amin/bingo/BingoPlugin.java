package de.amin.bingo;

import de.amin.bingo.commands.BoardCommand;
import de.amin.bingo.commands.ForceStart;
import de.amin.bingo.commands.RerollCommand;
import de.amin.bingo.game.BingoGame;
import de.amin.bingo.game.board.map.BoardRenderer;
import de.amin.bingo.gamestates.GameState;
import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.listeners.*;
import fr.minuskube.inv.InventoryManager;
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
        getLogger().info(ChatColor.GREEN + "Plugin has been initialized");

        INSTANCE = this;

        saveDefaultConfig();

        BingoGame game = new BingoGame(this);
        BoardRenderer renderer = new BoardRenderer(this, game);

        GameStateManager gameStateManager = new GameStateManager(this, game, renderer);
        gameStateManager.setGameState(GameState.PRE_STATE);

        //Initialization of InventoryManager for SmartInvs
        InventoryManager inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        registerListeners(getServer().getPluginManager(), gameStateManager);
        registerCommands(gameStateManager, game, renderer);
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners(PluginManager pluginManager, GameStateManager gameStateManager) {
        pluginManager.registerEvents(new BlockListeners(gameStateManager), this);
        pluginManager.registerEvents(new DamageListener(gameStateManager), this);
        pluginManager.registerEvents(new ConnectionListener(gameStateManager, this), this);
        pluginManager.registerEvents(new DeathListener(), this);
        pluginManager.registerEvents(new DropListener(gameStateManager), this);
    }

    private void registerCommands(GameStateManager gameStateManager, BingoGame game, BoardRenderer renderer) {
        getCommand("forcestart").setExecutor(new ForceStart(this, gameStateManager));
        getCommand("board").setExecutor(new BoardCommand(game, gameStateManager));
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
