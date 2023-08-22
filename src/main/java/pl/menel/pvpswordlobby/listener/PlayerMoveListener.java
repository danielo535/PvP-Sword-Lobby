package pl.menel.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.menel.pvpswordlobby.menager.PvPMenager;

public class PlayerMoveListener implements Listener {
    private final PvPMenager pvPMenager;
    public PlayerMoveListener(PvPMenager pvPMenager) {
        this.pvPMenager = pvPMenager;
    }
    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (pvPMenager.pvpList.contains(player)) {
            if (!player.getPassengers().isEmpty()) {
                event.setCancelled(true);
            }
        }
    }
}
