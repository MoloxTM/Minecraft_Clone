package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;

public class ForestBiome extends AbstractBiome{
    @Override
    public Material getUpperBlock() {
        return Material.GRASS;
    }

    @Override
    public Material getSecondBlock() {
        return Material.DIRT;
    }

    @Override
    public void buildTree(ServerChunk chunk, int x, int y, int z) {
        int treeSize = 10;
        for(int i = 1;i<=treeSize;i++){
            chunk.setBlock(x,y+i,z,Material.OAK_LOG.getId());
        }
    }

    @Override
    public void buildWeeds(ServerChunk chunk, int x, int y, int z) {

    }
}
