package fr.math.minecraft.server.world;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.world.biome.*;
import fr.math.minecraft.server.world.generator.TerrainGenerator;
import org.joml.Vector3i;

public class ServerChunk {

    private final Vector3i position;
    private final byte[] blocks;

    public final static int SIZE = 16;
    public final static int AREA = SIZE * SIZE;

    public final static int VOLUME = SIZE * AREA;

    private int biomeID;

    public ServerChunk(int x, int y, int z) {
        this.position = new Vector3i(x, y, z);
        this.blocks = new byte[VOLUME];
        for (int blockX = 0; blockX < ServerChunk.SIZE; blockX++) {
            for (int blockY = 0; blockY < ServerChunk.SIZE; blockY++) {
                for (int blockZ = 0; blockZ < ServerChunk.SIZE; blockZ++) {
                    if(!MinecraftServer.getInstance().getWorld().getOverworldGenerator().getStructure().getStructureMap().containsKey(new Coordinates(x, y ,z))){
                        this.setBlock(blockX, blockY, blockZ, Material.AIR.getId());
                    }
                }
            }
        }
        this.generate();
        this.biomeID = -1;
    }

    public void generate() {
        TerrainGenerator overworldGenerator = MinecraftServer.getInstance().getWorld().getOverworldGenerator();
        overworldGenerator.generateChunk(this);
        //System.out.println("Chunk made :" + this.getPosition());
        overworldGenerator.generateStructure(this);
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
    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        ArrayNode blocksArray = mapper.createArrayNode();

        for (int block : blocks) {
            blocksArray.add(block);
        }

        node.put("x", position.x);
        node.put("y", position.y);
        node.put("z", position.z);
        node.put("biome", biomeID);
        node.set("blocks", blocksArray);


        try {
            return mapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

    public int getBiome() {
        return biomeID;
    }

    public void setBiome(AbstractBiome biome) {
        this.biomeID = biome.getBiomeID();
    }
}
