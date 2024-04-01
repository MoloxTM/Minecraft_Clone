package fr.math.minecraft.client.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.math.minecraft.client.audio.Sounds;
import fr.math.minecraft.client.events.listeners.EntityUpdate;
import fr.math.minecraft.client.manager.SoundManager;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.server.pathfinding.Node;
import fr.math.minecraft.server.pathfinding.Pattern;
import fr.math.minecraft.shared.entity.Entity;
import fr.math.minecraft.shared.entity.EntityFactory;
import fr.math.minecraft.shared.entity.EntityType;
import fr.math.minecraft.shared.world.World;
import org.apache.log4j.Logger;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class EntityStateHandler implements Runnable {

    private final JsonNode entityData;
    private final World world;
    private final static Logger logger = LoggerUtility.getClientLogger(EntityStateHandler.class, LogType.TXT);

    public EntityStateHandler(World world, JsonNode entityData) {
        this.entityData = entityData;
        this.world = world;
    }

    @Override
    public void run() {
        String uuid = entityData.get("uuid").asText();
        String entityTypeValue = entityData.get("entityType").asText();
        Entity entity = world.getEntities().get(uuid);
        String entityName = entityData.get("name").asText();
        String lastAttackerID = entityData.get("lastAttacker").asText();
        String lastAttackerTypeValue = entityData.get("lastAttackerType").asText();
        float worldX = entityData.get("x").floatValue();
        float worldY = entityData.get("y").floatValue();
        float worldZ = entityData.get("z").floatValue();
        float health = entityData.get("health").asInt();
        float maxHealth = entityData.get("maxHealth").asInt();
        float yaw = entityData.get("yaw").floatValue();
        float pitch = entityData.get("pitch").floatValue();
        float bodyYaw = entityData.get("bodyYaw").floatValue();
        ArrayNode pathArrayNode = (ArrayNode) entityData.get("path");
        Vector3f worldPosition = new Vector3f(worldX, worldY, worldZ);
        try {
            EntityType entityType = EntityType.valueOf(entityTypeValue);
            if (entity == null) {
                entity = EntityFactory.createEntity(entityType);
                if (entity == null) {
                    return;
                }
                entity.setYaw(yaw);
                entity.setPitch(pitch);
                entity.setBodyYaw(yaw);
                entity.setUuid(uuid);
                entity.setName(entityName);
                entity.setPosition(worldPosition);

                world.addEntity(entity);
            } else {
                EntityUpdate entityUpdate = new EntityUpdate(worldPosition, yaw, pitch, bodyYaw);
                entity.setLastUpdate(entityUpdate);
            }
            List<Node> path = new ArrayList<>();
            for (int i = 0; i < pathArrayNode.size(); i++) {
                int x = pathArrayNode.get(i).get("x").asInt();
                int y = pathArrayNode.get(i).get("y").asInt();
                Vector2i position = new Vector2i(x, y);
                Node node = new Node(position);
                path.add(node);
            }

            Pattern pattern = new Pattern(path, null, null);
            entity.setPattern(pattern);
            if (!lastAttackerID.equals("NONE")) {
                if (health < entity.getHealth()) {
                    entity.setHitMarkDelay(20);
                    SoundManager soundManager = SoundManager.getInstance();
                    soundManager.play(Sounds.HIT);
                }
            }
            entity.setHealth(health);
            entity.setMaxHealth(maxHealth);

            if (entity.getHealth() <= 0.0f) {
                synchronized (world.getEntities()) {
                    world.getEntities().remove(entity.getUuid());
                    logger.info(entity.getType() + " est mort !");
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            logger.error("Le type d'entité : " + entityTypeValue + " est inconnu et a été ignoré.");
        }
    }
}
