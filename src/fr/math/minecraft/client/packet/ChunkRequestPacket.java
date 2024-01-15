package fr.math.minecraft.client.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;
import fr.math.minecraft.client.entity.Player;
import org.joml.Vector3f;

import java.io.IOException;

public class ChunkRequestPacket implements  ClientPacket{

    private final ObjectMapper mapper;
    private Vector3f coordinates;

    public ChunkRequestPacket(Vector3f coordinates) {
        this.mapper = new ObjectMapper();
        this.coordinates = coordinates;
    }
    @Override
    public void send() {
        MinecraftClient client = Game.getInstance().getClient();
        String message = this.toJSON();

        if(message == null) {
            return;
        }

        try {
            String chunk = client.sendString(message);
            System.out.println(chunk);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toJSON() {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "CHUNK_REQUEST");
        node.put("x", coordinates.x);
        node.put("y", coordinates.y);
        node.put("z", coordinates.z);

        try {
            return  mapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
