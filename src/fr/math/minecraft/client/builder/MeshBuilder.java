package fr.math.minecraft.client.builder;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.meshs.model.CactusModel;
import fr.math.minecraft.client.meshs.model.HandModel;
import fr.math.minecraft.client.meshs.model.NatureModel;
import fr.math.minecraft.client.vertex.BlockVertex;
import fr.math.minecraft.client.vertex.HandVertex;
import fr.math.minecraft.client.vertex.Vertex;
import fr.math.minecraft.client.meshs.model.BlockModel;
import fr.math.minecraft.client.world.BlockFace;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MeshBuilder {

    private final int SQUARE_POINTS = 4;
    private final static int CHUNK_MODE = 1;
    private final static int WATER_MODE = 2;
    private final static int OCCLUSION_MODE = 3;

    public boolean isEmpty(int worldX, int worldY, int worldZ) {
        return this.isEmpty(worldX, worldY, worldZ, OCCLUSION_MODE);
    }

    public boolean isEmpty(int worldX, int worldY, int worldZ, int mode) {

        int chunkX = (int) Math.floor(worldX / (double) Chunk.SIZE);
        int chunkY = (int) Math.floor(worldY / (double) Chunk.SIZE);
        int chunkZ = (int) Math.floor(worldZ / (double) Chunk.SIZE);

        Game game = Game.getInstance();
        World world = game.getWorld();
        Chunk chunk = world.getChunk(chunkX, chunkY, chunkZ);

        if (chunk == null) {
            chunk = world.getCachedChunks().get(new Vector3i(chunkX, chunkY, chunkZ));
            if (chunk == null) {
                chunk = new Chunk(chunkX, chunkY, chunkZ);
                chunk.generate(world, world.getTerrainGenerator());
                world.getCachedChunks().put(chunk.getPosition(), chunk);
            }
        }

        int blockX = worldX % Chunk.SIZE;
        int blockY = worldY % Chunk.SIZE;
        int blockZ = worldZ % Chunk.SIZE;

        blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
        blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
        blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;

        byte block = chunk.getBlock(blockX, blockY, blockZ);

        if (block == Material.WATER.getId()) {
            return mode != WATER_MODE;
        }

        if (mode == OCCLUSION_MODE) {
            if (block == Material.WEED.getId() || block == Material.ROSE.getId() || block == Material.DEAD_BUSH.getId()) {
                return true;
            }
            return block == Material.AIR.getId();
        }

        return world.getTransparents().contains(block);
    }

    public boolean isEmpty(Material material, int worldX, int worldY, int worldZ, int mode) {

        int chunkX = (int) Math.floor(worldX / (double) Chunk.SIZE);
        int chunkY = (int) Math.floor(worldY / (double) Chunk.SIZE);
        int chunkZ = (int) Math.floor(worldZ / (double) Chunk.SIZE);

        Game game = Game.getInstance();
        World world = game.getWorld();
        Chunk chunk = world.getChunk(chunkX, chunkY, chunkZ);

        if (chunk == null) {
            chunk = world.getCachedChunks().get(new Vector3i(chunkX, chunkY, chunkZ));
            if (chunk == null) {
                chunk = new Chunk(chunkX, chunkY, chunkZ);
                chunk.generate(world, world.getTerrainGenerator());
                world.getCachedChunks().put(chunk.getPosition(), chunk);
            }
        }

        int blockX = worldX % Chunk.SIZE;
        int blockY = worldY % Chunk.SIZE;
        int blockZ = worldZ % Chunk.SIZE;

        blockX = blockX < 0 ? blockX + Chunk.SIZE : blockX;
        blockY = blockY < 0 ? blockY + Chunk.SIZE : blockY;
        blockZ = blockZ < 0 ? blockZ + Chunk.SIZE : blockZ;

        byte block = chunk.getBlock(blockX, blockY, blockZ);

        if (block == Material.WATER.getId()) {
            return mode != WATER_MODE;
        }

        if (mode == OCCLUSION_MODE) {
            if (block == Material.WEED.getId() || block == Material.ROSE.getId() || block == Material.DEAD_BUSH.getId()) {
                return true;
            }
            return block == Material.AIR.getId();
        }

        return world.getTransparents().contains(block);
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

    public Vertex[] buildWaterChunkMesh(Chunk chunk, ArrayList<Integer> indices) {
        ArrayList<Vertex> vertices = new ArrayList<>();
        int currentIndice = 0;
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

                        boolean px = isEmpty(worldX + 1, worldY, worldZ, WATER_MODE);
                        boolean nx = isEmpty(worldX - 1, worldY, worldZ, WATER_MODE);
                        boolean py = isEmpty(worldX, worldY + 1, worldZ, WATER_MODE);
                        boolean ny = isEmpty(worldX, worldY - 1, worldZ, WATER_MODE);
                        boolean pz = isEmpty(worldX, worldY, worldZ + 1, WATER_MODE);
                        boolean nz = isEmpty(worldX, worldY, worldZ - 1, WATER_MODE);

                        Vector2f[] textureCoords;
                        if (px) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getPx().x, material.getPx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);
                            }

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.PX_POS[k]), textureCoords[k], material.getId(), 0));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (nx) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getNx().x, material.getNx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.NX_POS[k]), textureCoords[k],material.getId(), 1));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (py) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getPy().x, material.getPy().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.PY_POS[k]), textureCoords[k], material.getId(), 2));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (ny) {
                            if (material.isSymetric()) {
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
                            if(material.isSymetric()) {
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
                            if (material.isSymetric()) {
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

        return vertices.toArray(new Vertex[0]);
    }

    public Vertex[] buildChunkMesh(Chunk chunk, ArrayList<Integer> indices) {
        int currentIndice = 0;
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

                    boolean px = isEmpty(material, worldX + 1, worldY, worldZ, CHUNK_MODE);
                    boolean nx = isEmpty(material, worldX - 1, worldY, worldZ, CHUNK_MODE);
                    boolean py = isEmpty(material, worldX, worldY + 1, worldZ, CHUNK_MODE);
                    boolean ny = isEmpty(material, worldX, worldY - 1, worldZ, CHUNK_MODE);
                    boolean pz = isEmpty(material, worldX, worldY, worldZ + 1, CHUNK_MODE);
                    boolean nz = isEmpty(material, worldX, worldY, worldZ - 1, CHUNK_MODE);

                    Vector2f[] textureCoords;

                    if(material == Material.WEED || material == Material.ROSE || material == Material.DEAD_BUSH) {
                        textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                        for (int k = 0; k < SQUARE_POINTS; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(NatureModel.FIRST_FACE[k]), textureCoords[k], material.getId(), 2, 3));
                        }

                        currentIndice = this.updateIndice(indices, currentIndice);

                        for (int k = 0; k < SQUARE_POINTS; k++)  {
                            Vector3f blockVector = new Vector3f(x, y, z);
                            vertices.add(new Vertex(blockVector.add(NatureModel.SECOND_FACE[k]), textureCoords[k], material.getId(), 2, 3));
                        }

                        currentIndice = this.updateIndice(indices, currentIndice);
                    } else if (material == Material.CACTUS) {
                        if (px) {

                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getPx().x, material.getPx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            Map<Integer, Integer> map = calculateAmbiantOcclusion(worldX, worldY, worldZ, BlockFace.PX_FACE);

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.PX_POS[k]), textureCoords[k],material.getId(), 0, map.get(k)));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (nx) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getNx().x, material.getNx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            Map<Integer, Integer> map = calculateAmbiantOcclusion(worldX, worldY, worldZ, BlockFace.NX_FACE);

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.NX_POS[k]), textureCoords[k],material.getId(), 1, map.get(k)));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (py) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getPy().x, material.getPy().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            Map<Integer, Integer> map = calculateAmbiantOcclusion(worldX, worldY, worldZ, BlockFace.PY_FACE);

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.PY_POS[k]), textureCoords[k], material.getId(), 2, map.get(k)));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (ny) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getNy().x, material.getNy().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            Map<Integer, Integer> map = calculateAmbiantOcclusion(worldX, worldY, worldZ, BlockFace.NY_FACE);


                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.NY_POS[k]), textureCoords[k], material.getId(), 3, map.get(k)));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (pz) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getPz().x, material.getPz().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            Map<Integer, Integer> map = calculateAmbiantOcclusion(worldX, worldY, worldZ, BlockFace.PZ_FACE);

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.PZ_POS[k]), textureCoords[k], material.getId(), 4, map.get(k)));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (nz) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getNz().x, material.getNz().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            Map<Integer, Integer> map = calculateAmbiantOcclusion(worldX, worldY, worldZ, BlockFace.NZ_FACE);

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(CactusModel.NZ_POS[k]), textureCoords[k], material.getId(), 5, map.get(k)));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }
                    } else {
                        if (px) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getPx().x, material.getPx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);
                            }

                            Map<Integer, Integer> map = calculateAmbiantOcclusion(worldX, worldY, worldZ, BlockFace.PX_FACE);

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.PX_POS[k]), textureCoords[k], material.getId(), 0, map.get(k)));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);

                        }

                        if (nx) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getNx().x, material.getNx().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            Map<Integer, Integer> map = calculateAmbiantOcclusion(worldX, worldY, worldZ, BlockFace.NX_FACE);

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.NX_POS[k]), textureCoords[k], material.getId(), 1, map.get(k)));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (py) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getPy().x, material.getPy().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            Map<Integer, Integer> map = calculateAmbiantOcclusion(worldX, worldY, worldZ, BlockFace.PY_FACE);

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.PY_POS[k]), textureCoords[k], material.getId(), 2, map.get(k)));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (ny) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getNy().x, material.getNy().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            Map<Integer, Integer> map = calculateAmbiantOcclusion(worldX, worldY, worldZ, BlockFace.NY_FACE);

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.NY_POS[k]), textureCoords[k], material.getId(), 3, map.get(k)));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (pz) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getPz().x, material.getPz().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            Map<Integer, Integer> map = calculateAmbiantOcclusion(worldX, worldY, worldZ, BlockFace.PZ_FACE);

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.PZ_POS[k]), textureCoords[k], material.getId(), 4, map.get(k)));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }

                        if (nz) {
                            if (material.isSymetric()) {
                                textureCoords = calculateTexCoords(material.getNz().x, material.getNz().y, 16.0f);
                            } else {
                                textureCoords = calculateTexCoords(material.getX(), material.getY(), 16.0f);

                            }

                            Map<Integer, Integer> map = calculateAmbiantOcclusion(worldX, worldY, worldZ, BlockFace.NZ_FACE);

                            for (int k = 0; k < SQUARE_POINTS; k++)  {
                                Vector3f blockVector = new Vector3f(x, y, z);
                                vertices.add(new Vertex(blockVector.add(BlockModel.NZ_POS[k]), textureCoords[k], material.getId(), 5, map.get(k)));
                            }

                            currentIndice = this.updateIndice(indices, currentIndice);
                        }
                    }
                }
            }
        }

        return vertices.toArray(new Vertex[0]);
    }

    public Map<Integer, Integer> calculateAmbiantOcclusion(int worldX, int worldY, int worldZ, BlockFace face) {

        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;
        int f = 0;
        int g = 0;
        int h = 0;

        switch (face) {
            case PX_FACE:
                a = isEmpty(worldX + 1, worldY + 1, worldZ) ? 1 : 0;
                b = isEmpty(worldX + 1, worldY + 1, worldZ + 1) ? 1 : 0;
                c = isEmpty(worldX + 1, worldY, worldZ + 1) ? 1 : 0;
                d = isEmpty(worldX + 1, worldY - 1, worldZ + 1) ? 1 : 0;
                e = isEmpty(worldX + 1, worldY - 1, worldZ) ? 1 : 0;
                f = isEmpty(worldX + 1, worldY - 1, worldZ - 1) ? 1 : 0;
                g = isEmpty(worldX + 1, worldY, worldZ - 1) ? 1 : 0;
                h = isEmpty(worldX + 1, worldY + 1, worldZ - 1) ? 1 : 0;
                break;
            case PY_FACE:
                a = isEmpty(worldX, worldY + 1, worldZ - 1) ? 1 : 0;
                b = isEmpty(worldX + 1, worldY + 1, worldZ - 1) ? 1 : 0;
                c = isEmpty(worldX + 1, worldY + 1, worldZ) ? 1 : 0;
                d = isEmpty(worldX + 1, worldY + 1, worldZ + 1) ? 1 : 0;
                e = isEmpty(worldX, worldY + 1, worldZ + 1) ? 1 : 0;
                f = isEmpty(worldX - 1, worldY + 1, worldZ + 1) ? 1 : 0;
                g = isEmpty(worldX - 1, worldY + 1, worldZ) ? 1 : 0;
                h = isEmpty(worldX - 1, worldY + 1, worldZ - 1) ? 1 : 0;
                break;
            case PZ_FACE:
                a = isEmpty(worldX, worldY + 1, worldZ + 1) ? 1 : 0;
                b = isEmpty(worldX + 1, worldY + 1, worldZ + 1) ? 1 : 0;
                c = isEmpty(worldX + 1, worldY, worldZ + 1) ? 1 : 0;
                d = isEmpty(worldX + 1, worldY - 1, worldZ + 1) ? 1 : 0;
                e = isEmpty(worldX, worldY - 1, worldZ + 1) ? 1 : 0;
                f = isEmpty(worldX - 1, worldY - 1, worldZ + 1) ? 1 : 0;
                g = isEmpty(worldX - 1, worldY, worldZ + 1) ? 1 : 0;
                h = isEmpty(worldX - 1, worldY + 1, worldZ + 1) ? 1 : 0;
                break;
            case NX_FACE:
                a = isEmpty(worldX - 1, worldY + 1, worldZ) ? 1 : 0;
                b = isEmpty(worldX - 1, worldY + 1, worldZ + 1) ? 1 : 0;
                c = isEmpty(worldX - 1, worldY, worldZ + 1) ? 1 : 0;
                d = isEmpty(worldX - 1, worldY - 1, worldZ + 1) ? 1 : 0;
                e = isEmpty(worldX - 1, worldY - 1, worldZ) ? 1 : 0;
                f = isEmpty(worldX - 1, worldY - 1, worldZ - 1) ? 1 : 0;
                g = isEmpty(worldX - 1, worldY, worldZ - 1) ? 1 : 0;
                h = isEmpty(worldX - 1, worldY + 1, worldZ - 1) ? 1 : 0;
                break;
            case NY_FACE:
                a = isEmpty(worldX, worldY - 1, worldZ - 1) ? 1 : 0;
                b = isEmpty(worldX + 1, worldY - 1, worldZ - 1) ? 1 : 0;
                c = isEmpty(worldX + 1, worldY - 1, worldZ) ? 1 : 0;
                d = isEmpty(worldX + 1, worldY - 1, worldZ + 1) ? 1 : 0;
                e = isEmpty(worldX, worldY - 1, worldZ + 1) ? 1 : 0;
                f = isEmpty(worldX - 1, worldY - 1, worldZ + 1) ? 1 : 0;
                g = isEmpty(worldX - 1, worldY - 1, worldZ) ? 1 : 0;
                h = isEmpty(worldX - 1, worldY - 1, worldZ - 1) ? 1 : 0;
                break;
            case NZ_FACE:
                a = isEmpty(worldX, worldY + 1, worldZ - 1) ? 1 : 0;
                b = isEmpty(worldX + 1, worldY + 1, worldZ - 1) ? 1 : 0;
                c = isEmpty(worldX + 1, worldY, worldZ - 1) ? 1 : 0;
                d = isEmpty(worldX + 1, worldY - 1, worldZ - 1) ? 1 : 0;
                e = isEmpty(worldX, worldY - 1, worldZ - 1) ? 1 : 0;
                f = isEmpty(worldX - 1, worldY - 1, worldZ - 1) ? 1 : 0;
                g = isEmpty(worldX - 1, worldY, worldZ - 1) ? 1 : 0;
                h = isEmpty(worldX - 1, worldY + 1, worldZ - 1) ? 1 : 0;
                break;
        }

        Map<Integer, Integer> map = new HashMap<>();

        map.put(0, g + f + e);
        map.put(1, g + h + a);
        map.put(2, a + b + c);
        map.put(3, c + d + e);

        return map;
    }

    public HandVertex[] buildHandMesh() {
        ArrayList<HandVertex> vertices = new ArrayList<>();

        Collections.addAll(vertices, HandModel.HAND_VERTICES);

        return vertices.toArray(new HandVertex[0]);
    }

    public BlockVertex[] buildBlockMesh(Material material) {

        int pxX;
        int nxX;
        int pyX;
        int nyX;
        int pzX;
        int nzX;

        int pxY;
        int nxY;
        int pyY;
        int nyY;
        int pzY;
        int nzY;

        if (!material.isSymetric()) {
            pxX = nxX = pyX = nyX = pzX = nzX = material.getX();
            pxY = nxY = pyY = nyY = pzY = nzY = material.getY();
        } else {
            pxX = material.getPx().x;
            nxX = material.getNx().x;
            pyX = material.getPy().x;
            nyX = material.getNy().x;
            pzX = material.getPz().x;
            nzX = material.getNz().x;

            pxY = material.getPx().y;
            nxY = material.getNx().y;
            pyY = material.getPy().y;
            nyY = material.getNy().y;
            pzY = material.getPz().y;
            nzY = material.getNz().y;
        }

        Vector2f[] texCoordsPx = MeshBuilder.calculateTexCoords(pxX, pxY, 16.0f);
        Vector2f[] texCoordsNx = MeshBuilder.calculateTexCoords(nxX, nxY, 16.0f);
        Vector2f[] texCoordsPy = MeshBuilder.calculateTexCoords(pyX, pyY, 16.0f);
        Vector2f[] texCoordsNy = MeshBuilder.calculateTexCoords(nyX, nyY, 16.0f);
        Vector2f[] texCoordsPz = MeshBuilder.calculateTexCoords(pzX, pzY, 16.0f);
        Vector2f[] texCoordsNz = MeshBuilder.calculateTexCoords(nzX, nzY, 16.0f);

        BlockVertex[] vertices = new BlockVertex[] {
            // Face avant
            new BlockVertex(new Vector3f(-0.5f, -0.5f, 0.5f), texCoordsPz[0], 0, BlockFace.PZ_FACE.ordinal()),
            new BlockVertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsPz[1], 1, BlockFace.PZ_FACE.ordinal()),
            new BlockVertex(new Vector3f(0.5f, 0.5f, 0.5f), texCoordsPz[2], 2, BlockFace.PZ_FACE.ordinal()),
            new BlockVertex(new Vector3f(0.5f, -0.5f, 0.5f), texCoordsPz[3], 3, BlockFace.PZ_FACE.ordinal()),

            // Face arrière
            new BlockVertex(new Vector3f(-0.5f, -0.5f, -0.5f), texCoordsNz[0], 0, BlockFace.NZ_FACE.ordinal()),
            new BlockVertex(new Vector3f(-0.5f, 0.5f, -0.5f), texCoordsNz[1], 1, BlockFace.NZ_FACE.ordinal()),
            new BlockVertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsNz[2], 2, BlockFace.NZ_FACE.ordinal()),
            new BlockVertex(new Vector3f(0.5f, -0.5f, -0.5f), texCoordsNz[3], 3, BlockFace.NZ_FACE.ordinal()),
            // Face gauche
            new BlockVertex(new Vector3f(-0.5f, -0.5f, -0.5f), texCoordsNx[0], 0, BlockFace.NX_FACE.ordinal()),
            new BlockVertex(new Vector3f(-0.5f, 0.5f, -0.5f), texCoordsNx[1], 1, BlockFace.NX_FACE.ordinal()),
            new BlockVertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsNx[2], 2, BlockFace.NX_FACE.ordinal()),
            new BlockVertex(new Vector3f(-0.5f, -0.5f, 0.5f), texCoordsNx[3], 3, BlockFace.NX_FACE.ordinal()),


            // Face droite
            new BlockVertex(new Vector3f(0.5f, -0.5f, -0.5f), texCoordsPx[0], 0, BlockFace.PX_FACE.ordinal()),
            new BlockVertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsPx[1], 1, BlockFace.PX_FACE.ordinal()),
            new BlockVertex(new Vector3f(0.5f, 0.5f, 0.5f), texCoordsPx[2], 2, BlockFace.PX_FACE.ordinal()),
            new BlockVertex(new Vector3f(0.5f, -0.5f, 0.5f), texCoordsPx[3], 3, BlockFace.PX_FACE.ordinal()),

            // Face supérieure
            new BlockVertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsPy[0], 0, BlockFace.PY_FACE.ordinal()),
            new BlockVertex(new Vector3f(-0.5f, 0.5f, -0.5f), texCoordsPy[1], 1, BlockFace.PY_FACE.ordinal()),
            new BlockVertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsPy[2], 2, BlockFace.PY_FACE.ordinal()),
            new BlockVertex(new Vector3f(0.5f, 0.5f, 0.5f), texCoordsPy[3], 3, BlockFace.PY_FACE.ordinal()),

            // Face inférieure
            new BlockVertex(new Vector3f(-0.5f, -0.5f, 0.5f), texCoordsNy[0], 0, BlockFace.NY_FACE.ordinal()),
            new BlockVertex(new Vector3f(-0.5f, -0.5f, -0.5f), texCoordsNy[1], 1, BlockFace.NY_FACE.ordinal()),
            new BlockVertex(new Vector3f(0.5f, -0.5f, -0.5f), texCoordsNy[2], 2, BlockFace.NY_FACE.ordinal()),
            new BlockVertex(new Vector3f(0.5f, -0.5f, 0.5f), texCoordsNy[3], 3, BlockFace.NY_FACE.ordinal())

        };

        return vertices;
    }

}
