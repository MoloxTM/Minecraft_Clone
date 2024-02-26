package fr.math.minecraft.shared.world;

import fr.math.minecraft.client.meshs.WaterMesh;
import fr.math.minecraft.server.world.biome.AbstractBiome;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.shared.world.generator.OverworldGenerator;
import fr.math.minecraft.shared.world.generator.TerrainGenerator;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.HashMap;
import java.util.Map;

public class Chunk {

    private final Vector3i position;
    private Vector3f center;
    private final byte[] blocks;
    private boolean empty;

    public final static int SIZE = 16;
    public final static int AREA = SIZE * SIZE;
    public final static int VOLUME = SIZE * AREA;
    public final static float SPHERE_RADIUS = (float) (SIZE * Math.sqrt(3) / 2.0);
    private ChunkMesh mesh;
    private WaterMesh waterMesh;
    private boolean shouldDelete;
    private boolean loaded;
    private boolean generated;
    private AbstractBiome biome;
    private final Map<Coordinates, Boolean> emptyMap;

    public Chunk(int x, int y, int z) {
        this.position = new Vector3i(x, y, z);
        this.blocks = new byte[VOLUME];
        for (int blockX = 0; blockX < Chunk.SIZE; blockX++) {
            for (int blockY = 0; blockY < Chunk.SIZE; blockY++) {
                for (int blockZ = 0; blockZ < Chunk.SIZE; blockZ++) {
                    this.setBlock(blockX, blockY, blockZ, Material.AIR.getId());
                }
            }
        }
        this.emptyMap = new HashMap<>();
        this.center = this.calculateCenter();
        this.empty = true;
        this.shouldDelete = false;
        this.loaded = false;
        this.generated = false;
        this.mesh = null;
        this.waterMesh = null;
        this.biome = null;
    }

    public void generate(World world, TerrainGenerator terrainGenerator) {
        new OverworldGenerator().generateChunk(world, this);
        this.generated = true;
    }

    public void update() {
        if(!this.loaded) return;
        mesh.delete();
        mesh = new ChunkMesh(this);

        waterMesh.delete();
        waterMesh = new WaterMesh(this);
    }

    public byte[] getBlocks() {
        return blocks;
    }

    public void setBlock(int x, int y, int z, byte block) {
        blocks[x + y * AREA + z * SIZE] = block;
    }

    public byte getBlock(int x, int y, int z) {
        return blocks[x + y * AREA + z * SIZE];
    }

    public Vector3i getPosition() {
        return position;
    }

    public ChunkMesh getMesh() {
        return mesh;
    }

    public void setMesh(ChunkMesh chunkMesh) {
        this.mesh = chunkMesh;
    }

    public WaterMesh getWaterMesh() {
        return waterMesh;
    }

    public void setWaterMesh(WaterMesh waterMesh) {
        this.waterMesh = waterMesh;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public int getBlocksSize() {
        int count = 0;
        for (byte block : blocks) {
            if (block != Material.AIR.getId()) {
                count++;
            }
        }
        return count;
    }

    private Vector3f calculateCenter() {
        float centerX = (position.x + 0.5f) * SIZE;
        float centerY = (position.y + 0.5f) * SIZE;
        float centerZ = (position.z + 0.5f) * SIZE;

        return new Vector3f(centerX, centerY, centerZ);
    }

    public Vector3f getCenter() {
        return center;
    }

    public boolean isOutOfView(Player player) {

        int minX = (int) (player.getPosition().x / Chunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE);
        int minZ = (int) (player.getPosition().z / Chunk.SIZE - GameConfiguration.CHUNK_RENDER_DISTANCE);

        int maxX = (int) (player.getPosition().x / Chunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE);
        int maxZ = (int) (player.getPosition().z / Chunk.SIZE + GameConfiguration.CHUNK_RENDER_DISTANCE);

        return this.position.x < minX || this.position.x > maxX || this.position.z < minZ || this.position.z > maxZ;
    }

    public void setShouldDelete(boolean shouldDelete) {
        this.shouldDelete = shouldDelete;
    }

    public boolean shouldDelete() {
        return shouldDelete;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public AbstractBiome getBiome() {
        return biome;
    }

    public void setBiome(AbstractBiome biome) {
        this.biome = biome;
    }

    public Map<Coordinates, Boolean> getEmptyMap() {
        return emptyMap;
    }
}
