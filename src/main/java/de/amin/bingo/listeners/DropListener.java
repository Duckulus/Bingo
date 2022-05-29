package de.amin.bingo.listeners;

import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.gamestates.impl.PreState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class DropListener implements Listener {

    private final GameStateManager gameStateManager;

    public DropListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (gameStateManager.getCurrentGameState() instanceof PreState && !player.getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }

        ItemStack itemStack = event.getItemDrop().getItemStack();
        if (itemStack.getItemMeta() != null && itemStack.getItemMeta().isUnbreakable()) {
            event.setCancelled(true);
            event.getItemDrop().remove();
            event.getPlayer().getInventory().setItem(45, event.getItemDrop().getItemStack());
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if(!(event.getEntity() instanceof Player))return;
        Player player = (Player) event.getEntity();
        if (gameStateManager.getCurrentGameState() instanceof PreState && !player.getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }

}
