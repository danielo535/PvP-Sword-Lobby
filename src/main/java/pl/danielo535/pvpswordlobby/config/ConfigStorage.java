package pl.danielo535.pvpswordlobby.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ConfigStorage {

    public static int SETTINGS$PVP_TIME;
    public static String SETTINGS$TITLE_ENABLE$PVP_TITLE;
    public static String SETTINGS$TITLE_ENABLE$PVP_SUBTITLE;
    public static String SETTINGS$TITLE_DISABLE$PVP_TITLE;
    public static String SETTINGS$TITLE_DISABLE$PVP_SUBTITLE;
    public static String SETTINGS$TITLE_WAIT_TITLE;
    public static String SETTINGS$TITLE_WAIT_SUBTITLE;
    public static String MESSAGE_DISABLE$COMMAND;

    private static final String fileName = "config.yml";
    private static File cfgFile;
    private static FileConfiguration cfg;

    public static File getCfg() {
        return cfgFile;
    }

    public static FileConfiguration getFile() {
        return cfg;
    }

    public static void setUpConfig(Plugin plugin) {
        createDefaultFiles(plugin);
        load();
    }
    public static void createDefaultFiles(Plugin plugin) {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdir()) {
            throw new RuntimeException("Wystąpił błąd podczas tworzenia folderu pluginu!");
        } else {
            cfgFile = new File(dataFolder, fileName);
            if (!cfgFile.exists()) {
                try {
                    Files.copy(plugin.getClass().getClassLoader().getResourceAsStream(fileName), Paths.get(cfgFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException("Wystąpił błąd podczas tworzenia configu!", e);
                }
            }
            cfg = YamlConfiguration.loadConfiguration(cfgFile);
        }
    }

    public static void load() {
        try {
            Field[] fields = ConfigStorage.class.getFields();
            for (Field field : fields) {
                if (isConfigField(field)) {
                    String path = getPath(field);
                    if (cfg.isSet(path)) {
                        field.set(null, cfg.get(path));
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Wystąpił problem podczas ładowania configu!", e);
        }
    }

    public static void save() {
        try {
            Field[] fields = ConfigStorage.class.getFields();
            for (Field field : fields) {
                if (isConfigField(field)) {
                    String path = getPath(field);
                    cfg.set(path, field.get(null));
                }
            }
            cfg.save(cfgFile);
        } catch (Exception e) {
            throw new RuntimeException("Wystąpił problem podczas zapisywania configu!", e);
        }
    }

    public static void reload() {
        cfg = YamlConfiguration.loadConfiguration(cfgFile);
        load();
        save();
    }

    private static String getPath(Field field) {
        return field.getName().toLowerCase().replace("$", "-").replace("_", ".");
    }

    private static boolean isConfigField(Field field) {
        return Modifier.isPublic(field.getModifiers())
                && Modifier.isStatic(field.getModifiers())
                && !Modifier.isFinal(field.getModifiers())
                && !Modifier.isTransient(field.getModifiers());
    }
}

