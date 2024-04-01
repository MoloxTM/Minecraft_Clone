package fr.math.minecraft.server.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.math.minecraft.shared.inventory.Inventory;
import fr.math.minecraft.shared.inventory.InventoryType;
import fr.math.minecraft.shared.network.PlayerInputData;

import java.util.ArrayList;
import java.util.List;

public class InputPayload {

    private final int tick;
    private final String clientUuid;
    private final List<PlayerInputData> inputsData;

    public InputPayload(JsonNode payloadData) {
        this.tick = payloadData.get("tick").asInt();
        this.inputsData = new ArrayList<>();
        this.clientUuid = payloadData.get("uuid").asText();
        ArrayNode inputsArray = (ArrayNode) payloadData.get("inputs");

        for (int i = 0; i < inputsArray.size(); i++) {
            JsonNode inputNode = inputsArray.get(i);
            boolean movingForward = inputNode.get("movingForward").asBoolean();
            boolean movingBackward = inputNode.get("movingBackward").asBoolean();
            boolean movingLeft = inputNode.get("movingLeft").asBoolean();
            boolean movingRight = inputNode.get("movingRight").asBoolean();
            boolean sneaking = inputNode.get("sneaking").asBoolean();
            boolean sprinting = inputNode.get("sprinting").asBoolean();
            boolean flying = inputNode.get("flying").asBoolean();
            boolean jumping = inputNode.get("jumping").asBoolean();
            float yaw = inputNode.get("yaw").floatValue();
            float pitch = inputNode.get("pitch").floatValue();
            boolean breakingBlock = inputNode.get("breakingBlock").asBoolean();
            boolean placingBlock = inputNode.get("placingBlock").asBoolean();
            boolean droppingItem = inputNode.get("droppingItem").asBoolean();
            boolean collectingCraft = inputNode.get("collectingCraft").asBoolean();
            boolean pressingPlaceKey = inputNode.get("pressingPlaceKey").asBoolean();
            boolean closingCraftInventory = inputNode.get("closingCraftInventory").asBoolean();
            int hotbarSlot = inputNode.get("hotbarSlot").asInt();
            int holdedSlot = inputNode.get("holdedSlot").asInt();
            JsonNode inventoryTypeNode = inputNode.get("inventoryType");
            JsonNode nextInventoryNode = inputNode.get("nextInventory");
            int nextSlot = inputNode.get("nextSlot").asInt();

            PlayerInputData inputData;
            if (inventoryTypeNode != null && nextInventoryNode != null) {
                InventoryType inventoryType = InventoryType.values()[inputNode.get("inventoryType").asInt()];
                InventoryType nextInventory = InventoryType.values()[inputNode.get("nextInventory").asInt()];
                inputData = new PlayerInputData(holdedSlot, inventoryType, nextInventory, nextSlot, collectingCraft, pressingPlaceKey, closingCraftInventory);
            } else {
                inputData = new PlayerInputData(movingLeft, movingRight, movingForward, movingBackward, flying, sneaking, jumping, yaw, pitch, sprinting, placingBlock, breakingBlock, droppingItem, hotbarSlot);
            }

            inputData.setCollectingCraft(collectingCraft);

            inputsData.add(inputData);
        }
    }

    public int getTick() {
        return tick;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public List<PlayerInputData> getInputsData() {
        return inputsData;
    }
}
