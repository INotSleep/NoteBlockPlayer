package me.inotsleep.noteblockplayer;

import net.minecraft.block.enums.Instrument;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;

public class NoteBlockInstance {
    public BlockPos pos;
    public Instrument instrument;
    public int note;

    NoteBlockInstance (Instrument instrument, int note, BlockPos pos) {
        this.pos = pos;
        this.instrument = instrument;
        this.note = note;
    }

    NoteBlockInstance (Instrument instrument, int note) {
        this.pos = null;
        this.instrument = instrument;
        this.note = note;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public int getNote() {
        return note;
    }
}
