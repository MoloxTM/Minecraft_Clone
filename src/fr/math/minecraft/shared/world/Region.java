package fr.math.minecraft.shared.world;

import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.server.manager.BiomeManager;
import fr.math.minecraft.server.world.Structure;
import fr.math.minecraft.server.world.biome.AbstractBiome;
import fr.math.minecraft.server.world.biome.DesertBiome;
import fr.math.minecraft.server.world.biome.ForestBiome;
import fr.math.minecraft.server.world.biome.PlainBiome;
import fr.math.minecraft.shared.world.generator.OverworldGenerator;
import org.apache.log4j.Logger;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Region {

    private final Vector3i position;
    private final Map<Coordinates, Byte> structureMap;
    public final static int SIZE = 8;
    private final Structure structure;
    private boolean hasVillage;
    private ArrayList<Vector3i> villageArea;
    private final static Logger logger = LoggerUtility.getServerLogger(Region.class, LogType.TXT);
    public Region(Vector3i position) {
        this.position = position;
        this.structure = new Structure();
        this.structureMap = new HashMap<>();
        this.hasVillage=false;
        this.villageArea = new ArrayList<>();
    }

    public Region(int x, int y, int z) {
        this(new Vector3i(x, y, z));
    }

    public void generateStructure(World world) {

        logger.info("Generation des structures de la région " + position + "...");

        OverworldGenerator generator = new OverworldGenerator();
        for (int x = 0; x < SIZE * Chunk.SIZE; x++) {
            for (int z = 0; z < SIZE * Chunk.SIZE; z++) {

                int worldX = position.x * SIZE * Chunk.SIZE + x;
                int worldZ = position.z * SIZE * Chunk.SIZE + z;

                BiomeManager biomeManager = new BiomeManager();
                AbstractBiome currentBiome = biomeManager.getBiome(worldX, worldZ);

                int worldHeight = generator.getHeight(worldX, worldZ);
                if(!this.hasVillage && !hasVillageNeighboors(world)){
                    currentBiome.buildVillage(worldX, worldHeight, worldZ, structure, world,this);
                }
                if (SIZE / 4 < x && x < Chunk.SIZE * SIZE - SIZE / 4 && SIZE / 4 < z && z < Chunk.SIZE * SIZE - SIZE / 4) {
                    currentBiome.buildWeeds(worldX, worldHeight, worldZ, structure, world);
                    currentBiome.buildTree(worldX, worldHeight, worldZ, structure, world);
                }
            }
        }

        logger.info("Structure généré avec succès ! ");
    }

    private boolean inVillageArea(int x, int y, int z) {
        if(villageArea.size() < 2) {
            return false;
        }
        Vector3i firstCo = villageArea.get(0);
        Vector3i lastCo = villageArea.get(1);
        if((firstCo.x - 8) <= x && x <= (lastCo.x + 8) && (firstCo.z - 8) <= z && z <= (lastCo.z + 8)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean hasVillageNeighboors(World world) {
        Vector3i regionPos = this.getPosition();
        ArrayList<Vector3i> neighBoorsPos = new ArrayList<>();
        Vector3i regionNXPZ = regionPos.add(-1, 0, 1);
        Vector3i regionXPZ = regionPos.add(0, 0, 1);
        Vector3i regionPXPZ = regionPos.add(1, 0, 1);
        Vector3i regionNXZ = regionPos.add(-1, 0, 0);
        Vector3i regionPXZ = regionPos.add(1, 0, 0);
        Vector3i regionNXNZ = regionPos.add(-1, 0, -1);
        Vector3i regionXNZ = regionPos.add(0, 0, -1);
        Vector3i regionPXNZ = regionPos.add(1, 0, -1);

        neighBoorsPos.add(regionNXPZ);
        neighBoorsPos.add(regionNXZ);
        neighBoorsPos.add(regionPXPZ);
        neighBoorsPos.add(regionNXNZ);
        neighBoorsPos.add(regionXPZ);
        neighBoorsPos.add(regionPXNZ);
        neighBoorsPos.add(regionPXZ);
        neighBoorsPos.add(regionXNZ);

        for (Vector3i pos : neighBoorsPos) {
            Region neighB = world.getRegion(pos);
            if(neighB != null && neighB.hasVillage) {
                return true;
            }
        }
        return false;
    }

    public Map<Coordinates, Byte> getStructureMap() {
        return structureMap;
    }

    public Vector3i getPosition() {
        return position;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setHasVillage(boolean hasVillage) {
        this.hasVillage = hasVillage;
    }
    public boolean getHasVillage(){return hasVillage;}

    public ArrayList<Vector3i> getVillageArea() {
        return villageArea;
    }
}
