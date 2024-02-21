package fr.math.minecraft.client.builder;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.meshs.model.CactusModel;
import fr.math.minecraft.client.meshs.model.NatureModel;
import fr.math.minecraft.client.vertex.Vertex;
import fr.math.minecraft.client.meshs.model.BlockModel;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.Coordinates;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;

public class MeshBuilder {

    private static int counter = 0;
    private HashMap<Coordinates, Boolean> emptyMap = new HashMap<>();
    private final int SQUARE_POINTS = 4;

    public boolean isEmpty(int worldX, int worldY, int worldZ) {

        Coordinates coordinates = new Coordinates(worldX, worldY, worldZ);

        int chunkX = (int) Math.floor(worldX / (double) Chunk.SIZE);
        int chunkY = (int) Math.floor(worldY / (double) Chunk.SIZE);
        int chunkZ = (int) Math.floor(worldZ / (double) Chunk.SIZE);

        Game game = Game.getInstance();
        World world = game.getWorld();
        Chunk chunk = world.getChunk(chunkX, chunkY, chunkZ);

        if (chunk == null) {
            // chunk = new Chunk(chunkX, chunkY, chunkZ);
            // chunk.generate(world, world.getTerrainGenerator());
            return true;
        }

        int blockX = worldX % Chunk.SIZE;
        int blockY = worldY % Chunk.SIZE;
        int blockZ = worldZ % Chunk.SIZE;

        blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
        blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
        blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;

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

                    if(material == Material.WEED || material == Material.ROSE || material == Material.DEAD_BUSH) {
                        textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                        for (int k = 0; k < SQUARE_POINTS; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(NatureModel.FIRST_FACE[k]), textureCoords[k],material.getId(),0));
                        }

                        currentIndice = this.updateIndice(indices, currentIndice);

                        for (int k = 0; k < SQUARE_POINTS; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(NatureModel.SECOND_FACE[k]), textureCoords[k],material.getId(),0));
                        }

                        currentIndice = this.updateIndice(indices, currentIndice);
                    } else if (material == Material.CACTUS) {
                        if (px) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getPx().x, material.getPx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.PX_POS[k]), textureCoords[k],material.getId(),0));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (nx) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getNx().x, material.getNx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.NX_POS[k]), textureCoords[k],material.getId(),1));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (py) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getPy().x, material.getPy().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.PY_POS[k]), textureCoords[k],material.getId(),2));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (ny) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getNy().x, material.getNy().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.NY_POS[k]), textureCoords[k],material.getId(),3));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (pz) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getPz().x, material.getPz().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.PZ_POS[k]), textureCoords[k],material.getId(),4));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (nz) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getNz().x, material.getNz().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.NZ_POS[k]), textureCoords[k],material.getId(),5));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }
                    } else {
                        if (px) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getPx().x, material.getPx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);
                            }
                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.PX_POS[k]), textureCoords[k],material.getId(),0));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (nx) {
                            if (material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getNx().x, material.getNx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.NX_POS[k]), textureCoords[k],material.getId(),1));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (py) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getPy().x, material.getPy().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.PY_POS[k]), textureCoords[k],material.getId(),2));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (ny) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getNy().x, material.getNy().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.NY_POS[k]), textureCoords[k],material.getId(),3));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (pz) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getPz().x, material.getPz().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.PZ_POS[k]), textureCoords[k],material.getId(),4));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (nz) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getNz().x, material.getNz().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.NZ_POS[k]), textureCoords[k],material.getId(),5));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }
                    }
                }
            }
        }
        counter = 0;

        return vertices.toArray(new Vertex[0]);
    }

}
