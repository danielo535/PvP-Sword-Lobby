package pl.menel.pvpswordlobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.menel.pvpswordlobby.manager.CombatManager;

public class PlayerJoinListener implements Listener {
    private final CombatManager combatManager;
    public PlayerJoinListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }
    @EventHandler
    public void onJoinServer(PlayerJoinEvent event) {
        combatManager.onPlayerPvP(event.getPlayer(), false);

    }
}
