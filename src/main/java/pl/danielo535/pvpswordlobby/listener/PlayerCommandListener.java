package pl.danielo535.pvpswordlobby.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.danielo535.pvpswordlobby.config.ConfigStorage;
import pl.danielo535.pvpswordlobby.manager.CombatManager;
import pl.danielo535.pvpswordlobby.manager.base.PvPUser;
import pl.danielo535.pvpswordlobby.util.TextUtil;

public class PlayerCommandListener implements Listener {
    private final CombatManager combatManager;

    public PlayerCommandListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }

    @EventHandler
    public void onCommandUse(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        PvPUser pvPUser = combatManager.getUser(player);
        if (!pvPUser.isPvpEnabledStatus()) return;
        player.sendMessage(TextUtil.colorize(ConfigStorage.MESSAGE_DISABLE$COMMAND));
        event.setCancelled(true);
    }
}
