package de.amin.bingo.listeners;

import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.gamestates.impl.MainState;
import de.amin.bingo.utils.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class DamageListener implements Listener {

    private final GameStateManager gameStateManager;

    public DamageListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(gameStateManager.getCurrentGameState() instanceof MainState)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(!Config.PVP) {
            if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEnemyTarget(EntityTargetEvent event) {
        if (!(gameStateManager.getCurrentGameState() instanceof MainState)) {
            event.setCancelled(true);
        }
    }

}
