package pl.menel.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.menel.pvpswordlobby.manager.CombatManager;

public class PlayerMoveListener implements Listener {
    private final CombatManager combatManager;
    public PlayerMoveListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }
    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (combatManager.pvpList.contains(player)) {
            if (!player.getPassengers().isEmpty()) {
                event.setCancelled(true);
            }
        }
    }
}
