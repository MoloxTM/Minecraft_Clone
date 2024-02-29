package fr.math.minecraft.client;

import fr.math.minecraft.client.animations.HotbarAnimation;
import fr.math.minecraft.client.audio.Sound;
import fr.math.minecraft.client.audio.Sounds;
import fr.math.minecraft.client.entity.Ray;
import fr.math.minecraft.client.entity.player.PlayerAction;
import fr.math.minecraft.client.events.listeners.PlayerListener;
import fr.math.minecraft.client.gui.buttons.BlockButton;
import fr.math.minecraft.client.gui.menus.ConnectionMenu;
import fr.math.minecraft.client.gui.menus.MainMenu;
import fr.math.minecraft.client.gui.menus.Menu;
import fr.math.minecraft.client.handler.PlayerMovementHandler;
import fr.math.minecraft.client.manager.*;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.inventory.ItemStack;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.Coordinates;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.network.PlayerInputData;
import org.apache.log4j.Logger;
import org.joml.Vector3f;
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
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
    private FontManager fontManager;
    private WorldManager worldManager;
    private final static Logger logger = LoggerUtility.getClientLogger(Game.class, LogType.TXT);
    private float splasheScale = GameConfiguration.DEFAULT_SCALE;
    private int scaleFactor = 1;
    private String splash;
    private Renderer renderer;
    private ItemRenderer itemRenderer;
    private MenuManager menuManager;
    private DoubleBuffer mouseXBuffer, mouseYBuffer;
    private boolean debugging, occlusion;
    private int frames, fps, updates;
    private ThreadPoolExecutor chunkMeshLoadingQueue;
    private ThreadPoolExecutor packetPool;
    private Map<Coordinates, Boolean> loadingChunks;
    private Queue<Chunk> pendingMeshs;
    private PlayerMovementHandler playerMovementHandler;
    private double lastPingTime;
    private int tick;
    private GameConfiguration gameConfiguration;
    private List<Chunk> chunkUpdateQueue;

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

        glfwSwapInterval(0);
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
        this.world.buildSpawn();
        this.world.calculateSpawnPosition();
        this.state = GameState.MAIN_MENU;
        this.soundManager = new SoundManager();
        this.menuManager = new MenuManager(this);
        this.worldManager = new WorldManager();
        this.gameConfiguration = new GameConfiguration();
        this.renderer = new Renderer();
        this.itemRenderer = new ItemRenderer();
        this.mouseXBuffer = BufferUtils.createDoubleBuffer(1);
        this.mouseYBuffer = BufferUtils.createDoubleBuffer(1);
        this.debugging = false;
        this.occlusion = true;
        this.frames = 0;
        this.fps = 0;
        this.tick = 1;
        this.chunkMeshLoadingQueue = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        this.packetPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(7);
        this.loadingChunks = new HashMap<>();
        this.fontManager = new FontManager();
        this.pendingMeshs = new LinkedList<>();
        this.playerMovementHandler = new PlayerMovementHandler();
        this.lastPingTime = 0;
        this.chunkUpdateQueue = new ArrayList<>();

        world.buildSpawnMesh();

        player.addEventListener(new PlayerListener(this));

        this.loadSplashText();


        for (Sounds sound : Sounds.values()) {
            soundManager.addSound(sound.getFilePath(), false);
        }

        if (gameConfiguration.isMusicEnabled()) {
            for (Sound sound : soundManager.getAllSounds()) {
                sound.load();
            }
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

        double lastDeltaTime = glfwGetTime();
        double lastFramesTime = glfwGetTime();

        if (gameConfiguration.isMusicEnabled()) {
            soundManager.getRandomMusic().play();
        }

        menuManager.open(MainMenu.class);

        player.getHotbar().addItem(new ItemStack(Material.STONE, 1));
        player.getHotbar().addItem(new ItemStack(Material.DIRT, 1));
        player.getHotbar().addItem(new ItemStack(Material.SAND, 1));
        player.getHotbar().addItem(new ItemStack(Material.GRASS, 1));
        player.getHotbar().addItem(new ItemStack(Material.DIAMOND_SWORD, 1));
        player.getHotbar().addItem(new ItemStack(Material.APPLE, 1));
        player.getHotbar().addItem(new ItemStack(Material.DIAMOND_AXE, 1));

        while (!glfwWindowShouldClose(window)) {
            glClearColor(0.58f, 0.83f, 0.99f, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            double currentTime = glfwGetTime();
            double deltaTime = currentTime - lastDeltaTime;

            if (currentTime - lastFramesTime >= 1) {
                lastFramesTime = currentTime;
                fps = frames;
                frames = 0;
            }

            this.deltaTime = (float) deltaTime;

            updateTimer += deltaTime;

            lastDeltaTime = currentTime;
            this.render(renderer);

            while (updateTimer > GameConfiguration.UPDATE_TICK) {
                this.update();
                updateTimer -= GameConfiguration.UPDATE_TICK;
            }

            frames++;

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

        synchronized (this.getChunkUpdateQueue()) {
            for (Chunk chunk : chunkUpdateQueue) {
                chunk.update();
            }

            chunkUpdateQueue.clear();
        }

        tick++;
        if (tick % 10 == 0) {
            List<PlayerInputData> inputData = new ArrayList<>(player.getInputs());
            List<Vector3i> aimedBlockData = new ArrayList<>(player.getAimedBlocks());

            playerMovementHandler.handle(world, player, new Vector3f(player.getPosition()), inputData, aimedBlockData);

            player.getInputs().clear();
            player.getAimedBlocks().clear();
        }

        player.handleInputs(window);
        this.update(player);

        time += 0.01f;
        synchronized (this.getPlayers()) {
            for (Player player : this.getPlayers().values()) {
                player.update();
            }
        }
    }

    public void update(Player player) {
        player.updatePosition(world);
        player.updateAnimations();
        player.getHand().update(new Vector3f(player.getVelocity()));
        camera.update(player);
        player.getAttackRay().update(camera.getPosition(), camera.getFront(), world, false);
        player.getBuildRay().update(camera.getPosition(), camera.getFront(), world, false);
        player.getMiningAnimation().update(player);

        if (player.getSprite() != null) {
            player.getSprite().update(player);
        }

        if (player.getBuildRay().getAimedChunk() != null && (player.getBuildRay().getAimedBlock() != Material.AIR.getId() || player.getBuildRay().getAimedBlock() != Material.WATER.getId())){
            player.getAimedBlocks().add(player.getBuildRay().getBlockWorldPosition());
        }
    }

    private void render(Renderer renderer) {
        for (Menu menu : menus.values()) {
            if (menu.isOpen()) {
                renderer.renderMenu(camera, menu);
            }
        }

        if (state == GameState.MAIN_MENU) {
            return;
        }

        synchronized (world.getChunks()) {
            for (Chunk chunk : world.getChunks().values()) {

                if (chunk.isEmpty()) continue;
                if (chunk.getMesh() == null) continue;
                if (chunk.isOutOfView(player)) continue;

                if (!chunk.isLoaded()) {
                    continue;
                }

                if (!chunk.getMesh().isInitiated()) {
                    chunk.getMesh().init();
                }

                if (!camera.getFrustrum().isVisible(chunk)) {
                    continue;
                }

                renderer.render(camera, chunk);
            }
            for (Chunk chunk : world.getChunks().values()) {

                if (chunk.isEmpty()) continue;
                if (chunk.getWaterMesh() == null) continue;
                if (chunk.isOutOfView(player)) continue;

                if (!chunk.isLoaded()) {
                    continue;
                }

                if (!chunk.getWaterMesh().isInitiated()) {
                    chunk.getWaterMesh().init();
                }

                if (!camera.getFrustrum().isVisible(chunk)) {
                    continue;
                }

                renderer.renderWater(camera, chunk);
            }
        }

        synchronized (this.getPlayers()) {
            for (Player player : this.getPlayers().values()) {
                if (!player.getNametagMesh().isInitiated()) {
                    player.getNametagMesh().init();
                }
                renderer.render(camera, player);
            }
        }

        ItemStack selectedItem = player.getHotbar().getSelectedItem();
        Ray ray = player.getBuildRay();

        renderer.renderAimedBlock(camera, player.getBuildRay());

        if (player.getAction() == PlayerAction.MINING && ray.getAimedChunk() != null && ray.getAimedBlock() != Material.AIR.getId()) {
            renderer.renderMining(camera, ray.getBlockWorldPosition().x, ray.getBlockWorldPosition().y, ray.getBlockWorldPosition().z, player.getSprite());
        }

        if (selectedItem == null) {
            renderer.renderHand(camera, player.getHand());
        } else {
            if (selectedItem.getMaterial().isItem()) {
                renderer.renderItemInHand(camera, player, selectedItem.getMaterial());
            } else {
                renderer.renderSelectedBlock(camera, player, selectedItem.getMaterial());
            }
        }

        renderer.renderDebugTools(camera, player, fps);
        if (player.getInventory().isOpen()) {
            renderer.renderInventory(camera, player);
        }
        renderer.renderHotbar(camera, player, player.getHotbar());
        renderer.renderCrosshair(camera);
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

    public FontManager getFontManager() {
        return fontManager;
    }

    public Map<Coordinates, Boolean> getLoadingChunks() {
        return loadingChunks;
    }

    public ThreadPoolExecutor getChunkMeshLoadingQueue() {
        return chunkMeshLoadingQueue;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public Queue<Chunk> getPendingMeshs() {
        return pendingMeshs;
    }

    public ThreadPoolExecutor getPacketPool() {
        return packetPool;
    }

    public PlayerMovementHandler getPlayerMovementHandler() {
        return playerMovementHandler;
    }

    public void setLastPingTime(double lastPingTime) {
        this.lastPingTime = lastPingTime;
    }

    public GameConfiguration getGameConfiguration() {
        return gameConfiguration;
    }

    public List<Chunk> getChunkUpdateQueue() {
        return chunkUpdateQueue;
    }
}
