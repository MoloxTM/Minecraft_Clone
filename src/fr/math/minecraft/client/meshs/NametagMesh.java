package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.buffers.EBO;
import fr.math.minecraft.client.buffers.VAO;
import fr.math.minecraft.client.buffers.VBO;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL33.*;

public class NametagMesh extends Mesh {

    private final float width;
    private final float[] vertices;
    private boolean initiated;

    public NametagMesh(String text) {
        this.width = text.length() * 0.25f;

        this.vertices = new float[] {
            -0.5f + 0.5f - width / 2.0f, -0.5f, 0.0f,
            -0.5f + 0.5f - width / 2.0f, -0.2f, 0.0f,
            -0.5f + width + 0.5f - width / 2.0f, -0.2f, 0.0f,
            -0.5f + width + 0.5f - width / 2.0f, -0.5f, 0.0f,
        };

        this.indices = new int[] {
            0, 1, 2,
            2, 3, 0
        };
        initiated = false;
        // this.init();
    }

    @Override
    public void init() {
        this.vao = new VAO();
        vao.bind();

        vbo = new VBO(vertices);
        ebo = new EBO(indices);

        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, 3 * Float.BYTES, 0);

        vao.unbind();
        vbo.unbind();
        ebo.unbind();

        initiated = true;
    }

    @Override
    public void draw() {
        vao.bind();
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        vao.unbind();
    }

    public boolean isInitiated() {
        return initiated;
    }
}
