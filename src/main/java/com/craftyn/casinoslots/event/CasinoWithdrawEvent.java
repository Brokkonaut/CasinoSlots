package com.craftyn.casinoslots.event;

import com.craftyn.casinoslots.classes.SlotMachine;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class CasinoWithdrawEvent extends CasinoEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private double amount;

    public CasinoWithdrawEvent(Player player, SlotMachine slot, double amount) {
        super(player, slot);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CasinoWithdrawEvent call() {
        Bukkit.getPluginManager().callEvent(this);
        return this;
    }
}
