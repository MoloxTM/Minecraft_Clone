package fr.math.minecraft.server.builder;

import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.world.Structure;
import fr.math.minecraft.shared.world.Material;

public class  StructureBuilder {

    public static void buildSimpleTree(Structure structure, int x, int y, int z, Material wood, Material leaf, float droprate, float treeType) {
        int treeSize = 6;
        if(droprate <= treeType * 0.33f) {
            treeSize = 5;
        } else if (droprate >= treeType * 0.75) {
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

    public static void buildBallonTree(Structure structure, int x, int y, int z, Material wood, Material leaf) {

        /*-------------------Etage 7--------------------------------*/
        structure.setBlock(x+1, y+6, z, leaf.getId());
        structure.setBlock(x-1, y+6, z, leaf.getId());
        structure.setBlock(x, y+6, z+1, leaf.getId());
        structure.setBlock(x, y+6, z-1, leaf.getId());
        structure.setBlock(x, y+6, z, leaf.getId());

        for (int i = 3; i < 6; i ++) {
            structure.setBlock(x-1, y+i, z+2, leaf.getId());
            structure.setBlock(x, y+i, z+2, leaf.getId());
            structure.setBlock(x+1, y+i, z+2, leaf.getId());

            structure.setBlock(x-2, y+i, z+1, leaf.getId());
            structure.setBlock(x-1, y+i, z+1, leaf.getId());
            structure.setBlock(x, y+i, z+1, leaf.getId());
            structure.setBlock(x+1, y+i, z+1, leaf.getId());
            structure.setBlock(x+2, y+i, z+1, leaf.getId());

            structure.setBlock(x-2, y+i, z, leaf.getId());
            structure.setBlock(x-1, y+i, z, leaf.getId());
            structure.setBlock(x+1, y+i, z, leaf.getId());
            structure.setBlock(x+2, y+i, z, leaf.getId());

            structure.setBlock(x-2, y+i, z-1, leaf.getId());
            structure.setBlock(x-1, y+i, z-1, leaf.getId());
            structure.setBlock(x, y+i, z-1, leaf.getId());
            structure.setBlock(x+1, y+i, z-1, leaf.getId());
            structure.setBlock(x+2, y+i, z-1, leaf.getId());

            structure.setBlock(x-1, y+i, z-2, leaf.getId());
            structure.setBlock(x, y+i, z-2, leaf.getId());
            structure.setBlock(x+1, y+i, z-2, leaf.getId());
        }

        /*-------------------Tronc--------------------------------*/
        int treeSize = 5;
        for (int i = 1; i <= treeSize; i++) {
            structure.setBlock(x, y+i, z, wood.getId());
        }
    }

    public static void buildFancyTree(Structure structure, int x, int y, int z, Material wood, Material leaf){
        /*-------------------Etage 6--------------------------------*/
        structure.setBlock(x, y+10, z, leaf.getId());
        structure.setBlock(x+1, y+10, z, leaf.getId());
        structure.setBlock(x-1, y+10, z, leaf.getId());
        structure.setBlock(x, y+10, z+1, leaf.getId());
        structure.setBlock(x, y+10, z-1, leaf.getId());

        /*-------------------Etage 3 4 5--------------------------------*/
        for (int i = 0; i < 3; i++) {
            structure.setBlock(x-2, y+7+i, z-1, leaf.getId());
            structure.setBlock(x-2, y+7+i, z, leaf.getId());
            structure.setBlock(x-2, y+7+i, z+1, leaf.getId());

            structure.setBlock(x-1, y+7+i, z-2, leaf.getId());
            structure.setBlock(x-1, y+7+i, z-1, leaf.getId());
            structure.setBlock(x-1, y+7+i, z, leaf.getId());
            structure.setBlock(x-1, y+7+i, z+1, leaf.getId());
            structure.setBlock(x-1, y+7+i, z+2, leaf.getId());

            structure.setBlock(x, y+7+i, z-2, leaf.getId());
            structure.setBlock(x, y+7+i, z-1, leaf.getId());
            if(i == 1) {
                structure.setBlock(x, y+7+i, z, leaf.getId());

            } else {
                structure.setBlock(x, y+7+i, z, wood.getId());
            }
            structure.setBlock(x, y+7+i, z+1, leaf.getId());
            structure.setBlock(x, y+7+i, z+2, leaf.getId());

            if(i != 2){
                structure.setBlock(x+1, y+7+i, z-3, leaf.getId());
            }
            structure.setBlock(x+1, y+7+i, z-2, leaf.getId());
            structure.setBlock(x+1, y+7+i, z-1, leaf.getId());
            structure.setBlock(x+1, y+7+i, z, leaf.getId());
            structure.setBlock(x+1, y+7+i, z+1, leaf.getId());
            structure.setBlock(x+1, y+7+i, z+2, leaf.getId());


            structure.setBlock(x+2, y+7+i, z+1, leaf.getId());
            structure.setBlock(x+2, y+7+i, z, leaf.getId());
            structure.setBlock(x+2, y+7+i, z-1, leaf.getId());
            structure.setBlock(x+2, y+7+i, z-2, leaf.getId());
            if(i != 2){
                structure.setBlock(x+2, y+7+i, z-3, leaf.getId());
            }

            if(i != 2) {
                structure.setBlock(x+3, y+7+i, z-2, leaf.getId());
                structure.setBlock(x+3, y+7+i, z-3, leaf.getId());
                structure.setBlock(x+3, y+7+i, z+1, leaf.getId());
                structure.setBlock(x+3, y+7+i, z, leaf.getId());
            }
            structure.setBlock(x+3, y+7+i, z-1, leaf.getId());


            if(i != 2){
                structure.setBlock(x+4, y+7+i, z, leaf.getId());
                structure.setBlock(x+4, y+7+i, z-1, leaf.getId());
                structure.setBlock(x+4, y+7+i, z-2, leaf.getId());
            }

        }

        /*-------------------Etage 2--------------------------------*/
        structure.setBlock(x-1, y+6, z, leaf.getId());

        structure.setBlock(x, y+6, z, wood.getId());
        structure.setBlock(x, y+6, z+1, leaf.getId());
        structure.setBlock(x, y+6, z-1, leaf.getId());
        structure.setBlock(x, y+6, z-2, leaf.getId());

        structure.setBlock(x+1, y+6, z+1, leaf.getId());
        structure.setBlock(x+1, y+6, z, leaf.getId());
        structure.setBlock(x+1, y+6, z-1, leaf.getId());
        structure.setBlock(x+1, y+6, z-2, leaf.getId());
        structure.setBlock(x+1, y+6, z-3, leaf.getId());

        structure.setBlock(x+2, y+6, z+1, leaf.getId());
        structure.setBlock(x+2, y+6, z, leaf.getId());
        structure.setBlock(x+2, y+6, z-1, leaf.getId());
        structure.setBlock(x+2, y+6, z-2, leaf.getId());
        structure.setBlock(x+2, y+6, z-3, leaf.getId());

        structure.setBlock(x+3, y+6, z+1, leaf.getId());
        structure.setBlock(x+3, y+6, z, leaf.getId());
        structure.setBlock(x+3, y+6, z-1, leaf.getId());
        structure.setBlock(x+3, y+6, z-2, leaf.getId());
        structure.setBlock(x+3, y+6, z-3, leaf.getId());

        structure.setBlock(x+4, y+6, z, leaf.getId());
        structure.setBlock(x+4, y+6, z-1, leaf.getId());
        structure.setBlock(x+4, y+6, z-2, leaf.getId());


        /*-------------------Etage 1--------------------------------*/
        structure.setBlock(x+1, y+5, z-1, wood.getId());
        structure.setBlock(x+2, y+5, z-1, wood.getId());
        structure.setBlock(x+2, y+5, z-2, leaf.getId());
        structure.setBlock(x+2, y+5, z+1, leaf.getId());
        structure.setBlock(x+3, y+5, z-1, leaf.getId());

        /*-------------------Tronc--------------------------------*/
        for (int i = 1; i <= 5; i++) {
            structure.setBlock(x, y+i, z, wood.getId());
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

    public static void buildWeed(Structure structure, int x, int y, int z, float noiseValue) {
        if(noiseValue < 0.1f) {
            structure.setBlock(x, y+1, z, Material.ROSE.getId());
        } else {
            structure.setBlock(x, y+1, z, Material.WEED.getId());
        }
    }

    public static void buildDeadBush(Structure structure, int x, int y, int z) {
        structure.setBlock(x, y+1, z, Material.DEAD_BUSH.getId());
    }

    public static void buildSnowTree(Structure structure, int x, int y, int z, Material wood, Material leaf) {
        int treeSize = 12;
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
    public static void buildHouse(Structure structure, int x, int y, int z){
        //etage 0
        y=y+0;
        for(int i=0; i<9;i++){
            for(int j=0;j<6;j++){
                structure.setBlock(x+i,y,z+j,Material.STONE.getId());
            }
        }
        //etage 1
        y=y+1;
        for(int j =0;j<2;j++) {
            for(int i = 0; i < 6; i++) {
                structure.setBlock(x+i,y,z+j*9,Material.STONE.getId());
            }
        }
        for(int i=0;i<2;i++) {
            for (int j = 0; j <9; j++) {
                if(!(i==1 && j==7) ) {
                    structure.setBlock(x + i * 6, y, z + j, Material.STONE.getId());
                }
            }
        }
        //etage 2
        y=y+1;
        //stone
        structure.setBlock(x,y,z,Material.STONE.getId());
        structure.setBlock(x+5,y,z,Material.STONE.getId());
        structure.setBlock(x,y,z+8,Material.STONE.getId());
        structure.setBlock(x+5,y,z+8,Material.STONE.getId());
        //wood
        structure.setBlock(x+1,y,z,Material.BIRCH_LOG.getId());
        structure.setBlock(x+4,y,z,Material.BIRCH_LOG.getId());
        structure.setBlock(x+1,y,z+8,Material.BIRCH_LOG.getId());
        structure.setBlock(x+4,y,z+8,Material.BIRCH_LOG.getId());
        structure.setBlock(x,y,z+1,Material.BIRCH_LOG.getId());
        structure.setBlock(x,y,z+3,Material.BIRCH_LOG.getId());
        structure.setBlock(x,y,z+4,Material.BIRCH_LOG.getId());
        structure.setBlock(x,y,z+7,Material.BIRCH_LOG.getId());
        structure.setBlock(x+5,y,z+1,Material.BIRCH_LOG.getId());
        structure.setBlock(x+5,y,z+3,Material.BIRCH_LOG.getId());
        structure.setBlock(x+5,y,z+4,Material.BIRCH_LOG.getId());
        structure.setBlock(x+5,y,z+7,Material.BIRCH_LOG.getId());

        //etage 3
        y=y+1;
        for(int i=0;i<6;i++){
            structure.setBlock(x+i,y,z,Material.STONE.getId());
            structure.setBlock(x+i,y,z+8,Material.STONE.getId());
        }
        for(int i =0;i<9;i++){
            structure.setBlock(x+5,y,z+i,Material.BIRCH_LOG.getId());
            structure.setBlock(x+5,y,z+i,Material.BIRCH_LOG.getId());
        }
        for(int i =0;i<7;i++){
            structure.setBlock(x+5,y,z+1+i,Material.BIRCH_LOG.getId());
            structure.setBlock(x+5,y,z+1+i,Material.BIRCH_LOG.getId());
        }

        //etage 4
        y=y+1;
        for(int i = 0;i<9;i++){
            structure.setBlock(x,y,z+i,Material.BIRCH_LOG.getId());
            structure.setBlock(x+5,y,z+i,Material.BIRCH_LOG.getId());
        }
        for (int i = 0; i < 6; i++) {
            structure.setBlock(x+i,y,z,Material.BIRCH_LOG.getId());
            structure.setBlock(x+i,y,z+8,Material.BIRCH_LOG.getId());
        }

        //etage 5
        y=y+1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 4; j++) {
                structure.setBlock(x+j+1,y,z+i,Material.BIRCH_LOG.getId());
            }
        }

        //etage 6
        y=y+1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 2; j++) {
                structure.setBlock(x+j+2,y,z+i,Material.BIRCH_LOG.getId());
            }
        }
        System.out.println("A house has been built");
    }
}

