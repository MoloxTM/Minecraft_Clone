package fr.math.minecraft.shared.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.Renderer;
import fr.math.minecraft.client.animations.Animation;
import fr.math.minecraft.client.events.PlayerMoveEvent;
import fr.math.minecraft.client.events.listeners.EntityUpdate;
import fr.math.minecraft.client.events.listeners.EventListener;
import fr.math.minecraft.client.meshs.NametagMesh;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.entity.mob.MobBehavior;
import fr.math.minecraft.shared.entity.mob.MobType;
import fr.math.minecraft.shared.inventory.Inventory;
import fr.math.minecraft.shared.network.Hitbox;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import org.joml.Math;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {

    protected Vector3f position;
    protected final Vector3f velocity;
    protected final Vector3f gravity;
    protected float yaw, lastYaw, bodyYaw;
    protected float pitch;
    protected float speed, maxSpeed, maxFall;
    protected String name;
    protected String uuid;
    protected final List<Animation> animations;
    protected final List<EventListener> eventListeners;
    protected Hitbox hitbox;
    protected EntityUpdate lastUpdate;
    protected MobBehavior behavior;
    protected final MobType mobType;
    protected float health;
    protected float maxHealth;
    protected NametagMesh nametagMesh;
    protected Inventory inventory;
    protected EntityType type;
    protected boolean canJump;

    public Entity(String uuid, EntityType type) {
        this.type = type;
        this.position = new Vector3f(0.0f, 100.0f, 0.0f);
        this.gravity = new Vector3f(0, -0.0025f, 0);
        this.velocity = new Vector3f();
        this.animations = new ArrayList<>();
        this.lastUpdate = new EntityUpdate(new Vector3f(position), yaw, pitch, bodyYaw);
        this.eventListeners = new ArrayList<>();
        this.yaw = 0.0f;
        this.lastYaw = 0.0f;
        this.bodyYaw = 0.0f;
        this.pitch = 0.0f;
        this.speed = GameConfiguration.DEFAULT_SPEED;
        this.maxSpeed = 0.03f;
        this.maxFall = 0.03f;
        this.name = type.getName();
        this.health = type.getHealth();
        this.maxHealth = type.getMaxHealth();
        this.uuid = uuid;
        this.mobType = null;
        this.nametagMesh = new NametagMesh(name);
        this.canJump = false;
    }

    public void notifyEvent(PlayerMoveEvent event) {
        for (EventListener eventListener : eventListeners) {
            eventListener.onPlayerMove(event);
        }
    }

    public void update(World world) {

        velocity.add(gravity);

        Vector3f acceleration = new Vector3f(0, 0, 0);
        Vector3f front = new Vector3f();

        front.x = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
        front.y = Math.sin(Math.toRadians(0.0f));
        front.z = Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));

        front.normalize();
        acceleration.add(front);

        velocity.add(acceleration.mul(speed));

        if (new Vector3f(velocity.x, 0, velocity.z).length() > maxSpeed) {
            Vector3f velocityNorm = new Vector3f(velocity.x, velocity.y, velocity.z);
            velocityNorm.normalize().mul(maxSpeed);
            velocity.x = velocityNorm.x;
            velocity.z = velocityNorm.z;
        }

        position.x += velocity.x;
        handleCollisions(world, new Vector3f(velocity.x, 0, 0), true);

        position.z += velocity.z;
        handleCollisions(world, new Vector3f(0, 0, velocity.z), true);

        position.y += velocity.y;
        handleCollisions(world, new Vector3f(0, velocity.y, 0), true);
    }

    public void lerpPosition() {
        //this.updateAnimations();
        GameConfiguration gameConfiguration = GameConfiguration.getInstance();

        if (gameConfiguration.isEntityInterpolationEnabled()) {
            position.x = Math.lerp(position.x, lastUpdate.getPosition().x, 0.01f);
            position.y = Math.lerp(position.y, lastUpdate.getPosition().y, 0.01f);
            position.z = Math.lerp(position.z, lastUpdate.getPosition().z, 0.01f);
            yaw = Math.lerp(yaw, lastUpdate.getYaw(), 0.1f);
            pitch = Math.lerp(pitch, lastUpdate.getPitch(), 0.1f);
            bodyYaw = Math.lerp(bodyYaw, lastUpdate.getBodyYaw(), 0.1f);
        }
    }

    public void handleCollisions(World world, Vector3f velocity, boolean serverSide) {

        int minX = (int) Math.floor(position.x - hitbox.getWidth());
        int maxX = (int) Math.ceil(position.x + hitbox.getWidth());
        int minY = (int) Math.floor(position.y - hitbox.getHeight());
        int maxY = (int) Math.ceil(position.y + hitbox.getHeight());
        int minZ = (int) Math.floor(position.z - hitbox.getDepth());
        int maxZ = (int) Math.ceil(position.z + hitbox.getDepth());

        for (int worldX = minX; worldX < maxX; worldX++) {
            for (int worldY = minY; worldY < maxY; worldY++) {
                for (int worldZ = minZ; worldZ < maxZ; worldZ++) {

                    byte block;
                    if (serverSide) {
                        block = world.getServerBlockAt(worldX, worldY, worldZ);
                    } else {
                        block = world.getBlockAt(worldX, worldY, worldZ);
                    }

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
                        //maxFall = MAX_FALL_SPEED;
                        canJump = true;
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

    public ObjectNode toJSONObject() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode entityNode = mapper.createObjectNode();

        entityNode.put("type", "ENTITY_STATE");
        entityNode.put("entityType", type.toString());
        entityNode.put("uuid", uuid);
        entityNode.put("name", name);
        entityNode.put("x", position.x);
        entityNode.put("y", position.y);
        entityNode.put("z", position.z);
        entityNode.put("yaw", yaw);
        entityNode.put("pitch", pitch);
        entityNode.put("bodyYaw", bodyYaw);
        entityNode.put("health", health);
        entityNode.put("maxHealth", maxHealth);

        return entityNode;
    }

    public void addEventListener(EventListener event) {
        eventListeners.add(event);
    }

    public void removeEventListener(EventListener event) {
        eventListeners.remove(event);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public String getUuid() {
        return uuid;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public Vector3f getGravity() {
        return gravity;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getLastYaw() {
        return lastYaw;
    }

    public void setLastYaw(float lastYaw) {
        this.lastYaw = lastYaw;
    }

    public float getBodyYaw() {
        return bodyYaw;
    }

    public void setBodyYaw(float bodyYaw) {
        this.bodyYaw = bodyYaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public boolean canJump() {
        return this.canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public float getMaxFall() {
        return maxFall;
    }

    public void setMaxFall(float maxFall) {
        this.maxFall = maxFall;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Animation> getAnimations() {
        return animations;
    }

    public List<EventListener> getEventListeners() {
        return eventListeners;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public EntityUpdate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(EntityUpdate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public MobBehavior getBehavior() {
        return behavior;
    }

    public void setBehavior(MobBehavior behavior) {
        this.behavior = behavior;
    }

    public MobType getMobType() {
        return mobType;
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

    public NametagMesh getNametagMesh() {
        return nametagMesh;
    }

    public void setNametagMesh(NametagMesh nametagMesh) {
        this.nametagMesh = nametagMesh;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public abstract void render(Camera camera, Renderer renderer);

    public EntityType getType() {
        return type;
    }
}
