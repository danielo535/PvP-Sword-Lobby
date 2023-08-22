package pl.menel.pvpswordlobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.menel.pvpswordlobby.menager.PvPMenager;

public class PlayerJoinListener implements Listener {
    private final PvPMenager pvPMenager;
    public PlayerJoinListener(PvPMenager pvPMenager) {
        this.pvPMenager = pvPMenager;
    }
    @EventHandler
    public void onJoinServer(PlayerJoinEvent event) {
        pvPMenager.onPlayerPvP(event.getPlayer(), false);

    }
}
