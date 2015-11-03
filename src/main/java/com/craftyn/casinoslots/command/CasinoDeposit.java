package com.craftyn.casinoslots.command;

import org.bukkit.entity.Player;

import com.craftyn.casinoslots.CasinoSlots;
import com.craftyn.casinoslots.slot.SlotMachine;

public class CasinoDeposit extends AnCommand {

    // Command for removing slot machine
    public CasinoDeposit(CasinoSlots plugin, String[] args, Player player) {
        super(plugin, args, player);
    }

    public Boolean process() {
        // Correct command format
        if(args.length == 3) {

            // Slot exists
            if(plugin.getSlotData().isSlot(args[1])) {
                SlotMachine slot = plugin.getSlotData().getSlot(args[1]);

                // Can access slot
                if(isOwner(slot)) {
                    String Line3 = args[2];
                    double amount;
                    try {
                        if (Line3.startsWith("-")) {
                            sendMessage("Must deposit a postive amount.");
                            return true;
                        }else {
                            amount = Double.parseDouble(args[2]);
                        }
                    } catch (NumberFormatException e) {
                        sendMessage("Third arugment must be a number.");
                        return true;
                    }

                    double pAccount = plugin.getEconomy().getBalance(player.getName());
                    if (amount > pAccount) {
                        sendMessage("You can't deposit that much since you don't have that much.");
                        return true;
                    }else {
                        slot.deposit(amount);
                        plugin.getEconomy().withdrawPlayer(player.getName(), amount);
                        sendMessage(amount +  " deposited to " + args[1] + ".");
                        sendMessage(args[1] + " now has " + slot.getFunds() + " in it's account.");
                        plugin.getSlotData().saveSlot(slot);
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