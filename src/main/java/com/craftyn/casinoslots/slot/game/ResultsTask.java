package com.craftyn.casinoslots.slot.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import com.craftyn.casinoslots.classes.SlotMachine;
import com.craftyn.casinoslots.classes.Type;
import com.craftyn.casinoslots.util.Stat;
import com.craftyn.casinoslots.util.Util;

public class ResultsTask implements Runnable {
    private Game game;

    // Deploys rewards after game is finished
    public ResultsTask(Game game) {
        this.game = game;
    }

    public void run() {

        Type type = game.getType();
        Player player = game.getPlayer();
        String name = type.getName();
        Double cost = type.getCost();
        Double won = 0.0;

        ArrayList<BlockData> results = getResults();

        if (!results.isEmpty()) {
            SlotMachine slot = game.getSlot();

            if (!(slot.getSign() == null)) {
                Block b = slot.getSign();
                if (Util.isSign(b.getType())) {
                    Sign sign = (Sign) b.getState();
                    sign.setLine(3, player.getDisplayName());
                    sign.update(true);
                } else {
                    game.getPlugin().error("The block stored for the sign is NOT a sign, please remove it.");
                }
            }
            
            won += type.sendRewards(results, player);
            
            //Take the money from the slot machine, if it is managed
            if (slot.isManaged()) {
                if (won < 0) {
                    slot.deposit(Math.abs(won));
                } else {
                    slot.withdraw(won);
                }
            }
        }

        // No win
        else {
            game.getPlugin().debug("The player has won an amount of: " + won);
            game.getPlugin().sendMessage(player, type.getMessages().get("noWin"));
        }

        // Register statistics
        if (game.getPlugin().getConfigData().trackStats) {
            Stat stat;

            //Already have some stats for this type
            if (game.getPlugin().getStatData().isStat(name)) {
                stat = game.getPlugin().getStatData().getStat(name);
                if (!results.isEmpty()) {
                    stat.addWon(won, cost);
                    game.getPlugin().getStatData().addStat(stat);
                } else {
                    stat.addLost(won, cost);
                    game.getPlugin().getStatData().addStat(stat);
                }
            } else {
                game.getPlugin().debug("The player has won an amount of: " + won);
                game.getPlugin().debug("The player has lost an amount of: " + cost);
                if (!results.isEmpty()) {
                    stat = new Stat(name, 1, 1, 0, won, cost);
                    game.getPlugin().getStatData().addStat(stat);
                } else {
                    stat = new Stat(name, 1, 0, 1, won, cost);
                    game.getPlugin().getStatData().addStat(stat);
                }
            }
            game.getPlugin().getConfigData().saveStats();
        }

        // All done
        game.getSlot().toggleBusy();
    }

    // Gets the results
    private ArrayList<BlockData> getResults() {
        ArrayList<BlockData> results = new ArrayList<>();
        ArrayList<Block> blocks = game.getSlot().getBlocks();

        // checks horizontal matches
        for (int i = 0; i < 5; i++) {
            ArrayList<BlockData> currentId = new ArrayList<>();
            List<Block> current = null;

            if (i < 3) {
                int start = 0 + 3 * i;
                int end = 3 + 3 * i;
                current = blocks.subList(start, end);
            } else {
                //diagonals
                if (game.getPlugin().getConfigData().allowDiagonals) {
                    current = new ArrayList<Block>();
                    for (int j = 0; j < 3; j++) {
                        if (i == 3) {
                            current.add(blocks.get(j * 4));
                        } else {
                            current.add(blocks.get(2 + 2 * j));
                        }
                    }
                } else {
                    // Break loop if diagonals are disabled
                    break;
                }
            }

            for (Block b : current) {
                currentId.add(b.getBlockData());
            }

            // Check for matches, deploy rewards
            Set<BlockData> currentSet = new HashSet<>(currentId);
            if (currentSet.size() == 1) {
                results.add(current.get(0).getBlockData());
            }
        }

        //Play some sounds on rewards!
        game.getPlugin().getServer().getScheduler().runTaskLater(game.getPlugin(), new Runnable() {
            public void run() {
                Util.playNoteBlockSound(game.getSlot().getController().getLocation(), Instrument.PIANO, new Note((byte) 1, Tone.C, false));
            }
        }, 15);

        return results;
    }
}
