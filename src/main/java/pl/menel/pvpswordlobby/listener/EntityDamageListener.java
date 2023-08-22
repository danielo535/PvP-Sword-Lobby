package pl.menel.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.menel.pvpswordlobby.manager.PvPManager;

public class EntityDamageListener implements Listener {
    private final PvPManager pvPManager;
    public EntityDamageListener(PvPManager pvPManager) {
        this.pvPManager = pvPManager;
    }

    @EventHandler
    private void onPvP(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player victim = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();

            if (!pvPManager.pvpList.contains(attacker) || !pvPManager.pvpList.contains(victim)) {
                event.setCancelled(true);
            }
        }
    }
}
