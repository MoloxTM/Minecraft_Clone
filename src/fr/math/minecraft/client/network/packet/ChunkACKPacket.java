package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import org.joml.Vector3i;

public class ChunkACKPacket extends ClientPacket {

    private final Vector3i chunkPosition;

    public ChunkACKPacket(Vector3i chunkPosition) {
        this.chunkPosition = chunkPosition;
    }

    @Override
    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "CHUNK_REQUEST_ACK");
        node.put("uuid", Game.getInstance().getPlayer().getUuid());
        node.put("x", chunkPosition.x);
        node.put("y", chunkPosition.y);
        node.put("z", chunkPosition.z);

        try {
            return mapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getResponse() {
        return null;
    }
}
