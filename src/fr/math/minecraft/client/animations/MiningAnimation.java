package fr.math.minecraft.client.animations;

import fr.math.minecraft.client.Shader;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.shared.PlayerAction;

public class MiningAnimation extends Animation {

    private final float MINING_ANIMATION_SPEED = 10.0f;
    private float tick;
    private float rotation;
    private boolean rotatingForward, rotatingBackward;

    public MiningAnimation() {
        this.tick = 0;
        this.rotation = 0;
        this.rotatingForward = true;
        this.rotatingBackward = false;
    }

    public void update(Player player) {
        if (player.getAction() != PlayerAction.MINING) {
            tick *= 0.75f;
            rotation *= 0.75f;
            return;
        }
        tick += 0.01f;
        if (rotatingForward) {
            rotation += 5.0f;
            if (rotation > 120.0f) {
                rotatingBackward = true;
                rotatingForward = false;
            }
        } else {
            rotation -= 5.0f;
            if (rotation < 0.0f) {
                rotatingBackward = false;
                rotatingForward = true;
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void sendUniforms(Shader shader) {
        shader.sendFloat("rotationAngleX", rotation);
    }

    public float getRotation() {
        return rotation;
    }

    public float getTick() {
        return tick;
    }
}
