package pl.menel.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.menel.pvpswordlobby.manager.CombatManager;

public class EntityDamageListener implements Listener {
    private final CombatManager combatManager;
    public EntityDamageListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }

    @EventHandler
    private void onPvP(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player victim = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();

            if (!combatManager.pvpList.contains(attacker) || !combatManager.pvpList.contains(victim)) {
                event.setCancelled(true);
            }
        }
    }
}
