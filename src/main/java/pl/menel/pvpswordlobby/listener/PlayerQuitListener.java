package pl.menel.pvpswordlobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.menel.pvpswordlobby.manager.CombatManager;

public class PlayerQuitListener implements Listener {
    private final CombatManager combatManager;
    public PlayerQuitListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }
    @EventHandler
    public void onLeaveServer(PlayerQuitEvent event) {
        combatManager.onPlayerPvP(event.getPlayer(), false);
    }
}
