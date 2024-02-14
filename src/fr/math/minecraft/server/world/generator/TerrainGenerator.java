package fr.math.minecraft.server.world.generator;

import fr.math.minecraft.server.world.ServerChunk;
import fr.math.minecraft.server.world.Structure;

public interface TerrainGenerator {

    void generateChunk(ServerChunk chunk);
    void generateStructure(ServerChunk chunk);
    void placeStruture(ServerChunk chunk);
}
