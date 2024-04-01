package fr.math.minecraft.server.payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.shared.inventory.Hotbar;
import fr.math.minecraft.shared.inventory.Inventory;
import fr.math.minecraft.shared.world.*;
import fr.math.minecraft.shared.inventory.ItemStack;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.net.DatagramPacket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatePayload {

    private final InputPayload payload;
    private JsonNode data;
    private Vector3f position;
    private Vector3f velocity;
    private float yaw;
    private float pitch;
    private float health, maxHealth, maxSpeed, hunger, maxHunger;
    private List<PlacedBlock> placedBlocks;
    private List<BreakedBlock> brokenBlocks;
    private Inventory hotbar, craftInventory, completedCraftInventory, inventory, craftingTableInventory;

    public StatePayload(InputPayload payload) {
        this.payload = payload;
        this.position = new Vector3f();
        this.velocity = new Vector3f();
        this.placedBlocks = new ArrayList<>();
        this.brokenBlocks = new ArrayList<>();
        this.data = null;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.health = 0.0f;
        this.maxHealth = 0.0f;
    }

    public void predictMovement(World world, Client client) {
        client.update(world, payload);

        Vector3f newPosition = new Vector3f(client.getPosition());
        Vector3f newVelocity = new Vector3f(client.getVelocity());
        MinecraftServer server = MinecraftServer.getInstance();
        ObjectMapper mapper = new ObjectMapper();

        this.brokenBlocks = new ArrayList<>(client.getBreakedBlocks());
        this.placedBlocks = new ArrayList<>(client.getPlacedBlocks());

        synchronized (world.getDroppedItems()) {
            List<String> collectedItems = new ArrayList<>();
            for (DroppedItem droppedItem : world.getDroppedItems().values()) {
                if (droppedItem.isOnFloor() && newPosition.distance(droppedItem.getPosition()) < 1.5f) {
                    ItemStack item = new ItemStack(droppedItem.getMaterial(), 1);
                    Hotbar hotbar = client.getHotbar();
                    Inventory inventory = client.getInventory();

                    if (hotbar.isFull() && inventory.isFull()) {
                        continue;
                    }

                    client.addItem(item);

                    ObjectNode node = mapper.createObjectNode();

                    node.put("type", "NEW_ITEM");
                    node.put("droppedItemId", droppedItem.getUuid());
                    node.put("materialId", item.getMaterial().getId());
                    node.put("amount", item.getAmount());

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

        this.inventory = client.getInventory();
        this.hotbar = client.getHotbar();
        this.yaw = client.getYaw();
        this.pitch = client.getPitch();
        this.completedCraftInventory = client.getCompletedCraftPlayerInventory();
        this.craftInventory = client.getPlayerCraftInventory();
        this.craftingTableInventory = client.getCraftingTableInventory();
        this.position = newPosition;
        this.velocity = newVelocity;
        this.health = client.getHealth();
        this.maxHealth = client.getMaxHealth();
        this.maxSpeed = client.getMaxSpeed();
        this.hunger = client.getHunger();
        this.maxHunger = client.getMaxHunger();
    }

    public void send() {
        MinecraftServer server = MinecraftServer.getInstance();
        Client client = server.getClients().get(payload.getClientUuid());

        if (client == null) {
            return;
        }

        ObjectNode payloadNode = this.toJSON();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String payloadData = mapper.writeValueAsString(payloadNode);
            byte[] buffer = payloadData.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, client.getAddress(), client.getPort());
            server.sendPacket(packet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public ObjectNode toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode payloadNode = mapper.createObjectNode();
        ArrayNode brokenBlocksArray = mapper.createArrayNode();
        ArrayNode placedBlocksArray = mapper.createArrayNode();
        ArrayNode inventoryArray = mapper.createArrayNode();
        ArrayNode hotbarArray = mapper.createArrayNode();
        ArrayNode craftInventoryArray = mapper.createArrayNode();
        ArrayNode completedCraftInventoryArray = mapper.createArrayNode();
        ArrayNode craftingTableInventoryArray = mapper.createArrayNode();

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
        payloadNode.put("health", health);
        payloadNode.put("maxHealth", maxHealth);
        payloadNode.put("maxSpeed", maxSpeed);
        payloadNode.put("hunger", hunger);
        payloadNode.put("maxHunger", maxHunger);

        for (PlacedBlock placedBlock : placedBlocks) {
            Vector3i blockPosition = placedBlock.getWorldPosition();
            Vector3i localPosition = placedBlock.getLocalPosition();
            byte block = placedBlock.getBlock();
            ObjectNode blockNode = mapper.createObjectNode();

            blockNode.put("wx", blockPosition.x);
            blockNode.put("wy", blockPosition.y);
            blockNode.put("wz", blockPosition.z);
            blockNode.put("lx", localPosition.x);
            blockNode.put("ly", localPosition.y);
            blockNode.put("lz", localPosition.z);
            blockNode.put("block", block);

            placedBlocksArray.add(blockNode);
        }

        for (BreakedBlock breakedBlock : brokenBlocks) {

            Vector3i blockPosition = breakedBlock.getPosition();
            byte block = breakedBlock.getBlock();
            ObjectNode blockNode = mapper.createObjectNode();

            blockNode.put("x", blockPosition.x);
            blockNode.put("y", blockPosition.y);
            blockNode.put("z", blockPosition.z);
            blockNode.put("block", block);

            brokenBlocksArray.add(blockNode);

        }

        ObjectNode airNode = mapper.createObjectNode();
        airNode.put("block", -1);
        airNode.put("amount", 0);

        for (ItemStack item : inventory.getItems()) {
            if (item == null) {
                inventoryArray.add(airNode);
                continue;
            }
            inventoryArray.add(item.toJSONObject());
        }

        for (ItemStack item : hotbar.getItems()) {
            if (item == null) {
                hotbarArray.add(airNode);
                continue;
            }
            hotbarArray.add(item.toJSONObject());
        }

        for (ItemStack item : craftInventory.getItems()) {
            if (item == null) {
                craftInventoryArray.add(airNode);
                continue;
            }
            craftInventoryArray.add(item.toJSONObject());
        }

        for (ItemStack item : completedCraftInventory.getItems()) {
            if (item == null) {
                completedCraftInventoryArray.add(airNode);
                continue;
            }
            completedCraftInventoryArray.add(item.toJSONObject());
        }

        for (ItemStack item : craftingTableInventory.getItems()) {
            if (item == null) {
                craftingTableInventoryArray.add(airNode);
                continue;
            }
            craftingTableInventoryArray.add(item.toJSONObject());
        }

        payloadNode.set("aimedPlacedBlocks", placedBlocksArray);
        payloadNode.set("brokenBlocks", brokenBlocksArray);
        payloadNode.set("inventory", inventoryArray);
        payloadNode.set("hotbar", hotbarArray);
        payloadNode.set("craftInventory", craftInventoryArray);
        payloadNode.set("completedCraftInventory", completedCraftInventoryArray);
        payloadNode.set("craftingTableInventory", craftingTableInventoryArray);
        payloadNode.put("craftingTableOpen", craftingTableInventory.isOpen());

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
