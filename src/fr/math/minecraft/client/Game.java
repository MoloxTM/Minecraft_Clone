package fr.math.minecraft.client;

import fr.math.minecraft.client.audio.Sound;
import fr.math.minecraft.client.audio.Sounds;
import fr.math.minecraft.client.manager.SoundManager;
import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.packet.ConnectionInitPacket;
import fr.math.minecraft.client.packet.PlayersListPacket;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.tick.TickHandler;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.World;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.joml.Vector3f;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
public class Game {

    private long window;
    private long audioContext, audioDevice;
    private static Game instance = null;
    private MinecraftClient client;
    private Map<String, Player> players;
    private Map<String, Sound> sounds;
    private Player player;
    private World world;
    private Camera camera;
    private float updateTimer;
    private float time;
    private float deltaTime;
    private GameState state;
    private SoundManager soundManager;
    private final static Logger logger = LoggerUtility.getClientLogger(Game.class, LogType.TXT);

    private Game() {
        this.initWindow();
        //this.init();
    }

    public void initWindow() {
        if (!glfwInit()) {
            throw new IllegalStateException("Erreur lors de l'initialisation de GLFW !");
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow((int) GameConfiguration.WINDOW_WIDTH, (int) GameConfiguration.WINDOW_HEIGHT, "Minecraft", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Erreur lors de la crétion de la fenêtre !");
        }

        glfwMakeContextCurrent(window);
        String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        audioDevice = alcOpenDevice(defaultDeviceName);

        int[] attributes = {0};
        audioContext = alcCreateContext(audioDevice, attributes);
        alcMakeContextCurrent(audioContext);

        ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
        ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

        if (!alCapabilities.OpenAL10) {
            assert false : "Audio library not supported!";
        }

        GL.createCapabilities();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void init() {
        this.client = new MinecraftClient(50000);
        this.sounds = new HashMap<>();
        this.players = new HashMap<>();
        this.updateTimer = 0.0f;
        this.camera = new Camera(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
        this.world = new World();
        this.state = GameState.MAIN_MENU;
        this.soundManager = new SoundManager();

        for (Sounds sound : Sounds.values()) {
            soundManager.addSound(sound.getFilePath(), false);
        }

        for (Sound sound : soundManager.getAllSounds()) {
            sound.load();
        }
    }

    public void run() {

        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        double lastTime = glfwGetTime();

        /*
        TickHandler tickHandler = new TickHandler();
        tickHandler.start();

        new ConnectionInitPacket(player).send();
         */

        Renderer renderer = new Renderer();
        int lines = 0;
        String splash = "Miam Miam";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(GameConfiguration.SPLASHES_FILE_PATH));
            while (true) {
                if (!(reader.readLine() != null)) break;
                lines++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(lines != 0) {
            try {
                reader = new BufferedReader(new FileReader(GameConfiguration.SPLASHES_FILE_PATH));
                Random r = new Random();
                int randomLine = r.nextInt(0, lines);
                splash = Files.readAllLines(Paths.get(GameConfiguration.SPLASHES_FILE_PATH)).get(randomLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        soundManager.getRandomMusic().play();

        while (!glfwWindowShouldClose(window)) {
            glClearColor(0.58f, 0.83f, 0.99f, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            double currentTime = glfwGetTime();
            double deltaTime = currentTime - lastTime;

            this.deltaTime = (float) deltaTime;

            updateTimer += deltaTime;

            lastTime = currentTime;

            while (updateTimer > GameConfiguration.UPDATE_TICK) {
                this.update();
                updateTimer -= GameConfiguration.UPDATE_TICK;
                if (state == GameState.PLAYING) {
                    player.handleInputs(window);
                }
            }

            this.render(renderer);

            glfwSwapBuffers(window);
            glfwPollEvents();

        }

        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);
        glfwDestroyWindow(window);
        glfwTerminate();

    }

    private void update() {
        if (state == GameState.MAIN_MENU) {
            player.setYaw(player.getYaw() + .03f);
            camera.update(player);
            return;
        }
        new PlayersListPacket().send();
        camera.update(player);
        time += 0.01f;
        for (Player player : players.values()) {
            player.update();
        }
    }

    private void render(Renderer renderer) {
        if (state == GameState.MAIN_MENU) {
            renderer.renderMainMenu(camera, "Pierre mother love!");
            return;
        }

        synchronized (world.getChunks()) {
            for (Chunk chunk : world.getChunks().values()) {

                if (chunk.getChunkMesh() == null && !chunk.isEmpty()) {
                    chunk.setChunkMesh(new ChunkMesh(chunk));
                }

                if (!chunk.isEmpty()) {
                    renderer.render(camera, chunk);
                }
            }
        }

        for (Player player : players.values()) {
            renderer.render(camera, player);
        }

        renderer.renderText(camera, "Hello, World!", 200, 200, 0xFFFFFF);
    }

    public static Game getInstance() {
        if (instance == null) {
            System.out.println("Game getInstance :"+instance);
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

    public World getWorld() {
        return world;
    }
    public float getTime() {
        return time;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public long getWindow() {
        return window;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

}
