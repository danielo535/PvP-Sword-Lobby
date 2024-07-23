package pl.danielo535.pvpswordlobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.danielo535.pvpswordlobby.manager.CombatManager;
import pl.danielo535.pvpswordlobby.manager.base.PvPUser;

public class PlayerQuitListener implements Listener {
    private final CombatManager combatManager;

    public PlayerQuitListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }

    @EventHandler
    public void onLeaveServer(PlayerQuitEvent event) {
        PvPUser pvpUser = combatManager.getUser(event.getPlayer());
        if (!pvpUser.isPvpEnabledStatus()) return;
        pvpUser.setPvpEnabledStatus(false);
        if (pvpUser.getCountdown() == null) return;
        pvpUser.getCountdown().cancel();
    }
}
