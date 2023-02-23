package me.inotsleep.noteblockplayer;

import net.minecraft.block.enums.Instrument;

public class NoteInstance {
    public int tick;
    public Instrument instrument;
    public byte note;

    NoteInstance(Instrument instrument, byte note, int tick) {
        this.instrument = instrument;
        this.note = note;
        this.tick = tick;
    }
}
