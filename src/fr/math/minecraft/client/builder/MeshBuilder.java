package fr.math.minecraft.client.builder;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.packet.ChunkEmptyPacket;
import fr.math.minecraft.client.vertex.Vertex;
import fr.math.minecraft.client.meshs.model.BlockModel;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.Coordinates;
import fr.math.minecraft.client.world.Material;
import fr.math.minecraft.client.world.World;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.HashMap;

public class MeshBuilder {

    private static int counter = 0;
    private HashMap<Coordinates, Boolean> emptyMap = new HashMap<>();

    public boolean isEmpty(int worldX, int worldY, int worldZ) {

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);

        if (emptyMap.containsKey(coordinates)) {
            return emptyMap.get(coordinates);
        }

        int chunkX = (int) Math.floor(worldX / (double) Chunk.SIZE);
        int chunkY = (int) Math.floor(worldY / (double) Chunk.SIZE);
        int chunkZ = (int) Math.floor(worldZ / (double) Chunk.SIZE);

        World world = Game.getInstance().getWorld();
        Chunk chunk = world.getChunk(chunkX, chunkY, chunkZ);

        int blockX = worldX % Chunk.SIZE;
        int blockY = worldY % Chunk.SIZE;
        int blockZ = worldZ % Chunk.SIZE;

        blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
        blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
        blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;

        emptyMap.put(coordinates, chunk.getBlock(blockX, blockY, blockZ) == Material.AIR.getId());
        return chunk.getBlock(blockX, blockY, blockZ) == Material.AIR.getId();
    }

    public static Vector2f[] calculateTexCoords(int x, int y, float format) {
        Vector2f texCoordsBottomLeft = new Vector2f(x/format, y/format);
        Vector2f texCoordsUpLeft = new Vector2f(x/format, (y + 1)/format);
        Vector2f texCoordsUpRight = new Vector2f((x + 1)/format, (y + 1)/format);
        Vector2f texCoordsBottomRight = new Vector2f((x + 1)/format, y/format);

        Vector2f[] texCoords = {
            texCoordsBottomLeft,
            texCoordsUpLeft,
            texCoordsUpRight,
            texCoordsUpRight,
            texCoordsBottomRight,
            texCoordsBottomLeft,
        };

        return texCoords;
    }


    public Vertex[] buildChunkMesh(Chunk chunk) {
        BlockModel blockModel = new BlockModel();
        ArrayList<Vertex> vertices = new ArrayList<>();
        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int y = 0; y < Chunk.SIZE; y++) {
                for (int z = 0; z < Chunk.SIZE; z++) {

                    byte block = chunk.getBlock(x, y, z);
                    if (block == Material.AIR.getId()) continue;

                    Material material = Material.getMaterialById(block);

                    if (material == null) material = Material.DEBUG;

                    int worldX = x + chunk.getPosition().x * Chunk.SIZE;
                    int worldY = y + chunk.getPosition().y * Chunk.SIZE;
                    int worldZ = z + chunk.getPosition().z * Chunk.SIZE;

                    boolean px = isEmpty(worldX + 1, worldY, worldZ);
                    boolean nx = isEmpty(worldX - 1, worldY, worldZ);
                    boolean py = isEmpty(worldX, worldY + 1, worldZ);
                    boolean ny = isEmpty(worldX, worldY - 1, worldZ);
                    boolean pz = isEmpty(worldX, worldY, worldZ + 1);
                    boolean nz = isEmpty(worldX, worldY, worldZ - 1);

                    Vector2f[] textureCoords;

                    if (px) {
                        if(material.isFaces()) {
                            textureCoords = calculateTexCoords(material.getPx().x, material.getPx().y, 16.0f);
                        } else {
                            textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                        }
                        for (int k = 0; k < 6; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(blockModel.PX_POS[k]), textureCoords[k]));
                        }
                    }

                    if (nx) {
                        if(material.isFaces()) {
                            textureCoords = calculateTexCoords(material.getNx().x, material.getNx().y, 16.0f);
                        } else {
                            textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                        }
                        for (int k = 0; k < 6; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(blockModel.NX_POS[k]), textureCoords[k]));
                        }
                    }

                    if (py) {
                        if(material.isFaces()) {
                            textureCoords = calculateTexCoords(material.getPy().x, material.getPy().y, 16.0f);
                        } else {
                            textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                        }
                        for (int k = 0; k < 6; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(blockModel.PY_POS[k]), textureCoords[k]));
                        }
                    }

                    if (ny) {
                        if(material.isFaces()) {
                            textureCoords = calculateTexCoords(material.getNy().x, material.getNy().y, 16.0f);
                        } else {
                            textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                        }
                        for (int k = 0; k < 6; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(blockModel.NY_POS[k]), textureCoords[k]));
                        }
                    }

                    if (pz) {
                        if(material.isFaces()) {
                            textureCoords = calculateTexCoords(material.getPz().x, material.getPz().y, 16.0f);
                        } else {
                            textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                        }
                        for (int k = 0; k < 6; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(blockModel.PZ_POS[k]), textureCoords[k]));
                        }
                    }

                    if (nz) {
                        if(material.isFaces()) {
                            textureCoords = calculateTexCoords(material.getNz().x, material.getNz().y, 16.0f);
                        } else {
                            textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                        }
                        for (int k = 0; k < 6; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(blockModel.NZ_POS[k]), textureCoords[k]));
                        }
                    }
                }
            }
        }
        counter = 0;

        return vertices.toArray(new Vertex[0]);
    }

}
