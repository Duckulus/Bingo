package de.amin.bingo.gamestates.impl;

import de.amin.bingo.BingoPlugin;
import de.amin.bingo.gamestates.GameState;
import de.amin.bingo.gamestates.GameStateManager;
import de.amin.bingo.game.BingoGame;
import de.amin.bingo.game.board.BingoItem;
import de.amin.bingo.game.board.map.BoardRenderer;
import de.amin.bingo.utils.Constants;
import de.amin.bingo.utils.TimeUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.Random;

public class MainState extends GameState {

    private int time = Constants.GAME_DURATION;
    private int timerId;
    private final BingoPlugin plugin;
    private final GameStateManager gameStateManager;
    private final BingoGame game;
    private final BoardRenderer renderer;


    public MainState(BingoPlugin plugin, GameStateManager gameStateManager, BingoGame game, BoardRenderer renderer) {
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
        this.game = game;
        this.renderer = renderer;
    }

    @Override
    public void start() {
        game.createBoards();
        renderer.updateImages();
        ItemStack boardMap = getRenderedMapItem();

        plugin.getServer().getOnlinePlayers().forEach(player -> {
            player.setLevel(0);
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            player.setFoodLevel(100);
            player.getInventory().clear();
            player.getInventory().setItemInOffHand(boardMap);
            player.teleport(player.getWorld().getHighestBlockAt(new Random().nextInt(-25, 25), new Random().nextInt(-25, 25)).getLocation());

            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            player.sendMessage(ChatColor.AQUA + "The Game has started. Good Luck!");
        });

        WorldBorder border = plugin.getServer().getWorlds().get(0).getWorldBorder();
        border.setCenter(0,0);
        border.setSize(Constants.BORDER_SIZE);


        startTimer();
    }

    @Override
    public void end() {
        plugin.getServer().getScheduler().cancelTask(timerId);
    }

    private void startTimer() {
        timerId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if(time>0) {
                plugin.getServer().getOnlinePlayers().forEach(player -> {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(ChatColor.GREEN + TimeUtils.formatTime(time)).create());

                    //check for all players if they have a new item from the board
                    for (BingoItem item : game.getBoard(player).getItems()) {
                        if(!item.isFound()) {
                            for (ItemStack content : player.getInventory().getContents()) {
                                if(content!=null && content.getType().equals(item.getMaterial())) {
                                    item.setFound(true);
                                    plugin.getServer().broadcastMessage(ChatColor.YELLOW + player.getName() +
                                            " found an item! "
                                    + ChatColor.DARK_GRAY + "[" + game.getBoard(player).getFoundItems() + "/" + 9 + "]");
                                    plugin.getServer().getOnlinePlayers().forEach(all -> all.playSound(all.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP,1,1));
                                }
                            }
                        }
                    }

                    if(game.checkWin(player)) {
                        plugin.getServer().broadcastMessage(ChatColor.BLUE + "BINGO! " + player.getName() + " won the Game!");
                        gameStateManager.setGameState(GameState.END_STATE);
                    }
                    switch (time) {
                        case 30, 15, 10, 5, 3, 2, 1 -> {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                            player.sendMessage(ChatColor.GOLD + "The Game will end in "
                                    + ChatColor.RED + time + (time == 1 ? " Second" : " Seconds") + ChatColor.GOLD + "!");
                        }
                    }
                });

                time--;
            } else {
                plugin.getServer().broadcastMessage(ChatColor.RED + "No one finished, so there is no winner!");
                gameStateManager.setGameState(GameState.END_STATE);
            }
        },0, 20);

    }

    private ItemStack getRenderedMapItem() {
        ItemStack itemStack = new ItemStack(Material.FILLED_MAP);
        MapView view = Bukkit.createMap(Bukkit.getWorlds().get(0));
        //clear renderers one by one
        for(MapRenderer renderer : view.getRenderers())
            view.removeRenderer(renderer);

        view.addRenderer(renderer);
        MapMeta mapMeta = (MapMeta) itemStack.getItemMeta();
        mapMeta.setMapView(view);
        mapMeta.setUnbreakable(true);
        mapMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Bingo Board");
        itemStack.setItemMeta(mapMeta);
        return itemStack;
    }
}
