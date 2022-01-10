package de.amin.bingo.utils;

import de.amin.bingo.BingoPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.HashMap;

public class Localization {

    private static HashMap<String, YamlConfiguration> localizations = new HashMap<>();
    private static BingoPlugin plugin = BingoPlugin.INSTANCE;

    public static void load() {
        //copy localization rescource bundle from jar into plugin data folder
        try {
            copyFromJar("localization/", Paths.get("plugins/Bingo/localization/"));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        //register configurations for language bundle files
        if (new File(plugin.getDataFolder(), "localization").listFiles() == null) {
            return;
        }
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
        YamlConfiguration config = localizations.getOrDefault(player.getLocale(), localizations.get(Constants.DEFAULT_LOCALE));
        if (config != null && config.get(key) != null) {
            String localizedMessage = ChatColor.translateAlternateColorCodes('&', config.getString(key));
            for (int i = 0; i < replacements.length; i++) {
                localizedMessage = localizedMessage.replace("{" + i + "}", replacements[i]);
            }
            return localizedMessage;
        } else {
            return "MISSING " + player.getLocale() + ": " + key;
        }
    }

    public static void copyFromJar(String source, final Path target) throws URISyntaxException, IOException {
        URI resource = BingoPlugin.class.getResource("").toURI();
        try (FileSystem fileSystem = FileSystems.newFileSystem(
                resource,
                Collections.<String, String>emptyMap()
        );){

            final Path jarPath = fileSystem.getPath(source);

            Files.walkFileTree(jarPath, new SimpleFileVisitor<Path>() {

                private Path currentTarget;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    currentTarget = target.resolve(jarPath.relativize(dir).toString());
                    Files.createDirectories(currentTarget);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file, target.resolve(jarPath.relativize(file).toString()), StandardCopyOption.COPY_ATTRIBUTES);
                    return FileVisitResult.CONTINUE;
                }

            });
            plugin.getLogger().info("§aSuccesfully loaded Language bundles!");
        } catch (FileAlreadyExistsException e) {
            plugin.getLogger().info("§cNot copying Language files because they already exist!");
        } catch (FileSystemAlreadyExistsException e) {
            e.printStackTrace();
        }


    }

}
