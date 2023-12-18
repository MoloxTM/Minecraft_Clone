package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.Vertex;
import fr.math.minecraft.client.buffers.VAO;

public abstract class Mesh {

    protected Vertex[] vertices;
    protected int[] indices;
    protected VAO vao;

    public Mesh() {
        this.vertices = null;
        this.indices = null;
        this.vao = null;
    }

    public abstract void init();
    public abstract void draw();

    public Vertex[] getVertices() {
        return vertices;
    }

    public VAO getVao() {
        return vao;
    }
}
