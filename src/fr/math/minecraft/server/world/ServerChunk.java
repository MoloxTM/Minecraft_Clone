package fr.math.minecraft.server.world;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.builder.EmptyMapBuilder;
import fr.math.minecraft.server.world.generator.OverworldGenerator;
import org.joml.Vector3i;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerChunk {

    private final Vector3i position;
    private final byte[] blocks;
    private Map<String, Boolean> emptyMap;

    public final static int SIZE = 16;
    public final static int AREA = SIZE * SIZE;

    public final static int VOLUME = SIZE * AREA;
    private boolean empty;
    public ServerChunk(int x, int y, int z) {
        this.position = new Vector3i(x, y, z);
        this.blocks = new byte[VOLUME];
        for (int blockX = 0; blockX < ServerChunk.SIZE; blockX++) {
            for (int blockY = 0; blockY < ServerChunk.SIZE; blockY++) {
                for (int blockZ = 0; blockZ < ServerChunk.SIZE; blockZ++) {
                    this.setBlock(blockX, blockY, blockZ, Material.AIR.getId());
                }
            }
        }
        this.empty = true;
        this.emptyMap = new HashMap<>();
        this.generate();
    }

    public void generate() {
        OverworldGenerator overworldGenerator = new OverworldGenerator();
        overworldGenerator.generateChunk(this);
        //overworldGenerator.generateStructure(this);
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
        ObjectNode emptyMapNode = mapper.createObjectNode();

        for (int block : blocks) {
            blocksArray.add(block);
        }

        for (Map.Entry<String, Boolean> emptyMapSet : emptyMap.entrySet()) {
            String position = emptyMapSet.getKey();
            boolean emptyValue = emptyMapSet.getValue();
            emptyMapNode.put(position, emptyValue);
        }

        node.put("type", "CHUNK_PACKET");
        node.put("x", position.x);
        node.put("y", position.y);
        node.put("z", position.z);
        node.set("blocks", blocksArray);
        node.set("emptyMap", emptyMapNode);

        try {
            return mapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Map<String, Boolean> getEmptyMap() {
        return emptyMap;
    }

    public void setEmptyMap(Map<String, Boolean> emptyMap) {
        this.emptyMap = emptyMap;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
