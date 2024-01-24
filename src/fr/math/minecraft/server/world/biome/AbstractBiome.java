package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;

public abstract class AbstractBiome {
    public abstract Material getUpperBlock();
    public abstract Material getSecondBlock();
    public abstract void buildTree(ServerChunk chunk, int x, int y, int z);
    public abstract void buildWeeds(ServerChunk chunk, int x, int y, int z);

}
