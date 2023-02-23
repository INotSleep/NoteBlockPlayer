package me.inotsleep.noteblockplayer;

import com.xxmicloxx.NoteBlockAPI.Layer;
import com.xxmicloxx.NoteBlockAPI.Note;
import com.xxmicloxx.NoteBlockAPI.Song;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.enums.Instrument;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Util {

    static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public static void click(@NotNull ClientPlayerEntity player, @NotNull BlockPos pos, MinecraftClient client) {
        Vec3d vec = new Vec3d(pos.getX()+0.5d, pos.getY()+0.5d, pos.getZ()+0.5d);
        double distance = vec.distanceTo(player.getPos());
        if (distance > 3.8d) {
            return;
        }
        player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, vec);
        assert client.interactionManager != null;
        client.interactionManager.attackBlock(pos, Direction.UP);
    }

    public static ArrayList<NoteBlockInstance> findNoteBlocks(ClientWorld world, ClientPlayerEntity player) {
        ArrayList<NoteBlockInstance> noteBlocks = new ArrayList<>();
        for (int x = -4; x<5; x++) {
            for (int y = -4; y < 5; y++) {
                for (int z = -4; z < 5; z++) {
                    Vec3d vec = new Vec3d(player.getX() + x, player.getY() + y, player.getZ() + z);
                    if (player.getPos().distanceTo(vec) > 3.8d) continue;
                    BlockPos pos = new BlockPos(vec);
                    BlockState blockState = world.getBlockState(pos);
                    Block block = blockState.getBlock();
                    if (block instanceof NoteBlock) {
                        noteBlocks.add(new NoteBlockInstance(blockState.get(NoteBlock.INSTRUMENT), blockState.get(NoteBlock.NOTE), pos));
                    }
                }
            }
        }
        return noteBlocks;
    }

    public static Instrument getInstrumentName(byte i) {
        return switch (i) {
            case 1 -> Instrument.BASS;
            case 2 -> Instrument.BASEDRUM;
            case 3 -> Instrument.SNARE;
            case 4 -> Instrument.HAT;
            case 5 -> Instrument.GUITAR;
            case 6 -> Instrument.FLUTE;
            case 7 -> Instrument.BELL;
            case 8 -> Instrument.CHIME;
            case 9 -> Instrument.XYLOPHONE;
            case 10 -> Instrument.IRON_XYLOPHONE;
            case 11 -> Instrument.COW_BELL;
            case 12 -> Instrument.DIDGERIDOO;
            case 13 -> Instrument.BIT;
            case 14 -> Instrument.BANJO;
            case 15 -> Instrument.PLING;
            default -> Instrument.HARP;
        };
    }

    public static byte getNoteNumber(float pitch) {
        return (byte) ((byte) Math.round(Math.log(pitch)/Math.log(2d)*12)+12);
    }

    public static SongInstance parseSong(Song song) {
        HashMap<Integer, Layer> layers = song.getLayerHashMap();

        ArrayList<LayerInstance> list = new ArrayList<>();

        for (Map.Entry<Integer, Layer> entry : layers.entrySet()) {
            Integer layerTick = entry.getKey();
            Layer layer = entry.getValue();
            LayerInstance layerInstance = new LayerInstance(layerTick);
            HashMap<Integer, Note> notes = layer.getNotesAtTicks();

            for (Map.Entry<Integer, Note> noteEntry : notes.entrySet()) {
                Integer noteTick = noteEntry.getKey();
                Note note = noteEntry.getValue();
                layerInstance.addNote(new NoteInstance(Util.getInstrumentName(note.getInstrument()), Util.getNoteNumber(note.getRealPitch()), noteTick));
            }
            list.add(layerInstance);
        }
        list.sort((o1, o2) -> Integer.compare(o2.tick, o1.tick)*-1);
        for (LayerInstance layerInstance : list) {
            layerInstance.list.sort((o1, o2) -> Integer.compare(o2.tick, o1.tick)*-1);
        }

        return new SongInstance(list, song.getDelay(), song.getLength());
    }

    public static ArrayList<ArrayList<NoteInstance>> generatePlayableList(SongInstance song) {
        ArrayList<ArrayList<NoteInstance>> list = new ArrayList<>();
        for (int tick = 0; tick < song.length; tick++) {
            int finalTick = tick;
            ArrayList<NoteInstance> noteList = new ArrayList<>();
            song.list.forEach((layerInstance) -> {
                if (layerInstance.list.stream().anyMatch(noteInstance -> noteInstance.tick == finalTick)) {
                    noteList.add(layerInstance.list.stream().filter(noteInstance -> noteInstance.tick == finalTick).findFirst().get());
                } else noteList.add(null);
            });
            list.add(noteList);
        }
        return list;
    }

    public static void runWithDelay(float delayTicks, Runnable task) {
        executorService.schedule(task, (long) (delayTicks * 50L), TimeUnit.MILLISECONDS);
    }

    public static Text getBlockName(Instrument instrument) {
        return Text.translatable(switch (instrument) {
            case BASS -> "block.minecraft.oak_log";
            case HARP, PIGLIN, CUSTOM_HEAD, WITHER_SKELETON, DRAGON, SKELETON, CREEPER, ZOMBIE -> "block.minecraft.air";
            case BASEDRUM -> "block.minecraft.cobblestone";
            case SNARE -> "block.minecraft.sand";
            case HAT -> "block.minecraft.glass";
            case FLUTE -> "block.minecraft.clay";
            case BELL -> "block.minecraft.gold_block";
            case GUITAR -> "block.minecraft.wool";
            case CHIME -> "block.minecraft.packed_ice";
            case XYLOPHONE -> "block.minecraft.bone_block";
            case IRON_XYLOPHONE -> "block.minecraft.iron_block";
            case COW_BELL -> "block.minecraft.soul_sand";
            case DIDGERIDOO -> "block.minecraft.pumpkin";
            case BIT -> "block.minecraft.emerald_block";
            case BANJO -> "block.minecraft.hay_bale";
            case PLING -> "block.minecraft.glowstone";
        });
    }
}
