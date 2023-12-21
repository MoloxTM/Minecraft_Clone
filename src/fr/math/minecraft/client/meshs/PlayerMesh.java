package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.Vertex;
import fr.math.minecraft.client.buffers.VAO;
import fr.math.minecraft.client.buffers.VBO;
import fr.math.minecraft.client.meshs.model.BlockModel;
import fr.math.minecraft.client.meshs.model.PlayerModel;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.opengl.GL33.*;

public class PlayerMesh extends Mesh {

    public PlayerMesh() {
        this.build();
        this.init();
    }

    private void build() {
        ArrayList<Vertex> vertex = new ArrayList<>();
        this.addFace(vertex, PlayerModel.PLAYER_HEAD_POS);
        this.addFace(vertex, PlayerModel.PLAYER_CHEST_POS);
        this.addFace(vertex, PlayerModel.PLAYER_LEFT_HAND);

        vertices = vertex.toArray(new Vertex[0]);
    }

    private void addFace(ArrayList<Vertex> vertices, Vertex[] vertex) {
        vertices.addAll(Arrays.asList(vertex));
    }

    @Override
    public void init() {
        this.vao = new VAO();
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
