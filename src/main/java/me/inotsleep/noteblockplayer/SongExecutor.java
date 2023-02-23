package me.inotsleep.noteblockplayer;

import net.minecraft.text.Text;

import java.util.ArrayList;

public class SongExecutor {
    ArrayList<ArrayList<NoteInstance>> song;
    ArrayList<NoteBlockInstance> requiredList;
    float delay;

    SongExecutor(ArrayList<ArrayList<NoteInstance>> song, float delay) {
        this.song = song;
        this.delay = delay;
        requiredList = new ArrayList<>();
        generateRequiredList();
    }

    public void generateRequiredList() {
        for (ArrayList<NoteInstance> layer : song) {
            for (NoteInstance note : layer) {
                if (note != null && requiredList.stream().noneMatch((noteBlockInstance -> noteBlockInstance.instrument == note.instrument && noteBlockInstance.note == (int) note.note))) {
                    requiredList.add(new NoteBlockInstance(note.instrument, note.note));
                    System.out.println(note.instrument+" "+note.note);
                }
            }
        }
    }

    public Text generateStringRequiredList() {
        StringBuilder string = new StringBuilder();
        for (NoteBlockInstance noteBlock : requiredList) {
            if (noteBlock.pos != null) continue;
            string.append("[").append(Util.getBlockName(noteBlock.instrument).getString()).append(" ").append(noteBlock.note).append("]\n");
        }
        if (string.isEmpty()) string.append("Valid");
        return Text.translatable(string.toString());
    }
}
