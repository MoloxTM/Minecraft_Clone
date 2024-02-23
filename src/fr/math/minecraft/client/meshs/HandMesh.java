package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.buffers.EBO;
import fr.math.minecraft.client.buffers.VAO;
import fr.math.minecraft.client.buffers.VBO;
import fr.math.minecraft.client.builder.MeshBuilder;
import fr.math.minecraft.client.vertex.HandVertex;

import static org.lwjgl.opengl.GL33.*;

public class HandMesh extends Mesh {

    private final HandVertex[] vertices;

    public HandMesh() {
        this.vertices = new MeshBuilder().buildHandMesh();
        this.indices = new int[]{ 0, 1, 2, 2, 3, 0, 4, 5, 6, 6, 7, 4 };
        this.init();
    }

    @Override
    public void init() {

        this.vao = new VAO();
        vao.bind();

        this.vbo = new VBO(vertices);
        this.ebo = new EBO(indices);

        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, 5 * Float.BYTES, 0);
        vao.linkAttrib(vbo, 1, 2, GL_FLOAT, 5 * Float.BYTES, 3 * Float.BYTES);

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
