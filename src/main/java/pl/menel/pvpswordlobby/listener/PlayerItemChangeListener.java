package pl.menel.pvpswordlobby.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import pl.menel.pvpswordlobby.Main;
import pl.menel.pvpswordlobby.manager.PvPManager;

public class PlayerItemChangeListener implements Listener {

    private final Main plugin;
    private final PvPManager pvPManager;
    public PlayerItemChangeListener(Main plugin, PvPManager pvPManager) {
        this.plugin = plugin;
        this.pvPManager = pvPManager;
    }

    @EventHandler
    public void onItemChange(PlayerItemHeldEvent event) {
        int newSlot = event.getNewSlot();
        ItemStack newItem = event.getPlayer().getInventory().getItem(newSlot);
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.5F, 1.5F);

        if (pvPManager.pvpList.contains(player)) {
            if (newItem != null && newItem.getType() == Material.valueOf(plugin.getConfig().getString("settings-item.Sword"))) {
                pvPManager.onPvpCancel(player);
            } else {
                if (pvPManager.pvpEnabledStatus.get(player)) {
                    pvPManager.onPvpDisable(player);
                }
            }
        } else if (pvPManager.countdownTasks.containsKey(player))  {
            pvPManager.onPvpCancel(player);
            pvPManager.countdownTasks.remove(player);
        } else if (newItem != null && newItem.getType() == Material.valueOf(plugin.getConfig().getString("settings-item.Sword"))){
            pvPManager.onPvpEnable(player);
        }
    }
}