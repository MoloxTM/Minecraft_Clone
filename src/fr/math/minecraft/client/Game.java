package fr.math.minecraft.client;

import fr.math.minecraft.client.audio.Sound;
import fr.math.minecraft.client.audio.Sounds;
import fr.math.minecraft.client.gui.buttons.BlockButton;
import fr.math.minecraft.client.gui.menus.ConnectionMenu;
import fr.math.minecraft.client.gui.menus.MainMenu;
import fr.math.minecraft.client.gui.menus.Menu;
import fr.math.minecraft.client.manager.MenuManager;
import fr.math.minecraft.client.manager.SoundManager;
import fr.math.minecraft.client.meshs.ChunkMesh;
import fr.math.minecraft.client.packet.PlayersListPacket;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.World;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.joml.Vector3i;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import java.io.*;
import java.nio.DoubleBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private Map<Class<? extends Menu>, Menu> menus;
    private Player player;
    private World world;
    private Camera camera;
    private float updateTimer;
    private float time;
    private float deltaTime;
    private GameState state;
    private SoundManager soundManager;
    private final static Logger logger = LoggerUtility.getClientLogger(Game.class, LogType.TXT);
    private float splasheScale = GameConfiguration.DEFAULT_SCALE;
    private int scaleFactor = 1;
    private String splash;
    private Renderer renderer;
    private MenuManager menuManager;
    private DoubleBuffer mouseXBuffer, mouseYBuffer;
    private boolean debugging;

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
        this.menus = new HashMap<>();
        this.updateTimer = 0.0f;
        this.camera = new Camera(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
        this.world = new World();
        this.state = GameState.MAIN_MENU;
        this.soundManager = new SoundManager();
        this.menuManager = new MenuManager(this);
        this.renderer = new Renderer();
        this.mouseXBuffer = BufferUtils.createDoubleBuffer(1);
        this.mouseYBuffer = BufferUtils.createDoubleBuffer(1);
        this.debugging = false;

        this.loadSplashText();

        for (Sounds sound : Sounds.values()) {
            soundManager.addSound(sound.getFilePath(), false);
        }

        for (Sound sound : soundManager.getAllSounds()) {
            sound.load();
        }

        Menu mainMenu = new MainMenu(this);
        Menu connectionMenu = new ConnectionMenu(this);

        menuManager.registerMenu(mainMenu);
        menuManager.registerMenu(connectionMenu);
    }

    private void loadSplashText() {
        try {
            int lines = 0;
            BufferedReader reader = new BufferedReader(new FileReader(GameConfiguration.SPLASHES_FILE_PATH));
            while (reader.readLine() != null) {
                lines++;
            }
            if (lines == 0) {
                this.splash = "Miam Miam";
                return;
            }
            Random r = new Random();
            int randomLine = r.nextInt(0, lines);
            this.splash = Files.readAllLines(Paths.get(GameConfiguration.SPLASHES_FILE_PATH)).get(randomLine);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {

        double lastTime = glfwGetTime();

        soundManager.getRandomMusic().play();

        menuManager.open(MainMenu.class);

        PlayersListPacket playersListPacket = new PlayersListPacket();

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
                    playersListPacket.send();
                }
            }

            this.render(renderer);

            glfwSwapBuffers(window);
            glfwPollEvents();

        }

        MemoryUtil.memFree(mouseXBuffer);
        MemoryUtil.memFree(mouseYBuffer);

        alcDestroyContext(audioContext);
        alcCloseDevice(audioDevice);
        glfwDestroyWindow(window);
        glfwTerminate();

    }

    private void update() {
        for (Menu menu : menus.values()) {
            if (!menu.isOpen()) continue;

            menu.update();

            for (BlockButton button : menu.getButtons()) {
                glfwGetCursorPos(window, mouseXBuffer, mouseYBuffer);
                button.handleInputs(window, mouseXBuffer.get(), mouseYBuffer.get());

                mouseXBuffer.rewind();
                mouseYBuffer.rewind();
            }
        }
        if (state == GameState.MAIN_MENU) {
            return;
        }
        camera.update(player);
        time += 0.01f;
        for (Player player : players.values()) {
            player.update();
        }
    }

    private void render(Renderer renderer) {
        for (Menu menu : menus.values()) {
            if (menu.isOpen()) {
                renderer.renderMenu(camera, menu);
            }
        }
        if (state == GameState.MAIN_MENU) {
            // renderer.renderMainMenu(camera, splash, splasheScale);
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

        if (isDebugging()) {
            renderer.renderDebugTools(camera, player);
        }
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

    public Map<Class<? extends Menu>, Menu> getMenus() {
        return this.menus;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public String getSplashText() {
        return splash;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public boolean isDebugging() {
        return debugging;
    }

    public void setDebugging(boolean debugging) {
        this.debugging = debugging;
    }
}
