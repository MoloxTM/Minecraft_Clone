package fr.math.minecraft.client.animations;

import fr.math.minecraft.client.Shader;

public class HotbarAnimation extends Animation{

    private float yTranslation;
    private boolean firstPhase, secondPhase, completed;

    public HotbarAnimation() {
        this.yTranslation = 0;
        this.firstPhase = false;
        this.secondPhase = false;
        this.completed = true;
    }

    public void start() {
        firstPhase = true;
        secondPhase = false;
        completed = false;
    }

    public void end() {
        firstPhase = false;
        secondPhase = false;
        completed = true;
    }

    @Override
    public void update() {
        if (completed) {
            return;
        }

        if (firstPhase && !secondPhase) {
            yTranslation -= 0.1f;
            if (yTranslation < -1.0f) {
                secondPhase = true;
                firstPhase = false;
            }
        }

        if (secondPhase && !firstPhase) {
            yTranslation += 0.1f;
            if (yTranslation >= 0.0f) {
                this.end();
            }
        }
    }

    @Override
    public void sendUniforms(Shader shader) {
        shader.sendFloat("yTranslation", yTranslation);
        System.out.println(yTranslation);
    }

    public float getYTranslation() {
        return yTranslation;
    }

    public boolean isCompleted() {
        return completed;
    }
}
