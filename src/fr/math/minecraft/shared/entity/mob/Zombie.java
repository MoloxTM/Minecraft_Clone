package fr.math.minecraft.shared.entity.mob;

import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.Renderer;
import fr.math.minecraft.client.animations.*;
import fr.math.minecraft.client.entity.Ray;
import fr.math.minecraft.client.events.listeners.EntityUpdate;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.PlayerAction;
import fr.math.minecraft.shared.Sprite;
import fr.math.minecraft.shared.entity.Entity;
import fr.math.minecraft.shared.entity.EntityType;
import fr.math.minecraft.shared.inventory.*;
import fr.math.minecraft.shared.network.Hitbox;
import fr.math.minecraft.shared.network.PlayerInputData;
import fr.math.minecraft.shared.world.*;
import org.joml.Math;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.UUID;

public class Zombie extends Entity {

    public Zombie() {
        this(EntityType.ZOMBIE.getName());
    }

    public Zombie(String name) {
        super(UUID.randomUUID().toString(), EntityType.ZOMBIE);
        this.name = name;
        this.hitbox = new Hitbox(new Vector3f(0, 0, 0), new Vector3f(0.25f, 1.0f, 0.25f));
        animations.add(new ZombieWalkAnimation(this));
        this.damage = 1.0f;
        this.gravity = new Vector3f(0, -0.025f, 0);
    }

    @Override
    public void render(Camera camera, Renderer renderer) {
        renderer.render(camera, this);
    }
}
