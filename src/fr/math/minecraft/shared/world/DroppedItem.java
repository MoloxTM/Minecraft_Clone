package fr.math.minecraft.shared.world;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.MinecraftServer;
import fr.math.minecraft.shared.network.Hitbox;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.UUID;

public class DroppedItem {

    private final Vector3f acceleration;
    private final Vector3f velocity;
    private final Vector3f position, lastPosition;
    private final Material material;
    private final String uuid;
    private final Hitbox hitbox;
    private float time;
    private boolean onFloor;
    private float rotationAngle;

    public DroppedItem(String uuid, Vector3f position, Material material) {
        this.uuid = uuid;
        this.position = position;
        this.velocity = new Vector3f();
        this.acceleration = new Vector3f();
        this.lastPosition = new Vector3f(position);
        this.material = material;
        this.time = 0.0f;
        this.onFloor = false;
        this.hitbox = new Hitbox(new Vector3f(), new Vector3f(0.025f, 0.025f, 0.025f));
        this.rotationAngle = 0.0f;
    }

    public DroppedItem(Vector3f position, Material material) {
        this.uuid = UUID.randomUUID().toString();
        this.position = position;
        this.velocity = new Vector3f();
        this.acceleration = new Vector3f();
        this.lastPosition = new Vector3f(position);
        this.material = material;
        this.time = 0.0f;
        this.onFloor = false;
        this.hitbox = new Hitbox(new Vector3f(), new Vector3f(0.025f, 0.025f, 0.025f));
        this.rotationAngle = 0.0f;
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
                        position.y = worldY + hitbox.getHeight() + 1;
                        this.velocity.y = 0;
                        this.onFloor = true;
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

    public void update() {

        Vector3f acceleration = new Vector3f();

        velocity.add(new Vector3f(0, -0.0125f, 0));

        position.x += velocity.x;
        handleCollisions(new Vector3f(velocity.x, 0, 0));

        position.z += velocity.z;
        handleCollisions(new Vector3f(0, 0, velocity.z));

        position.y += velocity.y;
        handleCollisions(new Vector3f(0, velocity.y, 0));

        velocity.add(acceleration);

        if (velocity.length() > 0.2f) {
            velocity.normalize().mul(0.2f);
        }

        time += 1.0f;
        rotationAngle += 1.0f;

        if (rotationAngle > 360.0f) {
            rotationAngle = 0.0f;
        }

        velocity.mul(0.95f);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getLastPosition() {
        return lastPosition;
    }

    public Material getMaterial() {
        return material;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public JsonNode toJSON() {
        ObjectNode node = new ObjectMapper().createObjectNode();

        node.put("type", "DROPPED_ITEM_STATE");
        node.put("uuid", uuid);
        node.put("x", position.x);
        node.put("y", position.y);
        node.put("z", position.z);
        node.put("rotationAngle", rotationAngle);
        node.put("materialID", material.getId());

        return node;
    }

    public String getUuid() {
        return uuid;
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public boolean isOnFloor() {
        return onFloor;
    }
}
