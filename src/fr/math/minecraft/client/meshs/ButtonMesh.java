package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.buffers.EBO;
import fr.math.minecraft.client.buffers.VAO;
import fr.math.minecraft.client.buffers.VBO;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class ButtonMesh extends Mesh {

    private float[] vertices;
    public static final float BUTTON_WIDTH = 200 * 2;
    public static final float BUTTON_HEIGHT = 20 * 2;
    private final FloatBuffer buffer;
    private float x, y;

    public ButtonMesh() {
        this.x = 0;
        this.y = 0;
        this.buffer = BufferUtils.createFloatBuffer(4 * 4);
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

    public void hover() {
        this.vertices = new float[] {
            // x, y, u, v (u = texcoords.x, v = texcoords.y)
            x, y, 0.0f, 150.f / 256.0f,
            x, y + BUTTON_HEIGHT, 0.0f, 170.f / 256.0f,
            x + BUTTON_WIDTH, y + BUTTON_HEIGHT, 200.0f / 256.0f, 170.f / 256.0f,
            x + BUTTON_WIDTH, y, 200.0f / 256.0f, 150.f / 256.0f
        };
        vbo.bind();
        vbo.bufferFloat(buffer, GL_STATIC_DRAW);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        vbo.unbind();
    }

    public void unhover() {
        this.vertices = new float[] {
            // x, y, u, v (u = texcoords.x, v = texcoords.y)
            x, y, 0.0f, 170.f / 256.0f,
            x, y + BUTTON_HEIGHT, 0.0f, 190.f / 256.0f,
            x + BUTTON_WIDTH, y + BUTTON_HEIGHT, 200.0f / 256.0f, 190.f / 256.0f,
            x + BUTTON_WIDTH, y, 200.0f / 256.0f, 170.f / 256.0f
        };
        vbo.bind();
        vbo.bufferFloat(buffer, GL_STATIC_DRAW);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        vbo.unbind();
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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
