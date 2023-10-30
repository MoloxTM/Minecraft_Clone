package fr.math.minecraft.meshs;

import fr.math.minecraft.Vertex;
import fr.math.minecraft.buffers.VAO;

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
