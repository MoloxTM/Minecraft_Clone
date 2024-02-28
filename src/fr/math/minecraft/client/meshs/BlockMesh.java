package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.Shader;
import fr.math.minecraft.client.builder.MeshBuilder;
import fr.math.minecraft.client.vertex.BlockVertex;
import fr.math.minecraft.client.vertex.Vertex;
import fr.math.minecraft.client.buffers.EBO;
import fr.math.minecraft.client.buffers.VAO;
import fr.math.minecraft.client.buffers.VBO;
import fr.math.minecraft.shared.world.Material;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL33.*;

public class BlockMesh extends Mesh {

    private final BlockVertex[] vertices;

    public BlockMesh(Material material) {
        this(material.getX(), material.getY());
    }

    public BlockMesh(int x, int y) {
        super();

        Vector2f[] texCoords = MeshBuilder.calculateTexCoords(x, y, 16.0f);

        Vector2f texCoordsBottomLeft = texCoords[0];
        Vector2f texCoordsUpLeft = texCoords[1];
        Vector2f texCoordsUpRight = texCoords[2];
        Vector2f texCoordsBottomRight = texCoords[3];

        this.vertices = new BlockVertex[] {
                // Face avant
                new BlockVertex(new Vector3f(-0.5f, -0.5f, 0.5f), texCoordsBottomLeft, 0),
                new BlockVertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsUpLeft, 1),
                new BlockVertex(new Vector3f(0.5f, 0.5f, 0.5f), texCoordsUpRight, 2),
                new BlockVertex(new Vector3f(0.5f, -0.5f, 0.5f), texCoordsBottomRight, 3),

                // Face arrière
                new BlockVertex(new Vector3f(-0.5f, -0.5f, -0.5f), texCoordsBottomLeft, 0),
                new BlockVertex(new Vector3f(-0.5f, 0.5f, -0.5f), texCoordsUpLeft, 1),
                new BlockVertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsUpRight, 2),
                new BlockVertex(new Vector3f(0.5f, -0.5f, -0.5f), texCoordsBottomRight, 3),

                // Face gauche
                new BlockVertex(new Vector3f(-0.5f, -0.5f, -0.5f), texCoordsBottomLeft, 0),
                new BlockVertex(new Vector3f(-0.5f, 0.5f, -0.5f), texCoordsUpLeft, 1),
                new BlockVertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsUpRight, 2),
                new BlockVertex(new Vector3f(-0.5f, -0.5f, 0.5f), texCoordsBottomRight, 3),

                // Face droite
                new BlockVertex(new Vector3f(0.5f, -0.5f, 0.5f), texCoordsBottomLeft, 0),
                new BlockVertex(new Vector3f(0.5f, 0.5f, 0.5f), texCoordsUpLeft, 1),
                new BlockVertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsUpRight, 2),
                new BlockVertex(new Vector3f(0.5f, -0.5f, -0.5f), texCoordsBottomRight, 3),

                // Face supérieure
                new BlockVertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsBottomLeft, 0),
                new BlockVertex(new Vector3f(-0.5f, 0.5f, -0.5f), texCoordsUpLeft, 1),
                new BlockVertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsUpRight, 2),
                new BlockVertex(new Vector3f(0.5f, 0.5f, 0.5f), texCoordsBottomRight, 3),

                // Face inférieure
                new BlockVertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsBottomLeft, 0),
                new BlockVertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsUpLeft, 1),
                new BlockVertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsUpRight, 2),
                new BlockVertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsBottomRight, 3)
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


    public BlockMesh() {
        super();

        Vector2f texCoordsBottomLeft = new Vector2f(0.0f, 0.0f);
        Vector2f texCoordsUpLeft = new Vector2f(0.0f, 1.0f);
        Vector2f texCoordsUpRight = new Vector2f(1.0f, 1.0f);
        Vector2f texCoordsBottomRight = new Vector2f(1.0f, 0.0f);

        this.vertices = new BlockVertex[] {
                // Face avant
                new BlockVertex(new Vector3f(-0.5f, -0.5f, 0.5f), texCoordsBottomLeft),
                new BlockVertex(new Vector3f(0.5f, -0.5f, 0.5f), texCoordsUpLeft),
                new BlockVertex(new Vector3f(0.5f, 0.5f, 0.5f), texCoordsUpRight),
                new BlockVertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsBottomRight),

                // Face arrière
                new BlockVertex(new Vector3f(-0.5f, -0.5f, -0.5f), texCoordsBottomLeft),
                new BlockVertex(new Vector3f(0.5f, -0.5f, -0.5f), texCoordsUpLeft),
                new BlockVertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsUpRight),
                new BlockVertex(new Vector3f(-0.5f, 0.5f, -0.5f), texCoordsBottomRight),

                // Face gauche
                new BlockVertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsBottomLeft),
                new BlockVertex(new Vector3f(-0.5f, 0.5f, -0.5f), texCoordsUpLeft),
                new BlockVertex(new Vector3f(-0.5f, -0.5f, -0.5f), texCoordsUpRight),
                new BlockVertex(new Vector3f(-0.5f, -0.5f, 0.5f), texCoordsBottomRight),

                // Face droite
                new BlockVertex(new Vector3f(0.5f, 0.5f, 0.5f), texCoordsBottomLeft),
                new BlockVertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsUpLeft),
                new BlockVertex(new Vector3f(0.5f, -0.5f, -0.5f), texCoordsUpRight),
                new BlockVertex(new Vector3f(0.5f, -0.5f, 0.5f), texCoordsBottomRight),

                // Face supérieure
                new BlockVertex(new Vector3f(-0.5f, 0.5f, 0.5f), texCoordsBottomLeft),
                new BlockVertex(new Vector3f(0.5f, 0.5f, 0.5f), texCoordsUpLeft),
                new BlockVertex(new Vector3f(0.5f, 0.5f, -0.5f), texCoordsUpRight),
                new BlockVertex(new Vector3f(-0.5f, 0.5f, -0.5f), texCoordsBottomRight),

                // Face inférieure
                new BlockVertex(new Vector3f(-0.5f, -0.5f, 0.5f), texCoordsBottomLeft),
                new BlockVertex(new Vector3f(0.5f, -0.5f, 0.5f), texCoordsUpLeft),
                new BlockVertex(new Vector3f(0.5f, -0.5f, -0.5f), texCoordsUpRight),
                new BlockVertex(new Vector3f(-0.5f, -0.5f, -0.5f), texCoordsBottomRight)
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

        vbo = new VBO(vertices);
        ebo = new EBO(indices);

        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, 6 * Float.BYTES, 0);
        vao.linkAttrib(vbo, 1, 2, GL_FLOAT, 6 * Float.BYTES, 3 * Float.BYTES);
        vao.linkAttrib(vbo, 2, 1, GL_FLOAT, 6 * Float.BYTES, 5 * Float.BYTES);

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

    public void update(Shader shader, Material material) {

        int x = material.getX();
        int y = material.getY();

        if (material.getPx() != null) {
            x = material.getPx().x;
            y = material.getPx().y;
        }

        Vector2f[] textureCoords = MeshBuilder.calculateTexCoords(x, y, 16.0f);

        shader.sendVector2f("tex0", textureCoords[0]);
        shader.sendVector2f("tex1", textureCoords[1]);
        shader.sendVector2f("tex2", textureCoords[2]);
        shader.sendVector2f("tex3", textureCoords[3]);
    }
}
