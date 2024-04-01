package fr.math.minecraft.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.entity.AttackRay;
import fr.math.minecraft.client.entity.Ray;
import fr.math.minecraft.shared.entity.EntityType;
import fr.math.minecraft.shared.inventory.*;
import fr.math.minecraft.shared.math.MathUtils;
import fr.math.minecraft.shared.math.PhysicsController;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.shared.entity.Entity;
import fr.math.minecraft.shared.network.GameMode;
import fr.math.minecraft.shared.world.*;
import fr.math.minecraft.shared.PlayerAction;
import fr.math.minecraft.shared.Sprite;
import fr.math.minecraft.shared.world.DroppedItem;
import fr.math.minecraft.server.payload.InputPayload;
import fr.math.minecraft.server.payload.StatePayload;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.network.Hitbox;
import fr.math.minecraft.shared.network.PlayerInputData;
import org.apache.log4j.Logger;
import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

public class Client {

    private final String name;
    private final String uuid;
    private final InetAddress address;
    private final int port;
    private Vector3f position;
    private Vector3f front;
    private float yaw;
    private float bodyYaw;
    private float pitch;
    private float speed;
    private BufferedImage skin;
    private boolean movingLeft, movingRight, movingForward, movingBackward;
    private boolean flying, sneaking;
    private boolean canBreakBlock, canPlaceBlock;
    private boolean active;
    private final Vector3f velocity;
    private final Vector3f gravity;
    private GameMode gameMode;
    private float maxSpeed;
    private final Hitbox hitbox;
    private final Queue<InputPayload> inputQueue;
    private final StatePayload[] stateBuffer;
    private boolean canJump;
    private final Ray buildRay, breakRay;
    private final AttackRay attackRay;
    private List<BreakedBlock> breakedBlocks;
    private List<PlacedBlock> placedBlocks;
    private int breakBlockCooldown, placeBlockCoolDown;
    private Sprite sprite;
    private PlayerAction action;
    private final static Logger logger = LoggerUtility.getServerLogger(Client.class, LogType.TXT);
    private final PlayerInventory inventory;
    private final Hotbar hotbar;
    private float health;
    private float maxHealth;
    private float hunger;
    private float maxHunger;
    private String lastAttackerID;
    private EntityType lastAttackerType;
    private final PlayerCraftInventory craftInventory;
    private final CompletedCraftPlayerInventory completedCraftPlayerInventory;
    private final CraftingTableInventory craftingTableInventory;
    private final static float JUMP_VELOCITY = .125f;
    private boolean decreaseHunger;

    public Client(String uuid, String name, InetAddress address, int port) {
        this.address = address;
        this.port = port;
        this.uuid = uuid;
        this.name = name;
        this.health = 20.0f;
        this.maxHealth = 20.0f;
        this.hunger = 20.0f;
        this.maxHunger = 20.0f;
        this.velocity = new Vector3f();
        this.inputQueue = new LinkedList<>();
        this.gravity = new Vector3f(0, -0.0025f, 0);
        this.front = new Vector3f(0.0f, 0.0f, 0.0f);
        this.position = new Vector3f(0.0f, 100.0f, 0.0f);
        this.hitbox = new Hitbox(new Vector3f(0, 0, 0), new Vector3f(0.25f, 0.9f, 0.25f));
        this.stateBuffer = new StatePayload[GameConfiguration.BUFFER_SIZE];
        this.gameMode = GameMode.SURVIVAL;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.speed = GameConfiguration.DEFAULT_SPEED;
        this.maxSpeed = 0.03f;
        this.breakBlockCooldown = GameConfiguration.BLOCK_BREAK_COOLDOWN;
        this.placeBlockCoolDown = GameConfiguration.BLOCK_BREAK_COOLDOWN;
        this.skin = null;
        this.movingLeft = false;
        this.movingRight = false;
        this.movingBackward = false;
        this.movingForward = false;
        this.flying = false;
        this.sneaking = false;
        this.canJump = true;
        this.canBreakBlock = true;
        this.canPlaceBlock = true;
        this.active = false;
        this.sprite = new Sprite();
        this.action = PlayerAction.MINING;
        this.attackRay = new AttackRay(GameConfiguration.ATTACK_REACH);
        this.buildRay = new Ray(GameConfiguration.BUILDING_REACH);
        this.breakRay = new Ray(GameConfiguration.BUILDING_REACH);
        this.breakedBlocks = new ArrayList<>();
        this.placedBlocks = new ArrayList<>();
        this.inventory = new PlayerInventory();
        this.hotbar = new Hotbar();
        this.craftInventory = new PlayerCraftInventory();
        this.completedCraftPlayerInventory = new CompletedCraftPlayerInventory();
        this.craftingTableInventory = new CraftingTableInventory();
        this.decreaseHunger = false;
    }

    public String getName() {
        return name;
    }

    public void handleCollisions(Vector3f velocity) {
        MinecraftServer server = MinecraftServer.getInstance();
        World world = server.getWorld();

        int minX = (int) Math.floor(position.x - hitbox.getWidth());
        int maxX = (int) Math.ceil(position.x + hitbox.getWidth());
        int minY = (int) Math.floor(position.y - hitbox.getHeight());
        int maxY = (int) Math.ceil(position.y + hitbox.getHeight());
        int minZ = (int) Math.floor(position.z - hitbox.getDepth());
        int maxZ = (int) Math.ceil(position.z + hitbox.getDepth());

        for (int worldX = minX; worldX < maxX; worldX++) {
            for (int worldY = minY; worldY < maxY; worldY++) {
                for (int worldZ = minZ; worldZ < maxZ; worldZ++) {

                    byte block = world.getServerBlockAt(worldX, worldY, worldZ);
                    Material material = Material.getMaterialById(block);

                    if (!material.isSolid()) {
                        continue;
                    }

                    if (velocity.x > 0) {
                        position.x = worldX - hitbox.getWidth();
                    } else if (velocity.x < 0) {
                        position.x = worldX + hitbox.getWidth() + 1;
                    }

                    if (velocity.y > 0) {
                        position.y = worldY - hitbox.getHeight();
                        this.velocity.y = 0;
                    } else if (velocity.y < 0) {
                        canJump = true;
                        this.maxSpeed = 0.03f;
                        position.y = worldY + hitbox.getHeight() + 1;
                        this.velocity.y = 0;
                    }

                    if (velocity.z > 0) {
                        position.z = worldZ - hitbox.getDepth();
                    } else if (velocity.z < 0) {
                        position.z = worldZ + hitbox.getDepth() + 1;
                    }
                }
            }
        }
    }

    public void update(World world, InputPayload payload) {

        breakedBlocks.clear();
        placedBlocks.clear();

        this.setDecreaseHunger(false);

        for (PlayerInputData inputData : payload.getInputsData()) {
            float yaw = inputData.getYaw();
            float pitch = inputData.getPitch();

            this.yaw = yaw;
            this.pitch = pitch;

            Vector3f acceleration = new Vector3f(0, 0, 0);

            front.x = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
            front.y = Math.sin(Math.toRadians(0.0f));
            front.z = Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));

            front.normalize();

            Vector3f cameraFront = new Vector3f();

            cameraFront.x = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
            cameraFront.y = Math.sin(Math.toRadians(pitch));
            cameraFront.z = Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));

            cameraFront.normalize();

            Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();

            this.resetMoving();

            if (gameMode == GameMode.SURVIVAL) {
                velocity.add(gravity);
            }

            hotbar.setSelectedSlot(inputData.getHotbarSlot());

            if (inputData.isDroppingItem()) {
                ItemStack hotbarItem = hotbar.getItems()[hotbar.getSelectedSlot()];
                if (hotbarItem != null) {
                    int itemAmount = hotbarItem.getAmount();

                    DroppedItem droppedItem = new DroppedItem(new Vector3f(position), hotbarItem.getMaterial());
                    droppedItem.getVelocity().y = 0.8f;
                    droppedItem.getVelocity().x = cameraFront.x * 0.7f;
                    droppedItem.getVelocity().z = cameraFront.z * 0.7f;

                    world.getDroppedItems().put(droppedItem.getUuid(), droppedItem);
                    logger.info(name + "(" + uuid + ") " + " a drop un item de " + hotbarItem.getMaterial());
                    hotbarItem.setAmount(itemAmount - 1);

                    if (hotbarItem.getAmount() == 0) {
                        hotbar.setItem(null, hotbar.getSelectedSlot());
                        //hotbar.setItem(new ItemStack(Material.DIRT, 1), 0);
                    }
                    return;
                }
            }

            if (inputData.isMovingForward()) {
                acceleration.add(front);
                movingForward = true;
            }

            if (inputData.isMovingBackward()) {
                acceleration.sub(front);
                movingBackward = true;
            }

            if (inputData.isMovingLeft()) {
                acceleration.sub(right);
                movingLeft = true;
            }

            if (inputData.isMovingRight()) {
                acceleration.add(right);
                movingRight = true;
            }

            if (inputData.isFlying()) {
                acceleration.add(new Vector3f(0.0f, .5f, 0.0f));
            }

            if (inputData.isSneaking()) {
                acceleration.sub(new Vector3f(0.0f, .5f, 0.0f));
            }

            if (inputData.isJumping()) {
                // this.handleJump();
                if (canJump) {
                    velocity.y = JUMP_VELOCITY;
                    canJump = false;
                }
            }

            velocity.add(acceleration.mul(speed));

            if (new Vector3f(velocity.x, 0, velocity.z).length() > maxSpeed) {
                Vector3f velocityNorm = new Vector3f(velocity.x, velocity.y, velocity.z);
                velocityNorm.normalize().mul(maxSpeed);
                velocity.x = velocityNorm.x;
                velocity.z = velocityNorm.z;
            }

            position.x += velocity.x;
            handleCollisions(new Vector3f(velocity.x, 0, 0));

            position.z += velocity.z;
            handleCollisions(new Vector3f(0, 0, velocity.z));

            position.y += velocity.y;
            handleCollisions(new Vector3f(0, velocity.y, 0));

            velocity.mul(0.95f);

            buildRay.update(position, cameraFront, world, true);
            breakRay.update(position, cameraFront, world, true);
            attackRay.update(position, uuid, cameraFront, world, true);

            if (!breakRay.isAimingBlock()) {
                sprite.reset();
                breakRay.reset();
                action = null;
            }

            if (!inputData.isBreakingBlock()) {
                canBreakBlock = true;
            }

            if (inputData.isBreakingBlock()) {

                byte block = breakRay.getAimedBlock();
                sprite.update(PlayerAction.MINING);
                Entity target = attackRay.getTarget();
                Client clientTarget = attackRay.getClientTarget();

                if (target != null || clientTarget != null) {
                    if (target != null) {
                        Vector2f direction = MathUtils.getDirection2D(position, target.getPosition());
                        PhysicsController.infligeKnockback(target, direction);
                        target.setHealth(target.getHealth() - 0.5f);
                        target.setLastAttackerID(uuid);
                        target.setLastAttackerType(EntityType.PLAYER);
                        logger.info(name + "(" + uuid + ") a attaqué un " + target.getType() + " ! Health : " + target.getHealth() + "/" + target.getMaxHealth());
                    } else {
                        Vector2f direction = MathUtils.getDirection2D(position, clientTarget.getPosition());
                        PhysicsController.infligeKnockback(clientTarget, direction);
                        clientTarget.setHealth(clientTarget.getHealth() - 0.5f);
                        clientTarget.setLastAttackerID(uuid);
                        clientTarget.setLastAttackerType(EntityType.PLAYER);
                        logger.info(name + "(" + uuid + ") a attaqué un JOUEUR ! Health : " + clientTarget.getHealth() + "/" + clientTarget.getMaxHealth());
                    }
                } else {
                    if (action == PlayerAction.MINING && breakRay.isAimingBlock() && sprite.getIndex() == action.getLength() - 1) {
                        Vector3i rayPosition = breakRay.getBlockWorldPosition();
                        Vector3i blockPositionLocal = Utils.worldToLocal(rayPosition);
                        Material material = Material.getMaterialById(block);

                        BreakedBlock breakedBlock = new BreakedBlock(rayPosition, block);
                        breakedBlocks.add(breakedBlock);

                        logger.info(name + " (" + uuid + ") a cassé un block de " + material + " en " + breakRay.getBlockWorldPosition());

                        synchronized (world.getPlacedBlocks()) {
                            PlacedBlock placedBlock = world.getPlacedBlocks().get(breakedBlock.getPosition());
                            if (placedBlock != null) {
                                world.getPlacedBlocks().remove(breakedBlock.getPosition());
                            }
                        }

                        synchronized (world.getBrokenBlocks()) {
                            world.getBrokenBlocks().put(breakedBlock.getPosition(), breakedBlock);
                        }

                        breakRay.getAimedChunk().setBlock(blockPositionLocal.x, blockPositionLocal.y, blockPositionLocal.z, Material.AIR.getId());
                        breakRay.reset();
                        sprite.reset();

                        Random random = new Random();
                        DroppedItem droppedItem = new DroppedItem(new Vector3f(rayPosition), Material.getMiningBlock(material));

                        if (droppedItem.getMaterial() != Material.AIR) {
                            droppedItem.getVelocity().y = 0.8f;
                            droppedItem.getVelocity().x = random.nextFloat() * (0.75f - 0.35f) + 0.35f;
                            droppedItem.getVelocity().z = random.nextFloat() * (0.85f - 0.3f) + 0.3f;

                            world.getDroppedItems().put(droppedItem.getUuid(), droppedItem);
                        }
                    }

                    if (this.canBreakBlock) {
                        if (breakRay.isAimingBlock()) {
                            action = PlayerAction.MINING;
                            sprite.reset();
                        }
                        this.canBreakBlock = false;
                    }
                }

            } else {
                sprite.reset();
            }


            if (!inputData.isPlacingBlock()) {
                canPlaceBlock = true;
            }

            if (inputData.isClosingCraftInventory()) {
                craftingTableInventory.setOpen(false);
                System.out.println("?");
            }

            if (inputData.isPlacingBlock()) {
                byte block = buildRay.getAimedBlock();
                ItemStack hotbarItem = hotbar.getItems()[hotbar.getSelectedSlot()];

                if (canPlaceBlock && block == Material.CRAFTING_TABLE.getId()) {
                    craftingTableInventory.setOpen(true);
                }

                if (canPlaceBlock && hotbarItem != null && hotbarItem.getMaterial() != Material.AIR) {
                    if(hotbarItem.getMaterial().isFood()) {
                        System.out.println("Faim du joueur :"+(int)this.getHunger());
                        if((int)this.getHunger() <= this.getMaxHunger()) {
                            float hungerValue = 0.0f;
                            if((int)this.getHunger() + 2.0f <= this.getMaxHunger()) {
                                hungerValue = 2.0f;
                                hotbarItem.setAmount(hotbarItem.getAmount() - 1);
                            } else if((int)this.getHunger() + 1.0f <= this.getMaxHunger()){
                                hungerValue = this.getMaxHunger() - this.getHunger();
                                hotbarItem.setAmount(hotbarItem.getAmount() - 1);
                            }
                            this.setHunger(this.getHunger() + hungerValue);
                            if (hotbarItem.getAmount() <= 0) {
                                hotbar.getItems()[hotbar.getSelectedSlot()] = null;
                            }
                        }
                        buildRay.reset();
                        this.canPlaceBlock = false;
                    }
                    if (buildRay.isAimingBlock() && !hotbarItem.getMaterial().isItem()) {
                        Vector3i rayPosition = buildRay.getBlockWorldPosition();
                        Vector3i placedBlockWorldPosition = buildRay.getBlockPlacedPosition(rayPosition);
                        Vector3i blockPositionLocal = Utils.worldToLocal(placedBlockWorldPosition);
                        PlacedBlock placedBlock = new PlacedBlock(uuid, placedBlockWorldPosition, blockPositionLocal, hotbarItem.getMaterial().getId());
                        Material material = hotbarItem.getMaterial();
                        placedBlocks.add(placedBlock);

                        synchronized (world.getBrokenBlocks()) {
                            BreakedBlock breakedBlock = world.getBrokenBlocks().get(placedBlock.getWorldPosition());
                            if (breakedBlock != null) {
                                world.getBrokenBlocks().remove(placedBlock.getWorldPosition());
                            }
                        }

                        synchronized (world.getPlacedBlocks()) {
                            world.getPlacedBlocks().put(placedBlock.getWorldPosition(), placedBlock);
                        }

                        Chunk aimedChunk = world.getChunkAt(placedBlockWorldPosition);

                        aimedChunk.setBlock(blockPositionLocal.x, blockPositionLocal.y, blockPositionLocal.z, material.getId());
                        hotbarItem.setAmount(hotbarItem.getAmount() - 1);

                        if (hotbarItem.getAmount() == 0) {
                            hotbar.getItems()[hotbar.getSelectedSlot()] = null;
                        }

                        logger.info(name + " (" + uuid + ") a placé un block de " + hotbarItem.getMaterial() + " en " + buildRay.getBlockWorldPosition());

                        buildRay.reset();
                        this.canPlaceBlock = false;
                    }
                }
            }

            int holdedSlot = inputData.getHoldedSlot();
            InventoryType inventoryType = inputData.getInventoryType();
            InventoryType nextInventoryType = inputData.getNextInventory();
            int nextSlot = inputData.getNextSlot();
            Inventory lastInventory = null;
            Inventory nextInventory = null;

            if (inventoryType != null) {
                switch (inventoryType) {
                    case HOTBAR:
                        lastInventory = hotbar;
                        break;
                    case PLAYER_INVENTORY:
                        lastInventory = inventory;
                        break;
                    case CRAFT_INVENTORY:
                        lastInventory = craftInventory;
                        break;
                    case COMPLETED_CRAFT_INVENTORY:
                        lastInventory = completedCraftPlayerInventory;
                        break;
                    case CRAFTING_TABLE:
                        lastInventory = craftingTableInventory;
                        break;
                }
            }

            if (nextInventoryType != null) {
                switch (nextInventoryType) {
                    case HOTBAR:
                        nextInventory = hotbar;
                        break;
                    case PLAYER_INVENTORY:
                        nextInventory = inventory;
                        break;
                    case CRAFT_INVENTORY:
                        nextInventory = craftInventory;
                        break;
                    case COMPLETED_CRAFT_INVENTORY:
                        nextInventory = completedCraftPlayerInventory;
                        break;
                    case CRAFTING_TABLE:
                        nextInventory = craftingTableInventory;
                        break;
                }
            }

            ItemStack craftResult = completedCraftPlayerInventory.getItems()[0];

            if (inputData.isCollectingCraft() && craftResult != null) {
                CraftController controller = CraftController.getInstance();
                if (craftingTableInventory.isOpen()) {
                    CraftRecipes craft = controller.getCraft(craftingTableInventory);
                    CraftData matchingCraft = null;
                    if (craft != null) {
                        for (CraftData craftData : craft.getCraftingTable()) {
                            if (craftData.equals(craftingTableInventory)) {
                                matchingCraft = craftData;
                                break;
                            }
                        }

                        if (matchingCraft != null) {
                            for (byte block : matchingCraft.getTabCraft()) {
                                if (block == Material.AIR.getId()) {
                                    continue;
                                }
                                Material material = Material.getMaterialById(block);
                                craftingTableInventory.removeItem(material);
                            }
                        }

                        this.addItem(craftResult);
                        completedCraftPlayerInventory.clear();
                    }
                } else {
                    CraftRecipes craft = controller.getCraft(craftInventory);
                    CraftData matchingCraft = null;
                    if (craft != null) {
                        for (CraftData craftData : craft.getPlayerInventory()) {
                            if (craftData.equals(craftInventory)) {
                                matchingCraft = craftData;
                                break;
                            }
                        }

                        if (matchingCraft != null) {
                            for (byte block : matchingCraft.getTabCraft()) {
                                if (block == Material.AIR.getId()) {
                                    continue;
                                }
                                Material material = Material.getMaterialById(block);
                                craftInventory.removeItem(material);
                            }
                        }

                        this.addItem(craftResult);
                        completedCraftPlayerInventory.clear();
                    }
                }
            } else {
                if (lastInventory != null && nextInventory != null && holdedSlot != -1 && nextSlot != -1) {
                    ItemStack holdedItem = lastInventory.getItems()[holdedSlot];
                    ItemStack oldItem = nextInventory.getItems()[nextSlot];

                    if (inputData.isPressingPlaceKey()) {
                        if (holdedItem != null) {
                            if (oldItem == null) {
                                holdedItem.setAmount(holdedItem.getAmount() - 1);
                                if (holdedItem.getAmount() == 0) {
                                    lastInventory.setItem(null, holdedSlot);
                                }
                                nextInventory.setItem(new ItemStack(holdedItem.getMaterial(), 1), nextSlot);
                            }
                        }
                    } else {
                        if (holdedItem != null) {
                            if (oldItem == null) {
                                nextInventory.setItem(holdedItem, nextSlot);
                                lastInventory.setItem(null, holdedSlot);
                            } else {
                                if (nextSlot != holdedSlot && nextInventory.getItems()[nextSlot].getMaterial() == holdedItem.getMaterial()) {
                                    int newAmount = holdedItem.getAmount() + nextInventory.getItems()[nextSlot].getAmount();
                                    if (newAmount <= 64) {
                                        holdedItem.setAmount(newAmount);
                                        lastInventory.setItem(null, holdedSlot);
                                        nextInventory.setItem(holdedItem, nextSlot);
                                    }
                                } else {
                                    nextInventory.setItem(holdedItem, nextSlot);
                                    lastInventory.setItem(oldItem, holdedSlot);
                                }
                            }
                        }
                    }

                    CraftController controller = CraftController.getInstance();
                    if (craftingTableInventory.isOpen()) {
                        CraftRecipes craft = controller.getCraft(craftingTableInventory);
                        if (craft != null) {
                            completedCraftPlayerInventory.setItem(craft.getCraft(), 0);
                        }
                    } else {
                        CraftRecipes craft = controller.getCraft(craftInventory);
                        if (craft != null) {
                            completedCraftPlayerInventory.setItem(craft.getCraft(), 0);
                        }
                    }
                }
            }

            if (inputData.isMoving()) {
                this.setDecreaseHunger(true);
            }
        }
        
        if(this.isDecreaseHunger() == true) {
            if(this.getHunger() - 0.0005f >= 0){
                this.setHunger(this.getHunger() - 0.0005f);
            }
        }
        
    }

    private void handleJump() {
        if (canJump) {
            velocity.y += gravity.y * (2.5f - 1) * 1.0f / GameConfiguration.UPS;
            canJump = false;
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public ObjectNode toJSONWithSkin() {
        ObjectNode node = this.toJSON();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(skin, "png", baos);
            node.put("skin", Base64.getEncoder().encodeToString(baos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return node;
    }

    public void resetMoving() {
        this.movingRight = false;
        this.movingLeft = false;
        this.movingBackward = false;
        this.movingForward = false;
    }

    public ObjectNode toJSON() {
        ObjectNode node = new ObjectMapper().createObjectNode();

        node.put("name", this.name);
        node.put("uuid", this.uuid);
        node.put("x", this.position.x);
        node.put("y", this.position.y);
        node.put("z", this.position.z);
        node.put("vx", this.velocity.x);
        node.put("vy", this.velocity.y);
        node.put("vz", this.velocity.z);
        node.put("rx", this.buildRay.getBlockWorldPosition().x);
        node.put("ry", this.buildRay.getBlockWorldPosition().y);
        node.put("rz", this.buildRay.getBlockWorldPosition().z);
        node.put("movingLeft", movingLeft);
        node.put("movingRight", movingRight);
        node.put("movingForward", movingForward);
        node.put("movingBackward", movingBackward);
        node.put("action", action == null ? "NONE" : action.toString());
        node.put("spriteIndex", sprite.getIndex());
        node.put("yaw", this.yaw);
        node.put("pitch", this.pitch);
        node.put("bodyYaw", this.bodyYaw);
        node.put("health", this.health);
        node.put("maxHealth", this.maxHealth);
        node.put("hunger", this.hunger);
        node.put("maxHunger", this.maxHunger);
        node.put("lastAttacker", lastAttackerID == null ? "NONE" : lastAttackerID);
        node.put("lastAttackerType", lastAttackerType == null ? "NONE" : lastAttackerType.toString());

        return node;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public BufferedImage getSkin() {
        return skin;
    }

    public float getBodyYaw() {
        return bodyYaw;
    }

    public int getPort() {
        return port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSkin(BufferedImage skin) {
        this.skin = skin;
    }

    public String getUuid() {
        return uuid;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public Queue<InputPayload> getInputQueue() {
        return inputQueue;
    }

    public StatePayload[] getStateBuffer() {
        return stateBuffer;
    }

    public List<BreakedBlock> getBreakedBlocks() {
        return breakedBlocks;
    }

    public List<PlacedBlock> getPlacedBlocks() {
        return placedBlocks;
    }

    public Vector3f getFront() {
        return front;
    }

    public Vector3f getGravity() {
        return gravity;
    }

    public Ray getAttackRay() {
        return attackRay;
    }

    public Ray getBuildRay() {
        return buildRay;
    }

    public Ray getBreakRay() {
        return breakRay;
    }
    
    public PlayerInventory getInventory() {
        return inventory;
    }

    public CompletedCraftPlayerInventory getCompletedCraftPlayerInventory() {
        return completedCraftPlayerInventory;
    }

    public PlayerCraftInventory getPlayerCraftInventory() {
        return craftInventory;
    }

    public CraftingTableInventory getCraftingTableInventory() {
        return craftingTableInventory;
    }

    public Hotbar getHotbar() {
        return hotbar;
    }

    public void addItem(ItemStack item) {
        if (hotbar.getCurrentSize() < hotbar.getSize()) {
            hotbar.addItem(item);
        } else {
            if (inventory.getCurrentSize() >= inventory.getSize()) {
                return;
            }
            inventory.addItem(item);
        }
    }

    public float getSpeed() {
        return speed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getHunger() {
        return hunger;
    }

    public void setHunger(float hunger) {
        this.hunger = hunger;
    }

    public float getMaxHunger() {
        return maxHunger;
    }

    public void setMaxHunger(float maxHunger) {
        this.maxHunger = maxHunger;
    }

    public EntityType getLastAttackerType() {
        return lastAttackerType;
    }

    public void setLastAttackerType(EntityType lastAttackerType) {
        this.lastAttackerType = lastAttackerType;
    }

    public String getLastAttackerID() {
        return lastAttackerID;
    }

    public void setLastAttackerID(String lastAttackerID) {
        this.lastAttackerID = lastAttackerID;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setFront(Vector3f front) {
        this.front = front;
    }

    public void setBodyYaw(float bodyYaw) {
        this.bodyYaw = bodyYaw;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingForward() {
        return movingForward;
    }

    public void setMovingForward(boolean movingForward) {
        this.movingForward = movingForward;
    }

    public boolean isMovingBackward() {
        return movingBackward;
    }

    public void setMovingBackward(boolean movingBackward) {
        this.movingBackward = movingBackward;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }

    public boolean isCanBreakBlock() {
        return canBreakBlock;
    }

    public void setCanBreakBlock(boolean canBreakBlock) {
        this.canBreakBlock = canBreakBlock;
    }

    public boolean isCanPlaceBlock() {
        return canPlaceBlock;
    }

    public void setCanPlaceBlock(boolean canPlaceBlock) {
        this.canPlaceBlock = canPlaceBlock;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public void setBreakedBlocks(List<BreakedBlock> breakedBlocks) {
        this.breakedBlocks = breakedBlocks;
    }

    public void setPlacedBlocks(List<PlacedBlock> placedBlocks) {
        this.placedBlocks = placedBlocks;
    }

    public int getBreakBlockCooldown() {
        return breakBlockCooldown;
    }

    public void setBreakBlockCooldown(int breakBlockCooldown) {
        this.breakBlockCooldown = breakBlockCooldown;
    }

    public int getPlaceBlockCoolDown() {
        return placeBlockCoolDown;
    }

    public void setPlaceBlockCoolDown(int placeBlockCoolDown) {
        this.placeBlockCoolDown = placeBlockCoolDown;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public PlayerAction getAction() {
        return action;
    }

    public void setAction(PlayerAction action) {
        this.action = action;
    }

    public boolean isDecreaseHunger() {
        return decreaseHunger;
    }

    public void setDecreaseHunger(boolean decreaseHunger) {
        this.decreaseHunger = decreaseHunger;
    }
}
