package pl.danielo535.pvpswordlobby.manager.base;

import org.bukkit.scheduler.BukkitTask;

public class PvPUser {
    private BukkitTask countdown;
    private boolean pvpEnabledStatus;
    public PvPUser() {
        this(null,false);
    }
    public PvPUser(BukkitTask bukkitTask,boolean pvpEnabledStatus) {
        this.countdown = bukkitTask;
        this.pvpEnabledStatus = pvpEnabledStatus;
    }

    public void setPvpEnabledStatus(boolean pvpEnabledStatus) {
        this.pvpEnabledStatus = pvpEnabledStatus;
    }

    public void setCountdown(BukkitTask countdown) {
        this.countdown = countdown;
    }

    public BukkitTask getCountdown() {
        return countdown;
    }
    public void stopTask() {
        countdown.cancel();
        countdown = null;
    }

    public boolean isPvpEnabledStatus() {
        return pvpEnabledStatus;
    }
}
