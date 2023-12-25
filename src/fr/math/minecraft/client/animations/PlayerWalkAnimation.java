package fr.math.minecraft.client.animations;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.Shader;
import fr.math.minecraft.client.entity.Player;

public class PlayerWalkAnimation extends Animation {

    private float handRotation;
    private String state = "FORWARD";
    private final Player player;
    private final static float HAND_WALKING_ANIMATION_SPEED = 3.0f;
    private final static float MAX_HAND_ROTATION_ANGLE = 20.0f;

    public PlayerWalkAnimation(Player player) {
        this.handRotation = 0.0f;
        this.player = player;
    }

    @Override
    public void update() {
        if (!player.isMoving()) {
            handRotation *= 0.95f;
            return;
        }

        handRotation = (float) Math.sin(Game.getInstance().getTime() * HAND_WALKING_ANIMATION_SPEED) * MAX_HAND_ROTATION_ANGLE;

    }

    @Override
    public void sendUniforms(Shader shader) {
        shader.sendFloat("handRotation", (float) Math.toRadians(handRotation));
    }

}
