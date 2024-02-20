package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.buffers.EBO;
import fr.math.minecraft.client.buffers.VAO;
import fr.math.minecraft.client.buffers.VBO;

import static org.lwjgl.opengl.GL33.*;

public class ImageMesh extends Mesh {

    private final float[] vertices;

    public ImageMesh(float width, float height, float x, float y) {
        this(width, height, x, y, 1.0f);
    }

    public ImageMesh(float width, float height, float x, float y, float scale) {

        width = width * scale;
        height = height * scale;

        this.vertices = new float[] {
            // x, y, u, v (u = texcoords.x, v = texcoords.y)
            x, y, 0.0f, 0.0f,
            x, y + height, 0.0f, 1.0f,
            x + width, y + height, 1.0f, 1.0f,
            x + width, y, 1.0f, 0.0f
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
