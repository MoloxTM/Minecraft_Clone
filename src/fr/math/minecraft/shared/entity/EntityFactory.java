package fr.math.minecraft.shared.entity;

import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.shared.entity.mob.Zombie;
import org.joml.Vector3f;

public class EntityFactory {

    public static Entity createEntity(EntityType entityType) {
        switch (entityType) {
            case PLAYER:
                return new Player(entityType.getName());
            case VILLAGER:
                return new Villager(entityType.getName());
            case ZOMBIE:
                return new Zombie(entityType.getName());
        }
        return null;
    }

}
