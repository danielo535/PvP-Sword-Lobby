package pl.danielo535.pvpswordlobby.manager.base;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.danielo535.pvpswordlobby.config.ConfigStorage;
import pl.danielo535.pvpswordlobby.manager.CombatManager;
import pl.danielo535.pvpswordlobby.util.TextUtil;

public class PvPTask extends BukkitRunnable {
    private final Player player;
    private final PvPUser pvPUser;
    private final CombatManager combatManager;
    private final boolean status;
    private int secondsLeft;

    public PvPTask(Player player, CombatManager combatManager, boolean status) {
        this.combatManager = combatManager;
        this.status = status;
        this.pvPUser = combatManager.getUser(player);
        this.player = player;
        this.secondsLeft = ConfigStorage.SETTINGS$PVP_TIME;
    }

    @Override
    public void run() {
        if (secondsLeft > 0) {
            player.sendTitle(
                    TextUtil.colorize(ConfigStorage.SETTINGS$TITLE_WAIT_TITLE.replace("%time%", String.valueOf(secondsLeft))),
                    TextUtil.colorize(ConfigStorage.SETTINGS$TITLE_WAIT_SUBTITLE.replace("%time%", String.valueOf(secondsLeft))),
                    0, 20, 0);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, (float) secondsLeft);
            secondsLeft--;
            return;
        }
        if (status) {
            player.sendTitle(
                    TextUtil.colorize(ConfigStorage.SETTINGS$TITLE_ENABLE$PVP_TITLE),
                    TextUtil.colorize(ConfigStorage.SETTINGS$TITLE_ENABLE$PVP_SUBTITLE),
                    0, 20, 10);
            player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 1.0f);
            combatManager.startPlayerPvP(player, true);
            pvPUser.setPvpEnabledStatus(true);
            pvPUser.stopTask();
            return;
        }
        player.sendTitle(
                TextUtil.colorize(ConfigStorage.SETTINGS$TITLE_DISABLE$PVP_TITLE),
                TextUtil.colorize(ConfigStorage.SETTINGS$TITLE_DISABLE$PVP_SUBTITLE),
                0, 20, 10);
        player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_DAMAGE, 1.0F, 1.0f);
        combatManager.startPlayerPvP(player, false);
        pvPUser.setPvpEnabledStatus(false);
        pvPUser.stopTask();
    }
}
