package fr.math.minecraft.client.network.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.client.manager.ChunkManager;
import fr.math.minecraft.client.network.FixedPacketSender;
import fr.math.minecraft.client.network.packet.PlayerActionsPacket;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.shared.math.MathUtils;
import fr.math.minecraft.shared.inventory.Inventory;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.network.PlayerInputData;
import fr.math.minecraft.shared.world.*;
import org.apache.log4j.Logger;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatePayload {

    private InputPayload payload;
    private JsonNode data;
    private Vector3f position;
    private Vector3f velocity;
    private List<BreakedBlock> breakedBlockData;
    private List<PlacedBlock> placedBlocks;
    private float yaw, pitch, health, maxHealth, maxSpeed, hunger, maxHunger;
    private ArrayNode inventoryItems, hotbarItems, craftItems, completedCraftItems, craftingTableItems;
    private boolean craftTableOpen;
    private final static Logger logger = LoggerUtility.getClientLogger(StatePayload.class, LogType.TXT);


    public StatePayload(InputPayload payload) {
        this.payload = payload;
        this.position = new Vector3f();
        this.velocity = new Vector3f();
        this.breakedBlockData = new ArrayList<>();
        this.placedBlocks = new ArrayList<>();
        this.data = null;
    }

    public StatePayload(JsonNode stateData) {
        this.position = new Vector3f();
        this.velocity = new Vector3f();
        this.payload = new InputPayload(stateData.get("tick").asInt());
        this.breakedBlockData = new ArrayList<>();
        this.placedBlocks = new ArrayList<>();
        this.inventoryItems = (ArrayNode) stateData.get("inventory");
        this.hotbarItems = (ArrayNode) stateData.get("hotbar");
        this.craftItems = (ArrayNode) stateData.get("craftInventory");
        this.completedCraftItems = (ArrayNode) stateData.get("completedCraftInventory");
        this.craftingTableItems = (ArrayNode) stateData.get("craftingTableInventory");
        this.craftTableOpen = stateData.get("craftingTableOpen").booleanValue();

        this.extractBrokenBlocks(stateData);
        this.extractPlacedBlocks(stateData);

        position.x = stateData.get("x").floatValue();
        position.y = stateData.get("y").floatValue();
        position.z = stateData.get("z").floatValue();
        velocity.x = stateData.get("vx").floatValue();
        velocity.y = stateData.get("vy").floatValue();
        velocity.z = stateData.get("vz").floatValue();
        yaw = stateData.get("yaw").floatValue();
        pitch = stateData.get("pitch").floatValue();
        health = stateData.get("health").floatValue();
        maxHealth = stateData.get("maxHealth").floatValue();
        maxSpeed = stateData.get("maxSpeed").floatValue();
        hunger = stateData.get("hunger").floatValue();
        maxHunger = stateData.get("maxHunger").floatValue();
    }

    public void reconcileMovement(World world, Player player, Vector3f playerPosition, Vector3f playerVelocity) {
        Vector3f front = new Vector3f();
        //Vector3f position = new Vector3f(playerPosition);
        //Vector3f velocity = player.getVelocity();

        for (PlayerInputData inputData : payload.getInputData()) {
            float yaw = inputData.getYaw();
            float pitch = inputData.getPitch();

            this.yaw = yaw;
            this.pitch = pitch;

            Vector3f acceleration = new Vector3f(0,0,0);

            front.x = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
            front.y = Math.sin(Math.toRadians(0.0f));
            front.z = Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));

            front.normalize();
            Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();

            player.getVelocity().add(player.getGravity());

            if (inputData.isMovingForward()) {
                acceleration.add(front);
            }

            if (inputData.isMovingBackward()) {
                acceleration.sub(front);
            }

            if (inputData.isMovingLeft()) {
                acceleration.sub(right);
            }

            if (inputData.isMovingRight()) {
                acceleration.add(right);
            }

            if (inputData.isFlying()) {
                acceleration.add(new Vector3f(0.0f, .5f, 0.0f));
            }

            if (inputData.isSneaking()) {
                acceleration.sub(new Vector3f(0.0f, .5f, 0.0f));
            }

            if (inputData.isJumping()) {
                // this.handleJump();
                if (player.canJump()) {
                    player.getVelocity().y = Player.JUMP_VELOCITY;
                    player.setCanJump(false);
                }
            }

            player.getVelocity().add(acceleration.mul(player.getSpeed()));

            if (new Vector3f(player.getVelocity().x, 0, player.getVelocity().z).length() > player.getMaxSpeed()) {
                Vector3f velocityNorm = new Vector3f(player.getVelocity().x, player.getVelocity().y, player.getVelocity().z);
                velocityNorm.normalize().mul(player.getMaxSpeed());
                player.getVelocity().x = velocityNorm.x;
                player.getVelocity().z = velocityNorm.z;
            }

            player.getPosition().x += player.getVelocity().x;
            player.handleCollisions(world, new Vector3f(player.getVelocity().x, 0, 0), false);

            player.getPosition().z += player.getVelocity().z;
            player.handleCollisions(world, new Vector3f(0, 0, player.getVelocity().z), false);

            player.getPosition().y += player.getVelocity().y;
            player.handleCollisions(world, new Vector3f(0, player.getVelocity().y, 0), false);

            player.getVelocity().mul(0.95f);
        }

        this.position = new Vector3f(player.getPosition());
    }

    public void verifyPlacedBlocks(World world, List<PlacedBlock> clientPlacedBlocks) {
        for (PlacedBlock placedBlock : clientPlacedBlocks) {
            if (!placedBlocks.contains(placedBlock)) {
                ChunkManager chunkManager = new ChunkManager();
                Vector3i blockWorldPosition = placedBlock.getWorldPosition();
                Chunk chunk = world.getChunkAt(blockWorldPosition);
                Vector3i blockLocalPositon = placedBlock.getLocalPosition();
                byte oldBlock = chunk.getBlock(blockLocalPositon.x, blockLocalPositon.y, blockLocalPositon.z);

                chunkManager.removeBlock(chunk, blockLocalPositon, world);
                logger.info("[Reconciliation] Le block de " + Material.getMaterialById(oldBlock) + " en " + blockWorldPosition + " a été détruit.");
            }
        }
        for (PlacedBlock placedBlock : placedBlocks) {
            Vector3i blockWorldPosition = placedBlock.getWorldPosition();
            Chunk chunk = world.getChunkAt(blockWorldPosition);
            Vector3i blockLocalPositon = placedBlock.getLocalPosition();
            byte block = chunk.getBlock(blockLocalPositon.x, blockLocalPositon.y, blockLocalPositon.z);

            if (block != placedBlock.getBlock()) {
                ChunkManager chunkManager = new ChunkManager();
                Material material = Material.getMaterialById(placedBlock.getBlock());
                chunkManager.placeBlock(chunk, blockLocalPositon, world, material);
                logger.info("[Reconciliation] Un block de " + material + " en " + blockWorldPosition + " a été placé suite à une réconciliation.");
            }
        }
    }

    public void verifyBrokenBlocks(World world, List<BreakedBlock> clientBrokenBlocks) {
        for (BreakedBlock breakedBlock : clientBrokenBlocks) {
            if (!breakedBlockData.contains(breakedBlock)) {
                breakedBlock.restore(world);
                logger.info("[Reconciliation] Le block de " + Material.getMaterialById(breakedBlock.getBlock()) + " en " + breakedBlock.getPosition() + " a été restoré.");
            } else {
                logger.info(Material.getMaterialById(breakedBlock.getBlock()) + " validé par le serveur !");
                System.out.println(Arrays.toString(breakedBlockData.toArray()));
            }
        }
        for (BreakedBlock breakedBlock : breakedBlockData) {
            Vector3i worldPosition = breakedBlock.getPosition();
            Chunk chunk = world.getChunkAt(worldPosition);

            int blockX = MathUtils.mod(worldPosition.x, Chunk.SIZE);
            int blockY = MathUtils.mod(worldPosition.y, Chunk.SIZE);
            int blockZ = MathUtils.mod(worldPosition.z, Chunk.SIZE);
            byte block = chunk.getBlock(blockX, blockY, blockZ);

            if (block != Material.AIR.getId()) {
                ChunkManager chunkManager = new ChunkManager();
                Vector3i localPosition = new Vector3i(blockX, blockY, blockZ);
                chunkManager.removeBlock(chunk, localPosition, world);
                logger.info("[Reconciliation] Le serveur a cassé le block de " + Material.getMaterialById(block) + " en " + breakedBlock.getPosition());
            }
        }
    }

    public void reconcileInventory(Player player) {
        Inventory inventory = player.getInventory();
        Inventory hotbar = player.getHotbar();
        Inventory craftInventory = player.getCraftInventory();
        Inventory completedCraftInventory = player.getCompletedCraftPlayerInventory();
        Inventory craftingTableInventory = player.getCraftingTableInventory();

        for (int slot = 0; slot < inventoryItems.size(); slot++) {
            JsonNode itemNode = inventoryItems.get(slot);
            ItemStack item = new ItemStack(itemNode);
            if (item.getMaterial() == Material.AIR) {
                inventory.setItem(null, slot);
            } else {
                inventory.setItem(item, slot);
            }
        }

        for (int slot = 0; slot < hotbarItems.size(); slot++) {
            JsonNode itemNode = hotbarItems.get(slot);
            ItemStack item = new ItemStack(itemNode);
            if (item.getMaterial() == Material.AIR) {
                hotbar.setItem(null, slot);
            } else {
                hotbar.setItem(item, slot);
            }
        }

        for (int slot = 0; slot < craftItems.size(); slot++) {
            JsonNode itemNode = craftItems.get(slot);
            ItemStack item = new ItemStack(itemNode);
            if (item.getMaterial() == Material.AIR) {
                craftInventory.setItem(null, slot);
            } else {
                craftInventory.setItem(item, slot);
            }
        }

        for (int slot = 0; slot < completedCraftItems.size(); slot++) {
            JsonNode itemNode = completedCraftItems.get(slot);
            ItemStack item = new ItemStack(itemNode);
            if (item.getMaterial() == Material.AIR) {
                completedCraftInventory.setItem(null, slot);
            } else {
                completedCraftInventory.setItem(item, slot);
            }
        }

        craftingTableInventory.setOpen(craftTableOpen);

        for (int slot = 0; slot < craftingTableItems.size(); slot++) {
            JsonNode itemNode = craftingTableItems.get(slot);
            ItemStack item = new ItemStack(itemNode);
            if (item.getMaterial() == Material.AIR) {
                craftingTableInventory.setItem(null, slot);
            } else {
                craftingTableInventory.setItem(item, slot);
            }
        }
    }

    public void send(Player player) {
        PlayerActionsPacket packet = new PlayerActionsPacket(player, this, payload);
        FixedPacketSender.getInstance().enqueue(packet);
    }

    public void extractBrokenBlocks(JsonNode data) {
        ArrayNode positionNode = (ArrayNode) data.get("brokenBlocks");
        for (int i = 0; i < positionNode.size(); i++) {
            JsonNode node = positionNode.get(i);
            Vector3i position = new Vector3i(node.get("x").asInt(), node.get("y").asInt(), node.get("z").asInt());
            byte block = (byte) node.get("block").asInt();
            BreakedBlock breakedBlock = new BreakedBlock(position, block);
            this.breakedBlockData.add(breakedBlock);
        }
    }

    public void extractPlacedBlocks(JsonNode data) {
        ArrayNode positionNode = (ArrayNode) data.get("aimedPlacedBlocks");

        for (int i = 0; i < positionNode.size(); i++) {
            JsonNode node = positionNode.get(i);
            Vector3i worldPosition = new Vector3i(node.get("wx").asInt(), node.get("wy").asInt(), node.get("wz").asInt());
            Vector3i localPosition = new Vector3i(node.get("lx").asInt(), node.get("ly").asInt(), node.get("lz").asInt());
            byte block = (byte) node.get("block").asInt();
            String playerUuid = node.get("playerUuid").asText();
            PlacedBlock placedBlock = new PlacedBlock(playerUuid, worldPosition, localPosition, block);
            this.placedBlocks.add(placedBlock);
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

    public InputPayload getInputPayload() {
        return payload;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setBreakedBlocksData(List<BreakedBlock> breakedBlockData) {
        this.breakedBlockData = breakedBlockData;
    }

    public List<BreakedBlock> getBreakedBlockData() {
        return breakedBlockData;
    }

    public void setPlacedBlocksData(List<PlacedBlock> placedBlocks) {
        this.placedBlocks = placedBlocks;
    }

    public List<PlacedBlock> getPlacedBlocks() {
        return placedBlocks;
    }

    public ArrayNode getHotbarItems() {
        return hotbarItems;
    }

    public ArrayNode getInventoryItems() {
        return inventoryItems;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getHunger() {
        return hunger;
    }

    public float getMaxHunger() {
        return maxHunger;
    }
}