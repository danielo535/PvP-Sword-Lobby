package pl.danielo535.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import pl.danielo535.pvpswordlobby.manager.CombatManager;
import pl.danielo535.pvpswordlobby.manager.base.PvPUser;

public class PlayerItemChangeListener implements Listener {
    private final CombatManager combatManager;

    public PlayerItemChangeListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }

    @EventHandler
    public void onItemChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        PvPUser pvPUser = combatManager.getUser(player);
        boolean isHoldingSword = newItem != null && newItem.isSimilar(combatManager.getSword());
        if (isHoldingSword) {
            if (pvPUser.getCountdown() != null) {
                pvPUser.stopTask();
                return;
            }
            if (!pvPUser.isPvpEnabledStatus()) {
                combatManager.pvpEnable(player);
            }
            return;
        } else if (!pvPUser.isPvpEnabledStatus() && pvPUser.getCountdown() != null) {
            pvPUser.stopTask();
            return;
        }
        if (pvPUser.isPvpEnabledStatus() && pvPUser.getCountdown() == null) {
            combatManager.pvpDisable(player);
        }
    }
}