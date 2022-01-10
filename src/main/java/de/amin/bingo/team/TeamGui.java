package de.amin.bingo.team;

import de.amin.bingo.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TeamGui implements Listener {

    public static final String GUI_TITLE = "§bTeams";
    private Inventory inventory;
    private final TeamManager teamManager;
    private final Player player;

    public TeamGui(Player player, TeamManager teamManager) {
        this.teamManager = teamManager;
        this.player = player;
        inventory = Bukkit.createInventory(null, 9, GUI_TITLE);

        int index = 0;
        for (int i = 0; i < inventory.getSize(); i++) {
            if (i == 4) i++;
            BingoTeam bingoTeam = BingoTeam.values()[index];
            inventory.setItem(i, new ItemBuilder(bingoTeam.getItem()).setName(bingoTeam.getColor() + bingoTeam.getLocalizedName(player)).toItemStack());
            index++;
        }
    }


    public Inventory getInventory() {
        return inventory;
    }
}
