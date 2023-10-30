package fr.math.minecraft.meshs;

import fr.math.minecraft.buffers.VAO;
import fr.math.minecraft.buffers.VBO;
import fr.math.minecraft.world.Chunk;

import static org.lwjgl.opengl.GL33.*;

public class ChunkMesh extends Mesh {

    public ChunkMesh(Chunk chunk) {
        vertices = MeshBuilder.buildChunkMesh(chunk);
        this.init();
    }

    @Override
    public void init() {
        vao = new VAO();
        vao.bind();

        VBO vbo = new VBO(vertices);

        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, 5 * Float.BYTES, 0);
        vao.linkAttrib(vbo, 1, 2, GL_FLOAT, 5 * Float.BYTES, 3 * Float.BYTES);

        vao.unbind();
        vbo.unbind();
    }

    @Override
    public void draw() {
        vao.bind();
        glDrawArrays(GL_TRIANGLES, 0, vertices.length);
        vao.unbind();
    }
}
