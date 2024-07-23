package pl.danielo535.pvpswordlobby;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.danielo535.pvpswordlobby.config.ConfigStorage;
import pl.danielo535.pvpswordlobby.listener.EntityDamageListener;
import pl.danielo535.pvpswordlobby.listener.PlayerCommandListener;
import pl.danielo535.pvpswordlobby.listener.PlayerDeathListener;
import pl.danielo535.pvpswordlobby.listener.PlayerJoinListener;
import pl.danielo535.pvpswordlobby.listener.PlayerQuitListener;
import pl.danielo535.pvpswordlobby.manager.CombatManager;
import pl.danielo535.pvpswordlobby.listener.PlayerItemChangeListener;

public final class SwordPlugin extends JavaPlugin {
    public static SwordPlugin INSTANCE;
    @Override
    public void onEnable() {
        INSTANCE = this;
        new Metrics(this,19612);
        CombatManager combatManager = new CombatManager();
        ConfigStorage.setUpConfig(this);
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerItemChangeListener(combatManager), this);
        pluginManager.registerEvents(new EntityDamageListener(combatManager), this);
        pluginManager.registerEvents(new PlayerCommandListener(combatManager), this);
        pluginManager.registerEvents(new PlayerDeathListener(combatManager), this);
        pluginManager.registerEvents(new PlayerJoinListener(combatManager), this);
        pluginManager.registerEvents(new PlayerQuitListener(combatManager), this);
        getLogger().info("----------------------");
        getLogger().info(" ");
        getLogger().info("✔ PvP-Sword-Lobby enabled...");
        getLogger().info(" ");
        getLogger().info("----------------------");

    }
}
