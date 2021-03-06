package com.craftyn.casinoslots.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.craftyn.casinoslots.CasinoSlots;
import com.craftyn.casinoslots.classes.SlotMachine;
import com.craftyn.casinoslots.event.CasinoWithdrawEvent;

public class CasinoWithdraw extends AnCommand {

    // Command for withdrawing money from a managed slot machine
    public CasinoWithdraw(CasinoSlots plugin, String[] args, Player player) {
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
                    if(slot.isBusy()) {
                        sendMessage(ChatColor.RED + "You can't withdraw money while the machine is in use!");
                        return true;
                    }

                    double amount;
                    try {
                        amount = Double.parseDouble(args[2]);
                        if (amount <= 0 || !Double.isFinite(amount)) {
                            sendMessage("Must withdraw a postive amount.");
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        sendMessage("Third arugment must be a number.");
                        return true;
                    }

                    if (amount > slot.getFunds()) {
                        sendMessage("You can't withdraw more than is in the slot's account.");
                        return true;
                    } else {
                        if (new CasinoWithdrawEvent(player, slot, amount).call().isCancelled()) {
                            return true;
                        }

                        slot.withdraw(amount);
                        plugin.getEconomy().depositPlayer(player, amount);
                        sendMessage(amount +  " withdrew from " + args[1] + ".");
                        sendMessage(args[1] + " now has " + slot.getFunds() + " in it.");
                        plugin.getSlotManager().saveSlot(slot);
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
            sendMessage("/casino withdraw <slotname> <amount>");
        }
        return true;
    }

}