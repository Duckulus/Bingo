package de.amin.bingo.listeners;

import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.gamestates.impl.MainState;
import de.amin.bingo.gamestates.impl.PreState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListeners implements Listener {

    private final GameStateManager gameStateManager;

    public BlockListeners(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!(gameStateManager.getCurrentGameState() instanceof MainState)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!(gameStateManager.getCurrentGameState() instanceof MainState)) {
            event.setCancelled(true);
        }
    }
}
