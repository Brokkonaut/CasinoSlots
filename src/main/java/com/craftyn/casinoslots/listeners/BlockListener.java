package com.craftyn.casinoslots.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import com.craftyn.casinoslots.CasinoSlots;
import com.craftyn.casinoslots.classes.SlotMachine;
import java.util.Iterator;

public class BlockListener implements Listener {
    private CasinoSlots plugin;

    public BlockListener(CasinoSlots plugin) {
        this.plugin = plugin;
    }

    private boolean isProtected(Block b) {
        // Check if plugin is enabled
        if(plugin.isEnabled()) {

            // Slot protection enabled
            if(plugin.getConfigData().protection) {

                // Look for match in slots
                for(SlotMachine slot : plugin.getSlotManager().getSlots()) {

                    for(Block current : slot.getBlocks()) {

                        // Match found, cancel event
                        if(b.equals(current)) {
                            return true;
                        }
                    }

                    // Check controller as well
                    Block current = slot.getController();
                    if(b.equals(current)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if(isProtected(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if(isProtected(event.getBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        Iterator<Block> it = event.blockList().iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if(isProtected(block)) {
                it.remove();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockExplode(BlockExplodeEvent event) {
        Iterator<Block> it = event.blockList().iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if(isProtected(block)) {
                it.remove();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPistonExtend(BlockPistonExtendEvent event) {
        Iterator<Block> it = event.getBlocks().iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if(isProtected(block)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPistonRetract(BlockPistonRetractEvent event) {
        Iterator<Block> it = event.getBlocks().iterator();
        while (it.hasNext()) {
            Block block = it.next();
            if(isProtected(block)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}