package pl.menel.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.menel.pvpswordlobby.manager.PvPManager;

public class PlayerDeathListener implements Listener {
    private final PvPManager pvPManager;
    public PlayerDeathListener(PvPManager pvPManager) {
        this.pvPManager = pvPManager;
    }
    @EventHandler
    public void onDeathPlayer(PlayerDeathEvent event) {
        event.setKeepInventory(true);
        Player player = event.getPlayer();
        if (pvPManager.pvpList.contains(player)) {
            pvPManager.countdownTasks.remove(player);
            pvPManager.pvpEnabledStatus.remove(player);
            pvPManager.pvpList.remove(player);
            pvPManager.onPlayerPvP(player, false);
        }
    }
}
