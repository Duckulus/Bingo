package de.amin.bingo.team;

import de.amin.bingo.utils.Config;
import de.amin.bingo.utils.ItemBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Team;

public class TeamGui implements Listener {

    public static final String GUI_TITLE = centerTitle("§bTeams");
    private final Inventory inventory;
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
            Team team = teamManager.getTeam(bingoTeam.getItem());
            inventory.setItem(i, new ItemBuilder(bingoTeam.getItem()).setName(bingoTeam.getColor() + bingoTeam.getLocalizedName(player))
                    .addLoreLine(Config.TEAM_LIMIT ?
                            "§8<§7" + team.getSize() + "/" + getMaxTeamSize() + "§8>"
                            : "§8<§7" + team.getSize() + " " + (team.getSize() == 1 ? "Player" : "Players") + "§8>")
                    .toItemStack());
            index++;
        }
    }

    private int getMaxTeamSize() {
        return (int) Math.ceil((double) Bukkit.getOnlinePlayers().size() / (double) BingoTeam.values().length);
    }


    public Inventory getInventory() {
        return inventory;
    }

    private static String centerTitle(String title) {
        return StringUtils.repeat(" ", 21 - ChatColor.stripColor(title).length()) + title;
    }
}
