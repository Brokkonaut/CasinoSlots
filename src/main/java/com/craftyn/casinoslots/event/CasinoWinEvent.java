package com.craftyn.casinoslots.event;

import com.craftyn.casinoslots.classes.SlotMachine;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class CasinoWinEvent extends CasinoEvent {

    private static final HandlerList handlers = new HandlerList();
    private List<BlockData> results;
    private double money;

    public CasinoWinEvent(Player player, SlotMachine slot, List<BlockData> results, double money) {
        super(player, slot);
        this.results = Collections.unmodifiableList(results);
        this.money = money;
    }

    public double getMoney() {
        return money;
    }
    
    public List<BlockData> getResults() {
        return results;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CasinoWinEvent call() {
        Bukkit.getPluginManager().callEvent(this);
        return this;
    }
}
