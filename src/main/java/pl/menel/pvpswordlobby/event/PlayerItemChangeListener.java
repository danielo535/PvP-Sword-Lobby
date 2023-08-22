package pl.menel.pvpswordlobby.event;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import pl.menel.pvpswordlobby.Main;
import pl.menel.pvpswordlobby.menager.PvPMenager;

public class PlayerItemChangeListener implements Listener {

    private final Main plugin;
    private final PvPMenager pvPMenager;
    public PlayerItemChangeListener(Main plugin, PvPMenager pvPMenager) {
        this.plugin = plugin;
        this.pvPMenager = pvPMenager;
    }

    @EventHandler
    public void onItemChange(PlayerItemHeldEvent event) {
        int newSlot = event.getNewSlot();
        ItemStack newItem = event.getPlayer().getInventory().getItem(newSlot);
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.5F, 1.5F);

        if (pvPMenager.pvpList.contains(player)) {
            if (newItem != null && newItem.getType() == Material.valueOf(plugin.getConfig().getString("settings-item.Sword"))) {
                pvPMenager.onPvpCancel(player);
            } else {
                if (pvPMenager.pvpEnabledStatus.get(player)) {
                    pvPMenager.onPvpDisable(player);
                }
            }
        } else if (pvPMenager.countdownTasks.containsKey(player))  {
            pvPMenager.onPvpCancel(player);
            pvPMenager.countdownTasks.remove(player);
        } else if (newItem != null && newItem.getType() == Material.valueOf(plugin.getConfig().getString("settings-item.Sword"))){
            pvPMenager.onPvpEnable(player);
        }
    }
}