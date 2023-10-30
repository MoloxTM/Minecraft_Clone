package fr.math.minecraft;

import fr.math.minecraft.meshs.BlockMesh;
import fr.math.minecraft.world.Chunk;
import org.lwjgl.opengl.GL;

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

        Shader shader = new Shader("res/block.vert", "res/block.frag");

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        Texture texture1 = new Texture("res/terrain.png", 1);
        texture1.load();
        glEnable(GL_DEPTH_TEST);

        Player player = new Player();
        Camera camera = new Camera(1280.0f, 720.0f);
        Chunk chunk = new Chunk(0, 0, 0);

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

            glActiveTexture(GL_TEXTURE1);
            texture1.bind();

            camera.update(player);
            camera.matrix(shader, 0, 0, 0);

            shader.sendInt("uTexture", 1);
            chunk.getChunkMesh().draw();
            texture1.unbind();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
