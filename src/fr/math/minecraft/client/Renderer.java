package fr.math.minecraft.client;

import fr.math.minecraft.client.builder.TextureBuilder;
import fr.math.minecraft.client.entity.Ray;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.client.entity.player.PlayerHand;
import fr.math.minecraft.client.fonts.CFont;
import fr.math.minecraft.client.gui.buttons.BlockButton;
import fr.math.minecraft.client.gui.GuiText;
import fr.math.minecraft.client.gui.menus.MainMenu;
import fr.math.minecraft.client.gui.menus.Menu;
import fr.math.minecraft.client.gui.menus.MenuBackgroundType;
import fr.math.minecraft.client.manager.FontManager;
import fr.math.minecraft.client.meshs.*;
import fr.math.minecraft.client.meshs.model.ItemModelData;
import fr.math.minecraft.client.network.payload.ChatPayload;
import fr.math.minecraft.client.texture.CubemapTexture;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.server.pathfinding.Node;
import fr.math.minecraft.shared.ChatMessage;
import fr.math.minecraft.shared.PlayerAction;
import fr.math.minecraft.shared.Sprite;
import fr.math.minecraft.client.texture.Texture;
import fr.math.minecraft.shared.entity.Entity;
import fr.math.minecraft.shared.entity.Villager;
import fr.math.minecraft.shared.entity.mob.MobType;
import fr.math.minecraft.shared.entity.mob.Zombie;
import fr.math.minecraft.shared.inventory.*;
import fr.math.minecraft.shared.inventory.Hotbar;
import fr.math.minecraft.shared.inventory.Inventory;
import fr.math.minecraft.shared.inventory.ItemStack;
import fr.math.minecraft.shared.inventory.PlayerInventory;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.server.manager.BiomeManager;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.world.DroppedItem;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import org.apache.log4j.Logger;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.nio.DoubleBuffer;
import java.util.*;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    private final PlayerMesh playerMesh;
    private final FontMesh fontMesh;
    private final SkyboxMesh skyboxMesh;
    private final BlockMesh handBlockMesh;
    private final BlockMesh selectedBlockMesh;
    private final BlockMesh blockMesh;
    private final HandMesh handMesh;
    private final ItemMesh itemMesh;
    private final Shader playerShader;
    private final Shader chunkShader;
    private final Shader fontShader;
    private final Shader imageShader;
    private final Shader nametagTextShader;
    private final Shader nametagShader;
    private final Shader skyboxShader;
    private final Shader waterShader;
    private final Shader handShader;
    private final Shader blockShader;
    private final Shader crosshairShader;
    private final Shader handBlockShader;
    private final Shader colorShader;
    private final Shader itemShader;
    private final Shader selectedBlockShader;
    private final Shader villagerShader;
    private final Shader zombieShader;
    private final Shader hitboxShader;
    private final Texture terrainTexture;
    private final Texture skinTexture;
    private final Texture defaultSkinTexture;
    private final Texture minecraftTitleTexture;
    private final Texture widgetsTexture;
    private final Texture dirtTexture;
    private final Texture crosshairTexuture;
    private final Texture placeholdTexture;
    private final Texture playerInventoryTexture;
    private final Texture iconsTexture;
    private final Texture guiBlocksTexture;
    private final Texture villagerTexture;
    private final Texture zombieTexture;
    private final Texture craftingTableInventoryTexture;
    private final CrosshairMesh crosshairMesh;
    private final ImageMesh imageMesh;
    private final ImageMesh screenMesh;
    private final FontManager fontManager;
    private final VillagerMesh villagerMesh;
    private final CFont font;
    private final Map<String, Texture> skinsMap;
    private final Map<MobType,  Texture> mobTextureMap;
    private final CubemapTexture panoramaTexture;
    private String emptyText;
    private Set<String> loadedSkins;
    private Material lastItemInHand;
    private final GameConfiguration gameConfiguration;
    private final static float HOTBAR_SCALE = 1.8f;
    private final DoubleBuffer mouseX, mouseY;
    private final static Logger logger = LoggerUtility.getClientLogger(Renderer.class, LogType.TXT);

    public Renderer() {
        this.playerMesh = new PlayerMesh();
        this.villagerMesh = new VillagerMesh();
        this.imageMesh = new ImageMesh(0, 0, 0, 0);
        this.itemMesh = new ItemMesh(Material.DIAMOND_SWORD);
        this.font = new CFont(GameConfiguration.FONT_FILE_PATH, GameConfiguration.FONT_SIZE);
        this.fontMesh = new FontMesh(font);
        this.skyboxMesh = new SkyboxMesh();
        this.crosshairMesh = new CrosshairMesh();
        this.blockMesh = new BlockMesh();
        this.selectedBlockMesh = new BlockMesh(Material.BREAKING_ANIMATION);
        this.handMesh = new HandMesh();
        this.handBlockMesh = new BlockMesh(Material.STONE);
        this.loadedSkins = new HashSet<>();
        this.lastItemInHand = null;
        this.gameConfiguration = GameConfiguration.getInstance();
        this.mouseX = BufferUtils.createDoubleBuffer(1);
        this.mouseY = BufferUtils.createDoubleBuffer(1);

        for (int i = 0; i < 256; i++) {
            emptyText += " ";
        }

        String[] panoramas = new String[6];
        int[] index = new int[]{ 1, 3, 5, 4, 0, 2 };

        for (int i = 0; i < index.length; i++) {
            panoramas[i] = "res/textures/gui/title/panorama_" + index[i] + ".png";
        }

        this.screenMesh = new ImageMesh(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT, 0, 0);

        this.fontManager = new FontManager();

        this.playerShader = new Shader("res/shaders/player.vert", "res/shaders/player.frag");
        this.zombieShader = new Shader("res/shaders/zombie.vert", "res/shaders/player.frag");
        this.chunkShader = new Shader("res/shaders/chunk.vert", "res/shaders/chunk.frag");
        this.fontShader = new Shader("res/shaders/font.vert", "res/shaders/font.frag");
        this.nametagShader = new Shader("res/shaders/nametag.vert", "res/shaders/nametag.frag");
        this.nametagTextShader = new Shader("res/shaders/nametag_text.vert", "res/shaders/nametag_text.frag");
        this.skyboxShader = new Shader("res/shaders/skybox.vert", "res/shaders/skybox.frag");
        this.imageShader = new Shader("res/shaders/image.vert", "res/shaders/image.frag");
        this.waterShader = new Shader("res/shaders/water.vert", "res/shaders/water.frag");
        this.handShader = new Shader("res/shaders/hand.vert", "res/shaders/hand.frag");
        this.blockShader = new Shader("res/shaders/default.vert", "res/shaders/default.frag");
        this.selectedBlockShader = new Shader("res/shaders/selected_block.vert", "res/shaders/selected_block.frag");
        this.crosshairShader = new Shader("res/shaders/cursor.vert", "res/shaders/default.frag");
        this.handBlockShader = new Shader("res/shaders/handblock.vert", "res/shaders/handblock.frag");
        this.itemShader = new Shader("res/shaders/item.vert", "res/shaders/item.frag");
        this.colorShader = new Shader("res/shaders/color.vert", "res/shaders/color.frag");
        this.hitboxShader = new Shader("res/shaders/hitbox.vert", "res/shaders/hitbox.frag");
        this.villagerShader = new Shader("res/shaders/villager.vert", "res/shaders/villager.frag");

        this.terrainTexture = new Texture("res/textures/terrain.png", 1);
        this.defaultSkinTexture = new Texture("res/textures/skin.png", 2);
        this.minecraftTitleTexture = new Texture("res/textures/gui/title/minecraft_title.png", 3);
        this.panoramaTexture = new CubemapTexture(panoramas, 4);
        this.widgetsTexture = new Texture("res/textures/gui/widgets.png", 5);
        this.skinTexture = new Texture(Game.getInstance().getPlayer().getSkinPath(), 6);
        this.crosshairTexuture = new Texture("res/textures/gui/crosshair.png", 7);
        this.placeholdTexture = new Texture("res/textures/gui/placehold.png", 8);
        this.playerInventoryTexture = new Texture("res/textures/gui/inventory.png", 9);
        this.craftingTableInventoryTexture = new Texture("res/textures/gui/crafting_table.png", 9);
        this.iconsTexture = new Texture("res/textures/gui/icons.png", 10);
        this.guiBlocksTexture = new Texture("res/textures/gui/gui_blocks2.png", 11);
        this.villagerTexture = new Texture("res/textures/entity/villager2.png", 13);
        this.zombieTexture = new Texture("res/textures/zombie.png", 12);

        this.dirtTexture = TextureBuilder.buildDirtBackgroundTexture();

        this.skinsMap = new HashMap<>();
        this.mobTextureMap = new HashMap<>();

        this.terrainTexture.load();
        this.defaultSkinTexture.load();
        this.minecraftTitleTexture.load();
        this.panoramaTexture.load();
        this.widgetsTexture.load();
        this.dirtTexture.load();
        this.skinTexture.load();
        this.crosshairTexuture.load();
        this.placeholdTexture.load();
        this.playerInventoryTexture.load();
        this.iconsTexture.load();
        this.guiBlocksTexture.load();
        this.villagerTexture.load();
        this.zombieTexture.load();
        this.craftingTableInventoryTexture.load();
    }

    public void render(Camera camera, Player player) {

        Texture skinTexture = defaultSkinTexture;
        if (skinsMap.containsKey(player.getUuid())) {
            skinTexture = skinsMap.get(player.getUuid());
            if (!skinTexture.isLoaded()) {
                skinTexture.load();
            }
        } else {
            BufferedImage skin = player.getSkin();
            if (skin == null) {
                return;
            }
            skinTexture = new Texture(skin, 2);
            skinTexture.load();
            skinsMap.put(player.getUuid(), skinTexture);
        }

        playerShader.enable();
        playerShader.sendInt("uTexture", skinTexture.getSlot());
        playerShader.sendFloat("hit", player.getHitMarkDelay() > 0 ? 1.0f : 0.0f);

        glActiveTexture(GL_TEXTURE0 + skinTexture.getSlot());
        skinTexture.bind();

        camera.matrix(playerShader, player);

        playerMesh.draw();

        skinTexture.unbind();

        this.renderNametag(camera, player);
        Ray ray = player.getBuildRay();
        if (player.getAction() != null && player.getAction() == PlayerAction.MINING) {
            this.renderMining(camera, ray.getBlockWorldPosition().x, ray.getBlockWorldPosition().y, ray.getBlockWorldPosition().z, player.getSprite());
        }
    }

    public void render(Camera camera, Villager villager) {

        villagerShader.enable();
        villagerShader.sendInt("uTexture", villagerTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + villagerTexture.getSlot());
        villagerTexture.bind();

        camera.matrix(villagerShader, villager);

        villagerMesh.draw();

        villagerTexture.unbind();
    }

    public void render(Camera camera, Zombie zombie) {

        zombieShader.enable();
        zombieShader.sendInt("uTexture", zombieTexture.getSlot());

        zombieTexture.activeSlot();
        zombieTexture.bind();

        camera.matrix(zombieShader, zombie);
        zombieShader.sendFloat("hit", zombie.getHitMarkDelay() > 0 ? 1.0f : 0.0f);

        playerMesh.draw();
        zombieTexture.unbind();

        if (zombie.getPattern() != null && !zombie.getPattern().getPath().isEmpty()) {
            handBlockShader.enable();
            handBlockShader.sendInt("uTexture", terrainTexture.getSlot());
            terrainTexture.activeSlot();
            terrainTexture.bind();

            if (gameConfiguration.isEntitesPathEnabled()) {
                for (Node node : zombie.getPattern().getPath()) {
                    Vector3f pathWorldPosition = new Vector3f(node.getPosition().x, zombie.getPosition().y + 1, node.getPosition().y);
                    camera.matrixInWorld(handBlockShader, pathWorldPosition);

                    handBlockMesh.update(handBlockShader, Material.DEBUG);

                    handBlockMesh.draw();
                }
            }

            terrainTexture.unbind();
        }
    }

    public void renderNametag(Camera camera, Entity entity) {
        this.renderNametagBar(camera, entity);
        this.renderNametagText(camera, entity);
    }

    private void renderNametagBar(Camera camera, Entity entity) {

        NametagMesh nametagMesh = entity.getNametagMesh();

        if (nametagMesh == null)
            return;

        nametagShader.enable();

        camera.matrixNametag(nametagShader, entity);

        nametagMesh.draw();
    }

    private void renderNametagText(Camera camera, Entity entity) {
        Texture texture = font.getTexture();
        nametagTextShader.enable();
        nametagTextShader.sendInt("uTexture", texture.getSlot());

        glActiveTexture(GL_TEXTURE0 + texture.getSlot());
        texture.bind();
        camera.matrixNametag(nametagTextShader, entity);

        fontManager.addText(fontMesh, entity.getName(), 0, 0, 0, 1.0f, 0xFFFFFF, true);

        fontMesh.flush();

        texture.unbind();
    }

    public void renderChunk(Camera camera, Chunk chunk) {

        chunkShader.enable();
        chunkShader.sendInt("uTexture", terrainTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + terrainTexture.getSlot());
        terrainTexture.bind();

        camera.matrix(chunkShader, chunk);

        chunk.getMesh().draw();
        terrainTexture.unbind();
    }

    public void renderHand(Camera camera, PlayerHand hand) {

        glDisable(GL_DEPTH_TEST);

        handShader.enable();
        handShader.sendInt("uTexture", skinTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + skinTexture.getSlot());
        skinTexture.bind();

        camera.matrixHand(hand, handShader);

        handMesh.draw();

        skinTexture.unbind();
        glEnable(GL_DEPTH_TEST);

    }

    public void renderWater(Camera camera, Chunk chunk) {

        waterShader.enable();
        waterShader.sendInt("uTexture", terrainTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + terrainTexture.getSlot());
        terrainTexture.bind();

        camera.matrixWater(waterShader, chunk);

        chunk.getWaterMesh().draw();
        terrainTexture.unbind();
    }

    public void renderText(Camera camera, String text, float x, float y, int rgb, float scale) {
        this.renderText(camera, text, x, y, -10, rgb, scale, 0.0f, new Vector3i(0 , 0, 0));
    }

    public void renderText(Camera camera, String text, float x, float y, int rgb, float scale, float rotateAngle, Vector3i normal) {
        this.renderText(camera, text, x, y, -10, rgb, scale, rotateAngle, normal);
    }
    
    public void renderText(Camera camera, String text, float x, float y, float z, int rgb, float scale) {
        this.renderText(camera, text, x, y, z, rgb, scale, 0.0f, new Vector3i(0 , 0, 0));
    }

    public void renderText(Camera camera, String text, float x, float y, float z, int rgb, float scale, float rotateAngle, Vector3i normal) {
        this.renderString(camera, emptyText, -1000, -1000, 100, rgb, scale, rotateAngle, normal);
        this.renderString(camera, text, x, y, z, rgb, scale, rotateAngle, normal);
        this.renderString(camera, text, x + 2, y - 2, z - 1, 0x555555, scale, rotateAngle, normal);
    }

    private void renderString(Camera camera, String text, float x, float y, float z, int rgb, float scale, float rotateAngle, Vector3i normal) {
        Texture texture = font.getTexture();

        fontShader.enable();
        fontShader.sendInt("uTexture", texture.getSlot());

        glActiveTexture(GL_TEXTURE0 + texture.getSlot());
        texture.bind();

        camera.matrixOrtho(fontShader, rotateAngle, x, y, z, text, fontMesh, new Vector3f(normal.x, normal.y, normal.z));
        fontManager.addText(fontMesh, text, x, y, z, scale, rgb);

        fontMesh.flush();

        texture.unbind();
    }

    public void renderSkybox(Camera camera) {

        glDepthFunc(GL_LEQUAL);

        skyboxShader.enable();
        skyboxShader.sendInt("uTexture", panoramaTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + panoramaTexture.getSlot());
        panoramaTexture.bind();

        camera.matrixSkybox(skyboxShader);
        skyboxMesh.draw();

        panoramaTexture.unbind();
        glDepthFunc(GL_LESS);

    }

    public void renderImage(Camera camera, ImageMesh imageMesh, Texture texture) {
        imageShader.enable();
        imageShader.sendFloat("depth", -10);
        imageShader.sendInt("uTexture", texture.getSlot());

        int titleWidth = (int) (1002 * .5f);
        int titleHeight = (int) (197 * .5f);

        imageMesh.texSubImage(0, 0, 1002, 188, 1002, 188);
        imageMesh.translate(imageShader, GameConfiguration.WINDOW_CENTER_X - titleWidth * .5f, GameConfiguration.WINDOW_HEIGHT - titleHeight * .5f - 150, titleWidth, titleHeight);

        glActiveTexture(GL_TEXTURE0 + texture.getSlot());
        texture.bind();

        camera.matrixOrtho(imageShader, 0, 0);

        imageMesh.draw();

        texture.unbind();
    }

    public void renderButton(Camera camera, BlockButton button) {
        imageShader.enable();
        imageShader.sendInt("uTexture", widgetsTexture.getSlot());
        imageShader.sendFloat("depth", button.getZ());

        glActiveTexture(GL_TEXTURE0 + widgetsTexture.getSlot());
        widgetsTexture.bind();

        float scale = gameConfiguration.getGuiScale();

        if (button.isHovered()) {
            imageMesh.texSubImage(0, 256.0f - 106, 200 * scale, 20 * scale, 256.0f, 256.0f);
        } else {
            imageMesh.texSubImage(0, 256.0f - 86, 200 * scale, 20 * scale, 256.0f, 256.0f);
        }

        imageMesh.translate(imageShader, button.getX(), button.getY(), ButtonMesh.BUTTON_WIDTH, ButtonMesh.BUTTON_HEIGHT);
        camera.matrixOrtho(imageShader, button.getX(), button.getY());

        int textColor = 0xFFFFFF;

        imageMesh.draw();

        widgetsTexture.unbind();

        float width = fontManager.getTextWidth(fontMesh, GameConfiguration.DEFAULT_SCALE, button.getText());
        float height = fontManager.getTextHeight(fontMesh, GameConfiguration.DEFAULT_SCALE, button.getText());

        this.renderText(camera, button.getText(), button.getX() + (ButtonMesh.BUTTON_WIDTH - width) / 2.0f, button.getY() + (ButtonMesh.BUTTON_HEIGHT - height) / 2.0f, button.getZ() + 1, textColor, GameConfiguration.DEFAULT_SCALE);
    }

    public void renderCrosshair(Camera camera) {

        glDisable(GL_DEPTH_TEST);

        crosshairShader.enable();
        crosshairShader.sendInt("uTexture", crosshairTexuture.getSlot());

        glActiveTexture(GL_TEXTURE0 + crosshairTexuture.getSlot());
        crosshairTexuture.bind();

        camera.matrixCrosshair(crosshairShader);
        crosshairMesh.draw();

        crosshairTexuture.unbind();

        glEnable(GL_DEPTH_TEST);

    }

    public void renderAimedBlock(Camera camera, Ray ray) {

        if (ray.getAimedChunk() == null) {
            return;
        }

        if (ray.getAimedBlock() == Material.AIR.getId()) {
            return;
        }

        blockShader.enable();
        blockShader.sendInt("uTexture", placeholdTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + placeholdTexture.getSlot());
        placeholdTexture.bind();

        camera.matrixAimedBlock(ray, blockShader);
        blockMesh.draw();

        placeholdTexture.unbind();

    }

    public void renderMining(Camera camera, int worldX, int worldY, int worldZ, Sprite sprite) {

        selectedBlockShader.enable();
        selectedBlockShader.sendInt("uTexture", terrainTexture.getSlot());
        selectedBlockShader.sendFloat("spriteIndex", sprite.getIndex());
        selectedBlockShader.sendFloat("scale", 1.002f);

        glActiveTexture(GL_TEXTURE0 + terrainTexture.getSlot());
        terrainTexture.bind();

        camera.matrixInWorld(selectedBlockShader, new Vector3f(worldX, worldY, worldZ));
        selectedBlockMesh.draw();

        terrainTexture.unbind();

    }

    public FontMesh getFontMesh() {
        return fontMesh;
    }

    public void renderMenu(Camera camera, Menu menu) {

        if (menu.getBackgroundType() == MenuBackgroundType.SKYBOX_BACKGROUND) {
            this.renderSkybox(camera);
        } else {
            this.renderDirtBackground(camera);
        }

        if (menu instanceof MainMenu) {
            this.renderImage(camera, imageMesh, minecraftTitleTexture);
        }

        GuiText menuTitle = menu.getTitle();

        if (menuTitle != null) {
            String title = menuTitle.getText();
            float titleWidth = fontManager.getTextWidth(fontMesh, GameConfiguration.MENU_TITLE_SCALE, title);
            // float titleHeight = fontManager.getTextHeight(fontMesh, GameConfiguration.MENU_TITLE_SCALE, title);
            float titleX = GameConfiguration.WINDOW_CENTER_X - titleWidth / 2.0f;
            this.renderText(camera, menu.getTitle().getText(), titleX, menu.getTitle().getY(), -8, 0xFFFFFF, menu.getTitle().getScale());
        }

        for (GuiText text : menu.getTexts()) {
            this.renderText(
                camera,
                text.getText(),
                text.getX(),
                text.getY(),
                text.getZ(),
                text.getRgb(),
                text.getScale(),
                text.getRotateAngle(),
                new Vector3i(0, 0, 1)
            );
        }

        for (BlockButton button : menu.getButtons()) {
            this.renderButton(camera, button);
        }
    }

    private void renderDirtBackground(Camera camera) {

        imageShader.enable();
        imageShader.sendInt("uTexture", dirtTexture.getSlot());
        imageShader.sendFloat("depth", -10);

        glActiveTexture(GL_TEXTURE0 + dirtTexture.getSlot());
        dirtTexture.bind();

        screenMesh.texSubImage(0, 0, GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT, GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
        screenMesh.translate(imageShader, 0, 0, GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);

        camera.matrixOrtho(imageShader, 0, 0);
        screenMesh.draw();

        dirtTexture.unbind();
    }

    public void renderDebugTools(Camera camera, Player player, int frames) {
        GameConfiguration gameConfiguration = GameConfiguration.getInstance();
        this.renderText(camera, "XYZ: " + player.getPosition().x + " / " + player.getPosition().y + " / " + player.getPosition().z, 0, GameConfiguration.WINDOW_HEIGHT - 100,0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
        this.renderText(camera, "FPS: " + frames, 0, GameConfiguration.WINDOW_HEIGHT - 120,0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
        this.renderText(camera, "Ping: " + player.getPing() + "ms", 0, GameConfiguration.WINDOW_HEIGHT - 140,0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
        BiomeManager biomeManager = new BiomeManager();
        World world = Game.getInstance().getWorld();
        this.renderText(camera, "BIOME: " + biomeManager.getBiome((int) player.getPosition().x, (int)player.getPosition().z,world.getSeed()).getBiomeName(), 0, GameConfiguration.WINDOW_HEIGHT - 160, 0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
        this.renderText(camera, "Entity Interpolation: " + gameConfiguration.isEntityInterpolationEnabled(), 0, GameConfiguration.WINDOW_HEIGHT - 180, 0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
    }

    public void renderRect(Camera camera, float x, float y, float width, float height, int rgb, float alpha, float depth) {
        float r = (float) ((rgb >> 16) & 0xFF) / 255.0f;
        float g = (float) ((rgb >> 8) & 0xFF) / 255.0f;
        float b = (float) ((rgb >> 0) & 0xFF) / 255.0f;

        colorShader.enable();
        colorShader.sendFloat("depth", depth);
        colorShader.sendFloat("r", r);
        colorShader.sendFloat("g", g);
        colorShader.sendFloat("b", b);
        colorShader.sendFloat("a", alpha);

        imageMesh.translate(colorShader, x, y, width, height);
        camera.matrixOrtho(colorShader, 0, 0);

        imageMesh.draw();

    }

    public void renderInventory(Camera camera, Inventory inventory, InventoryType layer) {

        float inventoryWidth = GameConfiguration.INVENTORY_TEXTURE_WIDTH * 1.4f * gameConfiguration.getGuiScale();
        float inventoryHeight = GameConfiguration.INVENTORY_TEXTURE_HEIGHT * 1.4f * gameConfiguration.getGuiScale();

        float inventoryX = (GameConfiguration.WINDOW_WIDTH - inventoryWidth) / 2;
        float inventoryY = (GameConfiguration.WINDOW_HEIGHT - inventoryHeight) / 2;

        Texture inventoryTexture = layer == InventoryType.CRAFTING_TABLE ? craftingTableInventoryTexture : playerInventoryTexture;

        imageShader.enable();
        imageShader.sendInt("uTexture", inventoryTexture.getSlot());
        imageShader.sendFloat("depth", -12);

        if (inventory.getType() == InventoryType.PLAYER_INVENTORY || inventory.getType() == InventoryType.CRAFTING_TABLE) {
            glActiveTexture(GL_TEXTURE0 + inventoryTexture.getSlot());
            inventoryTexture.bind();

            imageMesh.texSubImage(0, 90, 177, 166, GameConfiguration.INVENTORY_TEXTURE_WIDTH, GameConfiguration.INVENTORY_TEXTURE_HEIGHT);
            imageMesh.translate(imageShader, inventoryX, inventoryY, inventoryWidth, inventoryHeight);

            camera.matrixOrtho(imageShader, inventoryX, inventoryY);

            imageMesh.draw();

            inventoryTexture.unbind();
        }

        glActiveTexture(GL_TEXTURE0 + guiBlocksTexture.getSlot());
        imageShader.sendInt("uTexture", guiBlocksTexture.getSlot());

        guiBlocksTexture.bind();

        float slotScaleX = inventoryWidth / 177.0f;
        float slotScaleY = inventoryHeight / 166.0f;
        float slotHeight = 18.0f * slotScaleY;
        float slotWidth = 18.0f * slotScaleX;
        float slotSize = 16.0f * 1.4f * gameConfiguration.getGuiScale();
        ItemStack selectedItem = inventory.getSelectedItem();
        int currentSlot = inventory.getCurrentSlot();

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItems()[i];
            glActiveTexture(GL_TEXTURE0 + guiBlocksTexture.getSlot());
            imageShader.enable();
            imageShader.sendInt("uTexture", guiBlocksTexture.getSlot());

            if (item == null) {
                continue;
            }

            float itemX = inventory.getItemX(i);
            float itemY = inventory.getItemY(i);

            Material material = item.getMaterial();

            float size = material.isItem() ? 16.0f : 48.0f;
            float offset = material.isItem() ? 0.0f : 80.0f;

            imageShader.sendFloat("depth", -11);
            imageMesh.texSubImage(material.getBlockIconX() * size, material.getBlockIconY() * size + offset, size, size, 512.0f, 512.0f);

            if (inventory.getHoldedSlot() == i) {
                glfwGetCursorPos(Game.getInstance().getWindow(), mouseX, mouseY);
                imageMesh.translate(imageShader, (float) mouseX.get(0) - slotSize * 1.4f / 2.0f, GameConfiguration.WINDOW_HEIGHT - (float) mouseY.get(0) - slotSize * 1.4f / 2.0f, slotSize * 1.4f, slotSize * 1.4f);
            } else {
                imageMesh.translate(imageShader, itemX, itemY, slotSize * 1.4f, slotSize * 1.4f);
            }

            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();

            if (item.getAmount() > 1) {
                this.renderText(camera, item.getAmount() + "", itemX + slotSize * 1.4f / 1.5f, itemY, -10, 0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
            }
        }

        guiBlocksTexture.unbind();

        if (selectedItem != null) {

            colorShader.enable();
            Material material = selectedItem.getMaterial();

            float textWidth = fontManager.getTextWidth(fontMesh, material.getName());
            float textHeight = fontManager.getTextHeight(fontMesh, material.getName());

            if (!selectedItem.getLore().isEmpty()) {
                for (String lore : selectedItem.getLore()) {
                    float loreWidth = fontManager.getTextWidth(fontMesh, lore);
                    if (loreWidth > textWidth) {
                        textWidth = loreWidth;
                    }
                }
            }

            float itemX = inventory.getItemX(currentSlot);
            float itemY = inventory.getItemY(currentSlot);

            float backgroundX = itemX + slotSize * 1.4f;
            float backgroundY = itemY;

            this.renderRect(camera, itemX, itemY, slotSize * 1.4f, slotSize * 1.4f, 0xFFFFFF, 0.7f, -10);

            float paddingY = 10 + (selectedItem.getLore().isEmpty() ? 0 : selectedItem.getLore().size() * textHeight + textHeight + 3 + 35);
            int borderSize = 3;

            this.renderRect(camera, backgroundX - 5, backgroundY - paddingY, textWidth + 20, textHeight + paddingY, 0x110210, 0.9f, -9);

            this.renderRect(camera, backgroundX - 5 - borderSize, backgroundY - paddingY + textHeight + paddingY, textWidth + 20 + borderSize * 2, borderSize, 0x2b0861, 1.0f, -9);
            this.renderRect(camera, backgroundX - 5 - borderSize, backgroundY - paddingY - borderSize, textWidth + 20 + borderSize * 2, borderSize, 0x2b0861, 1.0f, -9);

            this.renderRect(camera, backgroundX - 5 - borderSize, backgroundY - paddingY + textHeight + paddingY + borderSize, textWidth + 20 + borderSize * 2, borderSize, 0x110210, 1.0f, -9);
            this.renderRect(camera, backgroundX - 5 - borderSize, backgroundY - paddingY - borderSize * 2, textWidth + 20 + borderSize * 2, borderSize, 0x110210, 1.0f, -9);

            this.renderRect(camera, backgroundX - 5 - borderSize, backgroundY - paddingY, borderSize, textHeight + paddingY + borderSize, 0x2b0861, 1.0f, -9);
            this.renderRect(camera, backgroundX - 5 + textWidth + 20, backgroundY - paddingY, borderSize, textHeight + paddingY + borderSize, 0x2b0861, 1.0f, -9);

            this.renderRect(camera, backgroundX - 5 - borderSize * 2, backgroundY - paddingY - borderSize, borderSize, textHeight + paddingY + borderSize * 2, 0x110210, 1.0f, -9);
            this.renderRect(camera, backgroundX - 5 + textWidth + 20 + borderSize, backgroundY - paddingY - borderSize, borderSize, textHeight + paddingY + borderSize * 2, 0x110210, 1.0f, -9);

            float textY = selectedItem.getLore().isEmpty() ? backgroundY - paddingY / 2.0f : backgroundY - borderSize - 3;

            this.renderText(camera, material.getName(), backgroundX, textY, -8, 0xFFFFFF, GameConfiguration.DEFAULT_SCALE);

            float loreY = backgroundY - 15 - textHeight - 5;

            for (String lore : selectedItem.getLore()) {
                this.renderText(camera, lore, backgroundX, loreY, -8, 0xAAAAAA, GameConfiguration.DEFAULT_SCALE);
                loreY -= textHeight + 5;
            }
        } else {

            if (currentSlot >= inventory.getSize()) {
                return;
            }

            float itemX = inventory.getItemX(currentSlot);
            float itemY = inventory.getItemY(currentSlot);

            this.renderRect(camera, itemX, itemY, slotSize * 1.4f, slotSize * 1.4f, 0xFFFFFF, 0.7f, -10);
        }
    }

    public void renderHotbar(Camera camera, Player player, Hotbar hotbar) {

        imageShader.enable();
        imageShader.sendInt("uTexture", widgetsTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + widgetsTexture.getSlot());
        widgetsTexture.bind();

        int hotbarWidth = 182;
        int hotbarHeight = 22;
        float scale = gameConfiguration.getGuiScale() * HOTBAR_SCALE;

        float hotbarX = (GameConfiguration.WINDOW_WIDTH - hotbarWidth * scale) / 2.0f;
        float hotbarY = 0;

        int slotSize = 22;

        imageMesh.texSubImage(0, 256.0f - hotbarHeight, hotbarWidth, hotbarHeight, 256.0f, 256.0f);
        imageMesh.translate(imageShader, hotbarX, hotbarY, hotbarWidth, hotbarHeight, scale);

        imageShader.sendFloat("depth", -11);
        camera.matrixOrtho(imageShader, 0, 0);
        imageMesh.draw();

        slotSize = slotSize + 2;

        imageMesh.texSubImage(0, 256.0f - hotbarHeight - slotSize, slotSize, slotSize, 256.0f, 256.0f);
        imageMesh.translate(imageShader, hotbarX - 2 + hotbar.getSelectedSlot() * 20 * scale, hotbarY - 1, slotSize, slotSize, scale);

        imageShader.sendFloat("depth", -10);
        camera.matrixOrtho(imageShader, 0, 0);
        imageMesh.draw();

        widgetsTexture.unbind();

        for (int i = 0; i < hotbar.getSize(); i++) {
            ItemStack item = hotbar.getItems()[i];

            if (item == null) {
                continue;
            }

            imageShader.enable();
            glActiveTexture(GL_TEXTURE0 + guiBlocksTexture.getSlot());
            guiBlocksTexture.bind();
            imageShader.sendInt("uTexture", guiBlocksTexture.getSlot());

            Material material = item.getMaterial();
            float size = material.isItem() ? 16.0f : 48.0f;
            float offset = material.isItem() ? 0.0f : 80.0f;

            imageShader.sendFloat("depth", -9);
            imageMesh.texSubImage(material.getBlockIconX() * size, material.getBlockIconY() * size + offset, size, size, 512.0f, 512.0f);

            float itemX = hotbarX + i * 21 * scale + 2;

            if (i == 0) {
                itemX += 3;
            }

            float itemY = 22.0f * scale * .7f * 0.25f;

            imageMesh.translate(imageShader, itemX, itemY, 22 * scale * .7f, 22 * scale * .7f);
            camera.matrixOrtho(imageShader, 0, 0);

            imageMesh.draw();

            if (item.getAmount() > 1) {
                this.renderText(camera, item.getAmount() + "", itemX + 22 * scale * .7f / 1.5f, itemY, -8, 0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
            }
        }

        int filledHearts = (int) player.getHealth() / 2;
        imageShader.enable();
        glActiveTexture(GL_TEXTURE0 + iconsTexture.getSlot());
        iconsTexture.bind();
        imageShader.sendInt("uTexture", iconsTexture.getSlot());

        int iconSize = 9;

        imageMesh.texSubImage(16 + 4 * iconSize, 256.0f - iconSize, iconSize, iconSize, 256.0f, 256.0f);

        int currentHeart = 0;
        while (currentHeart < filledHearts) {
            imageShader.sendFloat("depth", -10);
            imageMesh.texSubImage(16 + 0 * iconSize, 256.0f - iconSize, iconSize, iconSize, 256.0f, 256.0f);
            imageMesh.translate(imageShader, hotbarX + currentHeart * iconSize * scale, hotbarY + hotbarHeight * scale + 5, iconSize * scale, iconSize * scale);
            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();

            imageShader.sendFloat("depth", -9);
            imageMesh.texSubImage(16 + 4 * iconSize, 256.0f - iconSize, iconSize, iconSize, 256.0f, 256.0f);
            imageMesh.translate(imageShader, hotbarX + currentHeart * iconSize * scale, hotbarY + hotbarHeight * scale + 5, iconSize * scale, iconSize * scale);
            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();
            currentHeart++;
        }

        if (player.getHealth() % 5 == 0.5f) {
            imageShader.sendFloat("depth", -10);
            imageMesh.texSubImage(16 + 0 * iconSize, 256.0f - iconSize, iconSize, iconSize, 256.0f, 256.0f);
            imageMesh.translate(imageShader, hotbarX + currentHeart * iconSize * scale, hotbarY + hotbarHeight * scale + 5, iconSize * scale, iconSize * scale);
            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();

            imageShader.sendFloat("depth", -9);
            imageMesh.texSubImage(16 + 5 * iconSize, 256.0f - iconSize, iconSize, iconSize, 256.0f, 256.0f);
            imageMesh.translate(imageShader, hotbarX + currentHeart * iconSize * scale, hotbarY + hotbarHeight * scale + 5, iconSize * scale, iconSize * scale);
            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();
        }

        while (currentHeart < player.getMaxHealth() / 2.0f) {
            imageShader.sendFloat("depth", -9);
            imageMesh.texSubImage(16 + 4 * iconSize, 256.0f - iconSize * 2, iconSize, iconSize, 256.0f, 256.0f);
            imageMesh.translate(imageShader, hotbarX + currentHeart * iconSize * scale, hotbarY + hotbarHeight * scale + 5, iconSize * scale, iconSize * scale);
            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();
            currentHeart++;
        }


        int filledFood = (int) player.getHunger() / 2;
        imageShader.enable();
        glActiveTexture(GL_TEXTURE0 + iconsTexture.getSlot());
        iconsTexture.bind();
        imageShader.sendInt("uTexture", iconsTexture.getSlot());

        imageMesh.texSubImage(16 + 4 * iconSize, 256.0f - iconSize * 4, iconSize, iconSize, 256.0f, 256.0f);

        int currentFood = 0;
        while (currentFood < filledFood) {
            imageShader.sendFloat("depth", -10);
            imageMesh.texSubImage(16 + 0 * iconSize, 256.0f - iconSize * 4, iconSize, iconSize, 256.0f, 256.0f);
            imageMesh.translate(imageShader, (hotbarX + (hotbarWidth * scale) - 2*iconSize) - (currentFood * iconSize * scale), hotbarY + hotbarHeight * scale + 5, iconSize * scale, iconSize * scale);
            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();

            imageShader.sendFloat("depth", -9);
            imageMesh.texSubImage(16 + 4 * iconSize, 256.0f - iconSize * 4, iconSize, iconSize, 256.0f, 256.0f);
            imageMesh.translate(imageShader, (hotbarX + (hotbarWidth * scale) - 2*iconSize) - (currentFood * iconSize * scale), hotbarY + hotbarHeight * scale + 5, iconSize * scale, iconSize * scale);
            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();
            currentFood++;
        }

        if (player.getHunger() % 5 == 0.5f) {
            imageShader.sendFloat("depth", -10);
            imageMesh.texSubImage(16 + 0 * iconSize, 256.0f - iconSize * 4, iconSize, iconSize, 256.0f, 256.0f);
            imageMesh.translate(imageShader, (hotbarX + (hotbarWidth * scale) - 2*iconSize) - (currentFood * iconSize * scale), hotbarY + hotbarHeight * scale + 5, iconSize * scale, iconSize * scale);
            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();

            imageShader.sendFloat("depth", -9);
            imageMesh.texSubImage(16 + 5 * iconSize, 256.0f - iconSize * 4, iconSize, iconSize, 256.0f, 256.0f);
            imageMesh.translate(imageShader, (hotbarX + (hotbarWidth * scale) - 2*iconSize) - (currentFood * iconSize * scale), hotbarY + hotbarHeight * scale + 5, iconSize * scale, iconSize * scale);
            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();
        }

        while (currentFood < player.getMaxHunger() / 2.0f) {
            imageShader.sendFloat("depth", -9);
            imageMesh.texSubImage(16 + 0 * iconSize, 256.0f - iconSize * 4, iconSize, iconSize, 256.0f, 256.0f);
            imageMesh.translate(imageShader, (hotbarX + (hotbarWidth * scale) - 2*iconSize) - (currentFood * iconSize * scale), hotbarY + hotbarHeight * scale + 5, iconSize * scale, iconSize * scale);
            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();
            currentFood++;
        }

        iconsTexture.unbind();

    }

    public void renderSelectedBlock(Camera camera, Player player, Material material) {

        glClear(GL_DEPTH_BUFFER_BIT);

        handBlockShader.enable();
        handBlockShader.sendInt("uTexture", terrainTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + terrainTexture.getSlot());
        terrainTexture.bind();

        handBlockMesh.update(handBlockShader, material);
        camera.matrixSelectedBlock(player.getHand(), handBlockShader);
        handBlockShader.sendFloat("blockID", material.getId());
        handBlockMesh.draw();

        terrainTexture.unbind();

    }

    public void renderItemInHand(Camera camera, Player player, Material material) {

        glClear(GL_DEPTH_BUFFER_BIT);

        if (lastItemInHand != material) {
            lastItemInHand = material;
            itemMesh.update(material);
        }

        itemShader.enable();
        itemShader.sendInt("uTexture", guiBlocksTexture.getSlot());
        itemShader.sendFloat("rotationAngleX", 0);

        //player.getHotbar().getAnimation().sendUniforms(itemShader);
        //itemShader.sendFloat("depth", -30);

        glActiveTexture(GL_TEXTURE0 + guiBlocksTexture.getSlot());
        guiBlocksTexture.bind();

        ItemModelData itemModelData;
        try {
            itemModelData = ItemModelData.valueOf(String.valueOf(material));
        } catch (IllegalArgumentException e) {
            itemModelData = ItemModelData.DEBUG;
        }

        camera.matrixItem(player.getHand(), player.getMiningAnimation(), itemShader, itemModelData);

        itemMesh.draw();
        guiBlocksTexture.unbind();

        float itemTextX = (GameConfiguration.WINDOW_WIDTH - fontManager.getTextWidth(fontMesh, material.getName())) / 2.0f;
        float itemTextY = 22 * HOTBAR_SCALE * gameConfiguration.getGuiScale() + 20;

        this.renderText(camera, material.getName(), itemTextX, itemTextY, 0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
        
    }

    public Map<String, Texture> getSkinsMap() {
        return skinsMap;
    }

    public void renderDroppedItem(Camera camera, DroppedItem droppedItem) {

        handBlockShader.enable();
        handBlockShader.sendInt("uTexture", terrainTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + terrainTexture.getSlot());
        terrainTexture.bind();

        handBlockMesh.update(handBlockShader, droppedItem.getMaterial());
        camera.matrixInWorld(handBlockShader, new Vector3f(droppedItem.getPosition()).sub(0, 0.35f, 0), 0.2f, droppedItem.getRotationAngle(), new Vector3f(0, 1, 0));
        handBlockMesh.draw();

        terrainTexture.unbind();
    }

    public void renderChatPayload(Camera camera, ChatPayload payload) {

        int width = 500;
        int height = 25;
        int margin = 10;

        this.renderRect(camera, margin, margin, width, height, 0x0, 0.45f, -12);
        StringBuilder message = payload.getMessage();

        this.renderText(camera, message.toString(), margin + 5, height / 2.0f, -11, 0xFFFFFF, GameConfiguration.DEFAULT_SCALE);

    }

    public void renderChat(Camera camera, Map<String, ChatMessage> messages) {
        int width = 500;
        int height = Math.min(30 * 5, 30 * messages.size());
        int margin = 10;

        this.renderRect(camera, margin, margin + 50, width, height, 0x0, 0.45f, -11);
        float messageX = margin;
        float messageY = margin + 52;
        List<ChatMessage> sortedMessages = new ArrayList<>(messages.values());
        sortedMessages.sort(new ChatTimestampComparator());

        int messageDisplayed = 0;

        for (ChatMessage chatMessage : sortedMessages) {
            this.renderText(camera, chatMessage.getSenderName() + ": " + chatMessage.getMessage(), messageX, messageY, 0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
            messageY += 30;
            messageDisplayed++;
            if (messageDisplayed == 5) break;
        }
    }

    private static class ChatTimestampComparator implements Comparator<ChatMessage> {

        @Override
        public int compare(ChatMessage o1, ChatMessage o2) {
            return (int) (o2.getTimestamp() - o1.getTimestamp());
        }
    }

}
