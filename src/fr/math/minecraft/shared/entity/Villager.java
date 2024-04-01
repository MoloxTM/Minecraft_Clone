package fr.math.minecraft.shared.entity;

import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.Renderer;
import fr.math.minecraft.shared.network.Hitbox;
import org.joml.Vector3f;

import java.util.UUID;
import java.util.Vector;

public class Villager extends Entity {

    public Villager(String name) {
        super(UUID.randomUUID().toString(), EntityType.VILLAGER);
        this.name = name;
        this.hitbox = new Hitbox(new Vector3f(0, 0, 0), new Vector3f(0.25f, 1.0f, 0.25f));
    }

    @Override
    public void render(Camera camera, Renderer renderer) {
        renderer.render(camera, this);
    }
}
