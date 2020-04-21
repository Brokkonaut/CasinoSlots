package com.craftyn.casinoslots.event;

import com.craftyn.casinoslots.classes.SlotMachine;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public abstract class CasinoEvent extends PlayerEvent {

    private final SlotMachine slot;

    public CasinoEvent(Player player, SlotMachine slot) {
        super(player);
        this.slot = slot;
    }

    public SlotMachine getSlot() {
        return slot;
    }
}
