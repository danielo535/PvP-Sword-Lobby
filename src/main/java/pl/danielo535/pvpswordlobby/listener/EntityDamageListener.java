package pl.danielo535.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.danielo535.pvpswordlobby.manager.CombatManager;
import pl.danielo535.pvpswordlobby.manager.base.PvPUser;

public class EntityDamageListener implements Listener {

    private final CombatManager combatManager;

    public EntityDamageListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }

    @EventHandler
    private void onPvP(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) && !(event.getDamager() instanceof Player)) return;
        Player victim = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();
        PvPUser pvPUserV = combatManager.getUser(victim);
        PvPUser pvPUserA = combatManager.getUser(attacker);
        if (!pvPUserA.isPvpEnabledStatus() || !pvPUserV.isPvpEnabledStatus()) {
            event.setCancelled(true);
        }
    }
}
