

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.FloatBuffer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
public class Game {

    private long window;

    public void run() {
        if (!glfwInit()) {
            throw new IllegalStateException("Erreur lors de l'initialisation de GLFW !");
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(1280, 720, "Minecraft", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Erreur lors de la crétion de la fenêtre !");
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        Vector2f[] textCoordsDiamondOre = new Texture("", 0).calculateTexCoords(8, 15, 16.0f);

        Vector2f texCoordsBottomLeft = textCoordsDiamondOre[0];
        Vector2f texCoordsUpLeft = textCoordsDiamondOre[1];
        Vector2f texCoordsUpRight = textCoordsDiamondOre[2];
        Vector2f texCoordsBottomRight = textCoordsDiamondOre[3];

        Vertex[] vertices = {
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

        int[] indices = {
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

        Shader shader = new Shader("res/block.vert", "res/block.frag");

        VAO vao = new VAO();
        vao.bind();

        VBO vbo = new VBO(vertices);
        EBO ebo = new EBO(indices);

        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, 5 * Float.BYTES, 0);
        vao.linkAttrib(vbo, 1, 2, GL_FLOAT, 5 * Float.BYTES, 3 * Float.BYTES);

        vao.unbind();
        vbo.unbind();
        ebo.unbind();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        Texture texture = new Texture("res/dirt.png", 0);
        Texture texture1 = new Texture("res/terrain.png", 1);
        texture.load();
        texture1.load();
        glEnable(GL_DEPTH_TEST);

        Player player = new Player();
        Camera camera = new Camera(1280.0f, 720.0f);

        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        double previousTime = 0.0f;
        int frames = 0;

        while (!glfwWindowShouldClose(window)) {
            glClearColor(0.58f, 0.83f, 0.99f, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            double currentTime = glfwGetTime();
            frames++;
            if (currentTime - previousTime >= 1.0) {
                System.out.println("FPS " + frames);
                frames = 0;
                previousTime = currentTime;
            }

            shader.enable();
            player.handleInputs(window);

            for (int x = 0; x < 20; x++) {
                for (int y = 0; y < 20; y++) {
                    for (int z = 0; z < 20; z++) {
                        vao.bind();
                        glActiveTexture(GL_TEXTURE0 + texture1.getSlot());
                        texture1.bind();

                        camera.update(player);
                        camera.matrix(shader, x, y, z);

                        shader.sendInt("uTexture", texture1.getSlot());
                        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
                        vao.unbind();

                        texture1.unbind();
                    }
                }
            }

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
