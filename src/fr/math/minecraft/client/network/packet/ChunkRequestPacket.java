package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;
import fr.math.minecraft.client.network.packet.ClientPacket;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.io.IOException;

public class ChunkRequestPacket extends ClientPacket {

    private final ObjectMapper mapper;
    private final Vector3i coordinates;
    private JsonNode chunkData;
    private final static Logger logger = LoggerUtility.getClientLogger(ChunkRequestPacket.class, LogType.TXT);


    public ChunkRequestPacket(Vector3i coordinates) {
        this.mapper = new ObjectMapper();
        this.coordinates = coordinates;
        this.chunkData = null;
    }

    @Override
    public String toJSON() {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "CHUNK_REQUEST");
        node.put("x", coordinates.x);
        node.put("y", coordinates.y);
        node.put("z", coordinates.z);

        try {
            return mapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public String getResponse() {
        return null;
    }

    public JsonNode getChunkData() {
        return this.chunkData;
    }
}