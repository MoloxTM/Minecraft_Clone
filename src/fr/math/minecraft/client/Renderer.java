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
import fr.math.minecraft.client.texture.CubemapTexture;
import fr.math.minecraft.client.texture.Texture;
import fr.math.minecraft.inventory.Hotbar;
import fr.math.minecraft.inventory.ItemStack;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.server.manager.BiomeManager;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.world.Material;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.awt.image.BufferedImage;
import java.util.*;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    private final PlayerMesh playerMesh;
    private final FontMesh fontMesh;
    private final SkyboxMesh skyboxMesh;
    private final BlockMesh handBlockMesh;
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
    private final Shader itemShader;
    private final Texture terrainTexture;
    private final Texture skinTexture;
    private final Texture defaultSkinTexture;
    private final Texture minecraftTitleTexture;
    private final Texture widgetsTexture;
    private final Texture dirtTexture;
    private final Texture crosshairTexuture;
    private final Texture placeholdTexture;
    private final Texture invetoryTexture;
    private final Texture iconsTexture;
    private final Texture guiBlocksTexture;
    private final CrosshairMesh crosshairMesh;
    private final ImageMesh imageMesh;
    private final ImageMesh screenMesh;
    private final FontManager fontManager;
    private final CFont font;
    private final Map<String, Texture> skinsMap;
    private final CubemapTexture panoramaTexture;
    private String emptyText;
    private Set<String> loadedSkins;

    public Renderer() {
        this.playerMesh = new PlayerMesh();
        this.imageMesh = new ImageMesh(0, 0, 0, 0);
        this.itemMesh = new ItemMesh(Material.DIAMOND_SWORD);
        this.font = new CFont(GameConfiguration.FONT_FILE_PATH, GameConfiguration.FONT_SIZE);
        this.fontMesh = new FontMesh(font);
        this.skyboxMesh = new SkyboxMesh();
        this.crosshairMesh = new CrosshairMesh();
        this.blockMesh = new BlockMesh();
        this.handMesh = new HandMesh();
        this.handBlockMesh = new BlockMesh(Material.STONE);
        this.loadedSkins = new HashSet<>();

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
        this.chunkShader = new Shader("res/shaders/chunk.vert", "res/shaders/chunk.frag");
        this.fontShader = new Shader("res/shaders/font.vert", "res/shaders/font.frag");
        this.nametagShader = new Shader("res/shaders/nametag.vert", "res/shaders/nametag.frag");
        this.nametagTextShader = new Shader("res/shaders/nametag_text.vert", "res/shaders/nametag_text.frag");
        this.skyboxShader = new Shader("res/shaders/skybox.vert", "res/shaders/skybox.frag");
        this.imageShader = new Shader("res/shaders/image.vert", "res/shaders/image.frag");
        this.waterShader = new Shader("res/shaders/water.vert", "res/shaders/water.frag");
        this.handShader = new Shader("res/shaders/hand.vert", "res/shaders/hand.frag");
        this.blockShader = new Shader("res/shaders/default.vert", "res/shaders/default.frag");
        this.crosshairShader = new Shader("res/shaders/cursor.vert", "res/shaders/default.frag");
        this.handBlockShader = new Shader("res/shaders/handblock.vert", "res/shaders/handblock.frag");
        this.itemShader = new Shader("res/shaders/item.vert", "res/shaders/item.frag");

        this.terrainTexture = new Texture("res/textures/terrain.png", 1);
        this.defaultSkinTexture = new Texture("res/textures/skin.png", 2);
        this.minecraftTitleTexture = new Texture("res/textures/gui/title/minecraft_title.png", 3);
        this.panoramaTexture = new CubemapTexture(panoramas, 4);
        this.widgetsTexture = new Texture("res/textures/gui/widgets.png", 5);
        this.skinTexture = new Texture(Game.getInstance().getPlayer().getSkinPath(), 6);
        this.crosshairTexuture = new Texture("res/textures/gui/crosshair.png", 7);
        this.placeholdTexture = new Texture("res/textures/gui/placehold.png", 8);
        this.invetoryTexture = new Texture("res/textures/gui/inventory.png", 9);
        this.iconsTexture = new Texture("res/textures/gui/icons.png", 10);
        this.guiBlocksTexture = new Texture("res/textures/gui/gui_blocks.png", 11);

        this.dirtTexture = new TextureBuilder().buildDirtBackgroundTexture();

        this.skinsMap = new HashMap<>();

        this.terrainTexture.load();
        this.defaultSkinTexture.load();
        this.minecraftTitleTexture.load();
        this.panoramaTexture.load();
        this.widgetsTexture.load();
        this.dirtTexture.load();
        this.skinTexture.load();
        this.crosshairTexuture.load();
        this.placeholdTexture.load();
        this.invetoryTexture.load();
        this.iconsTexture.load();
        this.guiBlocksTexture.load();
    }

    public void render(Camera camera, Player player) {

        Texture skinTexture;

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

        glActiveTexture(GL_TEXTURE0 + skinTexture.getSlot());
        skinTexture.bind();

        camera.matrix(playerShader, player);

        playerMesh.draw();

        skinTexture.unbind();

        this.renderNametag(camera, player);
    }

    public void renderNametag(Camera camera, Player player) {
        this.renderNametagBar(camera, player);
        this.renderNametagText(camera, player);
    }

    private void renderNametagBar(Camera camera, Player player) {

        NametagMesh nametagMesh = player.getNametagMesh();

        if (nametagMesh == null)
            return;

        nametagShader.enable();

        camera.matrixNametag(nametagShader, player);

        nametagMesh.draw();
    }

    private void renderNametagText(Camera camera, Player player) {
        Texture texture = font.getTexture();
        nametagTextShader.enable();
        nametagTextShader.sendInt("uTexture", texture.getSlot());

        glActiveTexture(GL_TEXTURE0 + texture.getSlot());
        texture.bind();
        camera.matrixNametag(nametagTextShader, player);

        fontManager.addText(fontMesh, player.getName(), 0, 0, 0, 1.0f, 0xFFFFFF, true);

        fontMesh.flush();

        texture.unbind();
    }

    public void render(Camera camera, Chunk chunk) {

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

        if (button.isHovered()) {
            imageMesh.texSubImage(0, 256.0f - 106, 200, 20, 256.0f, 256.0f);
        } else {
            imageMesh.texSubImage(0, 256.0f - 86, 200, 20, 256.0f, 256.0f);
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
        GameConfiguration gameConfiguration = Game.getInstance().getGameConfiguration();
        this.renderText(camera, "XYZ: " + player.getPosition().x + " / " + player.getPosition().y + " / " + player.getPosition().z, 0, GameConfiguration.WINDOW_HEIGHT - 100,0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
        this.renderText(camera, "FPS: " + frames, 0, GameConfiguration.WINDOW_HEIGHT - 120,0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
        this.renderText(camera, "Ping: " + player.getPing() + "ms", 0, GameConfiguration.WINDOW_HEIGHT - 140,0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
        BiomeManager biomeManager = new BiomeManager();
        this.renderText(camera, "BIOME: " + biomeManager.getBiome((int) player.getPosition().x, (int)player.getPosition().z).getBiomeName(), 0, GameConfiguration.WINDOW_HEIGHT - 160, 0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
        this.renderText(camera, "Entity Interpolation: " + gameConfiguration.isEntityInterpolationEnabled(), 0, GameConfiguration.WINDOW_HEIGHT - 180, 0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
    }

    public void renderInventory(Camera camera, Player player) {

        imageShader.enable();
        imageShader.sendInt("uTexture", invetoryTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + invetoryTexture.getSlot());
        invetoryTexture.bind();

        float inventoryWidth = 256.0f;
        float inventoryHeight = 256.0f;

        float inventoryX = (GameConfiguration.WINDOW_WIDTH - inventoryWidth) / 2;
        float inventoryY = (GameConfiguration.WINDOW_HEIGHT - inventoryHeight) / 2;

        imageMesh.texSubImage(0, 90, 177, 166, inventoryWidth, inventoryHeight);
        imageMesh.translate(imageShader, inventoryX, inventoryY, inventoryWidth, inventoryHeight);

        camera.matrixOrtho(imageShader, inventoryX, inventoryY);

        imageMesh.draw();

        invetoryTexture.unbind();
    }

    public void renderHotbar(Camera camera, Player player, Hotbar hotbar) {

        imageShader.enable();
        imageShader.sendInt("uTexture", widgetsTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + widgetsTexture.getSlot());
        widgetsTexture.bind();

        int hotbarWidth = 182;
        int hotbarHeight = 22;
        float scale = 2.0f;

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
        imageMesh.translate(imageShader, hotbarX - 2 + hotbar.getCurrentSlot() * 20 * scale, hotbarY - 1, slotSize, slotSize, scale);

        imageShader.sendFloat("depth", -10);
        camera.matrixOrtho(imageShader, 0, 0);
        imageMesh.draw();

        widgetsTexture.unbind();

        guiBlocksTexture.bind();

        for (int i = 0; i < hotbar.getCurrentSize(); i++) {
            ItemStack item = hotbar.getItems()[i];
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
        }

        int filledHearts = (int) player.getHealth() / 2;
        float missingHearts = player.getMaxHealth() - player.getHealth();

        glActiveTexture(GL_TEXTURE0 + iconsTexture.getSlot());
        iconsTexture.bind();

        imageShader.sendInt("uTexture", iconsTexture.getSlot());

        int iconSize = 9;

        imageMesh.texSubImage(16 + 4 * iconSize, 256.0f - iconSize, iconSize, iconSize, 256.0f, 256.0f);

        for (int i = 0; i < filledHearts; i++) {
            imageShader.sendFloat("depth", -10);
            imageMesh.texSubImage(16 + 0 * iconSize, 256.0f - iconSize, iconSize, iconSize, 256.0f, 256.0f);
            imageMesh.translate(imageShader, hotbarX + i * iconSize * scale, hotbarY + hotbarHeight * scale + 5, iconSize * scale, iconSize * scale);
            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();

            imageShader.sendFloat("depth", -9);
            imageMesh.texSubImage(16 + 4 * iconSize, 256.0f - iconSize, iconSize, iconSize, 256.0f, 256.0f);
            imageMesh.translate(imageShader, hotbarX + i * iconSize * scale, hotbarY + hotbarHeight * scale + 5, iconSize * scale, iconSize * scale);
            camera.matrixOrtho(imageShader, 0, 0);
            imageMesh.draw();
        }

        iconsTexture.unbind();

    }

    public void renderSelectedItem(Camera camera, Player player, Material material) {

        glDisable(GL_DEPTH_TEST);

        handBlockShader.enable();
        handBlockShader.sendInt("uTexture", terrainTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + terrainTexture.getSlot());
        terrainTexture.bind();

        handBlockMesh.update(handBlockShader, material);
        camera.matrixSelectedItem(player.getHand(), handBlockShader);

        handBlockMesh.draw();

        terrainTexture.unbind();

        glEnable(GL_DEPTH_TEST);

    }

    public void renderItemInHand(Camera camera, Player player) {

        glDisable(GL_DEPTH_TEST);

        itemShader.enable();
        itemShader.sendInt("uTexture", guiBlocksTexture.getSlot());
        //itemShader.sendFloat("depth", -30);

        glActiveTexture(GL_TEXTURE0 + guiBlocksTexture.getSlot());
        guiBlocksTexture.bind();

        camera.matrixSelectedItem(player.getHand(), itemShader);
        itemMesh.draw();

        guiBlocksTexture.unbind();
        glEnable(GL_DEPTH_TEST);

    }

    public Map<String, Texture> getSkinsMap() {
        return skinsMap;
    }
}
