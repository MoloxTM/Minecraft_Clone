package fr.math.minecraft.shared.math;

import fr.math.minecraft.server.Client;
import fr.math.minecraft.shared.entity.Entity;
import org.joml.Vector2f;
import static fr.math.minecraft.shared.GameConfiguration.*;

public class PhysicsController {

    public static void infligeKnockback(Entity entity, Vector2f direction) {
        entity.getVelocity().y = KNOCK_BACK_Y;
        entity.getVelocity().x = direction.x * KNOCK_BACK_X;
        entity.getVelocity().z = direction.y * KNOCK_BACK_Z;
        entity.setMaxSpeed(.4f);
    }

    public static void infligeKnockback(Client client, Vector2f direction) {
        client.getVelocity().y = KNOCK_BACK_Y;
        client.getVelocity().x = direction.x * KNOCK_BACK_X;
        client.getVelocity().z = direction.y * KNOCK_BACK_Z;
        client.setMaxSpeed(.4f);
    }

}
