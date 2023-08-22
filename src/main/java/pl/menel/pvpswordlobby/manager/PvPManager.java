package pl.menel.pvpswordlobby.manager;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.menel.pvpswordlobby.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PvPManager {
    private final Main plugin;
    public PvPManager(Main plugin) {
        this.plugin = plugin;
    }
    public final Map<Player, BukkitTask> countdownTasks = new HashMap<>();
    public final Map<Player, Boolean> pvpEnabledStatus = new HashMap<>();
    public final List<Player> pvpList = new ArrayList<>();

    public void onPvpEnable(Player player) {
        if (!countdownTasks.containsKey(player)) {
            CountdownTask task = new CountdownTask(player, true);
            BukkitTask taskInstance = task.runTaskTimer(plugin, 0, 20);
            countdownTasks.put(player, taskInstance);
            pvpEnabledStatus.put(player, true);
        }
    }

    public void onPvpDisable(Player player) {
        if (countdownTasks.containsKey(player)) {
            countdownTasks.get(player).cancel();
            CountdownTask task = new CountdownTask(player, false);
            BukkitTask taskInstance = task.runTaskTimer(plugin, 0, 20);
            countdownTasks.put(player, taskInstance);
            pvpEnabledStatus.put(player, false);
        }
    }

    public void onPvpCancel(Player player) {
        if (countdownTasks.containsKey(player)) {
            countdownTasks.get(player).cancel();
            pvpEnabledStatus.put(player, true);
        }
    }

    private class CountdownTask extends BukkitRunnable {
        private final Player player;
        private final Boolean status;
        private int secondsLeft = plugin.getConfig().getInt("settings-pvp.Time");

        public CountdownTask(Player player, Boolean status) {
            this.player = player;
            this.status = status;
        }

        @Override
        public void run() {
            if (secondsLeft > 0) {
                player.sendTitle(
                        plugin.colorize(plugin.getConfig().getString("settings-message.Wait.Line1").replace("%time%", String.valueOf(secondsLeft))),
                        plugin.colorize(plugin.getConfig().getString("settings-message.Wait.Line2").replace("%time%", String.valueOf(secondsLeft))),
                        0, 20, 0);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, (float) secondsLeft);
                secondsLeft--;
            } else if (status) {
                player.sendTitle(
                        plugin.colorize(plugin.getConfig().getString("settings-message.Enable-pvp.Line1")),
                        plugin.colorize(plugin.getConfig().getString("settings-message.Enable-pvp.Line2")),
                        0, 20, 10);
                player.playSound(player.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 1.0f);
                pvpList.add(player);
                onPlayerPvP(player, true);
                cancel();
            } else {
                player.sendTitle(
                        plugin.colorize(plugin.getConfig().getString("settings-message.Disable-pvp.Line1")),
                        plugin.colorize(plugin.getConfig().getString("settings-message.Disable-pvp.Line2")),
                        0, 20, 10);
                player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_DAMAGE, 1.0F, 1.0f);
                countdownTasks.remove(player);
                pvpEnabledStatus.remove(player);
                pvpList.remove(player);
                onPlayerPvP(player, false);
                cancel();
            }
        }
    }

    public void onPlayerPvP(Player player, Boolean status) {
        PlayerInventory playerInventory = player.getInventory();
        if (status) {
            ItemStack diamondHelmet = new ItemStack(Material.valueOf(plugin.getConfig().getString("settings-item.Helmet")));
            ItemStack diamondChestplate = new ItemStack(Material.valueOf(plugin.getConfig().getString("settings-item.Chestplate")));
            ItemStack diamondLeggings = new ItemStack(Material.valueOf(plugin.getConfig().getString("settings-item.Leggings")));
            ItemStack diamondBoots = new ItemStack(Material.valueOf(plugin.getConfig().getString("settings-item.Boots")));

            diamondHelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            diamondChestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            diamondLeggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
            diamondBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

            playerInventory.setHelmet(diamondHelmet);
            playerInventory.setChestplate(diamondChestplate);
            playerInventory.setLeggings(diamondLeggings);
            playerInventory.setBoots(diamondBoots);

            if (playerInventory.getItemInHand() != null) {
                ItemMeta meta = playerInventory.getItemInHand().getItemMeta();
                meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
                playerInventory.getItemInHand().setItemMeta(meta);
            }
        } else {
            playerInventory.setHelmet(null);
            playerInventory.setChestplate(null);
            playerInventory.setLeggings(null);
            playerInventory.setBoots(null);
        }
    }
}
