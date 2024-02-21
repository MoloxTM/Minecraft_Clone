package fr.math.minecraft.shared.world.generator;

import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.World;

public interface TerrainGenerator {

    void generateChunk(World world, Chunk chunk);
}
