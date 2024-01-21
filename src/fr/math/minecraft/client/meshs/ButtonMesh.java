package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.buffers.EBO;
import fr.math.minecraft.client.buffers.VAO;
import fr.math.minecraft.client.buffers.VBO;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;

public class ButtonMesh extends Mesh {

    private final float[] vertices;
    public static final float BUTTON_WIDTH = 200 * 2;
    public static final float BUTTON_HEIGHT = 20 * 2;

    public ButtonMesh() {
        int x = 0, y = 0;

        this.vertices = new float[] {
            // x, y, u, v (u = texcoords.x, v = texcoords.y)
            x, y, 0.0f, 170.f / 256.0f,
            x, y + BUTTON_HEIGHT, 0.0f, 190.f / 256.0f,
            x + BUTTON_WIDTH, y + BUTTON_HEIGHT, 200.0f / 256.0f, 190.f / 256.0f,
            x + BUTTON_WIDTH, y, 200.0f / 256.0f, 170.f / 256.0f
        };

        this.indices = new int[] {
                0, 1, 2,
                2, 3, 0
        };
        this.init();
    }

    @Override
    public void init() {
        this.vao = new VAO();
        vao.bind();

        this.vbo = new VBO(vertices);
        this.ebo = new EBO(indices);

        vao.linkAttrib(vbo, 0, 2, GL_FLOAT, 4 * Float.BYTES, 0);
        vao.linkAttrib(vbo, 1, 2, GL_FLOAT, 4 * Float.BYTES, 2 * Float.BYTES);

        vao.unbind();
        vbo.unbind();
        ebo.unbind();
    }

    @Override
    public void draw() {
        vao.bind();
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        vao.unbind();
    }
}
