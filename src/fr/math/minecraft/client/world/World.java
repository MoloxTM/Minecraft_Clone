package fr.math.minecraft.client.world;

import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class World {

    private final HashMap<Coordinates, Chunk> chunks;
    private final ArrayList<Byte> transparents;

    public World() {
        this.chunks = new HashMap<>();
        this.transparents = initTransparents();
    }

    public HashMap<Coordinates, Chunk> getChunks() {
        return chunks;
    }

    public void addChunk(Chunk chunk) {
        chunks.put(new Coordinates(chunk.getPosition().x, chunk.getPosition().y, chunk.getPosition().z), chunk);
    }

    public Chunk getChunk(int x, int y, int z) {
        Coordinates coordinates = new Coordinates(x, y, z);
        if (!chunks.containsKey(coordinates)) {
            return null;
        }
        return chunks.get(coordinates);
    }

    public ArrayList<Byte> initTransparents() {
        ArrayList<Byte> transparent = new ArrayList<>();
        transparent.add(Material.AIR.getId());
        transparent.add(Material.OAK_LEAVES.getId());
        transparent.add(Material.WEED.getId());
        transparent.add(Material.ROSE.getId());
        transparent.add(Material.CACTUS.getId());
        transparent.add(Material.DEAD_BUSH.getId());
        transparent.add(Material.BIRCH_LEAVES.getId());
        return transparent;
    }

    public ArrayList<Byte> getTransparents() {
        return transparents;
    }
}
