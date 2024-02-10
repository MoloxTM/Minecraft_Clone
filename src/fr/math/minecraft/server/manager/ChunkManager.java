package fr.math.minecraft.server.manager;

import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.worker.ChunkSender;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.ServerWorld;

import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ChunkManager {

    private final ThreadPoolExecutor chunkPool;

    public ChunkManager() {
        this.chunkPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
    }


    public void sendChunk(Client client, ServerChunk chunk) {
        ChunkSender sender = new ChunkSender(client, chunk);
        sender.run();
    }

}
