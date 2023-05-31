package com.craftyn.casinoslots.util;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.block.data.type.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;

public class Util {
    /**
     * Plays the CasinoSlot game sound at the given location.
     * 
     * @param location the {@link Location} to play the sound at.
     */
    public static void playGameSound(Location location) {
        playNoteBlockSound(location, Instrument.PIANO, new Note((byte) 0, Tone.C, false));
    }
    
    /**
     * Plays a note at the given location, if the block is a {@link NoteBlock}.
     * 
     * @param location the {@link Location} to play the sound at.
     * @param instrument the {@link Instrument} to use.
     * @param note the {@link Note} to play.
     */
    public static void playNoteBlockSound(Location location, Instrument instrument, Note note) {
        if(location.getBlock().getType() == Material.NOTE_BLOCK) {
            for(Player p : location.getWorld().getPlayers()) {
                if(p.getLocation().distanceSquared(location) < 50*50) {
                    p.playNote(location, instrument, note);
                }
            }
        }
    }

    /**
     * Checks if a {@link Material} is a sign.
     * @param m the {@link Material} to check
     * @return true, if it is a sign.
     */
    public static boolean isSign(Material m) {
        return m.data == Sign.class || m.data == WallSign.class;
    }
}
