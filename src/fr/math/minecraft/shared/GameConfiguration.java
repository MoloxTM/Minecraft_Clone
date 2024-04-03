package fr.math.minecraft.shared;

public class GameConfiguration {

    public final static float UPS = 200.0f;
    public final static float TICK_PER_SECONDS = 20.0f;
    public final static float TICK_RATE = 1.0f / TICK_PER_SECONDS;
    public final static float UPDATE_TICK = 1.0f / UPS;
    public final static float WINDOW_WIDTH = 854.0f;
    public final static float WINDOW_HEIGHT = 480.0f;
    public final static float WINDOW_CENTER_X = WINDOW_WIDTH / 2.0f;
    public final static float WINDOW_CENTER_Y = WINDOW_HEIGHT / 2.0f;
    public final static String FONT_FILE_PATH = "res/fonts/Monocraft.ttf";
    public final static int FONT_SIZE = 64;
    public final static float NAMETAG_FONT_SIZE = 32.0f;
    public final static int CHUNK_RENDER_DISTANCE = 6;
    public final static String SPLASHES_FILE_PATH = "res/splashes.txt";
    public final static float DEFAULT_SCALE = 0.28f;
    public final static float MENU_TITLE_SCALE = 0.3f;
    public final static int BUFFER_SIZE = 1024;
    public final static float CHUNK_TICK = 60.0f;
    public final static float CHUNK_TICK_RATE = 1000.0f / CHUNK_TICK;
    public final static float ATTACK_REACH = 3.5f;
    public final static float BUILDING_REACH = 4.5f;
    public final static float BREAKING_REACH = 4.5f;
    public final static float DEFAULT_SPEED= 0.0125f;
    public final static float SPRINT_SPEED = DEFAULT_SPEED * 2f ;
    public final static int BLOCK_BREAK_COOLDOWN = (int) UPS / 3;
    public final static int REGION_SIZE = 8;
    public final static float INVENTORY_TEXTURE_WIDTH = 256.0f;
    public final static float INVENTORY_TEXTURE_HEIGHT = 256.0f;
    public final static int PLAYER_INVENTORY_SIZE = 27;
    public final static float KNOCK_BACK_X = 0.03f;
    public final static float KNOCK_BACK_Y = 0.14f;
    public final static float KNOCK_BACK_Z = 0.03f;

    private boolean entityInterpolation;
    private boolean occlusionEnabled;
    private boolean debugging;
    private boolean musicEnabled;
    private float guiScale;
    private boolean entitesPathEnabled;
    private static GameConfiguration instance = null;

    private GameConfiguration() {
        this.entityInterpolation = true;
        this.occlusionEnabled = true;
        this.debugging = true;
        this.musicEnabled = true;
        this.entitesPathEnabled = true;
        this.guiScale = 1.0f;
    }

    public boolean isOcclusionEnabled() {
        return occlusionEnabled;
    }

    public void setOcclusionEnabled(boolean occlusionEnabled) {
        this.occlusionEnabled = occlusionEnabled;
    }

    public boolean isEntityInterpolationEnabled() {
        return entityInterpolation;
    }

    public void setEntityInterpolation(boolean entityInterpolation) {
        this.entityInterpolation = entityInterpolation;
    }

    public void setDebugging(boolean debugging) {
        this.debugging = debugging;
    }

    public boolean isDebugging() {
        return debugging;
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public void disableMusic() {
        musicEnabled = false;
    }

    public void enableMusic() {
        musicEnabled = true;
    }

    public float getGuiScale() {
        return guiScale;
    }

    public void setGuiScale(float guiScale) {
        this.guiScale = guiScale;
    }

    public boolean isEntitesPathEnabled() {
        return entitesPathEnabled;
    }

    public void setEntitesPathEnabled(boolean entitesPathEnabled) {
        this.entitesPathEnabled = entitesPathEnabled;
    }

    public static GameConfiguration getInstance() {
        if (instance == null) {
            instance = new GameConfiguration();
        }
        return instance;
    }
}