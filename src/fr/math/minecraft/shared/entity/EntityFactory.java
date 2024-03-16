package fr.math.minecraft.shared.entity;

import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.shared.entity.mob.Zombie;

public class EntityFactory {

    public static Entity createEntity(EntityType entityType) {
        switch (entityType) {
            case PLAYER:
                return new Player(entityType.getName());
            case VILLAGER:
                return new Villager(entityType.getName());
        }
        return null;
    }

}
