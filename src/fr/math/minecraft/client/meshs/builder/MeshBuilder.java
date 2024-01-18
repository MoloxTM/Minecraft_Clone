package fr.math.minecraft.client.meshs.builder;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.vertex.Vertex;
import fr.math.minecraft.client.meshs.model.BlockModel;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.Material;
import fr.math.minecraft.client.world.World;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;

public class MeshBuilder {

    private static int blockX = 0, blockY = 0;

    public static boolean isEmpty(int[] blocks, int worldX, int worldY, int worldZ) {
        if (worldX < 0 || worldY < 0 || worldZ < 0)return true;
        World world = Game.getInstance().getWorld();
        Chunk chunk = world.getChunk(worldX / Chunk.SIZE, worldY / Chunk.SIZE, worldZ / Chunk.SIZE);
        if (chunk == null) return true;
        //System.out.println("ID : " +( chunk.getBlock((worldX % Chunk.SIZE), (worldY % Chunk.SIZE), (worldZ % Chunk.SIZE)) == Material.AIR.getId()));
        return chunk.getBlock((worldX % Chunk.SIZE), (worldY % Chunk.SIZE), (worldZ % Chunk.SIZE)) == Material.AIR.getId();
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


    public static Vertex[] buildChunkMesh(Chunk chunk) {
        ArrayList<Vertex> vertices = new ArrayList<>();
        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int y = 0; y < Chunk.SIZE; y++) {
                for (int z = 0; z < Chunk.SIZE; z++) {

                    int block = chunk.getBlock(x, y, z);
                    if (block == Material.AIR.getId()) continue;

                    Material material = Material.getMaterialById(block);

                    if (material == null) material = Material.DEBUG;

                    int worldX = x + chunk.getPosition().x * Chunk.SIZE;
                    int worldY = y + chunk.getPosition().y * Chunk.SIZE;
                    int worldZ = z + chunk.getPosition().z * Chunk.SIZE;

                    boolean px = isEmpty(chunk.getBlocks(), worldX + 1, worldY, worldZ);
                    boolean nx = isEmpty(chunk.getBlocks(), worldX - 1, worldY, worldZ);
                    boolean py = isEmpty(chunk.getBlocks(), worldX, worldY + 1, worldZ);
                    boolean ny = isEmpty(chunk.getBlocks(), worldX, worldY - 1, worldZ);
                    boolean pz = isEmpty(chunk.getBlocks(), worldX, worldY, worldZ + 1);
                    boolean nz = isEmpty(chunk.getBlocks(), worldX, worldY, worldZ - 1);

                    Vector2f[] textureCoords;

                    if (px) {
                        if(material.isFaces()) {
                            textureCoords = calculateTexCoords(material.getPx().x, material.getPx().y, 16.0f);
                        } else {
                            textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                        }
                        for (int k = 0; k < 6; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(BlockModel.PX_POS[k]), textureCoords[k]));
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
                            vertices.add(new Vertex(blockVector.add(BlockModel.NX_POS[k]), textureCoords[k]));
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
                            vertices.add(new Vertex(blockVector.add(BlockModel.PY_POS[k]), textureCoords[k]));
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
                            vertices.add(new Vertex(blockVector.add(BlockModel.NY_POS[k]), textureCoords[k]));
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
                            vertices.add(new Vertex(blockVector.add(BlockModel.PZ_POS[k]), textureCoords[k]));
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
                            vertices.add(new Vertex(blockVector.add(BlockModel.NZ_POS[k]), textureCoords[k]));
                        }
                    }
                }
            }
        }

        return vertices.toArray(new Vertex[0]);
    }

}
