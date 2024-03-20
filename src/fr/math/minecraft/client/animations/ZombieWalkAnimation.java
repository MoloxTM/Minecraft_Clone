package fr.math.minecraft.client.animations;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.Shader;
import fr.math.minecraft.shared.entity.mob.Zombie;
import org.joml.Math;


public class ZombieWalkAnimation extends Animation {

    private float legRotation;
    private final static float HAND_WALKING_ANIMATION_SPEED = 3.0f;
    private final static float MAX_HAND_ROTATION_ANGLE = 35.0f;
    private final Zombie zombie;

    public ZombieWalkAnimation(Zombie zombie) {
        this.legRotation = 0.0f;
        this.zombie = zombie;
    }

    @Override
    public void update() {
        /*
        if(!zombie.isMoving()) {
            legRotation *=0.95;
            return;
        }
         */
        legRotation = Math.sin(Game.getInstance().getTime() * HAND_WALKING_ANIMATION_SPEED) * MAX_HAND_ROTATION_ANGLE;

    }

    @Override
    public void sendUniforms(Shader shader) {
        shader.sendFloat("legRotation", Math.toRadians(legRotation));
    }
}
