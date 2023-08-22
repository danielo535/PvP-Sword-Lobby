package pl.menel.pvpswordlobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.menel.pvpswordlobby.manager.PvPManager;

public class PlayerQuitListener implements Listener {
    private final PvPManager pvPManager;
    public PlayerQuitListener(PvPManager pvPManager) {
        this.pvPManager = pvPManager;
    }
    @EventHandler
    public void onLeaveServer(PlayerQuitEvent event) {
        pvPManager.onPlayerPvP(event.getPlayer(), false);
    }
}
