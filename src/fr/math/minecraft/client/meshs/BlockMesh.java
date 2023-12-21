package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.vertex.Vertex;
import fr.math.minecraft.client.buffers.EBO;
import fr.math.minecraft.client.buffers.VAO;
import fr.math.minecraft.client.buffers.VBO;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL33.*;

public class BlockMesh extends Mesh {

    public BlockMesh(int x, int y) {
        super();
        Vector2f[] textCoordsDiamondOre = MeshBuilder.calculateTexCoords(x, y, 16.0f);

        Vector2f texCoordsBottomLeft = textCoordsDiamondOre[0];
        Vector2f texCoordsUpLeft = textCoordsDiamondOre[1];
        Vector2f texCoordsUpRight = textCoordsDiamondOre[2];
        Vector2f texCoordsBottomRight = textCoordsDiamondOre[3];
        this.vertices = new Vertex[] {
                // Face avant
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f), texCoordsBottomLeft),
                new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), texCoordsUpLeft),
                new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), texCoordsUpRight),
                new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsBottomRight),

                // Face arrière
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), texCoordsBottomLeft),
                new Vertex(new Vector3f(0.5f, -0.5f, -0.5f), texCoordsUpLeft),
                new Vertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsUpRight),
                new Vertex(new Vector3f(-0.5f, 0.5f, -0.5f), texCoordsBottomRight),

                // Face gauche
                new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsBottomLeft),
                new Vertex(new Vector3f(-0.5f, 0.5f, -0.5f), texCoordsUpLeft),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), texCoordsUpRight),
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f), texCoordsBottomRight),

                // Face droite
                new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), texCoordsBottomLeft),
                new Vertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsUpLeft),
                new Vertex(new Vector3f(0.5f, -0.5f, -0.5f), texCoordsUpRight),
                new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), texCoordsBottomRight),

                // Face supérieure
                new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsBottomLeft),
                new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), texCoordsUpLeft),
                new Vertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsUpRight),
                new Vertex(new Vector3f(-0.5f, 0.5f, -0.5f), texCoordsBottomRight),

                // Face inférieure
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f), texCoordsBottomLeft),
                new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), texCoordsUpLeft),
                new Vertex(new Vector3f(0.5f, -0.5f, -0.5f), texCoordsUpRight),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), texCoordsBottomRight)
        };

        this.indices = new int[] {
                // Face avant
                0, 1, 2,
                2, 3, 0,

                // Face arrière
                4, 5, 6,
                6, 7, 4,

                // Face gauche
                8, 9, 10,
                10, 11, 8,

                // Face droite
                12, 13, 14,
                14, 15, 12,

                // Face supérieure
                16, 17, 18,
                18, 19, 16,

                // Face inférieure
                20, 21, 22,
                22, 23, 20
        };
        this.init();
    }

    @Override
    public void init() {
        this.vao = new VAO();
        vao.bind();

        VBO vbo = new VBO(vertices);
        EBO ebo = new EBO(indices);

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
