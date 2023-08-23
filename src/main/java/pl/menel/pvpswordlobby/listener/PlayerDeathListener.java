package pl.menel.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.menel.pvpswordlobby.manager.CombatManager;

public class PlayerDeathListener implements Listener {
    private final CombatManager combatManager;
    public PlayerDeathListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }
    @EventHandler
    public void onDeathPlayer(PlayerDeathEvent event) {
        event.setKeepInventory(true);
        Player player = event.getPlayer();
        if (combatManager.pvpList.contains(player)) {
            combatManager.countdownTasks.remove(player);
            combatManager.pvpEnabledStatus.remove(player);
            combatManager.pvpList.remove(player);
            combatManager.onPlayerPvP(player, false);
        }
    }
}
