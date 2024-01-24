package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.ServerChunk;

public class PlainBiome extends AbstractBiome{
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

        /*-------------------Etage 7--------------------------------*/
        chunk.setBlock(x+1, y+7, z, Material.OAK_LEAVES.getId());
        chunk.setBlock(x-1, y+7, z, Material.OAK_LEAVES.getId());
        chunk.setBlock(x, y+7, z+1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x, y+7, z-1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x, y+7, z, Material.OAK_LEAVES.getId());

        /*-------------------Etage 6--------------------------------*/
        chunk.setBlock(x+1, y+6, z, Material.OAK_LEAVES.getId());
        chunk.setBlock(x-1, y+6, z, Material.OAK_LEAVES.getId());
        chunk.setBlock(x, y+6, z+1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x, y+6, z-1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x-1, y+6, z-1, Material.OAK_LEAVES.getId());

        /*-------------------Etage 5--------------------------------*/
        chunk.setBlock(x+1, y+5, z-1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x+1, y+5, z-2, Material.OAK_LEAVES.getId());
        chunk.setBlock(x+1, y+5, z, Material.OAK_LEAVES.getId());
        chunk.setBlock(x+1, y+5, z+1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x+1, y+5, z+2, Material.OAK_LEAVES.getId());

        chunk.setBlock(x+2, y+5, z-1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x+2, y+5, z, Material.OAK_LEAVES.getId());
        chunk.setBlock(x+2, y+5, z+1, Material.OAK_LEAVES.getId());

        chunk.setBlock(x, y+5, z+2, Material.OAK_LEAVES.getId());
        chunk.setBlock(x, y+5, z+1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x, y+5, z-1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x, y+5, z-2, Material.OAK_LEAVES.getId());

        chunk.setBlock(x-1, y+5, z+2, Material.OAK_LEAVES.getId());
        chunk.setBlock(x-1, y+5, z+1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x-1, y+5, z, Material.OAK_LEAVES.getId());
        chunk.setBlock(x-1, y+5, z-1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x-1, y+5, z-2, Material.OAK_LEAVES.getId());

        chunk.setBlock(x-2, y+5, z+1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x-1, y+5, z, Material.OAK_LEAVES.getId());
        chunk.setBlock(x-2, y+5, z-1, Material.OAK_LEAVES.getId());
        chunk.setBlock(x-2, y+5, z-2, Material.OAK_LEAVES.getId());

        /*-------------------Etage 4--------------------------------*/
        for (int i = -2; i <= 2 ; i++) {
            for (int j = -2 ; j <= 2 ; j++) {
                chunk.setBlock(x+i, y+4, z+j, Material.OAK_LEAVES.getId());
            }
        }

        int treeSize = 6;
        for (int i = 1; i <= treeSize; i++) {
            chunk.setBlock(x, y+i, z, Material.OAK_LOG.getId());
        }
    }

    @Override
    public void buildWeeds(ServerChunk chunk, int x, int y, int z) {

    }
};
