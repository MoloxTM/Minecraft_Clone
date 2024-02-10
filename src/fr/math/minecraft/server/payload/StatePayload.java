package fr.math.minecraft.server.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.manager.ClientManager;
import fr.math.minecraft.server.world.ServerChunk;
import org.joml.Vector3f;

import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;

public class StatePayload {

    private final InputPayload payload;
    private JsonNode data;
    private final boolean movingLeft, movingRight, movingForward, movingBackward;
    private final boolean sneaking, flying;
    private Vector3f position;

    public StatePayload(InputPayload payload) {
        this.payload = payload;
        this.movingLeft = payload.isMovingLeft();
        this.movingRight = payload.isMovingRight();
        this.movingForward = payload.isMovingForward();
        this.movingBackward = payload.isMovingBackward();
        this.flying = payload.isFlying();
        this.sneaking = payload.isSneaking();
        this.position = new Vector3f();
        this.data = null;
    }

    public void predictMovement(Client client) {
        client.updatePosition(payload);
        Vector3f newPosition = new Vector3f(client.getPosition());

        if (client.getLastChunkPosition().distance(position.x, position.y, position.z) >= ServerChunk.SIZE) {
            ClientManager clientManager = new ClientManager();
            client.getNearChunks().clear();
            clientManager.fillNearChunksQueue(client);
            client.setLastChunkPosition(newPosition);
        }

        this.position = newPosition;
    }

    public void send() {
        MinecraftServer server = MinecraftServer.getInstance();
        Client client = server.getClients().get(payload.getClientUuid());

        if (client == null) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payloadNode = mapper.createObjectNode();

        payloadNode.put("tick", payload.getTick());
        payloadNode.put("type", "STATE_PAYLOAD");
        payloadNode.put("x", position.x);
        payloadNode.put("y", position.y);
        payloadNode.put("z", position.z);
        payloadNode.put("inputX", payload.getInputVector().x);
        payloadNode.put("inputY", payload.getInputVector().y);
        payloadNode.put("inputZ", payload.getInputVector().z);

        try {
            String payloadData = mapper.writeValueAsString(payloadNode);
            byte[] buffer = payloadData.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, client.getAddress(), client.getPort());
            server.sendPacket(packet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public Vector3f getPosition() {
        return position;
    }
}
