package pl.danielo535.pvpswordlobby.manager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.danielo535.pvpswordlobby.SwordPlugin;
import pl.danielo535.pvpswordlobby.config.ConfigStorage;
import pl.danielo535.pvpswordlobby.manager.base.PvPTask;
import pl.danielo535.pvpswordlobby.manager.base.PvPUser;
import pl.danielo535.pvpswordlobby.util.ItemUtil;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CombatManager {
    public final Map<UUID, PvPUser> pvpUsers;

    public CombatManager() {
        this.pvpUsers = new ConcurrentHashMap<>();
    }

    public void handleJoin(Player player) {
        this.pvpUsers.put(player.getUniqueId(), new PvPUser());
    }

    public PvPUser getUser(Player player) {
        PvPUser pvPUser;
        if (pvpUsers.containsKey(player.getUniqueId())) {
            pvPUser = this.pvpUsers.get(player.getUniqueId());
        } else {
            pvPUser = new PvPUser();
            pvpUsers.put(player.getUniqueId(), pvPUser);
        }
        return pvPUser;
    }

    public ItemStack getSword() {
        return ItemUtil.deserialize(ConfigStorage.getFile().getConfigurationSection("settings-item.sword"));
    }

    public void pvpEnable(Player player) {
        PvPUser pvPUser = getUser(player);
        if (pvPUser.isPvpEnabledStatus()) return;
        pvPUser.setCountdown(new PvPTask(player, this, true).runTaskTimer(SwordPlugin.INSTANCE, 0, 20));
    }

    public void pvpDisable(Player player) {
        PvPUser pvPUser = getUser(player);
        if (!pvPUser.isPvpEnabledStatus()) return;
        pvPUser.setCountdown(new PvPTask(player, this, false).runTaskTimer(SwordPlugin.INSTANCE, 0, 20));
    }

    public void startPlayerPvP(Player player, Boolean status) {
        PlayerInventory playerInventory = player.getInventory();
        if (status) {
            playerInventory.setHelmet(ItemUtil.deserialize(ConfigStorage.getFile().getConfigurationSection("settings-item.helmet")));
            playerInventory.setChestplate(ItemUtil.deserialize(ConfigStorage.getFile().getConfigurationSection("settings-item.chestplate")));
            playerInventory.setLeggings(ItemUtil.deserialize(ConfigStorage.getFile().getConfigurationSection("settings-item.leggings")));
            playerInventory.setBoots(ItemUtil.deserialize(ConfigStorage.getFile().getConfigurationSection("settings-item.boots")));
            return;
        }
        playerInventory.setHelmet(null);
        playerInventory.setChestplate(null);
        playerInventory.setLeggings(null);
        playerInventory.setBoots(null);
    }
}
