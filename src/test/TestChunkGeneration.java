package test;

import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.Region;
import fr.math.minecraft.shared.world.World;
import org.junit.Assert;
import org.junit.Test;

public class TestChunkGeneration {

    private World world;

    @Test
    public void testGeneration() {

        this.world = new World();
        Region region = new Region(0, 0, 0);
        region.generateStructure(world);
        world.addRegion(region);

        Chunk chunk = new Chunk(-1, 10, 0);
        Chunk sameChunk = new Chunk(-1, 10, 0);

        chunk.generate(world, world.getTerrainGenerator());
        sameChunk.generate(world, world.getTerrainGenerator());

        int i = 0;
        while (i < Chunk.VOLUME && chunk.getBlocks()[i] == sameChunk.getBlocks()[i]) {
            i++;
        }

        Assert.assertEquals(Chunk.VOLUME, i);
    }

    @Test
    public void testWorldPosToChunkPos() {

        int worldX = -1000;
        int worldY = 1000;
        int worldZ = 1000;

        int chunkX = (int) Math.floor(worldX / (double) Chunk.SIZE);
        int chunkY = (int) Math.floor(worldY / (double) Chunk.SIZE);
        int chunkZ = (int) Math.floor(worldZ / (double) Chunk.SIZE);

        Assert.assertEquals(chunkX, -63);
        Assert.assertEquals(chunkY, 62);
        Assert.assertEquals(chunkZ, 62);
    }

}
