package fr.math.minecraft;

import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.pathfinding.AStar;
import fr.math.minecraft.shared.world.World;

import java.io.IOException;

public class ServerMain {
    public static float seedNumber;
    public static void main(String[] args) {
        if(args.length==2){
            if (!args[0].equalsIgnoreCase("--seed")) {
                throw new IllegalArgumentException("Veuillez renseigner un seed ! (--seed nombre)");
            }
            try {
                seedNumber = Integer.parseInt(args[1]);
            } catch(Exception e){
                seedNumber = 0;
            }
        }

        MinecraftServer server = MinecraftServer.getInstance();
        World world = server.getWorld();
        world.setSeed(seedNumber);
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
