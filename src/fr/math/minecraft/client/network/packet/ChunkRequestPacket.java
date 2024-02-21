package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.shared.world.World;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

public class ChunkRequestPacket extends ClientPacket {

    private final ObjectMapper mapper;
    private JsonNode chunkData;
    private final static Logger logger = LoggerUtility.getClientLogger(ChunkRequestPacket.class, LogType.TXT);


    public ChunkRequestPacket() {
        this.mapper = new ObjectMapper();
        this.chunkData = null;
    }

    @Override
    public String toJSON() {
        ObjectNode node = mapper.createObjectNode();
        ArrayNode knownChunks = mapper.createArrayNode();
        World world = Game.getInstance().getWorld();

        node.put("type", "CHUNK_REQUEST");
        node.put("uuid", Game.getInstance().getPlayer().getUuid());

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