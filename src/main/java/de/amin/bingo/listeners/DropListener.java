package de.amin.bingo.listeners;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.gamestates.impl.MainState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class DropListener implements Listener {

    private GameStateManager gameStateManager;

    public DropListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(!(gameStateManager.getCurrentGameState()instanceof MainState)) {
            event.setCancelled(true);
        }

        ItemStack itemStack = event.getItemDrop().getItemStack();
        if(itemStack.getItemMeta()!=null && itemStack.getItemMeta().isUnbreakable()) {
            event.setCancelled(true);
            event.getItemDrop().remove();
            event.getPlayer().getInventory().setItem(45, event.getItemDrop().getItemStack());
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if(!(gameStateManager.getCurrentGameState()instanceof MainState)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getSlot()==45) {
            event.setCancelled(true);
        }
    }

}
