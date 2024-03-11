package fr.math.minecraft.server.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.events.listeners.PacketListener;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.shared.inventory.DroppedItem;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.world.World;
import org.apache.log4j.Logger;
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
    private List<Byte> aimedPLacedBlocksIDs, aimedBreakedBlocksIDs;
    private List<Vector3i> aimedPlacedBlocks, aimedBreakedBlocks;



    public StatePayload(InputPayload payload) {
        this.payload = payload;
        this.position = new Vector3f();
        this.velocity = new Vector3f();
        this.aimedPlacedBlocks = new ArrayList<>();
        this.aimedPLacedBlocksIDs = new ArrayList<>();
        this.aimedBreakedBlocks = new ArrayList<>();
        this.aimedBreakedBlocksIDs = new ArrayList<>();
        this.data = null;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }

    public void predictMovement(World world, Client client) {
        client.update(world, payload);

        Vector3f newPosition = new Vector3f(client.getPosition());
        Vector3f newVelocity = new Vector3f(client.getVelocity());
        MinecraftServer server = MinecraftServer.getInstance();
        ObjectMapper mapper = new ObjectMapper();

        this.aimedPlacedBlocks = new ArrayList<>(client.getAimedPLacedBlocks());
        this.aimedPLacedBlocksIDs = new ArrayList<>(client.getAimedPLacedBlocksIDs());
        this.aimedBreakedBlocks = new ArrayList<>(client.getAimedBreakedBlocks());
        this.aimedBreakedBlocksIDs = new ArrayList<>(client.getAimedBreakedBlocksIDs());

        synchronized (world.getDroppedItems()) {
            List<String> collectedItems = new ArrayList<>();
            for (DroppedItem droppedItem : world.getDroppedItems().values()) {
                if (newPosition.distance(droppedItem.getPosition()) < 1.5f) {
                    ItemStack itemStack = new ItemStack(droppedItem.getMaterial(), 1);
                    client.getInventory().addItem(new ItemStack(droppedItem.getMaterial(), 1));

                    ObjectNode node = mapper.createObjectNode();

                    node.put("type", "NEW_ITEM");
                    node.put("droppedItemId", droppedItem.getUuid());
                    node.put("materialId", itemStack.getMaterial().getId());
                    node.put("amount", itemStack.getAmount());

                    try {
                        byte[] buffer = mapper.writeValueAsBytes(node);
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, client.getAddress(), client.getPort());

                        collectedItems.add(droppedItem.getUuid());
                        server.sendPacket(packet);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (String droppedItemId : collectedItems) {
                world.getDroppedItems().remove(droppedItemId);
            }

            for (String droppedItemId : collectedItems) {
                ObjectNode removedEventNode = mapper.createObjectNode();

                removedEventNode.put("type", "DROPPED_ITEM_REMOVED");
                removedEventNode.put("droppedItemId", droppedItemId);
                try {
                    byte[] buffer = mapper.writeValueAsBytes(removedEventNode);
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                    for (Client onlineClient : server.getClients().values()) {

                        if (onlineClient.getUuid().equalsIgnoreCase(client.getUuid())) {
                            continue;
                        }

                        packet.setAddress(onlineClient.getAddress());
                        packet.setPort(onlineClient.getPort());

                        server.sendPacket(packet);
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

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
        ObjectNode payloadEventBreakNode = this.toJSONEventBreak();
        ObjectNode payloadEventPlaceNode = this.toJSONEventPlace();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String payloadData = mapper.writeValueAsString(payloadNode);
            byte[] buffer = payloadData.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, client.getAddress(), client.getPort());
            server.sendPacket(packet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (payloadEventBreakNode != null) {
            try {
                String payloadEventData = mapper.writeValueAsString(payloadEventBreakNode);
                byte[] buffer = payloadEventData.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packetEvent = new DatagramPacket(buffer, buffer.length);

                System.out.println(payloadEventData);

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


        if (payloadEventPlaceNode != null) {
            try {
                String payloadEventData = mapper.writeValueAsString(payloadEventPlaceNode);
                byte[] buffer = payloadEventData.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packetEvent = new DatagramPacket(buffer, buffer.length);

                System.out.println(payloadEventData);

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
    }

    public ObjectNode toJSONEventBreak() {

        if (aimedBreakedBlocks.isEmpty()) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payloadNode = mapper.createObjectNode();
        ArrayNode blocksArray = mapper.createArrayNode();

        payloadNode.put("type", "PLAYER_BREAK_EVENT");
        payloadNode.put("uuid", payload.getClientUuid());
        
        for (int i = 0; i < aimedBreakedBlocks.size(); i++) {
            Vector3i blockPosition = aimedBreakedBlocks.get(i);
            byte block = aimedBreakedBlocksIDs.get(i);
            ObjectNode blockNode = mapper.createObjectNode();

            blockNode.put("x", blockPosition.x);
            blockNode.put("y", blockPosition.y);
            blockNode.put("z", blockPosition.z);
            blockNode.put("block", block);

            blocksArray.add(blockNode);
        }

        payloadNode.set("aimedBreakedBlocks", blocksArray);

        return payloadNode;
    }

    public ObjectNode toJSONEventPlace() {

        if (aimedPlacedBlocks.isEmpty()) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payloadNode = mapper.createObjectNode();
        ArrayNode blocksArray = mapper.createArrayNode();

        payloadNode.put("type", "PLAYER_PLACE_EVENT");
        payloadNode.put("uuid", payload.getClientUuid());

        for (int i = 0; i < aimedPlacedBlocks.size(); i++) {
            Vector3i blockPosition = aimedPlacedBlocks.get(i);
            byte block = aimedPLacedBlocksIDs.get(i);
            ObjectNode blockNode = mapper.createObjectNode();

            blockNode.put("x", blockPosition.x);
            blockNode.put("y", blockPosition.y);
            blockNode.put("z", blockPosition.z);
            blockNode.put("block", block);

            blocksArray.add(blockNode);
        }

        payloadNode.set("aimedPlacedBlocks", blocksArray);

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

        for (int i = 0; i < aimedPlacedBlocks.size(); i++) {
            Vector3i blockPosition = aimedPlacedBlocks.get(i);
            byte block = aimedPLacedBlocksIDs.get(i);
            ObjectNode blockNode = mapper.createObjectNode();

            blockNode.put("x", blockPosition.x);
            blockNode.put("y", blockPosition.y);
            blockNode.put("z", blockPosition.z);
            blockNode.put("block", block);

        }

        payloadNode.set("aimedPlacedBlocks", blocksArray);

        for (int i = 0; i < aimedBreakedBlocks.size(); i++) {
            Vector3i blockPosition = aimedBreakedBlocks.get(i);
            byte block = aimedBreakedBlocksIDs.get(i);
            ObjectNode blockNode = mapper.createObjectNode();

            blockNode.put("x", blockPosition.x);
            blockNode.put("y", blockPosition.y);
            blockNode.put("z", blockPosition.z);
            blockNode.put("block", block);

        }

        payloadNode.set("aimedBreakedBlocks", blocksArray);

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
