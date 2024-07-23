package pl.danielo535.pvpswordlobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.danielo535.pvpswordlobby.manager.CombatManager;

public class PlayerJoinListener implements Listener {
    private final CombatManager combatManager;
    public PlayerJoinListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }
    @EventHandler
    public void onJoinServer(PlayerJoinEvent event) {
        combatManager.handleJoin(event.getPlayer());
    }
}
