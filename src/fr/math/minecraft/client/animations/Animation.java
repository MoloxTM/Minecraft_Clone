package fr.math.minecraft.client.animations;

import fr.math.minecraft.client.Shader;

public abstract class Animation {

    public abstract void update();
    public abstract void sendUniforms(Shader shader);

}
