package fr.math.minecraft.server.builder;

import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.world.Structure;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;

public class  StructureBuilder {

    public static void buildSimpleTree(Structure structure, int x, int y, int z, Material wood, Material leaf, float droprate, float treeType) {
        int treeSize = 6;
        if (droprate <= treeType * 0.33f) {
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

    public static void buildSimpleCactus(Structure structure, int x, int y, int z, float noiseValue) {
        int cactusSize = noiseValue < .12f ? 4 : 5;
        if(noiseValue < .12f) {
            for(int i = 1; i <= cactusSize; i++) {
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
            for (int i = 1; i <= cactusSize; i++) {
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

    public static void buildWeed(World world, Structure structure, int x, int y, int z, float noiseValue) {
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
                if(j == 0 || j == 5) {
                    structure.setBlock(x+i,y,z+j,Material.COBBLESTONE.getId());
                } else if(i == 0 || i == 8) {
                    structure.setBlock(x+i,y,z+j,Material.COBBLESTONE.getId());
                } else {
                    structure.setBlock(x+i,y,z+j,Material.OAK_PLANKS.getId());
                }
            }
        }
        //etage 1
        y=y+1;
        for(int j = 0; j < 2 ; j++) {
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
    }

    public static void buildBigHousePlain(Structure structure, int x, int worldY, int z){
        //etage 0
        int y = worldY;
        y=y+0;
        for(int i=0; i< 9 ;i++){
            for(int j=0;j < 6;j++){
                if(j == 0 || j == 5) {
                    structure.setBlock(x+j,y,z+i,Material.COBBLESTONE.getId());
                } else if(i == 0 || i == 8) {
                    structure.setBlock(x+j,y,z+i,Material.COBBLESTONE.getId());
                } else {
                    structure.setBlock(x+j,y,z+i,Material.OAK_PLANKS.getId());
                }
            }
        }
        //etage 1
        y=y+1;
        for(int i=0; i<9;i++){
            for(int j=0;j<6;j++){
                if(j == 0 || j == 5) {
                    structure.setBlock(x+j,y,z+i,Material.COBBLESTONE.getId());
                } else if(i == 0 || i == 8) {
                    structure.setBlock(x+j,y,z+i,Material.COBBLESTONE.getId());
                } else {
                    structure.setBlock(x+j,y,z+i,Material.AIR.getId());
                }

                if(i == 2 && j == 0) {
                    structure.setBlock(x+j,y,z+i,Material.AIR.getId());
                }
            }
        }
        structure.setBlock(x + 1, y ,z + 7, Material.CRAFTING_TABLE.getId());
        structure.setBlock(x + 2, y ,z + 7, Material.FURNACE.getId());
        //etage 2
        y=y+1;
        for(int i=0; i<9;i++){
            for(int j=0;j<6;j++){
                if(j == 0 || j == 5) {
                    structure.setBlock(x+j,y,z+i,Material.COBBLESTONE.getId());
                } else if(i == 0 || i == 8) {
                    structure.setBlock(x+j,y,z+i,Material.COBBLESTONE.getId());
                } else {
                    structure.setBlock(x+j,y,z+i,Material.AIR.getId());
                }

                if(i == 2 && j == 0) {
                    structure.setBlock(x+j,y,z+i,Material.AIR.getId());
                }

                if(j == 5) {
                    if( i == 2 || i == 3 || i == 5 || i == 6) {
                        structure.setBlock(x+j,y,z+i,Material.GLASS.getId());
                    }
                }
                if(i == 5 && j == 0) {
                    structure.setBlock(x+j,y,z+i,Material.GLASS.getId());
                }
            }
        }

        //etage 3
        y=y+1;
        for(int i=0; i<9;i++){
            for(int j=0;j<6;j++){
                if(j == 0 || j == 5) {
                    structure.setBlock(x+j,y,z+i,Material.COBBLESTONE.getId());
                } else if(i == 0 || i == 8) {
                    structure.setBlock(x+j,y,z+i,Material.COBBLESTONE.getId());
                } else {
                    structure.setBlock(x+j,y,z+i,Material.AIR.getId());
                }
            }
        }

        y=y+1;
        for(int i = 0;i<9;i++){
            structure.setBlock(x,y,z+i,Material.OAK_PLANKS.getId());
            structure.setBlock(x+5,y,z+i,Material.OAK_PLANKS.getId());
        }
        for (int i = 0; i < 6; i++) {
            structure.setBlock(x+i,y,z,Material.OAK_PLANKS.getId());
            structure.setBlock(x+i,y,z+8,Material.OAK_PLANKS.getId());
        }

        //etage 5
        y=y+1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 4; j++) {
                structure.setBlock(x+j+1,y,z+i,Material.OAK_PLANKS.getId());
            }
        }

        //etage 6
        y=y+1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 2; j++) {
                structure.setBlock(x+j+2,y,z+i,Material.OAK_PLANKS.getId());
            }
        }

        for (int k = 0; k < 4; k++) {
            structure.setBlock(x,worldY + k,z,Material.OAK_LOG.getId());
            structure.setBlock(x + 5,worldY + k,z,Material.OAK_LOG.getId());
            structure.setBlock(x,worldY + k, z + 8, Material.OAK_LOG.getId());
            structure.setBlock(x + 5,worldY + k, z + 8, Material.OAK_LOG.getId());
        }
    }


    public static void buildCube(Structure structure,int x, int y, int z, int size1, int size2, Material material){
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                for (int k = 0; k < 5; k++) {
                    structure.setBlock(x+i,y+k,z+j, material.getId());
                }
            }
        }
    }
    public static void buildHouseDesert(Structure structure, int worldX, int worldY, int worldZ) {
        int y = worldY;
        int x = worldX;
        int z = worldZ;
        y = y + 1;
        //Etage 1
        for (int i = 0; i < 5; i++) {
            if(i == 2) {
                structure.setBlock(x + i, y, z, Material.AIR.getId());
                structure.setBlock(x + i, y, z + 4, Material.SAND_STONE.getId());
            } else {
                structure.setBlock(x + i, y, z, Material.SAND_STONE.getId());
                structure.setBlock(x + i, y, z + 4, Material.SAND_STONE.getId());
            }
        }
        for (int i = 0; i < 5; i++) {
            if(i == 1 || i == 3) {
                structure.setBlock(x, y, z + i, Material.CUT_SANDSTONE.getId());
                structure.setBlock(x + 4, y, z + i, Material.CUT_SANDSTONE.getId());
            } else {
                structure.setBlock(x, y, z + i, Material.SAND_STONE.getId());
                structure.setBlock(x + 4, y, z + i, Material.SAND_STONE.getId());
            }
        }
        y = y + 1;
        //Etage 2
        for (int i = 0; i < 5; i++) {
            if(i == 2) {
                structure.setBlock(x + i, y, z, Material.AIR.getId());
                structure.setBlock(x + i, y, z + 4, Material.SMOOTH_SANDSTONE.getId());
            } else {
                structure.setBlock(x + i, y, z, Material.SMOOTH_SANDSTONE.getId());
                structure.setBlock(x + i, y, z + 4, Material.SMOOTH_SANDSTONE.getId());
            }
        }
        for (int i = 0; i < 5; i++) {
            if(i == 1 || i == 3) {
                structure.setBlock(x, y, z + i, Material.CUT_SANDSTONE.getId());
                structure.setBlock(x + 4, y, z + i, Material.CUT_SANDSTONE.getId());
            } else {
                structure.setBlock(x, y, z + i, Material.SMOOTH_SANDSTONE.getId());
                structure.setBlock(x + 4, y, z + i, Material.SMOOTH_SANDSTONE.getId());
            }
        }
        y = y + 1;
        //Etage 3
        for (int i = 0; i < 5; i++) {
            if(i == 2) {
                structure.setBlock(x + i, y, z, Material.SMOOTH_SANDSTONE.getId());
                structure.setBlock(x + i, y, z + 4, Material.SMOOTH_SANDSTONE.getId());
            } else {
                structure.setBlock(x + i, y, z, Material.SMOOTH_SANDSTONE.getId());
                structure.setBlock(x + i, y, z + 4, Material.SMOOTH_SANDSTONE.getId());
            }
        }
        for (int i = 0; i < 5; i++) {
            if(i == 1 || i == 3) {
                structure.setBlock(x, y, z + i, Material.CUT_SANDSTONE.getId());
                structure.setBlock(x + 4, y, z + i, Material.CUT_SANDSTONE.getId());
            } else {
                structure.setBlock(x, y, z + i, Material.SMOOTH_SANDSTONE.getId());
                structure.setBlock(x + 4, y, z + i, Material.SMOOTH_SANDSTONE.getId());
            }
        }
        y = y + 1;
        //Etage 4
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                structure.setBlock(x + i, y, z + j, Material.CUT_SANDSTONE.getId());
            }
        }
        y = y + 1;
        //Etage 5
        for (int i = 0; i < 5; i++) {
            if(i % 2 == 0) {
                structure.setBlock(x + i, y, z, Material.CUT_SANDSTONE.getId());
                structure.setBlock(x + i, y, z + 4, Material.CUT_SANDSTONE.getId());
            }
        }
        for (int i = 0; i < 5; i++) {
            if(i % 2 == 0) {
                structure.setBlock(x, y, z + i, Material.CUT_SANDSTONE.getId());
                structure.setBlock(x + 4, y, z + i, Material.CUT_SANDSTONE.getId());
            }
        }
    }
}

