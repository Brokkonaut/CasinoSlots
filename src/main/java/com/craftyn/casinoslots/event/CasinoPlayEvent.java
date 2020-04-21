package com.craftyn.casinoslots.event;

import com.craftyn.casinoslots.classes.SlotMachine;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class CasinoPlayEvent extends CasinoEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    public CasinoPlayEvent(Player player, SlotMachine slot) {
        super(player, slot);
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

    public CasinoPlayEvent call() {
        Bukkit.getPluginManager().callEvent(this);
        return this;
    }
}
