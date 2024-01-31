package fr.math.minecraft.client.builder;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.meshs.model.NatureModel;
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

    private HashMap<Coordinates, Boolean> emptyMap = new HashMap<>();
    private final int SQUARE_POINTS = 4;

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

        if (chunk == null) return true;

        emptyMap.put(coordinates, world.getTransparents().contains(chunk.getBlock(blockX, blockY, blockZ)));
        return world.getTransparents().contains(chunk.getBlock(blockX, blockY, blockZ));
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
            texCoordsBottomRight,
        };

        return texCoords;
    }


    /*
    x : 6 bits, y : 6 bits, z : 6 bits, blockId : 8 bits, blockFace : 3 bits, occlusion : 2 bits
     */
    private float packData(float x, float y, float z, int blockId, int blockFace) {
        int xSize = 6, ySize = 6, zSize = 6, blockIdSize = 8, blockFaceSize = 3;

        int packFactor = 64 / (Chunk.SIZE + 1);

        int packedX = (int) ((x + .5f) * packFactor);
        int packedY = (int) ((y + .5f) * packFactor);
        int packedZ = (int) ((z + .5f) * packFactor);

        int packedData = (
            packedX << (ySize + zSize + blockIdSize + blockFaceSize) |
            packedY << (zSize + blockIdSize + blockFaceSize) |
            packedZ << (blockIdSize + blockFaceSize) |
            blockId << (blockFaceSize) |
            blockFace
        );

        return Float.intBitsToFloat(packedData);
    }

    private int updateIndice(ArrayList<Integer> indices, int currentIndice) {
        indices.add(currentIndice);
        indices.add(currentIndice + 1);
        indices.add(currentIndice + 2);
        indices.add(currentIndice + 2);
        indices.add(currentIndice + 3);
        indices.add(currentIndice);

        currentIndice += SQUARE_POINTS;

        return currentIndice;
    }

    private void addChunkVertex(ArrayList<Vertex> vertices, int x, int y, int z, Vector3f[] vertex, Vector2f[] textureCoords, Material material, int blockFace) {
        for (int k = 0; k < SQUARE_POINTS; k++)  {
            Vector3f blockVector = new Vector3f(x, y, z).add(vertex[k]);
            float packedData = this.packData(blockVector.x, blockVector.y, blockVector.z, material.getId(), blockFace);
            vertices.add(new Vertex(packedData, textureCoords[k]));
        }
    }

    public Vertex[] buildChunkMesh(Chunk chunk, ArrayList<Integer> indices) {
        int currentIndice = 0;

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

                    if(material == Material.WEED || material == Material.ROSE) {
                        textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                        this.addChunkVertex(vertices, x, y, z, NatureModel.FIRST_FACE, textureCoords, material, 0);
                        currentIndice = this.updateIndice(indices, currentIndice);

                        this.addChunkVertex(vertices, x, y, z, NatureModel.SECOND_FACE, textureCoords, material, 0);
                        currentIndice = this.updateIndice(indices, currentIndice);
                    } else {
                        if (px) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getPx().x, material.getPx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);
                            }

                            this.addChunkVertex(vertices, x, y, z, BlockModel.PX_POS, textureCoords, material, 0);
                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (nx) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getNx().x, material.getNx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            this.addChunkVertex(vertices, x, y, z, BlockModel.NX_POS, textureCoords, material, 1);
                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (py) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getPy().x, material.getPy().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            this.addChunkVertex(vertices, x, y, z, BlockModel.PY_POS, textureCoords, material, 2);
                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (ny) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getNy().x, material.getNy().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            this.addChunkVertex(vertices, x, y, z, BlockModel.NY_POS, textureCoords, material, 3);
                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (pz) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getPz().x, material.getPz().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            this.addChunkVertex(vertices, x, y, z, BlockModel.PZ_POS, textureCoords, material, 4);
                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (nz) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getNz().x, material.getNz().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            this.addChunkVertex(vertices, x, y, z, BlockModel.NZ_POS, textureCoords, material, 5);
                            currentIndice = this.updateIndice(indices, currentIndice);
                        }
                    }
                }
            }
        }

        return vertices.toArray(new Vertex[0]);
    }

}
