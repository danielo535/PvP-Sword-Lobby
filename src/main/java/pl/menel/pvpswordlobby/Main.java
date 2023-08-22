package pl.menel.pvpswordlobby;

import me.kodysimpson.simpapi.colors.ColorTranslator;
import org.bukkit.plugin.java.JavaPlugin;
import pl.menel.pvpswordlobby.event.PlayerItemChangeListener;
import pl.menel.pvpswordlobby.listener.*;
import pl.menel.pvpswordlobby.menager.PvPMenager;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        PvPMenager pvPMenager = new PvPMenager(this);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerItemChangeListener(this,pvPMenager), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(pvPMenager), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandListener(this,pvPMenager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(pvPMenager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(pvPMenager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(pvPMenager), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(pvPMenager), this);
        getLogger().info("----------------------");
        getLogger().info(" ");
        getLogger().info("✔ Włączam Plugin...");
        getLogger().info(" ");
        getLogger().info("----------------------");

    }

    /**
     * Konwertuje ciąg znaków zawierający kody kolorów oraz formatowania Minecraft na pokolorowany tekst.
     *
     * @param message Ciąg znaków zawierający kody kolorów oraz formatowania Minecraft.
     * @return Pokolorowany tekst z uwzględnieniem kolorów i formatowania.
     */
    public String colorize(String message) {
        return ColorTranslator.translateColorCodes(message);
    }
}
