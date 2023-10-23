

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.nio.FloatBuffer;

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

        Vector2f texCoordsBottomLeft = new Vector2f(0.0f, 0.0f);
        Vector2f texCoordsUpLeft = new Vector2f(0.0f, 1.0f);
        Vector2f texCoordsUpRight = new Vector2f(1.0f, 1.0f);
        Vector2f texCoordsBottomRight = new Vector2f(1.0f, 0.0f);

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

        Texture texture = new Texture("res/dirt.png");
        texture.load();
        glEnable(GL_DEPTH_TEST);

        while (!glfwWindowShouldClose(window)) {
            glClearColor(0.58f, 0.83f, 0.99f, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            shader.enable();

            Matrix4f projection = new Matrix4f().identity();
            Matrix4f view = new Matrix4f().identity();
            Matrix4f model = new Matrix4f().identity();

            if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) {
                z -= 0.01f;
            }

            if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) {
                z += 0.01f;
            }

            if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
                x -= 0.01f;
            }

            if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
                x += 0.01f;
            }

            if (glfwGetKey(window, GLFW_KEY_U) == GLFW_PRESS) {
                y += 0.01f;
            }

            if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
                y -= 0.01f;
            }

            projection = projection.perspective((float) Math.toRadians(45.0f), 1280.0f / 720.0f, 0.1f, 100.0f);
            view = view.lookAt(
                    new Vector3f(x, y, z),
                    new Vector3f(x, y, 0.0f),
                    new Vector3f(0.0f, 1.0f, 0.0f)
            );

            model.rotate(angle, 1.0f, 0.0f, 0.0f);

            FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);
            projection.get(projectionBuffer);

            FloatBuffer viewBuffer = BufferUtils.createFloatBuffer(16);
            view.get(viewBuffer);

            FloatBuffer modelBuffer = BufferUtils.createFloatBuffer(16);
            model.get(modelBuffer);

            glUniformMatrix4fv(glGetUniformLocation(shader.getId(), "projection"), false, projectionBuffer);
            glUniformMatrix4fv(glGetUniformLocation(shader.getId(), "view"), false, viewBuffer);
            glUniformMatrix4fv(glGetUniformLocation(shader.getId(), "model"), false, modelBuffer);

            angle += 0.001f;
            if (angle > 360.0f) {
                angle = 0.0f;
            }
            texture.bind();
            glUniform1i(glGetUniformLocation(shader.getId(), "uTexture"), 0);
            vao.bind();
            glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
            texture.unbind();
            vao.unbind();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
