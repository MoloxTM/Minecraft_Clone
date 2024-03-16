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

import java.util.HashMap;
import java.util.Map;

public class Region {

    private final Vector3i position;
    private final Map<Coordinates, Byte> structureMap;
    public final static int SIZE = 8;
    private final Structure structure;
    private boolean hasVillage;
    private final static Logger logger = LoggerUtility.getServerLogger(Region.class, LogType.TXT);
    public Region(Vector3i position) {
        this.position = position;
        this.structure = new Structure();
        this.structureMap = new HashMap<>();
        this.hasVillage=false;
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
                if (SIZE / 4 < x && x < Chunk.SIZE * SIZE - SIZE / 4 && SIZE / 4 < z && z < Chunk.SIZE * SIZE - SIZE / 4) {
                    if(!this.hasVillage){currentBiome.buildVillage(worldX, worldHeight, worldZ, structure, world,this);}
                    currentBiome.buildWeeds(worldX, worldHeight, worldZ, structure, world);
                    currentBiome.buildTree(worldX, worldHeight, worldZ, structure, world);

                }
            }
        }

        logger.info("Structure généré avec succès ! ");
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
}
