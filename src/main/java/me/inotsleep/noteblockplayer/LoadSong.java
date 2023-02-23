package me.inotsleep.noteblockplayer;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.xxmicloxx.NoteBlockAPI.NBSDecoder;
import com.xxmicloxx.NoteBlockAPI.Song;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

import java.io.File;

public class LoadSong {
    String song;

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
            ClientCommandManager.literal("loadsong")
            .then(ClientCommandManager.argument("file", StringArgumentType.string())
                .executes(context -> {
                    FabricClientCommandSource source = context.getSource();
                    Song song = NBSDecoder.parse(new File("./music/"+StringArgumentType.getString(context, "file")));
                    if (song == null) {
                        source.sendError(Text.of("File not found!"));
                        return 0;
                    }
                    NoteblockPlayer.setExecutor(new SongExecutor(Util.generatePlayableList(Util.parseSong(song)), song.getDelay()));
                    source.sendFeedback(NoteblockPlayer.executor.generateStringRequiredList());

                    return 1;
                })
            )
        );

    }
}
