package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.buffers.EBO;
import fr.math.minecraft.client.buffers.VAO;
import fr.math.minecraft.client.buffers.VBO;
import org.joml.Vector2f;

import static org.lwjgl.opengl.GL33.*;

public class ImageMesh extends Mesh {

    private float[] vertices;
    private Vector2f[] textureCoords;

    public ImageMesh(float width, float height, float x, float y) {
        this(width, height, x, y, 1.0f);
    }

    public ImageMesh(float width, float height, float x, float y, float scale) {

        width = width * scale;
        height = height * scale;

        this.textureCoords = new Vector2f[] {
            new Vector2f(0.0f, 0.0f),
            new Vector2f(0.0f, 1.0f),
            new Vector2f(1.0f, 1.0f),
            new Vector2f(1.0f, 0.0f),
        };

        this.vertices = new float[] {
            // x, y, u, v (u = texcoords.x, v = texcoords.y)
            x, y, textureCoords[0].x, textureCoords[0].y,
            x, y + height, textureCoords[1].x, textureCoords[1].y,
            x + width, y + height, textureCoords[2].x, textureCoords[2].y,
            x + width, y, textureCoords[3].x, textureCoords[3].y
        };

        this.indices = new int[] {
            0, 1, 2,
            2, 3, 0
        };
        this.init();
    }

    public void getSubImage(float x, float y, float width, float height, float imageWidth, float imageHeight) {

        float textureX = x / imageWidth;
        float textureY = y / imageHeight;

        width = width / imageWidth;
        height = height / imageWidth;

        textureCoords[0] = new Vector2f(textureX, textureY);
        textureCoords[1] = new Vector2f(textureX, textureY + height);
        textureCoords[2] = new Vector2f(textureX + width, textureY + height);
        textureCoords[3] = new Vector2f(textureX + width, textureY);

    }

    public void translate(float x, float y, float width, float height) {
        this.translate(x, y, width, height, 1.0f);
    }

    public void translate(float x, float y, float width, float height, float scale) {

        width = (int) (width * scale);
        height = (int) (height * scale);

        this.vertices = new float[] {
            // x, y, u, v (u = texcoords.x, v = texcoords.y)
            x, y, textureCoords[0].x, textureCoords[0].y,
            x, y + height, textureCoords[1].x, textureCoords[1].y,
            x + width, y + height, textureCoords[2].x, textureCoords[2].y,
            x + width, y, textureCoords[3].x, textureCoords[3].y
        };

        this.indices = new int[] {
            0, 1, 2,
            2, 3, 0
        };

        vbo.bind();
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        vbo.unbind();

        ebo.bind();
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, indices);
        ebo.unbind();
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
