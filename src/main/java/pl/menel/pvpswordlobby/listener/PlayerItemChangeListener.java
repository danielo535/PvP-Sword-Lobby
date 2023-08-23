package pl.menel.pvpswordlobby.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import pl.menel.pvpswordlobby.Main;
import pl.menel.pvpswordlobby.manager.CombatManager;

public class PlayerItemChangeListener implements Listener {

    private final Main plugin;
    private final CombatManager combatManager;
    public PlayerItemChangeListener(Main plugin, CombatManager combatManager) {
        this.plugin = plugin;
        this.combatManager = combatManager;
    }

    @EventHandler
    public void onItemChange(PlayerItemHeldEvent event) {
        int newSlot = event.getNewSlot();
        ItemStack newItem = event.getPlayer().getInventory().getItem(newSlot);
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.5F, 1.5F);

        if (combatManager.pvpList.contains(player)) {
            if (newItem != null && newItem.getType() == Material.valueOf(plugin.getConfig().getString("settings-item.Sword"))) {
                combatManager.onPvpCancel(player);
            } else {
                if (combatManager.pvpEnabledStatus.get(player)) {
                    combatManager.onPvpDisable(player);
                }
            }
        } else if (combatManager.countdownTasks.containsKey(player))  {
            combatManager.onPvpCancel(player);
            combatManager.countdownTasks.remove(player);
        } else if (newItem != null && newItem.getType() == Material.valueOf(plugin.getConfig().getString("settings-item.Sword"))){
            combatManager.onPvpEnable(player);
        }
    }
}