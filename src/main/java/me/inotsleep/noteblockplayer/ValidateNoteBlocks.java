package me.inotsleep.noteblockplayer;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ValidateNoteBlocks {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("validatenoteblocks").executes(context -> {
            if (NoteblockPlayer.executor == null) {
                context.getSource().sendError(Text.of("Song not loaded!"));
                return 0;
            }
            ArrayList<NoteBlockInstance> list = Util.findNoteBlocks(context.getSource().getWorld(), context.getSource().getPlayer());
            for (NoteBlockInstance noteBlockInstance : NoteblockPlayer.executor.requiredList) {
                int index = NoteblockPlayer.executor.requiredList.indexOf(noteBlockInstance);
                AtomicInteger listIndex = new AtomicInteger();
                if (list.stream().anyMatch((requiredNoteBlockInstance) -> {
                    listIndex.set(list.indexOf(requiredNoteBlockInstance));
                    return requiredNoteBlockInstance.note == noteBlockInstance.note && requiredNoteBlockInstance.instrument == noteBlockInstance.instrument;
                })) {
                    NoteblockPlayer.executor.requiredList.set(index, list.get(listIndex.get()));
                } else {
                    noteBlockInstance.pos = null;
                    NoteblockPlayer.executor.requiredList.set(index, noteBlockInstance);
                }
            }
            context.getSource().sendFeedback(NoteblockPlayer.executor.generateStringRequiredList());
            return 1;
        }));
    }
}
