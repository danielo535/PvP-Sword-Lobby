package pl.menel.pvpswordlobby;

import me.kodysimpson.simpapi.colors.ColorTranslator;
import org.bukkit.plugin.java.JavaPlugin;
import pl.menel.pvpswordlobby.listener.PlayerItemChangeListener;
import pl.menel.pvpswordlobby.listener.*;
import pl.menel.pvpswordlobby.manager.PvPManager;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        int pluginId = 19612;
        new Metrics(this, pluginId);
        PvPManager pvPManager = new PvPManager(this);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerItemChangeListener(this, pvPManager), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(pvPManager), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandListener(this, pvPManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(pvPManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(pvPManager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(pvPManager), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(pvPManager), this);
        getLogger().info("----------------------");
        getLogger().info(" ");
        getLogger().info("✔ PvP-Sword-Lobby enabled...");
        getLogger().info(" ");
        getLogger().info("----------------------");

    }

    public String colorize(String message) {
        return ColorTranslator.translateColorCodes(message);
    }
}
