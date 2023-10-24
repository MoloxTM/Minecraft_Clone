

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

        Vector2f[] textCoordsDiamondOre = new Texture("", 0).calculateTexCoords(2, 12, 16.0f);

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

        float angle = 0.0f;

        float z = 10.0f;
        float x = 0.0f;
        float y = 0.0f;

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        Texture texture = new Texture("res/dirt.png", 0);
        Texture texture1 = new Texture("res/terrain.png", 1);
        texture.load();
        texture1.load();
        glEnable(GL_DEPTH_TEST);

        FloatBuffer modelBuffer = BufferUtils.createFloatBuffer(16);
        FloatBuffer viewBuffer = BufferUtils.createFloatBuffer(16);
        FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);

        Player player = new Player();

        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        while (!glfwWindowShouldClose(window)) {
            glClearColor(0.58f, 0.83f, 0.99f, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            shader.enable();

            Matrix4f projection = new Matrix4f().identity();
            Matrix4f view = new Matrix4f().identity();
            Matrix4f model = new Matrix4f().identity();

            player.handleInputs(window);

            player.getFront().x = (float) (cos(Math.toRadians(player.getYaw())) * cos(Math.toRadians(player.getPitch())));
            player.getFront().y = (float) sin(Math.toRadians(player.getPitch()));
            player.getFront().z = (float) (sin(Math.toRadians(player.getYaw())) * cos(Math.toRadians(player.getPitch())));; // Cela affichera (0.0, 0.0, 3.0)

            player.setFront(player.getFront().normalize());

            Vector3f test = new Vector3f();
            test.x = player.getPosition().x + player.getFront().x;
            test.y = player.getPosition().y + player.getFront().y;
            test.z = player.getPosition().z + player.getFront().z;

            view.lookAt(
                    player.getPosition(),
                    test,
                    new Vector3f(0.0f, 1.0f, 0.0f)
            );


            projection = projection.perspective((float) Math.toRadians(45.0f), 1280.0f / 720.0f, 0.1f, 100.0f);
            /*
            view = view.lookAt(
                    new Vector3f(x, y, z),
                    new Vector3f(x, y, 0.0f),
                    new Vector3f(0.0f, 1.0f, 0.0f)
            );
            */
            model.rotate(angle, 1.0f, 0.0f, 0.0f);

            projection.get(projectionBuffer);
            view.get(viewBuffer);
            model.get(modelBuffer);

            glUniformMatrix4fv(glGetUniformLocation(shader.getId(), "projection"), false, projectionBuffer);
            glUniformMatrix4fv(glGetUniformLocation(shader.getId(), "view"), false, viewBuffer);

            angle += 0.1f;
            if (angle > 360.0f) {
                angle = 0.0f;
            }

            vao.bind();

            for (int blockX = 0; blockX < 10; blockX++) {
                for (int blockZ = 0; blockZ < 10; blockZ++) {
                    model = model.translate(blockX, 0, blockZ);
                    model.get(modelBuffer);
                    glUniformMatrix4fv(glGetUniformLocation(shader.getId(), "model"), false, modelBuffer);
                    glActiveTexture(GL_TEXTURE0 + texture1.getSlot());
                    texture1.bind();
                    glUniform1i(glGetUniformLocation(shader.getId(), "uTexture"), texture1.getSlot());
                    glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
                    texture1.unbind();
                    model = model.identity();
                }
            }

            vao.unbind();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
