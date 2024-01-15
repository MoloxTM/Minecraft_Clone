package fr.math.minecraft.server.world.generator;

import fr.math.minecraft.server.world.ServerChunk;

public interface TerrainGenerator {

    void generate(ServerChunk chunk);

}
