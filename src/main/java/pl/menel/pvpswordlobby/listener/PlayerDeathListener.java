package pl.menel.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.menel.pvpswordlobby.menager.PvPMenager;

public class PlayerDeathListener implements Listener {
    private final PvPMenager pvPMenager;
    public PlayerDeathListener(PvPMenager pvPMenager) {
        this.pvPMenager = pvPMenager;
    }
    @EventHandler
    public void onDeathPlayer(PlayerDeathEvent event) {
        event.setKeepInventory(true);
        Player player = event.getPlayer();
        if (pvPMenager.pvpList.contains(player)) {
            pvPMenager.countdownTasks.remove(player);
            pvPMenager.pvpEnabledStatus.remove(player);
            pvPMenager.pvpList.remove(player);
            pvPMenager.onPlayerPvP(player, false);
        }
    }
}
