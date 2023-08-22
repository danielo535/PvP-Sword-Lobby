package pl.menel.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.menel.pvpswordlobby.manager.PvPManager;

public class PlayerMoveListener implements Listener {
    private final PvPManager pvPManager;
    public PlayerMoveListener(PvPManager pvPManager) {
        this.pvPManager = pvPManager;
    }
    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (pvPManager.pvpList.contains(player)) {
            if (!player.getPassengers().isEmpty()) {
                event.setCancelled(true);
            }
        }
    }
}
