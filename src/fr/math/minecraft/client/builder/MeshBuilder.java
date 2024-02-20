package fr.math.minecraft.client.builder;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.meshs.model.CactusModel;
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

    private static int counter = 0;
    private HashMap<Coordinates, Boolean> emptyMap = new HashMap<>();

    public boolean isEmpty(int worldX, int worldY, int worldZ, byte mode) {

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

        if(chunk.getBlock(blockX, blockY, blockZ) == 2) {
            if(mode == 2){
                return false;
            } else {
                return true;
            }
        }

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
            texCoordsUpRight,
            texCoordsBottomRight,
            texCoordsBottomLeft,
        };

        return texCoords;
    }


    public Vertex[] buildWaterChunkMesh(Chunk chunk) {
        ArrayList<Vertex> vertices = new ArrayList<>();
        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int y = 0; y < Chunk.SIZE; y++) {
                for (int z = 0; z < Chunk.SIZE; z++) {

                    byte block = chunk.getBlock(x, y, z);
                    if (block == Material.WATER.getId()) {
                        Material material = Material.getMaterialById(block);

                        if (material == null) material = Material.DEBUG;

                        int worldX = x + chunk.getPosition().x * Chunk.SIZE;
                        int worldY = y + chunk.getPosition().y * Chunk.SIZE;
                        int worldZ = z + chunk.getPosition().z * Chunk.SIZE;

                        boolean px = isEmpty(worldX + 1, worldY, worldZ, material.getId());
                        boolean nx = isEmpty(worldX - 1, worldY, worldZ, material.getId());
                        boolean py = isEmpty(worldX, worldY + 1, worldZ, material.getId());
                        boolean ny = isEmpty(worldX, worldY - 1, worldZ, material.getId());
                        boolean pz = isEmpty(worldX, worldY, worldZ + 1, material.getId());
                        boolean nz = isEmpty(worldX, worldY, worldZ - 1, material.getId());

                        Vector2f[] textureCoords;
                        if (px) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getPx().x, material.getPx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < 6; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.PX_POS[k]), textureCoords[k],material.getId(),0));
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
                                vertices.add(new Vertex(blockVector.add(BlockModel.NX_POS[k]), textureCoords[k],material.getId(),1));
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
                                vertices.add(new Vertex(blockVector.add(BlockModel.PY_POS[k]), textureCoords[k],material.getId(),2));
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
                                vertices.add(new Vertex(blockVector.add(BlockModel.NY_POS[k]), textureCoords[k],material.getId(),3));
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
                                vertices.add(new Vertex(blockVector.add(BlockModel.PZ_POS[k]), textureCoords[k],material.getId(),4));
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
                                vertices.add(new Vertex(blockVector.add(BlockModel.NZ_POS[k]), textureCoords[k],material.getId(),5));
                            }
                        }
                    }
                }
            }
        }
        counter = 0;

        return vertices.toArray(new Vertex[0]);
    }

    public Vertex[] buildChunkMesh(Chunk chunk) {
        ArrayList<Vertex> vertices = new ArrayList<>();
        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int y = 0; y < Chunk.SIZE; y++) {
                for (int z = 0; z < Chunk.SIZE; z++) {

                    byte block = chunk.getBlock(x, y, z);
                    if (block == Material.AIR.getId()) continue;
                    if (block == Material.WATER.getId()) continue;
                    Material material = Material.getMaterialById(block);

                    if (material == null) material = Material.DEBUG;

                    int worldX = x + chunk.getPosition().x * Chunk.SIZE;
                    int worldY = y + chunk.getPosition().y * Chunk.SIZE;
                    int worldZ = z + chunk.getPosition().z * Chunk.SIZE;

                    boolean px = isEmpty(worldX + 1, worldY, worldZ, material.getId());
                    boolean nx = isEmpty(worldX - 1, worldY, worldZ, material.getId());
                    boolean py = isEmpty(worldX, worldY + 1, worldZ, material.getId());
                    boolean ny = isEmpty(worldX, worldY - 1, worldZ, material.getId());
                    boolean pz = isEmpty(worldX, worldY, worldZ + 1, material.getId());
                    boolean nz = isEmpty(worldX, worldY, worldZ - 1, material.getId());

                    Vector2f[] textureCoords;

                    if(material == Material.WEED || material == Material.ROSE || material == Material.DEAD_BUSH) {
                        textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                        for (int k = 0; k < 6; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(NatureModel.FIRST_FACE[k]), textureCoords[k],material.getId(),0));
                        }
                        for (int k = 0; k < 6; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(NatureModel.SECOND_FACE[k]), textureCoords[k],material.getId(),0));
                        }
                    } else if (material == Material.CACTUS){
                        if (px) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getPx().x, material.getPx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < 6; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.PX_POS[k]), textureCoords[k],material.getId(),0));
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
                                vertices.add(new Vertex(blockVector.add(CactusModel.NX_POS[k]), textureCoords[k],material.getId(),1));
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
                                vertices.add(new Vertex(blockVector.add(CactusModel.PY_POS[k]), textureCoords[k],material.getId(),2));
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
                                vertices.add(new Vertex(blockVector.add(CactusModel.NY_POS[k]), textureCoords[k],material.getId(),3));
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
                                vertices.add(new Vertex(blockVector.add(CactusModel.PZ_POS[k]), textureCoords[k],material.getId(),4));
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
                                vertices.add(new Vertex(blockVector.add(CactusModel.NZ_POS[k]), textureCoords[k],material.getId(),5));
                            }
                        }
                    }else {
                        if (px) {
                            if(material.isFaces()) {
                                textureCoords = calculateTexCoords(material.getPx().x, material.getPx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }
                            for (int k = 0; k < 6; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.PX_POS[k]), textureCoords[k],material.getId(),0));
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
                                vertices.add(new Vertex(blockVector.add(BlockModel.NX_POS[k]), textureCoords[k],material.getId(),1));
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
                                vertices.add(new Vertex(blockVector.add(BlockModel.PY_POS[k]), textureCoords[k],material.getId(),2));
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
                                vertices.add(new Vertex(blockVector.add(BlockModel.NY_POS[k]), textureCoords[k],material.getId(),3));
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
                                vertices.add(new Vertex(blockVector.add(BlockModel.PZ_POS[k]), textureCoords[k],material.getId(),4));
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
                                vertices.add(new Vertex(blockVector.add(BlockModel.NZ_POS[k]), textureCoords[k],material.getId(),5));
                            }
                        }
                    }



                }
            }
        }
        counter = 0;

        return vertices.toArray(new Vertex[0]);
    }

}
