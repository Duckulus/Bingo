package de.amin.bingo.utils;

import de.amin.bingo.team.BingoTeam;
import de.amin.bingo.team.TeamGui;
import de.amin.bingo.team.TeamManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TeamGuiListener implements Listener {

    private final TeamManager teamManager;

    public TeamGuiListener(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getCurrentItem()==null)return;
        if(!event.getView().getTitle().equals(TeamGui.GUI_TITLE));
        Player player = (Player) event.getWhoClicked();
        BingoTeam team = teamManager.addToTeam(player, event.getCurrentItem().getType());

        if(team==null) {
            player.sendMessage(Localization.get(player, "team.not_found"));
            player.closeInventory();
            return;
        }

        player.sendMessage(Localization.get(player, "team.added"));
        player.closeInventory();
        event.setCancelled(true);
    }

}
