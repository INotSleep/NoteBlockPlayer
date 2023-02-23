package me.inotsleep.noteblockplayer;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.Song;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class TestCommand {
    public static void register (CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
            literal("test")
            .then(argument("file", StringArgumentType.string())
            .executes(context -> {
                StringBuilder a = new StringBuilder();
                Song song = NBSDecoder.parse(new File("./music/"+StringArgumentType.getString(context, "file")));
                if (song == null) {
                    context.getSource().sendError(Text.of("File not found"));
                    return 0;
                }
                a.append("[");
                Util.generatePlayableList(Util.parseSong(song)).forEach((list) -> {
                    a.append("\n\t[");
                    list.forEach((note) -> {
                        if (note == null) a.append("\n\t\tnull,");
                        else a.append("\n\t\t{\n\t\t\tnote:"+note.note+"\n\t\t\ttick:"+note.tick+"\n\t\t\tinstrument:"+note.instrument.toString()+"\n\t\t},");
                    });
                    a.append("\n\t],");
                });
                a.append("]");
                System.out.println(a.toString());
                return 1;
            }))
        );
    }



}
