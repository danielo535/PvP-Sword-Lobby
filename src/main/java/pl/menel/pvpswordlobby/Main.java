package pl.menel.pvpswordlobby;

import me.kodysimpson.simpapi.colors.ColorTranslator;
import org.bukkit.plugin.java.JavaPlugin;
import pl.menel.pvpswordlobby.listener.PlayerItemChangeListener;
import pl.menel.pvpswordlobby.listener.*;
import pl.menel.pvpswordlobby.manager.CombatManager;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        new Metrics(this,19612);
        CombatManager combatManager = new CombatManager(this);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerItemChangeListener(this, combatManager), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(combatManager), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandListener(this, combatManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(combatManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(combatManager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(combatManager), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(combatManager), this);
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
