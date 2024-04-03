package fr.math.minecraft.shared.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.Renderer;
import fr.math.minecraft.client.animations.Animation;
import fr.math.minecraft.client.events.PlayerMoveEvent;
import fr.math.minecraft.client.events.listeners.EntityUpdate;
import fr.math.minecraft.client.events.listeners.EventListener;
import fr.math.minecraft.client.meshs.NametagMesh;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.server.pathfinding.AStar;
import fr.math.minecraft.server.pathfinding.Node;
import fr.math.minecraft.server.pathfinding.Pattern;
import fr.math.minecraft.shared.Direction;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.entity.mob.MobBehavior;
import fr.math.minecraft.shared.entity.mob.MobType;
import fr.math.minecraft.shared.entity.mob.Zombie;
import fr.math.minecraft.shared.inventory.Inventory;
import fr.math.minecraft.shared.network.Hitbox;
import fr.math.minecraft.shared.world.*;
import org.apache.log4j.Logger;
import org.joml.*;
import org.joml.Math;

import java.util.*;

public abstract class Entity {

    protected Vector3f position;
    protected final Vector3f velocity;
    protected Vector3f gravity;
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
    protected float hunger;
    protected float maxHunger;
    protected NametagMesh nametagMesh;
    protected Inventory inventory;
    protected EntityType type;
    protected boolean canJump;
    protected float damage;
    private Pattern pattern;
    private String lastAttackerID;
    private EntityType lastAttackerType;
    private int patternUpdateCooldown;
    protected int hitMarkDelay;
    private final static Logger logger = LoggerUtility.getClientLogger(Entity.class, LogType.TXT);
    public Entity(String uuid, EntityType type) {
        this.type = type;
        this.position = new Vector3f(0.0f, 100.0f, 0.0f);
        this.gravity = new Vector3f(0, -0.0025f, 0);
        this.velocity = new Vector3f();
        this.animations = new ArrayList<>();
        this.pattern = null;
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
        this.damage = 0.0f;
        this.patternUpdateCooldown = 0;
        this.lastAttackerID = null;
        this.lastAttackerType = null;
        this.hitMarkDelay = 0;
        this.hunger = type.getHunger();
        this.maxHunger = type.getMaxHunger();
    }

    public void notifyEvent(PlayerMoveEvent event) {
        for (EventListener eventListener : eventListeners) {
            eventListener.onPlayerMove(event);
        }
    }

    private Direction followNode(Node node) {
        if (position.y < node.getPosition().y && Math.abs(position.x - node.getPosition().x) < .2f) {
            return Direction.RIGHT;
        } else if (position.y > node.getPosition().y && Math.abs(position.x - node.getPosition().x) < .2f) {
            return Direction.LEFT;
        }
        if (position.z < node.getPosition().y) {
            return Direction.BACKWARD;
        } else {
            return Direction.FORWARD;
        }
    }

    public void update(World world) {

        MinecraftServer server = MinecraftServer.getInstance();
        Map<String, Client> clients = server.getClients();
        velocity.add(gravity);

        patternUpdateCooldown++;

        if (patternUpdateCooldown == GameConfiguration.TICK_PER_SECONDS && (this instanceof Zombie)) {

            int chunkX = (int) java.lang.Math.floor(position.x / (double) Chunk.SIZE);
            int chunkY = (int) java.lang.Math.floor(position.y / (double) Chunk.SIZE);
            int chunkZ = (int) java.lang.Math.floor(position.z / (double) Chunk.SIZE);

            Vector3i chunkPosition = new Vector3i(chunkX, chunkY, chunkZ);

            if (!world.getGraph().getLoadedChunks().contains(chunkPosition)) {
                server.getPathfindingQueue().submit(() -> {
                   AStar.initGraph(world, position);
                });
            }

            synchronized (server.getClients()) {
                float minDistance = Float.MAX_VALUE;
                Client target = null;
                for (Client client : clients.values()) {
                    float clientDistance = client.getPosition().distance(position);
                    if (clientDistance < 1.5f) {
                        Vector2f entityPosition = new Vector2f(position.x, position.z);
                        Vector2f direction = new Vector2f(client.getPosition().x, client.getPosition().z).sub(entityPosition);
                        client.setHealth(client.getHealth() - damage);
                        client.getVelocity().y = GameConfiguration.KNOCK_BACK_Y;
                        client.getVelocity().x = direction.x * GameConfiguration.KNOCK_BACK_X;
                        client.getVelocity().z = direction.y * GameConfiguration.KNOCK_BACK_Z;
                        client.setMaxSpeed(.4f);
                        logger.debug("Un " + type.getName() + " a attaqué " + client.getName() + " (" + client.getUuid() + ") " + client.getHealth() + "/" + client.getMaxHealth());
                        continue;
                    }
                    if (clientDistance < 10 && clientDistance < minDistance) {
                        target = client;
                        minDistance = clientDistance;
                    }
                }
                if (target != null) {
                    Node start = new Node(new Vector3f(position));
                    Node end = new Node(new Vector3f(target.getPosition()));
                    Vector2f entityPosition = new Vector2f(position.x, position.z);
                    Vector2f direction = new Vector2f(target.getPosition().x, target.getPosition().z).sub(entityPosition);
                    pattern = new Pattern(AStar.shortestPath(world, start, end), start, end);
                    yaw = (float) Math.toDegrees(Math.atan2(direction.y, direction.x));
                }
            }
            patternUpdateCooldown = 0;
        }

        Vector3f acceleration = new Vector3f(0, 0, 0);
        Vector3f front = new Vector3f();

        front.x = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
        front.y = Math.sin(Math.toRadians(0.0f));
        front.z = Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));

        front.normalize();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();

        if (pattern != null && !pattern.getPath().isEmpty()) {
            Node currentNode = pattern.getPath().get(0);
            if (currentNode != null) {
                Vector2f entityPosition = new Vector2f(position.x, position.z);
                Vector2f direction = new Vector2f(currentNode.getPosition()).sub(entityPosition);
                if (direction.x != 0 && direction.y != 0) {
                    direction.normalize();
                }
                acceleration.add(direction.x, 0, direction.y);
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
        handleCollisions(world, new Vector3f(velocity.x, 0, 0), true);

        position.z += velocity.z;
        handleCollisions(world, new Vector3f(0, 0, velocity.z), true);

        position.y += velocity.y;
        handleCollisions(world, new Vector3f(0, velocity.y, 0), true);

        if (pattern != null && !pattern.getPath().isEmpty()) {
            float distance = (float) new Vector2i((int) position.x, (int) position.z).distance(pattern.getPath().get(0).getPosition());
            //System.out.println("distance : " + distance);
            if (distance <= 1.0) {
                pattern = pattern.next();
            }
        }

        velocity.mul(0.95f);
    }

    public void updateAnimations() {
        for (Animation animation : animations) {
            animation.update();
        }
    }

    public void lerpPosition() {
        this.updateAnimations();
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
                        if (type != EntityType.PLAYER) {
                            handleJumpCollision();
                        }
                        position.x = worldX - hitbox.getWidth();
                    } else if (velocity.x < 0) {
                        if (type != EntityType.PLAYER) {
                            handleJumpCollision();
                        }
                        position.x = worldX + hitbox.getWidth() + 1;
                    }

                    if (velocity.y > 0) {
                        position.y = worldY - hitbox.getHeight();
                        this.velocity.y = 0;
                    } else if (velocity.y < 0) {
                        //maxFall = MAX_FALL_SPEED;
                        canJump = true;
                        this.maxSpeed = 0.03f;
                        position.y = worldY + hitbox.getHeight() + 1;
                        this.velocity.y = 0;
                    }

                    if (velocity.z > 0) {
                        if (type != EntityType.PLAYER) {
                            handleJumpCollision();
                        }
                        position.z = worldZ - hitbox.getDepth();
                    } else if (velocity.z < 0) {
                        if (type != EntityType.PLAYER) {
                            handleJumpCollision();
                        }
                        position.z = worldZ + hitbox.getDepth() + 1;
                    }
                }
            }
        }
    }

    private void handleJumpCollision() {
        if (type != EntityType.PLAYER && canJump) {
            velocity.y = .27f;
            canJump = false;
        }
    }

    public ObjectNode toJSONObject() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode entityNode = mapper.createObjectNode();
        ArrayNode pathArrayNode = mapper.createArrayNode();

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
        entityNode.put("hunger", hunger);
        entityNode.put("maxHunger", maxHunger);
        entityNode.put("lastAttacker", lastAttackerID == null ? "NONE" : lastAttackerID);
        entityNode.put("lastAttackerType", lastAttackerType == null ? "NONE" : lastAttackerType.toString());

        if (pattern != null && !pattern.getPath().isEmpty()) {
            for (Node node : pattern.getPath()) {
                ObjectNode pathNode = mapper.createObjectNode();
                pathNode.put("x", node.getPosition().x);
                pathNode.put("y", node.getPosition().y);
                pathArrayNode.add(pathNode);
            }
        }

        entityNode.set("path", pathArrayNode);
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

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public String getLastAttackerID() {
        return lastAttackerID;
    }

    public EntityType getLastAttackerType() {
        return lastAttackerType;
    }

    public void setLastAttackerType(EntityType lastAttackerType) {
        this.lastAttackerType = lastAttackerType;
    }

    public void setLastAttackerID(String lastAttackerID) {
        this.lastAttackerID = lastAttackerID;
    }

    public int getHitMarkDelay() {
        return hitMarkDelay;
    }

    public void setHitMarkDelay(int hitMarkDelay) {
        this.hitMarkDelay = hitMarkDelay;
    }
}
