package fr.math.minecraft.client.handler;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.events.listeners.EntityUpdate;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.shared.entity.Entity;
import fr.math.minecraft.shared.entity.EntityFactory;
import fr.math.minecraft.shared.entity.EntityType;
import fr.math.minecraft.shared.world.World;
import org.apache.log4j.Logger;
import org.joml.Vector3f;

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
        int worldX = entityData.get("x").asInt();
        int worldY = entityData.get("y").asInt();
        int worldZ = entityData.get("z").asInt();
        float health = entityData.get("health").asInt();
        float maxHealth = entityData.get("maxHealth").asInt();
        float yaw = entityData.get("yaw").floatValue();
        float pitch = entityData.get("pitch").floatValue();
        float bodyYaw = entityData.get("bodyYaw").floatValue();
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
            entity.setHealth(health);
            entity.setMaxHealth(maxHealth);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            logger.error("Le type d'entité : " + entityTypeValue + " est inconnu et a été ignoré.");
        }
    }
}
