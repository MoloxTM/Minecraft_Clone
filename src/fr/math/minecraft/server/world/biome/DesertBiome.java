package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.shared.world.*;
import fr.math.minecraft.shared.world.generator.NoiseGenerator;
import fr.math.minecraft.shared.world.generator.OverworldGenerator;
import org.joml.Vector3i;

public class DesertBiome extends AbstractBiome {

    private static int noiseOffset = (Chunk.SIZE / 2) - 1;

    public DesertBiome() {
        this.noise = new NoiseGenerator(9, 30, 500.0f, .5f, 25);
        this.treeNoise = new NoiseGenerator(4, 25, 0.001f, 0.31f, 0);
        this.weedNoise = new NoiseGenerator(4, 25, 0.05f, 0.2f, 0);
        this.biomeName = "Deserts";
        this.biomeID = 0;
    }

    public Material getUpperBlock() {
        return Material.SAND;
    }

    @Override
    public Material getSecondBlock() {
        return Material.SAND;
    }

    @Override
    public void buildTree(int worldX, int worldY, int worldZ, Structure structure, World world) {

        Coordinates coordinates = new Coordinates(worldX + noiseOffset, worldY, worldZ + noiseOffset);

        if(structure.getStructureMap().containsKey(coordinates)) {
            return;
        }

        if(worldY <= OverworldGenerator.WATER_LEVEL) {
            return;
        }

        float treeNoiseValue = treeNoise.getNoise(worldX + noiseOffset, worldZ + noiseOffset);

        if(treeNoiseValue < 0.1f) {
            StructureBuilder.buildSimpleCactus(structure, worldX, worldY, worldZ, treeNoiseValue);
        }
    }

    @Override
    public void buildWeeds(int worldX, int worldY, int worldZ, Structure structure, World world) {

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);

        if (worldY <= OverworldGenerator.WATER_LEVEL) {
            return;
        }

        if(structure.getStructureMap().containsKey(coordinates)) {
            return;
        }

        float weedNoiseValue = weedNoise.getNoise(worldX, worldZ);

        if (weedNoiseValue < 0.1f) {
            StructureBuilder.buildDeadBush(structure, worldX, worldY, worldZ);
        }
    }

    @Override
    public void buildVillage(int worldX, int worldY, int worldZ, Structure structure, World world, Region region) {
        if (worldY <= OverworldGenerator.WATER_LEVEL) {
            return;
        }
        Vector3i regionPosition = region.getPosition();
        int houseCount=0;
        int maxHousePerVillage=5;

        for (int i = regionPosition.x; i < (Region.SIZE * Chunk.SIZE)+ regionPosition.x; i+=9) {
            for (int j = regionPosition.z; j < (Region.SIZE *Chunk.SIZE)+ regionPosition.z; j+=11) {

                int x = i - regionPosition.x;
                int z = j - regionPosition.z;

                if(Region.SIZE / 4 < x && x < Chunk.SIZE * Region.SIZE - Region.SIZE / 4 && Region.SIZE / 4 < z && z < Chunk.SIZE * Region.SIZE - Region.SIZE / 4) {
                    if(canBuildHouse(worldX+i,worldY,worldZ+j,world) && houseCount<=maxHousePerVillage){
                        StructureBuilder.buildHouseDesert(structure,worldX+i,worldY,worldZ+j);
                        houseCount++;
                        region.setHasVillage(true);
                    }
                }
                if(houseCount==5){return;}
            }
        }
    }

    public boolean canBuildHouse(int worldX, int worldY, int worldZ,World world){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if((int)this.getHeight(worldX+i,worldZ+j,world.getSeed())!=worldY) {
                    return false;
                }
            }
        }
        return true;
    }


}
