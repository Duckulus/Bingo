package de.amin.bingo.listeners;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.gamestates.impl.MainState;
import de.amin.bingo.gamestates.impl.PreState;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    private final GameStateManager gameStateManager;
    private final BingoPlugin plugin;

    public ConnectionListener(GameStateManager gameStateManager, BingoPlugin plugin) {
        this.gameStateManager = gameStateManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onConnect(AsyncPlayerPreLoginEvent event) {
        if (!(gameStateManager.getCurrentGameState() instanceof PreState)) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "This Game has already started!");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setFoodLevel(100);
        player.getInventory().clear();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (gameStateManager.getCurrentGameState() instanceof MainState && plugin.getServer().getOnlinePlayers().size() == 1) {
            plugin.getServer().shutdown();
        }

    }

}
