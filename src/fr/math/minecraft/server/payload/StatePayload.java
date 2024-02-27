package fr.math.minecraft.server.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.shared.world.World;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StatePayload {

    private final InputPayload payload;
    private JsonNode data;
    private Vector3f position;
    private Vector3f velocity;
    private float yaw;
    private float pitch;
    private List<Byte> aimedBlocksIDs;
    private List<Vector3i> aimedBlocks;

    public StatePayload(InputPayload payload) {
        this.payload = payload;
        this.position = new Vector3f();
        this.velocity = new Vector3f();
        this.aimedBlocks = new ArrayList<>();
        this.aimedBlocksIDs = new ArrayList<>();
        this.data = null;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }

    public void predictMovement(World world, Client client) {
        client.update(world, payload);

        Vector3f newPosition = new Vector3f(client.getPosition());
        Vector3f newVelocity = new Vector3f(client.getVelocity());

        this.aimedBlocks = new ArrayList<>(client.getAimedBlocks());
        this.aimedBlocksIDs = new ArrayList<>(client.getAimedBlocksIDs());

        // System.out.println(aimedBlocks);

        /*
        if (client.getLastChunkPosition().distance(position.x, position.y, position.z) >= ServerChunk.SIZE) {
            ClientManager clientManager = new ClientManager();
            client.getNearChunks().clear();
            clientManager.fillNearChunksQueue(client);
            client.setLastChunkPosition(newPosition);
        }

         */
        this.yaw = client.getYaw();
        this.pitch = client.getPitch();
        this.position = newPosition;
        this.velocity = newVelocity;
    }

    public void send() {
        MinecraftServer server = MinecraftServer.getInstance();
        Client client = server.getClients().get(payload.getClientUuid());

        if (client == null) {
            return;
        }

        ObjectNode payloadNode = this.toJSON();
        ObjectNode payloadEventNode = this.toJSONEvent();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String payloadData = mapper.writeValueAsString(payloadNode);
            byte[] buffer = payloadData.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, client.getAddress(), client.getPort());
            server.sendPacket(packet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            String payloadEventData = mapper.writeValueAsString(payloadEventNode);
            byte[] buffer = payloadEventData.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packetEvent = new DatagramPacket(buffer, buffer.length);

            synchronized (server.getClients()) {
                for (Client onlineClient : server.getClients().values()) {
                    if(!onlineClient.getUuid().equalsIgnoreCase(payload.getClientUuid())) {
                        packetEvent.setAddress(onlineClient.getAddress());
                        packetEvent.setPort(onlineClient.getPort());
                        server.sendPacket(packetEvent);
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    public ObjectNode toJSONEvent() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payloadNode = mapper.createObjectNode();
        ArrayNode blocksArray = mapper.createArrayNode();

        payloadNode.put("type", "PLAYER_BREAK_EVENT");
        payloadNode.put("uuid", payload.getClientUuid());
        
        for (int i = 0; i < aimedBlocks.size(); i++) {
            Vector3i blockPosition = aimedBlocks.get(i);
            byte block = aimedBlocksIDs.get(i);
            ObjectNode blockNode = mapper.createObjectNode();

            blockNode.put("x", blockPosition.x);
            blockNode.put("y", blockPosition.y);
            blockNode.put("z", blockPosition.z);
            blockNode.put("block", block);
        }
        payloadNode.set("aimedBlocks", blocksArray);

        return payloadNode;
    }

    public ObjectNode toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payloadNode = mapper.createObjectNode();
        ArrayNode blocksArray = mapper.createArrayNode();

        payloadNode.put("tick", payload.getTick());
        payloadNode.put("type", "STATE_PAYLOAD");
        payloadNode.put("x", position.x);
        payloadNode.put("y", position.y);
        payloadNode.put("z", position.z);
        payloadNode.put("vx", velocity.x);
        payloadNode.put("vy", velocity.y);
        payloadNode.put("vz", velocity.z);
        payloadNode.put("yaw", yaw);
        payloadNode.put("pitch", pitch);
        payloadNode.put("uuid", payload.getClientUuid());

        for (int i = 0; i < aimedBlocks.size(); i++) {
            Vector3i blockPosition = aimedBlocks.get(i);
            byte block = aimedBlocksIDs.get(i);
            ObjectNode blockNode = mapper.createObjectNode();

            blockNode.put("x", blockPosition.x);
            blockNode.put("y", blockPosition.y);
            blockNode.put("z", blockPosition.z);
            blockNode.put("block", block);

        }

        payloadNode.set("aimedBlocks", blocksArray);

        return payloadNode;
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
