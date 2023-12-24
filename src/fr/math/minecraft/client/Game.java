package fr.math.minecraft.client;

import fr.math.minecraft.client.packet.ConnectionInitPacket;
import fr.math.minecraft.client.packet.PlayersListPacket;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.World;
import org.lwjgl.opengl.GL;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
public class Game {

    private long window;
    private static Game instance = null;
    private final MinecraftClient client;
    private final Map<String, Player> players;
    private final Player player;

    private Game() {
        this.client = new MinecraftClient(50000);
        this.players = new HashMap<>();
        this.player = new Player(null);
    }

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

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_DEPTH_TEST);

        Camera camera = new Camera(1280.0f, 720.0f);
        World world = new World();

        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        double previousTime = 0.0f;
        int frames = 0;

        client.connect();
        new ConnectionInitPacket(player).send();

        Renderer renderer = new Renderer();

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

            player.handleInputs(window);

            camera.update(player);

            for (Chunk chunk : world.getChunks()) {
                renderer.render(camera, chunk);
            }

            for (Player p : players.values()) {
                renderer.render(camera, p);
            }

            glfwSwapBuffers(window);
            glfwPollEvents();

            new PlayersListPacket().send();
        }
    }

    public static Game getInstance() {
        if (instance == null) {
            System.out.println(instance);
            instance = new Game();
        }
        return instance;
    }

    public MinecraftClient getClient() {
        return this.client;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public Player getPlayer() {
        return player;
    }
}
