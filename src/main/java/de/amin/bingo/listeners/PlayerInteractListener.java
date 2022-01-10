package de.amin.bingo.listeners;

import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.gamestates.impl.PreState;
import de.amin.bingo.team.TeamGui;
import de.amin.bingo.team.TeamManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    private GameStateManager gameStateManager;
    private TeamManager teamManager;

    public PlayerInteractListener(GameStateManager gameStateManager, TeamManager teamManager) {
        this.gameStateManager = gameStateManager;
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(gameStateManager.getCurrentGameState() instanceof PreState) {
            Player player = event.getPlayer();
            if(player.getInventory().getItemInMainHand().getType().equals(Material.ORANGE_BED)) {
                player.openInventory(new TeamGui(player, teamManager).getInventory());
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if(gameStateManager.getCurrentGameState() instanceof PreState && event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            event.setCancelled(true);
        }
    }

}
