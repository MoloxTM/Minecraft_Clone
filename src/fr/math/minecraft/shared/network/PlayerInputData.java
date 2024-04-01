package fr.math.minecraft.shared.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.shared.inventory.Inventory;
import fr.math.minecraft.shared.inventory.InventoryType;

public class PlayerInputData {

    private final float yaw;
    private final float pitch;
    private final boolean movingLeft, movingRight, movingForward, movingBackward;
    private final boolean flying, sneaking, jumping, sprinting;
    private final boolean placingBlock, breakingBlock, droppingItem;
    private final int hotbarSlot;
    private final int holdedSlot;
    private final InventoryType inventoryType;
    private final InventoryType nextInventory;
    private final int nextSlot;
    private boolean collectingCraft;
    private boolean pressingPlaceKey;
    private boolean closingCraftInventory;

    public PlayerInputData(boolean movingLeft, boolean movingRight, boolean movingForward, boolean movingBackward, boolean flying, boolean sneaking, boolean jumping, float yaw, float pitch, boolean sprinting, boolean placingBlock, boolean breakingBlock, boolean droppingItem, int hotbarSlot) {
        this.movingLeft = movingLeft;
        this.movingRight = movingRight;
        this.movingForward = movingForward;
        this.movingBackward = movingBackward;
        this.flying = flying;
        this.sneaking = sneaking;
        this.jumping = jumping;
        this.yaw = yaw;
        this.pitch = pitch;
        this.sprinting = sprinting;
        this.breakingBlock = breakingBlock;
        this.placingBlock = placingBlock;
        this.droppingItem = droppingItem;
        this.hotbarSlot = hotbarSlot;
        this.holdedSlot = 0;
        this.inventoryType = null;
        this.nextInventory = null;
        this.nextSlot = 0;
        this.collectingCraft = false;
        this.pressingPlaceKey = false;
        this.closingCraftInventory = false;
    }

    public PlayerInputData(int holdedSlot, InventoryType type, InventoryType nextInventory, int nextSlot, boolean collectingCraft, boolean pressingPressKey, boolean closingCraftInventory) {
        this.holdedSlot = holdedSlot;
        this.inventoryType = type;
        this.nextInventory = nextInventory;
        this.nextSlot = nextSlot;
        this.collectingCraft = collectingCraft;
        this.movingLeft = false;
        this.movingRight = false;
        this.movingForward = false;
        this.movingBackward = false;
        this.flying = false;
        this.sneaking = false;
        this.jumping = false;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.sprinting = false;
        this.breakingBlock = false;
        this.placingBlock = false;
        this.droppingItem = false;
        this.pressingPlaceKey = pressingPressKey;
        this.closingCraftInventory = closingCraftInventory;
        this.hotbarSlot = 0;
    }

    public ObjectNode toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("movingLeft", movingLeft);
        node.put("movingRight", movingRight);
        node.put("movingForward", movingForward);
        node.put("movingBackward", movingBackward);
        node.put("flying", flying);
        node.put("sneaking", sneaking);
        node.put("yaw", yaw);
        node.put("pitch", pitch);
        node.put("jumping", jumping);
        node.put("sprinting", sprinting);
        node.put("placingBlock", placingBlock);
        node.put("breakingBlock", breakingBlock);
        node.put("droppingItem", droppingItem);
        node.put("hotbarSlot", hotbarSlot);
        node.put("holdedSlot", holdedSlot);

        if (inventoryType != null) {
            node.put("inventoryType", inventoryType.ordinal());
        }
        if (nextInventory != null) {
            node.put("nextInventory", nextInventory.ordinal());
        }

        node.put("nextSlot", nextSlot);
        node.put("collectingCraft", collectingCraft);
        node.put("pressingPlaceKey", pressingPlaceKey);
        node.put("closingCraftInventory", closingCraftInventory);

        return node;
    }

    public boolean isMoving() {
        return (movingBackward || movingForward || movingLeft || movingRight || jumping || sprinting);
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public boolean isMovingForward() {
        return movingForward;
    }

    public boolean isMovingBackward() {
        return movingBackward;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public boolean isFlying() {
        return flying;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isBreakingBlock() {
        return breakingBlock;
    }

    public boolean isPlacingBlock() {
        return placingBlock;
    }

    public boolean isDroppingItem() {
        return droppingItem;
    }

    public int getHotbarSlot() {
        return hotbarSlot;
    }

    public int getHoldedSlot() {
        return holdedSlot;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public InventoryType getNextInventory() {
        return nextInventory;
    }

    public int getNextSlot() {
        return nextSlot;
    }

    public boolean isCollectingCraft() {
        return collectingCraft;
    }

    public void setCollectingCraft(boolean collectingCraft) {
        this.collectingCraft = collectingCraft;
    }

    public boolean isPressingPlaceKey() {
        return pressingPlaceKey;
    }

    public boolean isClosingCraftInventory() {
        return closingCraftInventory;
    }
}
