package fr.math.minecraft.shared.world;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.gui.menus.ConnectionMenu;
import fr.math.minecraft.client.gui.menus.Menu;
import fr.math.minecraft.client.manager.MenuManager;
import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.meshs.WaterMesh;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.server.pathfinding.Graph;
import fr.math.minecraft.shared.entity.Entity;
import fr.math.minecraft.shared.entity.EntityType;
import fr.math.minecraft.shared.entity.Villager;
import fr.math.minecraft.shared.world.generator.OverworldGenerator;
import fr.math.minecraft.shared.world.generator.TerrainGenerator;
import org.apache.log4j.Logger;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private final HashMap<Coordinates, Chunk> chunks;
    private final Map<String, Entity> entities;
    private final Map<Vector3i, Byte> cavesBlocks;
    private final ConcurrentHashMap<Coordinates, Chunk> pendingChunks;
    private final Set<Coordinates> loadingChunks;
    private final ArrayList<Byte> transparents;
    private final Set<Byte> solidBlocks;
    private final Map<Vector3i, Chunk> cachedChunks;
    private final Map<Coordinates, Region> regions;
    private Vector3f spawnPosition;
    private final Map<String, DroppedItem> droppedItems;
    private final static Logger logger = LoggerUtility.getServerLogger(World.class, LogType.TXT);
    private final Map<Vector3i, BreakedBlock> brokenBlocks;
    private final Map<Vector3i, PlacedBlock> placedBlocks;
    private Set<Coordinates> loadedRegions;
    private TerrainGenerator terrainGenerator;
    private final int SPAWN_SIZE = 2;
    private final Graph graph;
    private float seed;

    public World() {
        this.chunks = new HashMap<>();
        this.regions = new HashMap<>();
        this.cavesBlocks = new HashMap<>();
        this.pendingChunks = new ConcurrentHashMap<>();
        this.loadingChunks = new HashSet<>();
        this.solidBlocks = new HashSet<>();
        this.transparents = initTransparents();
        this.terrainGenerator = new OverworldGenerator();
        this.cachedChunks = new HashMap<>();
        this.droppedItems = new HashMap<>();
        this.brokenBlocks = new HashMap<>();
        this.placedBlocks = new HashMap<>();
        this.loadedRegions = new HashSet<>();
        this.entities = new HashMap<>();
        this.graph = new Graph();
        this.seed = 0;

        for (Material material : Material.values()) {
            if (material.isSolid()) {
                solidBlocks.add(material.getId());
            }
        }

    }

    public void calculateSpawnPosition() {
        int spawnX = 0;
        int spawnZ = 0;
        for (int chunkY = 5; chunkY < 10; chunkY++) {
            for (int y = 0; y < Chunk.SIZE; y++) {
                int worldY = chunkY * Chunk.SIZE + y;
                byte block = this.getBlockAt(spawnX, worldY, spawnZ);
                if (block == Material.AIR.getId()) {
                    spawnPosition = new Vector3f(spawnX, worldY + 20, spawnZ);
                    return;
                }
            }
        }
        spawnPosition = new Vector3f(0, 300.0f, 0);
    }


    public void buildSpawn() {
        logger.info("Construction du spawn...");
        for (int x = -SPAWN_SIZE; x < SPAWN_SIZE; x++) {
            for (int y = 0; y < 10; y++) {
                for (int z = -SPAWN_SIZE; z < SPAWN_SIZE; z++) {
                    Chunk chunk = new Chunk(x, y, z);
                    chunk.generate(this, terrainGenerator);
                    this.addChunk(chunk);
                }
            }
        }
        logger.info("Spawn construit avec succès !");
    }

    public void buildSpawnMesh() {
        for (int x = -SPAWN_SIZE; x < SPAWN_SIZE; x++) {
            for (int y = 0; y < 10; y++) {
                for (int z = -SPAWN_SIZE; z < SPAWN_SIZE; z++) {
                    Chunk chunk = this.getChunk(x, y, z);

                    if (chunk.isEmpty()) {
                        continue;
                    }

                    ChunkMesh chunkMesh = new ChunkMesh(chunk);
                    WaterMesh waterMesh = new WaterMesh(chunk);

                    chunk.setMesh(chunkMesh);
                    chunk.setWaterMesh(waterMesh);
                    chunk.setLoaded(true);

                }
            }
        }
    }

    public HashMap<Coordinates, Chunk> getChunks() {
        return chunks;
    }

    public void removeChunk(Chunk chunk) {
        Coordinates coordinates = new Coordinates(chunk.getPosition().x, chunk.getPosition().y, chunk.getPosition().z);
        synchronized (this.getChunks()) {
            chunks.remove(coordinates);
        }
    }

    public synchronized void addChunk(Chunk chunk) {
        chunks.put(new Coordinates(chunk.getPosition().x, chunk.getPosition().y, chunk.getPosition().z), chunk);
    }

    public void addPendingChunk(Chunk chunk) {
        synchronized (this.getPendingChunks()) {
            pendingChunks.put(new Coordinates(chunk.getPosition().x, chunk.getPosition().y, chunk.getPosition().z), chunk);
        }
    }

    public Chunk getChunk(int x, int y, int z) {
        Coordinates coordinates = new Coordinates(x, y, z);
        return chunks.get(coordinates);
    }

    public Chunk getChunkAt(Vector3i position) {
        return this.getChunkAt(position.x, position.y, position.z);
    }

    public Chunk getChunkAt(int worldX, int worldY, int  worldZ) {
        int chunkX = (int) Math.floor(worldX / (double) Chunk.SIZE);
        int chunkY = (int) Math.floor(worldY / (double) Chunk.SIZE);
        int chunkZ = (int) Math.floor(worldZ / (double) Chunk.SIZE);

        return this.getChunk(chunkX, chunkY, chunkZ);
    }

    public byte getServerBlockAt(int worldX, int worldY, int worldZ) {
        Chunk chunk = getChunkAt(worldX, worldY, worldZ);

        if (chunk == null) {
            int chunkX = (int) Math.floor(worldX / (double) Chunk.SIZE);
            int chunkY = (int) Math.floor(worldY / (double) Chunk.SIZE);
            int chunkZ = (int) Math.floor(worldZ / (double) Chunk.SIZE);
            chunk = new Chunk(chunkX, chunkY, chunkZ);
            chunk.generate(this, terrainGenerator);
            synchronized (this.getChunks()) {
                this.addChunk(chunk);
            }
        }
        //Chopper les coos du block
        int blockX = worldX % Chunk.SIZE;
        int blockY = worldY % Chunk.SIZE;
        int blockZ = worldZ % Chunk.SIZE;

        blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
        blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
        blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;

        return chunk.getBlock(blockX, blockY, blockZ);
    }

    public byte getBlockAt(int worldX, int worldY, int worldZ) {
        //Déterminer le chunck
        Chunk chunk = getChunkAt(worldX, worldY, worldZ);

        if (chunk == null) {
            return Material.AIR.getId();
        }

        //Chopper les coos du block
        int blockX = worldX % Chunk.SIZE;
        int blockY = worldY % Chunk.SIZE;
        int blockZ = worldZ % Chunk.SIZE;

        blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
        blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
        blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;

        return chunk.getBlock(blockX, blockY, blockZ);
    }

    public Set<Coordinates> getLoadingChunks() {
        return loadingChunks;
    }
    
    public ArrayList<Byte> initTransparents() {
        ArrayList<Byte> transparent = new ArrayList<>();
        transparent.add(Material.AIR.getId());
        transparent.add(Material.OAK_LEAVES.getId());
        transparent.add(Material.BIRCH_LEAVES.getId());
        transparent.add(Material.WEED.getId());
        transparent.add(Material.ROSE.getId());
        transparent.add(Material.CACTUS.getId());
        transparent.add(Material.DEAD_BUSH.getId());
        transparent.add(Material.GLASS.getId());
        return transparent;
    }

    public ArrayList<Byte> getTransparents() {
        return transparents;
    }

    public ConcurrentHashMap<Coordinates, Chunk> getPendingChunks() {
        return pendingChunks;
    }

    public Set<Byte> getSolidBlocks() {
        return solidBlocks;
    }

    public TerrainGenerator getTerrainGenerator() {
        return terrainGenerator;
    }

    public Vector3i regionPosition(Vector3f playerPos) {
        Vector3i intPlayerPos = new Vector3i((int)playerPos.x, (int)playerPos.y, (int)playerPos.z);
        Vector3i regionPosition = new Vector3i();
        int offset = (Region.SIZE * Chunk.SIZE)/2;
        Vector3i offsetVector = new Vector3i(-offset, 0, -offset);
        regionPosition.add(intPlayerPos);
        regionPosition.add(offsetVector);
        return regionPosition;
    }

    public void generateRegion(Vector3i regionPosition) {
        Coordinates coordinates = new Coordinates(regionPosition);
        if (!loadedRegions.contains(coordinates)) {
            loadedRegions.add(coordinates);
            Region region = new Region(regionPosition);
            region.generateStructure(this);
            this.addRegion(region, coordinates);
        }
    }

    public void addRegion(Region region) {
        this.regions.put(new Coordinates(region.getPosition().x, region.getPosition().y, region.getPosition().z), region);
    }

    public void addRegion(Region region, Coordinates coordinates) {
        this.regions.put(coordinates, region);
    }

    public Region getRegion(int x, int y, int z) {
        Coordinates coordinates = new Coordinates(x, y, z);
        return regions.get(coordinates);
    }

    public Region getRegion(Vector3i pos) {
        Coordinates coordinates = new Coordinates(pos);
        return regions.get(coordinates);
    }

    public Region getRegion(Coordinates coordinates) {
        return regions.get(coordinates);
    }

    public Map<Coordinates, Region> getRegions() {
        return regions;
    }

    public Vector3f getSpawnPosition() {
        return spawnPosition;
    }

    public Map<Vector3i, Chunk> getCachedChunks() {
        return cachedChunks;
    }

    public void setBlock(Vector3i position, byte block) {
        this.setBlock(position.x, position.y, position.z, block);
    }

    public void setBlock(int worldX, int worldY, int worldZ, byte block) {

    }

    public Map<Vector3i, Byte> getCavesBlocks() {
        return cavesBlocks;
    }

    public Map<String, DroppedItem> getDroppedItems() {
        return droppedItems;
    }

    public void setSpawnPosition(Vector3f spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

    public Map<Vector3i, BreakedBlock> getBrokenBlocks() {
        return brokenBlocks;
    }

    public Map<Vector3i, PlacedBlock> getPlacedBlocks() {
        return placedBlocks;
    }

    public ObjectNode toJSONObject() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode worldNode = mapper.createObjectNode();
        ArrayNode brokenBlocksArray = mapper.createArrayNode();
        ArrayNode placedBlocksArray = mapper.createArrayNode();

        for (BreakedBlock breakedBlock : brokenBlocks.values()) {
            brokenBlocksArray.add(breakedBlock.toJSONObject());
        }

        for (PlacedBlock placedBlock : placedBlocks.values()) {
            placedBlocksArray.add(placedBlock.toJSONObject());
        }

        worldNode.set("brokenBlocks", brokenBlocksArray);
        worldNode.set("placedBlocks", placedBlocksArray);

        return worldNode;
    }

    public Map<String, Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        synchronized (this.getEntities()) {
            entities.put(entity.getUuid(), entity);
        }
    }

    public void removeEntity(Entity entity) {
        synchronized (this.getEntities()) {
            entities.remove(entity.getUuid());
        }
    }

    public Graph getGraph() {
        return graph;
    }

    public float getSeed() {
        return seed;
    }

    public void setSeed(float seed) {
        this.seed = seed;
    }
}
