package de.amin.bingo.utils;

import de.amin.bingo.BingoPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Localization {

    private static HashMap<String, YamlConfiguration> localizations = new HashMap<>();


    public static void load() {
        BingoPlugin plugin = BingoPlugin.INSTANCE;

        plugin.saveResource("localization/en_us.yml", false);
        plugin.saveResource("localization/de_de.yml", false);

        //register
        for (File f : new File(plugin.getDataFolder(), "localization").listFiles()) {
            try {
                YamlConfiguration config = new YamlConfiguration();
                config.load(f);
                localizations.put(f.getName().replace(".yml", ""), config);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public static String get(Player player, String key, String... replacements) {
        //look for localization in players locale or fallback to english if not found
        YamlConfiguration config = localizations.getOrDefault(player.getLocale(), localizations.get("en_us"));
        if (config.get(key) != null) {
            String localizedMessage = ChatColor.translateAlternateColorCodes('&', config.getString(key));
            for (int i = 0; i < replacements.length; i++) {
                localizedMessage = localizedMessage.replace("{" + i + "}", replacements[i]);
            }
            return localizedMessage;
        } else {
            return "MISSING " + player.getLocale() + ": " + key;
        }
    }

}
