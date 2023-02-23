package me.inotsleep.noteblockplayer;

import java.util.ArrayList;

public class SongInstance {
    ArrayList<LayerInstance> list;
    float delay;
    int length;

    SongInstance(ArrayList<LayerInstance> list, float delay, int length) {
        this.list = list;
        this.delay = delay;
        this.length = length;
    }
}
