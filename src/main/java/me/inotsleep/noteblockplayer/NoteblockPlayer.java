package me.inotsleep.noteblockplayer;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;

import java.io.File;

public class NoteblockPlayer implements ClientModInitializer {
    public static SongExecutor executor;
    public static boolean playing;

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(this::registerCommands);
        new File(new File("."), "music").mkdir();
    }

    public void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        LoadSong.register(dispatcher);
        ValidateNoteBlocks.register(dispatcher);
        PlayCommand.register(dispatcher);
    }

    public static void setExecutor(SongExecutor executor) {
        NoteblockPlayer.executor = executor;
    }
}
