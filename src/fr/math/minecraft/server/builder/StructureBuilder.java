package fr.math.minecraft.server.builder;

import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.world.Material;
import fr.math.minecraft.server.world.Structure;

public class  StructureBuilder {

    public static void buildSimpleTree(Structure structure, int x, int y, int z, Material wood, Material leaf, float droprate) {
        int treeSize = 6;
        if(droprate <= 2.7f) {
            treeSize = 5;
        } else if (droprate >= 6.75) {
            treeSize = 4;
        }

        /*-------------------Etage 7--------------------------------*/
        structure.setBlock(x+1, y+treeSize+1, z, leaf.getId());
        structure.setBlock(x-1, y+treeSize+1, z, leaf.getId());
        structure.setBlock(x, y+treeSize+1, z+1, leaf.getId());
        structure.setBlock(x, y+treeSize+1, z-1, leaf.getId());
        structure.setBlock(x, y+treeSize+1, z, leaf.getId());

        /*-------------------Etage 6--------------------------------*/
        structure.setBlock(x+1, y+treeSize, z, leaf.getId());
        structure.setBlock(x-1, y+treeSize, z, leaf.getId());
        structure.setBlock(x, y+treeSize, z+1, leaf.getId());
        structure.setBlock(x, y+treeSize, z-1, leaf.getId());
        structure.setBlock(x-1, y+treeSize, z-1, leaf.getId());

        /*-------------------Etage 5--------------------------------*/
        structure.setBlock(x+1, y+treeSize-1, z-1, leaf.getId());
        structure.setBlock(x+1, y+treeSize-1, z-2, leaf.getId());
        structure.setBlock(x+1, y+treeSize-1, z, leaf.getId());
        structure.setBlock(x+1, y+treeSize-1, z+1, leaf.getId());
        structure.setBlock(x+1, y+treeSize-1, z+2, leaf.getId());

        structure.setBlock(x+2, y+treeSize-1, z-1, leaf.getId());
        structure.setBlock(x+2, y+treeSize-1, z, leaf.getId());
        structure.setBlock(x+2, y+treeSize-2, z+1, leaf.getId());

        structure.setBlock(x, y+treeSize-1, z+2, leaf.getId());
        structure.setBlock(x, y+treeSize-1, z+1, leaf.getId());
        structure.setBlock(x, y+treeSize-1, z-1, leaf.getId());
        structure.setBlock(x, y+treeSize-1, z-2, leaf.getId());

        structure.setBlock(x-1, y+treeSize-1, z+2, leaf.getId());
        structure.setBlock(x-1, y+treeSize-1, z+1, leaf.getId());
        structure.setBlock(x-1, y+treeSize-1, z, leaf.getId());
        structure.setBlock(x-1, y+treeSize-1, z-1, leaf.getId());
        structure.setBlock(x-1, y+treeSize-1, z-2, leaf.getId());

        structure.setBlock(x-2, y+treeSize-1, z+1, leaf.getId());
        structure.setBlock(x-2, y+treeSize-1, z, leaf.getId());
        structure.setBlock(x-2, y+treeSize-1, z-1, leaf.getId());
        structure.setBlock(x-2, y+treeSize-1, z-2, leaf.getId());

        /*-------------------Etage 4--------------------------------*/
        for (int i = -2; i <= 2 ; i++) {
            for (int j = -2 ; j <= 2 ; j++) {
                structure.setBlock(x+i, y+treeSize-2, z+j, leaf.getId());
            }
        }

        /*-------------------Tronc--------------------------------*/
        for (int i = 1; i <= treeSize; i++) {
            structure.setBlock(x, y+i, z, wood.getId());
        }
    }

    public static void buildBallonTree(Structure structure, int x, int y, int z, Material wood, Material leaf, float droprate) {

        /*-------------------Etage 7--------------------------------*/
        structure.setBlock(x+1, y+6, z, Material.OAK_LEAVES.getId());
        structure.setBlock(x-1, y+6, z, Material.OAK_LEAVES.getId());
        structure.setBlock(x, y+6, z+1, Material.OAK_LEAVES.getId());
        structure.setBlock(x, y+6, z-1, Material.OAK_LEAVES.getId());
        structure.setBlock(x, y+6, z, Material.OAK_LEAVES.getId());

        for (int i = 3; i < 6; i ++) {
            structure.setBlock(x-1, y+i, z+2, Material.OAK_LEAVES.getId());
            structure.setBlock(x, y+i, z+2, Material.OAK_LEAVES.getId());
            structure.setBlock(x+1, y+i, z+2, Material.OAK_LEAVES.getId());

            structure.setBlock(x-2, y+i, z+1, Material.OAK_LEAVES.getId());
            structure.setBlock(x-1, y+i, z+1, Material.OAK_LEAVES.getId());
            structure.setBlock(x, y+i, z+1, Material.OAK_LEAVES.getId());
            structure.setBlock(x+1, y+i, z+1, Material.OAK_LEAVES.getId());
            structure.setBlock(x+2, y+i, z+1, Material.OAK_LEAVES.getId());

            structure.setBlock(x-2, y+i, z, Material.OAK_LEAVES.getId());
            structure.setBlock(x-1, y+i, z, Material.OAK_LEAVES.getId());
            structure.setBlock(x+1, y+i, z, Material.OAK_LEAVES.getId());
            structure.setBlock(x+2, y+i, z, Material.OAK_LEAVES.getId());

            structure.setBlock(x-2, y+i, z-1, Material.OAK_LEAVES.getId());
            structure.setBlock(x-1, y+i, z-1, Material.OAK_LEAVES.getId());
            structure.setBlock(x, y+i, z-1, Material.OAK_LEAVES.getId());
            structure.setBlock(x+1, y+i, z-1, Material.OAK_LEAVES.getId());
            structure.setBlock(x+2, y+i, z-1, Material.OAK_LEAVES.getId());

            structure.setBlock(x-1, y+i, z-2, Material.OAK_LEAVES.getId());
            structure.setBlock(x, y+i, z-2, Material.OAK_LEAVES.getId());
            structure.setBlock(x+1, y+i, z-2, Material.OAK_LEAVES.getId());
        }


        /*-------------------Tronc--------------------------------*/
        int treeSize = 5;
        for (int i = 1; i <= treeSize; i++) {
            structure.setBlock(x, y+i, z, Material.OAK_LOG.getId());
        }
    }

    public static void buildSimpleCactus(Structure structure, int x, int y, int z) {
        RandomSeed randomSeed = RandomSeed.getInstance();
        int cactusSize = randomSeed.nextInt(3, 5);
        float cactusSizeDrop = randomSeed.nextFloat(100);
        if(cactusSizeDrop <= 99) {
            for(int i = 1; i<=cactusSize;i++){
                structure.setBlock(x,y+i,z,Material.CACTUS.getId());
                structure.setBlock(x-1,y+i,z-1,Material.AIR.getId());
                structure.setBlock(x,y+i,z-1,Material.AIR.getId());
                structure.setBlock(x + 1,y+i,z-1,Material.AIR.getId());
                structure.setBlock(x-1,y+i,z,Material.AIR.getId());
                structure.setBlock(x+1,y+i,z,Material.AIR.getId());
                structure.setBlock(x-1,y+i,z+1,Material.AIR.getId());
                structure.setBlock(x,y+i,z+1,Material.AIR.getId());
                structure.setBlock(x+1,y+i,z+1,Material.AIR.getId());
            }
        } else {
            for(int i = 1; i<=cactusSize;i++){
                structure.setBlock(x,y+i,z,Material.CACTUS.getId());
                structure.setBlock(x-1,y+i,z-1,Material.AIR.getId());
                structure.setBlock(x,y+i,z-1,Material.AIR.getId());
                structure.setBlock(x + 1,y+i,z-1,Material.AIR.getId());
                structure.setBlock(x-1,y+i,z,Material.AIR.getId());
                structure.setBlock(x+1,y+i,z,Material.AIR.getId());
                structure.setBlock(x-1,y+i,z+1,Material.AIR.getId());
                structure.setBlock(x,y+i,z+1,Material.AIR.getId());
                structure.setBlock(x+1,y+i,z+1,Material.AIR.getId());
            }
            structure.setBlock(x-1,y+1,z,Material.CACTUS.getId());
            structure.setBlock(x+1,y+1,z,Material.CACTUS.getId());
        }
    }

    public static void buildWeed(Structure structure, int x, int y, int z) {
        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate < 5) {
            structure.setBlock(x, y+1, z, Material.ROSE.getId());
        } else {
            structure.setBlock(x, y+1, z, Material.WEED.getId());
        }
    }

    public static void buildDeadBush(Structure structure, int x, int y, int z) {
        structure.setBlock(x, y+1, z, Material.DEAD_BUSH.getId());
    }
}
