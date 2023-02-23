package me.inotsleep.noteblockplayer;

import java.util.ArrayList;

public class LayerInstance {
    public int tick;
    public ArrayList<NoteInstance> list;

    LayerInstance(int tick) {
        this.tick = tick;
        this.list = new ArrayList<>();
    }

    public void addNote(NoteInstance note) {
        list.add(note);
    }
}
