package pl.menel.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.menel.pvpswordlobby.Main;
import pl.menel.pvpswordlobby.manager.CombatManager;

public class PlayerCommandListener implements Listener {
    private final Main plugin;
    private final CombatManager combatManager;
    public PlayerCommandListener(Main plugin, CombatManager combatManager) {
        this.plugin = plugin;
        this.combatManager = combatManager;
    }
    @EventHandler
    public void onCommandUse(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (combatManager.pvpList.contains(player)) {
            player.sendMessage(plugin.colorize(plugin.getConfig().getString("settings-message.DisableCommand")));
            event.setCancelled(true);
        }
    }
}
