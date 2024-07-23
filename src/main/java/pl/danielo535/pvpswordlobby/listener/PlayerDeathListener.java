package pl.danielo535.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.danielo535.pvpswordlobby.manager.CombatManager;
import pl.danielo535.pvpswordlobby.manager.base.PvPUser;

public class PlayerDeathListener implements Listener {
    private final CombatManager combatManager;

    public PlayerDeathListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }

    @EventHandler
    public void onDeathPlayer(PlayerDeathEvent event) {
        event.setKeepInventory(true);
        Player player = event.getPlayer();
        PvPUser pvPUser = combatManager.getUser(player);
        if (!pvPUser.isPvpEnabledStatus()) return;
        pvPUser.stopTask();
        pvPUser.setPvpEnabledStatus(false);
        combatManager.startPlayerPvP(player, false);
    }
}
