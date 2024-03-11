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

    private BlockVertex[] vertices;

    public BlockMesh(Material material) {
        this.vertices = new MeshBuilder().buildBlockMesh(material);
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

        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, 7 * Float.BYTES, 0);
        vao.linkAttrib(vbo, 1, 2, GL_FLOAT, 7 * Float.BYTES, 3 * Float.BYTES);
        vao.linkAttrib(vbo, 2, 1, GL_FLOAT, 7 * Float.BYTES, 5 * Float.BYTES);
        vao.linkAttrib(vbo, 3, 1, GL_FLOAT, 7 * Float.BYTES, 6 * Float.BYTES);

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

        int pxX;
        int nxX;
        int pyX;
        int nyX;
        int pzX;
        int nzX;

        int pxY;
        int nxY;
        int pyY;
        int nyY;
        int pzY;
        int nzY;

        if (!material.isSymetric()) {
            pxX = nxX = pyX = nyX = pzX = nzX = material.getX();
            pxY = nxY = pyY = nyY = pzY = nzY = material.getY();
        } else {
            pxX = material.getPx().x;
            nxX = material.getNx().x;
            pyX = material.getPy().x;
            nyX = material.getNy().x;
            pzX = material.getPz().x;
            nzX = material.getNz().x;

            pxY = material.getPx().y;
            nxY = material.getNx().y;
            pyY = material.getPy().y;
            nyY = material.getNy().y;
            pzY = material.getPz().y;
            nzY = material.getNz().y;
        }

        Vector2f[] texCoordsPx = MeshBuilder.calculateTexCoords(pxX, pxY, 16.0f);
        Vector2f[] texCoordsNx = MeshBuilder.calculateTexCoords(nxX, nxY, 16.0f);
        Vector2f[] texCoordsPy = MeshBuilder.calculateTexCoords(pyX, pyY, 16.0f);
        Vector2f[] texCoordsNy = MeshBuilder.calculateTexCoords(nyX, nyY, 16.0f);
        Vector2f[] texCoordsPz = MeshBuilder.calculateTexCoords(pzX, pzY, 16.0f);
        Vector2f[] texCoordsNz = MeshBuilder.calculateTexCoords(nzX, nzY, 16.0f);

        shader.sendVector2f("texPx0", texCoordsPx[0]);
        shader.sendVector2f("texPx1", texCoordsPx[1]);
        shader.sendVector2f("texPx2", texCoordsPx[2]);
        shader.sendVector2f("texPx3", texCoordsPx[3]);

        shader.sendVector2f("texNx0", texCoordsNx[0]);
        shader.sendVector2f("texNx1", texCoordsNx[1]);
        shader.sendVector2f("texNx2", texCoordsNx[2]);
        shader.sendVector2f("texNx3", texCoordsNx[3]);

        shader.sendVector2f("texPy0", texCoordsPy[0]);
        shader.sendVector2f("texPy1", texCoordsPy[1]);
        shader.sendVector2f("texPy2", texCoordsPy[2]);
        shader.sendVector2f("texPy3", texCoordsPy[3]);

        shader.sendVector2f("texNy0", texCoordsNy[0]);
        shader.sendVector2f("texNy1", texCoordsNy[1]);
        shader.sendVector2f("texNy2", texCoordsNy[2]);
        shader.sendVector2f("texNy3", texCoordsNy[3]);

        shader.sendVector2f("texPz0", texCoordsPz[0]);
        shader.sendVector2f("texPz1", texCoordsPz[1]);
        shader.sendVector2f("texPz2", texCoordsPz[2]);
        shader.sendVector2f("texPz3", texCoordsPz[3]);

        shader.sendVector2f("texNz0", texCoordsNz[0]);
        shader.sendVector2f("texNz1", texCoordsNz[1]);
        shader.sendVector2f("texNz2", texCoordsNz[2]);
        shader.sendVector2f("texNz3", texCoordsNz[3]);
    }
}
