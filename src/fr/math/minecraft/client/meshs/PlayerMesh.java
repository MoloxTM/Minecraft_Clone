package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.vertex.PlayerVertex;
import fr.math.minecraft.client.vertex.Vertex;
import fr.math.minecraft.client.buffers.VAO;
import fr.math.minecraft.client.buffers.VBO;
import fr.math.minecraft.client.meshs.model.PlayerModel;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.opengl.GL33.*;

public class PlayerMesh extends Mesh {

    private PlayerVertex[] vertices;

    public PlayerMesh() {
        this.build();
        this.init();
    }

    private void build() {
        PlayerModel playerModel = new PlayerModel();
        ArrayList<PlayerVertex> vertex = new ArrayList<>();
        this.addFace(vertex, playerModel.PLAYER_HEAD_POS);
        this.addFace(vertex, playerModel.PLAYER_CHEST_POS);
        this.addFace(vertex, playerModel.PLAYER_LEFT_HAND);
        this.addFace(vertex, playerModel.PLAYER_RIGHT_HAND);
        this.addFace(vertex, playerModel.PLAYER_RIGHT_LEG);
        this.addFace(vertex, playerModel.PLAYER_LEFT_LEG);

        vertices = vertex.toArray(new PlayerVertex[0]);
    }

    private void addFace(ArrayList<PlayerVertex> vertices, PlayerVertex[] vertex) {
        vertices.addAll(Arrays.asList(vertex));
    }

    @Override
    public void init() {
        this.vao = new VAO();
        vao.bind();

        vbo = new VBO(vertices);

        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, 6 * Float.BYTES, 0);
        vao.linkAttrib(vbo, 1, 2, GL_FLOAT, 6 * Float.BYTES, 3 * Float.BYTES);
        vao.linkAttrib(vbo, 2, 1, GL_FLOAT, 6 * Float.BYTES, 5 * Float.BYTES);

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
