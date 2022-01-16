package de.amin.bingo.listeners;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.game.BingoGame;
import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.gamestates.impl.MainState;
import de.amin.bingo.gamestates.impl.PreState;
import de.amin.bingo.team.TeamManager;
import de.amin.bingo.utils.ItemBuilder;
import de.amin.bingo.utils.Localization;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    private final GameStateManager gameStateManager;
    private final TeamManager teamManager;
    private final BingoPlugin plugin;
    private final BingoGame game;

    public ConnectionListener(GameStateManager gameStateManager, TeamManager teamManager, BingoPlugin plugin, BingoGame game) {
        this.gameStateManager = gameStateManager;
        this.teamManager = teamManager;
        this.plugin = plugin;
        this.game = game;
    }

    @EventHandler
    public void onConnect(AsyncPlayerPreLoginEvent event) {
        if (!(this.gameStateManager.getCurrentGameState() instanceof PreState)) {
            if (!this.game.getRejoinPlayer().contains(event.getUniqueId())) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "This Game has already started!");
            }

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(gameStateManager.getCurrentGameState() instanceof PreState) {
            setup(player);
        }else {
            //because displayname resets after leaving
            player.setDisplayName(teamManager.getTeam(player).getColor() + player.getName());
        }

    }

    public static void setup(Player player) {
        player.setLevel(0);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setFoodLevel(100);
        player.getInventory().clear();
        player.teleport(player.getWorld().getSpawnLocation());
        player.getInventory().setItem(0, new ItemBuilder(Material.ORANGE_BED).setName(Localization.get(
                player, "team.item_name"
        )).toItemStack());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        if (!(gameStateManager.getCurrentGameState() instanceof PreState)) {
            this.game.getRejoinPlayer().add(event.getPlayer().getUniqueId());
        } else {
            teamManager.removeFromTeam(event.getPlayer());
        }

    }

}
