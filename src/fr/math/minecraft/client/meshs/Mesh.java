package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.buffers.EBO;
import fr.math.minecraft.client.buffers.VBO;
import fr.math.minecraft.client.vertex.Vertex;
import fr.math.minecraft.client.buffers.VAO;

public abstract class Mesh {

    protected Vertex[] vertices;
    protected int[] indices;
    protected VAO vao;
    protected VBO vbo;
    protected EBO ebo;

    public Mesh() {
        this.vertices = null;
        this.indices = null;
        this.vbo = null;
        this.vao = null;
    }

    public abstract void init();
    public abstract void draw();
    public void delete() {
        if (vao != null) vao.delete();
        if (vbo != null) vbo.delete();
        if (ebo != null) ebo.delete();
    }

    public VAO getVAO() {
        return vao;
    }

    public VBO getVBO() {
        return vbo;
    }

    public EBO getEBO() {
        return ebo;
    }
}
