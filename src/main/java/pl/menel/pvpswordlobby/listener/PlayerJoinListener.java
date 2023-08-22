package pl.menel.pvpswordlobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.menel.pvpswordlobby.manager.PvPManager;

public class PlayerJoinListener implements Listener {
    private final PvPManager pvPManager;
    public PlayerJoinListener(PvPManager pvPManager) {
        this.pvPManager = pvPManager;
    }
    @EventHandler
    public void onJoinServer(PlayerJoinEvent event) {
        pvPManager.onPlayerPvP(event.getPlayer(), false);

    }
}
