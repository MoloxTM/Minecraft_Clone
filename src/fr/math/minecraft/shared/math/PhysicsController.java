package fr.math.minecraft.shared.math;

import fr.math.minecraft.server.Client;
import fr.math.minecraft.shared.entity.Entity;
import org.joml.Vector2f;
public class PhysicsController {

    public static void infligeKnockback(Entity entity, Vector2f direction) {
        entity.getVelocity().y = 0.14f;
        entity.getVelocity().x = direction.x * 0.125f;
        entity.getVelocity().z = direction.y * 0.125f;
        entity.setMaxSpeed(.4f);
    }

    public static void infligeKnockback(Client client, Vector2f direction) {
        client.getVelocity().y = 0.14f;
        client.getVelocity().x = direction.x * 0.125f;
        client.getVelocity().z = direction.y * 0.125f;
        client.setMaxSpeed(.4f);
    }

}
