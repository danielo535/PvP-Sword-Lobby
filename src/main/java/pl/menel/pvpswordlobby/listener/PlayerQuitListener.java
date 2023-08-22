package pl.menel.pvpswordlobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.menel.pvpswordlobby.menager.PvPMenager;

public class PlayerQuitListener implements Listener {
    private final PvPMenager pvPMenager;
    public PlayerQuitListener(PvPMenager pvPMenager) {
        this.pvPMenager = pvPMenager;
    }
    @EventHandler
    public void onLeaveServer(PlayerQuitEvent event) {
        pvPMenager.onPlayerPvP(event.getPlayer(), false);
    }
}
