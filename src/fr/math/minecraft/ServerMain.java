package fr.math.minecraft;

import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.pathfinding.AStar;
import fr.math.minecraft.shared.world.World;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {
        int seed = 0;
        int port = 50000;
        if(args.length==2){
            if (!args[0].equalsIgnoreCase("-p")) {
                throw new IllegalArgumentException("Veuillez renseigner un port ! (-p <port>)");
            }
            try {
                port = Integer.parseInt(args[1]);
            } catch(Exception e){
                port = 50000;
            }
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-seed")) {
                seed = Integer.parseInt(args[i + 1]);
            }
        }

        MinecraftServer server = MinecraftServer.getInstance();
        server.setPort(port);
        World world = server.getWorld();
        world.setSeed(seed);
        world.buildSpawn();
        world.calculateSpawnPosition();
        AStar.initGraph(world, world.getSpawnPosition());
        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
