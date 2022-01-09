package de.amin.bingo.team;

import de.amin.bingo.utils.Localization;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public enum BingoTeam {
    BLUE("blue", ChatColor.BLUE, Material.BLUE_CANDLE),
    RED("red", ChatColor.RED, Material.RED_CANDLE),
    GREEN("green", ChatColor.GREEN, Material.GREEN_CANDLE),
    ORANGE("orange", ChatColor.GOLD, Material.ORANGE_CANDLE),

    CYAN("cyan", ChatColor.AQUA, Material.CYAN_CANDLE),
    PURPLE("purple", ChatColor.DARK_PURPLE, Material.PURPLE_CANDLE),
    PINK("pink", ChatColor.LIGHT_PURPLE, Material.PINK_CANDLE),
    BLACK("black", ChatColor.BLACK, Material.BLACK_CANDLE),
    ;

    private String nameKey;
    private ChatColor color;
    private Material item;

    BingoTeam(String nameKey, ChatColor color, Material item) {
        this.nameKey = nameKey;
        this.color = color;
        this.item = item;
    }

    public ChatColor getColor() {
        return color;
    }

    public Material getItem() {
        return item;
    }

    public String getNameKey() {
        return nameKey;
    }

    public String getLocalizedName(Player player) {
        return Localization.get(player, "team." + nameKey);
    }
}
