

import org.joml.Matrix4f;
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

        Vertex[] vertices = {
                new Vertex(-0.5f, 0.5f, 0),
                new Vertex(0.5f, 0.5f, 0),
                new Vertex(0.5f, -0.5f, 0),
                new Vertex(-0.5f, -0.5f, 0),
        };

        int[] indices = {
                0, 1, 2,
                0, 2, 3
        };

        Shader shader = new Shader("res/block.vert", "res/block.frag");

        VAO vao = new VAO();
        vao.bind();

        VBO vbo = new VBO(vertices);
        EBO ebo = new EBO(indices);

        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, 3 * 4, 0);

        vao.unbind();
        vbo.unbind();
        ebo.unbind();

        float angle = 0.0f;

        float z = 10.0f;

        while (!glfwWindowShouldClose(window)) {
            glClearColor(0.58f, 0.83f, 0.99f, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            shader.enable();

            Matrix4f projection = new Matrix4f().identity();
            Matrix4f view = new Matrix4f().identity();
            Matrix4f model = new Matrix4f().identity();

            if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) {
                z -= 0.05f;
            }

            if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) {
                z += 0.05f;
            }

            projection = projection.perspective((float) Math.toRadians(45.0f), 1280.0f / 720.0f, 0.1f, 100.0f);
            view = view.lookAt(
                    new Vector3f(0.0f, 0.0f, z),
                    new Vector3f(0.0f, 0.0f, 0.0f),
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

            angle += 0.01f;
            if (angle > 360.0f) {
                angle = 0.0f;
            }

            vao.bind();
            glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
            vao.unbind();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
