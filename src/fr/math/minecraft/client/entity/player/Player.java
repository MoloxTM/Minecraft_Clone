package fr.math.minecraft.client.entity.player;

import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.Renderer;
import fr.math.minecraft.client.entity.AttackRay;
import fr.math.minecraft.client.manager.SoundManager;
import fr.math.minecraft.client.network.payload.ChatPayload;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.animations.*;
import fr.math.minecraft.client.entity.Ray;
import fr.math.minecraft.client.events.listeners.EntityUpdate;
import fr.math.minecraft.client.events.listeners.EventListener;
import fr.math.minecraft.client.events.PlayerMoveEvent;
import fr.math.minecraft.client.handler.ChatInputsHandler;
import fr.math.minecraft.client.handler.InventoryInputsHandler;
import fr.math.minecraft.client.manager.ChunkManager;
import fr.math.minecraft.client.meshs.NametagMesh;
import fr.math.minecraft.shared.inventory.*;
import fr.math.minecraft.server.Utils;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.entity.Entity;
import fr.math.minecraft.shared.entity.EntityType;
import fr.math.minecraft.shared.world.*;
import fr.math.minecraft.shared.Sprite;
import fr.math.minecraft.shared.PlayerAction;
import fr.math.minecraft.shared.inventory.Hotbar;
import fr.math.minecraft.shared.inventory.PlayerInventory;
import fr.math.minecraft.shared.network.GameMode;
import fr.math.minecraft.shared.network.Hitbox;
import fr.math.minecraft.shared.network.PlayerInputData;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.DoubleBuffer;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {

    private final Hotbar hotbar;
    private boolean firstMouse;
    private boolean movingLeft, movingRight, movingForward, movingBackward;
    private boolean flying, sneaking, canBreakBlock, canPlaceBlock, jumping, sprinting;
    private boolean droppingItem;
    private boolean placingBlock, breakingBlock;
    private boolean canHoldItem, canPlaceHoldedItem;
    private boolean debugKeyPressed, occlusionKeyPressed, pathKeyPressed, interpolationKeyPressed, inventoryKeyPressed, chatKeyPressed;
    private float lastMouseX, lastMouseY;
    private final MiningAnimation miningAnimation;
    private BufferedImage skin;
    private float sensitivity;
    private int ping;
    private final List<PlayerInputData> inputs;
    private final Set<Coordinates> receivedChunks;
    private GameMode gameMode;
    private String skinPath;
    private final PlayerHand hand;
    private int breakBlockCooldown, placeBlockCooldown;
    private final Ray buildRay, breakRay;
    private final AttackRay attackRay;
    private final List<PlacedBlock> placedBlocks;
    private final ArrayList<BreakedBlock> breakedBlocks;
    private Inventory lastInventory;
    private PlayerAction action;
    private Sprite sprite;
    private final PlayerCraftInventory craftInventory;
    public final static float JUMP_VELOCITY = .125f;
    private final ChatPayload chatPayload;
    private final CompletedCraftPlayerInventory completedCraftPlayerInventory;
    private final CraftingTableInventory craftingTableInventory;
    
    public Player(String name) {
        super(null, EntityType.PLAYER);
        this.name = name;
        this.receivedChunks = new HashSet<>();
        this.inputs = new ArrayList<>();
        this.hand = new PlayerHand();
        this.inventory = new PlayerInventory();
        this.hitbox = new Hitbox(new Vector3f(0, 0, 0), new Vector3f(0.25f, 0.9f, 0.25f));
        this.nametagMesh = new NametagMesh(name);
        this.hotbar = new Hotbar();
        this.lastUpdate = new EntityUpdate(new Vector3f(position), yaw, pitch, bodyYaw);
        this.sprite = new Sprite();
        this.miningAnimation = new MiningAnimation();
        this.action = PlayerAction.MINING;
        this.gameMode = GameMode.SURVIVAL;
        this.chatPayload = new ChatPayload(this);
        this.firstMouse = true;
        this.lastMouseX = 0.0f;
        this.lastMouseY = 0.0f;
        this.speed = gameMode == GameMode.SURVIVAL ? GameConfiguration.DEFAULT_SPEED : 0.1f;
        this.maxSpeed = gameMode == GameMode.SURVIVAL ? 0.03f : 0.1f;
        this.maxFall = 0;
        this.ping = 0;
        this.sensitivity = 0.1f;
        this.movingLeft = false;
        this.movingRight = false;
        this.movingForward = false;
        this.movingBackward = false;
        this.droppingItem = false;
        this.chatKeyPressed = false;
        this.debugKeyPressed = false;
        this.pathKeyPressed = false;
        this.occlusionKeyPressed = false;
        this.interpolationKeyPressed = false;
        this.inventoryKeyPressed = false;
        this.canHoldItem = false;
        this.canPlaceHoldedItem = false;
        this.sneaking = false;
        this.sprinting = false;
        this.flying = false;
        this.canJump = true;
        this.canBreakBlock = true;
        this.canPlaceBlock = true;
        this.jumping = false;
        this.placingBlock = false;
        this.breakingBlock = false;
        this.skin = null;
        this.skinPath = "res/textures/skin.png";
        this.attackRay = new AttackRay(GameConfiguration.ATTACK_REACH);
        this.buildRay = new Ray(GameConfiguration.BUILDING_REACH);
        this.breakRay = new Ray(GameConfiguration.BUILDING_REACH);
        this.placedBlocks = new ArrayList<>();
        this.breakedBlocks = new ArrayList<>();
        this.craftInventory = new PlayerCraftInventory();
        this.completedCraftPlayerInventory = new CompletedCraftPlayerInventory();
        this.craftingTableInventory = new CraftingTableInventory();
        this.lastInventory = inventory;
        this.initAnimations();
    }

    private void initAnimations() {
        animations.add(new PlayerWalkAnimation(this));
    }

    public void handleInputs(long window) {

        if (chatPayload.isOpen() && glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            chatPayload.setOpen(false);
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
            glfwFocusWindow(window);
            return;
        }

        if (chatPayload.isOpen()) {
            if (glfwGetKey(window, GLFW_KEY_ENTER) == GLFW_PRESS) {
                chatPayload.send();
                chatPayload.getMessage().delete(0, chatPayload.getMessage().length());
                return;
            }
            if (glfwGetKey(window, GLFW_KEY_BACKSPACE) == GLFW_PRESS) {
                if (chatPayload.getMessage().length() != 0) {
                    chatPayload.getMessage().deleteCharAt(chatPayload.getMessage().length() - 1);
                }
                return;
            }

            ChatInputsHandler handler = new ChatInputsHandler();
            handler.handleInputs(window, chatPayload);
            return;
        }

        if (glfwGetKey(window, GLFW_KEY_T) == GLFW_PRESS) {
            if (!chatKeyPressed) {
                if (!chatPayload.isOpen() && !inventory.isOpen()) {
                    chatPayload.getMessage().delete(0, chatPayload.getMessage().length());
                    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                } else {
                    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                }
                if (!inventory.isOpen()) {
                    chatPayload.setOpen(!chatPayload.isOpen());
                    chatKeyPressed = true;
                    this.resetMoving();
                }
            }
        }

        if (glfwGetKey(window, GLFW_KEY_E) == GLFW_PRESS) {
            if (!inventoryKeyPressed) {
                if (!inventory.isOpen() && !chatPayload.isOpen()) {
                    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                } else {
                    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                }
                if (!chatPayload.isOpen()) {
                    inventory.setOpen(!inventory.isOpen());
                    inventoryKeyPressed = true;
                    this.resetMoving();
                }
            }
        }

        if (glfwGetKey(window, GLFW_KEY_E) == GLFW_RELEASE) {
            inventoryKeyPressed = false;
        }

        if (glfwGetKey(window, GLFW_KEY_P) == GLFW_RELEASE) {
            pathKeyPressed = false;
        }

        if (glfwGetKey(window, GLFW_KEY_T) == GLFW_RELEASE) {
            chatKeyPressed = false;
        }

        DoubleBuffer mouseX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer mouseY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, mouseX, mouseY);

        if (inventory.isOpen()) {
            InventoryInputsHandler handler = new InventoryInputsHandler();
            handler.handleInputs(window, this, inventory, (float) mouseX.get(0), (float) mouseY.get(0));
            handler.handleInputs(window, this, craftInventory, (float) mouseX.get(0), (float) mouseY.get(0));
            handler.handleInputs(window, this, hotbar, (float) mouseX.get(0), (float) mouseY.get(0));
            handler.handleInputs(window, this, completedCraftPlayerInventory, (float) mouseX.get(0), (float) mouseY.get(0));
            return;
        }

        if (craftingTableInventory.isOpen()) {
            InventoryInputsHandler handler = new InventoryInputsHandler();
            handler.handleInputs(window, this, inventory, (float) mouseX.get(0), (float) mouseY.get(0));
            handler.handleInputs(window, this, hotbar, (float) mouseX.get(0), (float) mouseY.get(0));
            handler.handleInputs(window, this, craftingTableInventory, (float) mouseX.get(0), (float) mouseY.get(0));
            handler.handleInputs(window, this, completedCraftPlayerInventory, (float) mouseX.get(0), (float) mouseY.get(0));
            return;
        }

        if (firstMouse) {
            lastMouseX = (float) mouseX.get(0);
            lastMouseY = (float) mouseY.get(0);
            firstMouse = false;
        }

        double mouseOffsetX = mouseX.get(0) - lastMouseX;
        double mouseOffsetY = mouseY.get(0) - lastMouseY;

        yaw += (float) mouseOffsetX * sensitivity;
        if (yaw > 360.0f || yaw < -360.0f){
            yaw = 0.0f;
        }

        pitch -= (float) mouseOffsetY * sensitivity;
        if (pitch > 90.0f){
            pitch = 89.0f;
        } else if (pitch < -90.0f){
            pitch = -89.0f;
        }

        this.resetMoving();

        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            movingForward = true;
        }

        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            movingLeft = true;
        }

        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            movingBackward = true;
        }

        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            movingRight = true;
        }


        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            switch (gameMode) {
                case CREATIVE :
                    flying = true;
                    break;
                case SURVIVAL :
                    jumping = true;
                    break;
            }
        }

        if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            sneaking = true;
        }

        GameConfiguration gameConfiguration = GameConfiguration.getInstance();

        if (glfwGetKey(window, GLFW_KEY_O) == GLFW_PRESS) {
            if (!occlusionKeyPressed) {
                gameConfiguration.setOcclusionEnabled(!gameConfiguration.isOcclusionEnabled());
                occlusionKeyPressed = true;
            }
        }

        if (glfwGetKey(window, GLFW_KEY_P) == GLFW_PRESS) {
            if (!pathKeyPressed) {
                gameConfiguration.setEntitesPathEnabled(!gameConfiguration.isEntitesPathEnabled());
                pathKeyPressed = true;
            }
        }

        if (glfwGetKey(window, GLFW_KEY_F1) == GLFW_PRESS) {
            if (!interpolationKeyPressed) {
                gameConfiguration.setEntityInterpolation(!gameConfiguration.isEntityInterpolationEnabled());
                interpolationKeyPressed = true;
            }
        }

        if (glfwGetKey(window, GLFW_KEY_F3) == GLFW_PRESS) {
            if (!debugKeyPressed) {
                gameConfiguration.setDebugging(!gameConfiguration.isDebugging());
                debugKeyPressed = true;
            }
        }

        if (glfwGetKey(window, GLFW_KEY_Q) == GLFW_PRESS) {
            droppingItem = true;
        }

        if (glfwGetKey(window, GLFW_KEY_1) == GLFW_PRESS) {
            hotbar.setSelectedSlot(0);
        }

        if (glfwGetKey(window, GLFW_KEY_2) == GLFW_PRESS) {
            hotbar.setSelectedSlot(1);
        }

        if (glfwGetKey(window, GLFW_KEY_3) == GLFW_PRESS) {
            hotbar.setSelectedSlot(2);
        }

        if (glfwGetKey(window, GLFW_KEY_4) == GLFW_PRESS) {
            hotbar.setSelectedSlot(3);
        }

        if (glfwGetKey(window, GLFW_KEY_5) == GLFW_PRESS) {
            hotbar.setSelectedSlot(4);
        }

        if (glfwGetKey(window, GLFW_KEY_6) == GLFW_PRESS) {
            hotbar.setSelectedSlot(5);
        }

        if (glfwGetKey(window, GLFW_KEY_7) == GLFW_PRESS) {
            hotbar.setSelectedSlot(6);
        }

        if (glfwGetKey(window, GLFW_KEY_8) == GLFW_PRESS) {
            hotbar.setSelectedSlot(7);
        }

        if (glfwGetKey(window, GLFW_KEY_9) == GLFW_PRESS) {
            hotbar.setSelectedSlot(8);
        }

        if (glfwGetKey(window, GLFW_KEY_F3) == GLFW_RELEASE) {
            debugKeyPressed = false;
        }

        if (glfwGetKey(window, GLFW_KEY_O) == GLFW_RELEASE) {
            occlusionKeyPressed = false;
        }

        if (glfwGetKey(window, GLFW_KEY_F1) == GLFW_RELEASE) {
            interpolationKeyPressed = false;
        }

        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS) {
            breakingBlock = true;
        }

        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_RELEASE) {
            breakingBlock = false;
            canBreakBlock = true;
            action = null;
        }

        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS) {
            placingBlock = true;
        }


        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_RELEASE) {
            placingBlock = false;
            canPlaceBlock = true;
        }

        if (movingLeft || movingRight || movingForward || movingBackward || sneaking || flying) {
            this.notifyEvent(new PlayerMoveEvent(this));
        }

        lastMouseX = (float) mouseX.get(0);
        lastMouseY = (float) mouseY.get(0);
    }

    public void resetMoving() {
        movingLeft = false;
        movingRight = false;
        movingForward = false;
        movingBackward = false;
        flying = false;
        sneaking = false;
        jumping = false;
        droppingItem = false;
    }

    public void update() {
        this.updateAnimations();
        GameConfiguration gameConfiguration = GameConfiguration.getInstance();
        if (hitMarkDelay > 0) {
            hitMarkDelay = hitMarkDelay - 1;
        }
        if (gameConfiguration.isEntityInterpolationEnabled()) {
            position.x = Math.lerp(position.x, lastUpdate.getPosition().x, 0.1f);
            position.y = Math.lerp(position.y, lastUpdate.getPosition().y, 0.1f);
            position.z = Math.lerp(position.z, lastUpdate.getPosition().z, 0.1f);
            yaw = Math.lerp(yaw, lastUpdate.getYaw(), 0.1f);
            pitch = Math.lerp(pitch, lastUpdate.getPitch(), 0.1f);
            bodyYaw = Math.lerp(bodyYaw, lastUpdate.getBodyYaw(), 0.1f);
        }
    }

    private void handleJump() {
        if (canJump) {
            maxFall = 0.5f;
            canJump = false;
        }
    }

    public void updatePosition(World world) {

        Vector3f front = new Vector3f();
        front.x = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
        front.y = Math.sin(Math.toRadians(0.0f));
        front.z = Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));

        front.normalize();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f acceleration = new Vector3f(0, 0, 0);

        if (gameMode == GameMode.SURVIVAL) {
            velocity.add(gravity);
        }

        if (sprinting) {
            this.setSpeed(GameConfiguration.SPRINT_SPEED);
        } else {
            this.setSpeed(GameConfiguration.DEFAULT_SPEED);
        }

        if (movingForward) {
            acceleration.add(front);
        }

        if (movingBackward) {
            acceleration.sub(front);
        }

        if (movingLeft) {
            acceleration.sub(right);
        }

        if (movingRight) {
            acceleration.add(right);
        }

        if (flying) {
            acceleration.add(new Vector3f(0.0f, .5f, 0.0f));
        }

        if (sneaking) {
            acceleration.sub(new Vector3f(0.0f, .5f, 0.0f));
        }

        if (jumping) {
            // handleJump();
            if (canJump) {
                //maxFall = MAX_JUMP_FALL_SPEED;
                velocity.y = JUMP_VELOCITY;
                canJump = false;
            }
        }

        if (breakingBlock) {
            sprite.update(PlayerAction.MINING);
            Entity target = attackRay.getTarget();
            if (target == null) {
                if (action == PlayerAction.MINING && sprite.getIndex() == action.getLength() - 1) {
                    ChunkManager chunkManager = new ChunkManager();
                    if (breakRay.getAimedChunk() != null && (breakRay.getAimedBlock() != Material.AIR.getId() || breakRay.getAimedBlock() != Material.WATER.getId())) {
                        BreakedBlock breakedBlock = new BreakedBlock(new Vector3i(breakRay.getBlockWorldPosition()), breakRay.getAimedBlock());
                        chunkManager.removeBlock(breakRay.getAimedChunk(), breakRay.getBlockChunkPositionLocal(), Game.getInstance().getWorld());
                        breakedBlocks.add(breakedBlock);
                        Material material = Material.getMaterialById(breakedBlock.getBlock());
                        sprite.reset();
                        SoundManager soundManager = SoundManager.getInstance();
                        soundManager.play(soundManager.getDigSound(material));
                    }
                }

                if (canBreakBlock) {
                    if (breakRay.getAimedChunk() != null && (breakRay.getAimedBlock() != Material.AIR.getId() || breakRay.getAimedBlock() != Material.WATER.getId())) {
                        action = PlayerAction.MINING;
                    }
                    canBreakBlock = false;
                    breakBlockCooldown = (int) GameConfiguration.UPS / 3;
                }
            }
        } else {
            sprite.reset();
        }

        if (gameMode == GameMode.CREATIVE && breakBlockCooldown > 0) {
            breakBlockCooldown--;
            if (breakBlockCooldown == 0) {
                canBreakBlock = true;
            }
        }

        if (placingBlock) {
            ItemStack hotbarItem = hotbar.getItems()[hotbar.getSelectedSlot()];
            if (canPlaceBlock && hotbarItem != null && hotbarItem.getMaterial() != Material.AIR) {
                ChunkManager chunkManager = new ChunkManager();
                if (buildRay.isAimingBlock() && !hotbarItem.getMaterial().isFood()) {
                    if (!hotbarItem.getMaterial().isItem()) {
                        Vector3i rayPosition = buildRay.getBlockWorldPosition();
                        Vector3i placedBlockWorldPosition = buildRay.getBlockPlacedPosition(rayPosition);
                        Vector3i blockPositionLocal = Utils.worldToLocal(placedBlockWorldPosition);
                        PlacedBlock placedBlock = new PlacedBlock(uuid, placedBlockWorldPosition, blockPositionLocal, hotbarItem.getMaterial().getId());

                        placedBlocks.add(placedBlock);
                        Chunk aimedChunk = world.getChunkAt(placedBlockWorldPosition);

                        chunkManager.placeBlock(aimedChunk, blockPositionLocal, Game.getInstance().getWorld(), hotbarItem.getMaterial());
                    }
                }
                canPlaceBlock = false;
                placeBlockCooldown = (int) GameConfiguration.UPS / 3;
            }
        }

        if (placeBlockCooldown > 0) {
            placeBlockCooldown--;
            if (placeBlockCooldown == 0) {
                canPlaceBlock = true;
            }
        }

        if (movingBackward || movingForward || movingLeft || movingRight) {
            hand.setAnimation(PlayerHandAnimation.MOVING);
        } else {
            hand.setAnimation(PlayerHandAnimation.IDLE);
        }

        velocity.add(acceleration.mul(speed));

        if (new Vector3f(velocity.x, 0, velocity.z).length() > maxSpeed) {
            Vector3f velocityNorm = new Vector3f(velocity.x, velocity.y, velocity.z);
            velocityNorm.normalize().mul(maxSpeed);
            velocity.x = velocityNorm.x;
            velocity.z = velocityNorm.z;
        }

        position.x += velocity.x;
        handleCollisions(world, new Vector3f(velocity.x, 0, 0), false);

        position.z += velocity.z;
        handleCollisions(world, new Vector3f(0, 0, velocity.z), false);

        position.y += velocity.y;
        handleCollisions(world, new Vector3f(0, velocity.y, 0), false);

        velocity.mul(0.95f);

        if (inventory.isOpen() || craftingTableInventory.isOpen()) {
            this.resetMoving();
            placingBlock = false;
            breakingBlock = false;
        }

        PlayerInputData inputData = new PlayerInputData(movingLeft, movingRight, movingForward, movingBackward, flying, sneaking, jumping, yaw, pitch, sprinting, placingBlock, breakingBlock, droppingItem, hotbar.getSelectedSlot());
        inputs.add(inputData);
    }

    public boolean isMoving() {
        return movingLeft || movingRight || movingForward || movingBackward || sneaking || flying || jumping;
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

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingForward(boolean movingForward) {
        this.movingForward = movingForward;
    }

    public void setMovingBackward(boolean movingBackward) {
        this.movingBackward = movingBackward;
    }

    public NametagMesh getNametagMesh() {
        return nametagMesh;
    }

    @Override
    public void render(Camera camera, Renderer renderer) {
        renderer.render(camera, this);
    }

    public BufferedImage getSkin() {
        return skin;
    }

    public void setSkin(BufferedImage skin) {
        this.skin = skin;
    }

    public void notifyEvent(PlayerMoveEvent event) {
        for (EventListener eventListener : eventListeners) {
            eventListener.onPlayerMove(event);
        }
    }

    public boolean isFlying() {
        return flying;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }


    public List<PlayerInputData> getInputs() {
        return inputs;
    }

    public Set<Coordinates> getReceivedChunks() {
        return receivedChunks;
    }

    public String getSkinPath() {
        return skinPath;
    }

    public void setSkinPath(String skinPath) {
        this.skinPath = skinPath;
    }

    public PlayerHand getHand() {
        return hand;
    }

    public float getMaxFallSpeed() {
        return maxFall;
    }

    public void setMaxFallSpeed(float maxFall) {
        this.maxFall = maxFall;
    }

    public AttackRay getAttackRay() {
        return attackRay;
    }

    public Ray getBuildRay() {
        return buildRay;
    }

    public Ray getBreakRay() {
        return breakRay;
    }

    public List<PlacedBlock> getPlacedBlocks() {
        return placedBlocks;
    }

    public ArrayList<BreakedBlock> getBreakedBlocks() {
        return breakedBlocks;
    }

    public Hotbar getHotbar() {
        return hotbar;
    }

    public PlayerAction getAction() {
        return action;
    }

    public void setAction(PlayerAction action) {
        this.action = action;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public MiningAnimation getMiningAnimation() {
        return miningAnimation;
    }

    public boolean canPlaceHoldedItem() {
        return canPlaceHoldedItem;
    }

    public void setCanPlaceHoldedItem(boolean canPlaceHoldedItem) {
        this.canPlaceHoldedItem = canPlaceHoldedItem;
    }

    public boolean canHoldItem() {
        return canHoldItem;
    }

    public void setCanHoldItem(boolean canHoldItem) {
        this.canHoldItem = canHoldItem;
    }

    public PlayerCraftInventory getCraftInventory() {
        return craftInventory;
    }

    public CompletedCraftPlayerInventory getCompletedCraftPlayerInventory() {
        return completedCraftPlayerInventory;
    }

    public Inventory getLastInventory() {
        return lastInventory;
    }

    public void setLastInventory(Inventory lastInventory) {
        this.lastInventory = lastInventory;
    }

    public ChatPayload getChatPayload() {
        return chatPayload;
    }

    public CraftingTableInventory getCraftingTableInventory() {
        return craftingTableInventory;
    }
}
