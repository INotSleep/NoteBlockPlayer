package me.inotsleep.noteblockplayer;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class PlayCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("play").executes(context -> {
            if (NoteblockPlayer.executor == null) {
                context.getSource().sendError(Text.of("Song not loaded!"));
                return 0;
            }
            if (NoteblockPlayer.executor.requiredList.stream().anyMatch(noteBlockInstance -> noteBlockInstance.pos == null)) {
                context.getSource().sendError(Text.of("Song not valid!"));
                return 0;
            }
            NoteblockPlayer.playing = true;
            playLoop(0, context.getSource().getPlayer(), context.getSource().getClient());

            return 1;
        }));

        dispatcher.register(ClientCommandManager.literal("stop").executes(context -> {
            NoteblockPlayer.playing = false;
            return 1;
        }));
    }

    public static void playLoop(int frame, ClientPlayerEntity player, MinecraftClient client) {
        if (frame>=NoteblockPlayer.executor.song.size() || !NoteblockPlayer.playing) {
            player.sendMessage(Text.of("Song ended"), true);
            return;
        }
        for (NoteInstance noteInstance : NoteblockPlayer.executor.song.get(frame)) {
            if (noteInstance == null) continue;
            System.out.println(noteInstance.instrument.toString() + " " + noteInstance.note);
            Util.click(player, NoteblockPlayer.executor.requiredList.stream().filter((noteBlockInstance -> noteBlockInstance.note == noteInstance.note && noteBlockInstance.instrument == noteInstance.instrument)).findFirst().get().pos, client);
        }
        Util.runWithDelay(NoteblockPlayer.executor.delay, () -> {
            playLoop(frame+1, player, client);
        });
    }
}
