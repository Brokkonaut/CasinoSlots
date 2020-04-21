package com.craftyn.casinoslots.command;

import org.bukkit.entity.Player;

import com.craftyn.casinoslots.CasinoSlots;
import com.craftyn.casinoslots.classes.SlotMachine;
import com.craftyn.casinoslots.event.CasinoDepositEvent;

public class CasinoDeposit extends AnCommand {

    // Command for removing slot machine
    public CasinoDeposit(CasinoSlots plugin, String[] args, Player player) {
        super(plugin, args, player);
    }

    public Boolean process() {
        // Correct command format
        if(args.length == 3) {

            // Slot exists
            if(plugin.getSlotManager().isSlot(args[1])) {
                SlotMachine slot = plugin.getSlotManager().getSlot(args[1]);

                // Can access slot
                if(isOwner(slot)) {
                    double amount;
                    try {
                        amount = Double.parseDouble(args[2]);
                        if (amount <= 0 || !Double.isFinite(amount)) {
                            sendMessage("Must deposit a postive amount.");
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        sendMessage("Third arugment must be a number.");
                        return true;
                    }

                    if (plugin.getEconomy().has(player, amount)) {
                        if (new CasinoDepositEvent(player, slot, amount).call().isCancelled()) {
                            return true;
                        }
                        slot.deposit(amount);
                        plugin.getEconomy().withdrawPlayer(player, amount);
                        sendMessage(amount +  " deposited to " + args[1] + ".");
                        sendMessage(args[1] + " now has " + slot.getFunds() + " in it.");
                        plugin.getSlotManager().saveSlot(slot);
                    }else {
                        sendMessage("You can't deposit that much since you don't have that much.");
                        return true;
                    }
                }
                // No access
                else {
                    sendMessage("You do not own this slot machine.");
                }
            }

            // Slot does not exist
            else {
                sendMessage("Invalid slot machine.");
            }
        }

        // Incorrect command format
        else {
            sendMessage("Usage:");
            sendMessage("/casino deposit <slotname> <amount>");
        }
        return true;
    }

}