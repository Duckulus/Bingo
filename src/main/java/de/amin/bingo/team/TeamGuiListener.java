package de.amin.bingo.team;

import de.amin.bingo.utils.Localization;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scoreboard.Team;

public class TeamGuiListener implements Listener {

    private final TeamManager teamManager;

    public TeamGuiListener(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (!event.getView().getTitle().equals(TeamGui.GUI_TITLE));
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        Team scoreboardTeam = teamManager.getTeam(event.getCurrentItem().getType());

        if(isFull(scoreboardTeam.getSize())) {
            player.sendMessage(Localization.get(player, "team.full"));
            return;
        }

        BingoTeam team = teamManager.addToTeam(player, event.getCurrentItem().getType());

        if (team == null) {
            player.sendMessage(Localization.get(player, "team.not_found"));
            player.closeInventory();
            return;
        }

        player.sendMessage(Localization.get(player, "team.added"));
        player.closeInventory();
    }

    private boolean isFull(int teamSize) {
        double maxsize = Math.ceil((double) Bukkit.getOnlinePlayers().size() / (double) BingoTeam.values().length );
        return teamSize >= maxsize;
    }

}
