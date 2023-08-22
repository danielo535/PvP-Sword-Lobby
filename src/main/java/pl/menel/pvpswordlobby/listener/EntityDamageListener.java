package pl.menel.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.menel.pvpswordlobby.Main;
import pl.menel.pvpswordlobby.menager.PvPMenager;

public class EntityDamageListener implements Listener {
    private final PvPMenager pvPMenager;
    public EntityDamageListener(PvPMenager pvPMenager) {
        this.pvPMenager = pvPMenager;
    }

    @EventHandler
    private void onPvP(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player victim = (Player) event.getEntity();
            Player attacker = (Player) event.getDamager();

            if (!pvPMenager.pvpList.contains(attacker) || !pvPMenager.pvpList.contains(victim)) {
                event.setCancelled(true);
            }
        }
    }
}
