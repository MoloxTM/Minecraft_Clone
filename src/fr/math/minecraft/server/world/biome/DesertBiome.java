package fr.math.minecraft.server.world.biome;

import fr.math.minecraft.server.RandomSeed;
import fr.math.minecraft.server.Utils;
import fr.math.minecraft.server.builder.StructureBuilder;
import fr.math.minecraft.server.world.*;
import fr.math.minecraft.shared.world.*;
import fr.math.minecraft.shared.world.generator.NoiseGenerator;
import fr.math.minecraft.shared.world.generator.OverworldGenerator;
import org.joml.Vector3i;

public class DesertBiome extends AbstractBiome{

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

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);
        //Calul distance
        for (Coordinates coordinates1 : structure.getStructureMap().keySet()) {
            double dist = Utils.distance(coordinates, coordinates1);
            if (dist <= 20) return;
        }

        RandomSeed randomSeed = RandomSeed.getInstance();
        float dropRate = randomSeed.nextFloat() * 100.0f;
        if(dropRate < 0.3f) {
            StructureBuilder.buildSimpleCactus(structure, worldX, worldY, worldZ);
            structure.getStructures().add(coordinates);
        }
    }

    @Override
    public void buildWeeds(int worldX, int worldY, int worldZ, Structure structure, World world) {
        if (worldY <= OverworldGenerator.WATER_LEVEL) {
            return;
        }

        float weedNoiseValue = weedNoise.getNoise(worldX, worldZ);

        if (weedNoiseValue < 0.1f) {
            StructureBuilder.buildDeadBush(structure, worldX, worldY, worldZ);
        }
    }

    @Override
    public void buildVillage(int worldX, int worldY, int worldZ, Structure structure, World world, Region region) {
        Vector3i regionPosition = region.getPosition();
        int houseCount=0;
        int maxHousePerVillage=5;
        Vector3i beginningCoordinate= new Vector3i(worldX,worldY,worldZ);
        Vector3i endCoordinate= new Vector3i(worldX,worldY,worldZ);

        for (int i = regionPosition.x; i < (Region.SIZE * Chunk.SIZE)+ regionPosition.x; i+=9) {
            for (int j = regionPosition.z; j < (Region.SIZE *Chunk.SIZE)+ regionPosition.z; j+=11) {

                int x = i - regionPosition.x;
                int z = j - regionPosition.z;

                if(Region.SIZE / 4 < x && x < Chunk.SIZE * Region.SIZE - Region.SIZE / 4 && Region.SIZE / 4 < z && z < Chunk.SIZE * Region.SIZE - Region.SIZE / 4) {
                    if(canBuildHouse(worldX+i,worldY,worldZ+j) && houseCount<=maxHousePerVillage){
                        StructureBuilder.buildHouseDesert(structure,worldX+i,worldY,worldZ+j);
                        houseCount++;
                        region.setHasVillage(true);
                    }
                }
                if(houseCount==5){return;}
            }
        }
    }

    public boolean canBuildHouse(int worldX, int worldY, int worldZ){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                /*
                if((int)this.getHeight(worldX+i,worldZ+j)!=worldY){
                    return false;
                }
                */
                if((int)this.getHeight(worldX+i,worldZ+j)!=worldY) {
                    return false;
                }
            }
        }
        return true;
    }


}
