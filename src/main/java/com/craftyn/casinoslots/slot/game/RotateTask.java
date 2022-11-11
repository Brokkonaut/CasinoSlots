package com.craftyn.casinoslots.slot.game;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import com.craftyn.casinoslots.classes.ReelBlock;
import com.craftyn.casinoslots.enums.SlotMachineColumnType;

public class RotateTask implements Runnable {
    private Game game;
    private SlotMachineColumnType column;
    private Random generator;

    // Task for rotating one column
    public RotateTask(Game game, SlotMachineColumnType column) {
        this.game = game;
        this.column = column;
        this.generator = new Random();
    }

    // The task itself
    public void run() {
        rotateColumn();
    }

    // Rotates one column one block
    private void rotateColumn() {
        ArrayList<Block> blocks = game.getSlot().getBlocks();

        ArrayList<BlockData> last = new ArrayList<BlockData>();
        last.add(blocks.get(column.getFirstRow()).getBlockData());
        last.add(blocks.get(column.getSecondRow()).getBlockData());

        //Get the id and split it
        ReelBlock block = getNext();

        // Prevent silly-looking duplicate blocks
        while(block.getBlockData().equals(last.get(0))) {
            block = getNext();
        }

        // First block
        blocks.get(column.getFirstRow()).setBlockData(block.getBlockData());

        // Second block
        blocks.get(column.getSecondRow()).setBlockData(last.get(0));

        // Third block
        blocks.get(column.getThirdRow()).setBlockData(last.get(1));

    }

    // Gets the next block in the reel
    private ReelBlock getNext() {
        int id = generator.nextInt(game.getType().getReel().size());
        return game.getType().getReel().get(id);
    }
}