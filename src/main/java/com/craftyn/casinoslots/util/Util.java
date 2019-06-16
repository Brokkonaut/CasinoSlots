package com.craftyn.casinoslots.util;

import java.util.EnumSet;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;

public class Util {

    private final static EnumSet<Material> SIGNS = EnumSet.of(Material.OAK_SIGN, Material.OAK_WALL_SIGN, Material.BIRCH_SIGN, Material.BIRCH_WALL_SIGN, Material.SPRUCE_SIGN, Material.SPRUCE_WALL_SIGN, Material.JUNGLE_SIGN, Material.JUNGLE_WALL_SIGN, Material.DARK_OAK_SIGN,
            Material.DARK_OAK_WALL_SIGN, Material.ACACIA_SIGN, Material.ACACIA_WALL_SIGN);

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

    public static boolean isSign(Material m) {
        return SIGNS.contains(m);
    }
}
