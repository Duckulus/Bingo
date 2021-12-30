package de.amin.bingo.gamestates.impl;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.gamestates.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;

public class EndState extends GameState {

    private BingoPlugin plugin;

    public EndState(BingoPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void start() {
        plugin.getServer().getOnlinePlayers().forEach(player -> player.setGameMode(GameMode.SPECTATOR));
        plugin.getServer().broadcastMessage(ChatColor.GRAY + "The Game has ended. The Server will close down shortly!");
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getServer().shutdown(),15*20);
    }

    @Override
    public void end() {

    }
}
