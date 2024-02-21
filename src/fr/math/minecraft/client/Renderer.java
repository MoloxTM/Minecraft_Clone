package fr.math.minecraft.client;

import fr.math.minecraft.client.builder.TextureBuilder;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.fonts.CFont;
import fr.math.minecraft.client.gui.buttons.BackToTitleButton;
import fr.math.minecraft.client.gui.buttons.BlockButton;
import fr.math.minecraft.client.gui.GuiText;
import fr.math.minecraft.client.gui.menus.MainMenu;
import fr.math.minecraft.client.gui.menus.Menu;
import fr.math.minecraft.client.gui.menus.MenuBackgroundType;
import fr.math.minecraft.client.manager.FontManager;
import fr.math.minecraft.client.meshs.*;
import fr.math.minecraft.client.packet.SkinRequestPacket;
import fr.math.minecraft.client.texture.CubemapTexture;
import fr.math.minecraft.client.texture.Texture;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.server.manager.BiomeManager;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    private final PlayerMesh playerMesh;
    private final FontMesh fontMesh;
    private final SkyboxMesh skyboxMesh;
    private final ButtonMesh buttonMesh;
    private final Shader playerShader;
    private final Shader chunkShader;
    private final Shader fontShader;
    private final Shader imageShader;
    private final Shader nametagTextShader;
    private final Shader nametagShader;
    private final Shader skyboxShader;
    private final Shader waterShader;
    private final Texture terrainTexture;
    private final Texture defaultSkinTexture;
    private final Texture minecraftTitleTexture;
    private final Texture widgetsTexture;
    private final Texture dirtTexture;
    private final ImageMesh minecraftTitleMesh;
    private final ImageMesh screenMesh;
    private final FontManager fontManager;
    private final CFont font;
    private final Map<String, Texture> skinsMap;
    private final CubemapTexture panoramaTexture;
    private String emptyText;
    private static int count = 0;

    public Renderer() {
        this.playerMesh = new PlayerMesh();
        this.font = new CFont(GameConfiguration.FONT_FILE_PATH, GameConfiguration.FONT_SIZE);
        this.fontMesh = new FontMesh(font);
        this.skyboxMesh = new SkyboxMesh();

        for (int i = 0; i < 256; i++) {
            emptyText += " ";
        }

        String[] panoramas = new String[6];
        int[] index = new int[]{ 1, 3, 5, 4, 0, 2 };

        for (int i = 0; i < index.length; i++) {
            panoramas[i] = "res/textures/gui/title/panorama_" + index[i] + ".png";
        }

        this.buttonMesh = new ButtonMesh();

        int titleWidth = (int) (1002 * .5f);
        int titleHeight = (int) (197 * .5f);

        this.minecraftTitleMesh = new ImageMesh(
                titleWidth,
                titleHeight,
                GameConfiguration.WINDOW_CENTER_X - titleWidth * .5f ,
                GameConfiguration.WINDOW_HEIGHT - titleHeight * .5f - 150
        );
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

        this.terrainTexture = new Texture("res/textures/terrain.png", 1);
        this.defaultSkinTexture = new Texture("res/textures/skin.png", 2);
        this.minecraftTitleTexture = new Texture("res/textures/gui/title/minecraft_title.png", 3);
        this.panoramaTexture = new CubemapTexture(panoramas, 4);
        this.widgetsTexture = new Texture("res/textures/gui/widgets.png", 5);
        this.dirtTexture = new TextureBuilder().buildDirtBackgroundTexture();

        this.skinsMap = new HashMap<>();

        this.terrainTexture.load();
        this.defaultSkinTexture.load();
        this.minecraftTitleTexture.load();
        this.panoramaTexture.load();
        this.widgetsTexture.load();
        this.dirtTexture.load();
    }

    public void render(Camera camera, Player player) {

        Texture skinTexture;

        if (skinsMap.containsKey(player.getUuid())) {
            skinTexture = skinsMap.get(player.getUuid());
        } else {
            SkinRequestPacket packet = new SkinRequestPacket(player.getUuid());
            packet.send();
            BufferedImage skin = packet.getSkin();
            if (skin != null) {
                player.setSkin(skin);
                skinTexture = new Texture(player.getSkin(), 2);
                skinTexture.load();
                skinsMap.put(player.getUuid(), skinTexture);
            } else {
                skinTexture = defaultSkinTexture;
            }
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
        Player player = Game.getInstance().getPlayer();
        chunkShader.enable();
        chunkShader.sendInt("uTexture", terrainTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + terrainTexture.getSlot());
        terrainTexture.bind();

        camera.matrix(chunkShader, chunk);

        chunk.getChunkMesh().draw();
        terrainTexture.unbind();
    }

    public void renderWater(Camera camera, Chunk chunk) {

        //chunkShader.enable();
        waterShader.enable();

        //chunkShader.sendInt("uTexture", terrainTexture.getSlot());
        waterShader.sendInt("uTexture", terrainTexture.getSlot());


        glActiveTexture(GL_TEXTURE0 + terrainTexture.getSlot());
        terrainTexture.bind();

        //camera.matrix(chunkShader, chunk);
        camera.matrixWater(waterShader, camera, chunk);

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

        camera.matrixOrtho(imageShader, button.getX(), button.getY());

        int textColor = 0xFFFFFF;

        if (button.isHovered()) {
            textColor = 0xFFFF00;
            buttonMesh.hover();
        } else {
            buttonMesh.unhover();
        }

        buttonMesh.draw();

        widgetsTexture.unbind();

        float width = fontManager.getTextWidth(fontMesh,GameConfiguration.DEFAULT_SCALE, button.getText());
        float height = fontManager.getTextHeight(fontMesh,GameConfiguration.DEFAULT_SCALE, button.getText());

        this.renderText(camera, button.getText(), button.getX() + (ButtonMesh.BUTTON_WIDTH - width) / 2.0f, button.getY() + (ButtonMesh.BUTTON_HEIGHT - height) / 2.0f, button.getZ() + 1, textColor, GameConfiguration.DEFAULT_SCALE);
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
            this.renderImage(camera, minecraftTitleMesh, minecraftTitleTexture);
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

        camera.matrixOrtho(imageShader, 0, 0);
        screenMesh.draw();

        dirtTexture.unbind();
    }

    public void renderDebugTools(Camera camera, Player player, int frames) {
        this.renderText(camera, "XYZ: " + player.getPosition().x + " / " + player.getPosition().y + " / " + player.getPosition().z, 0, GameConfiguration.WINDOW_HEIGHT - 100,0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
        this.renderText(camera, "FPS: " + frames, 0, GameConfiguration.WINDOW_HEIGHT - 120,0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
        BiomeManager biomeManager = new BiomeManager();
        this.renderText(camera, "BIOME: " + biomeManager.getBiome((int) player.getPosition().x, (int)player.getPosition().z).getBiomeName(), 0, GameConfiguration.WINDOW_HEIGHT - 140, 0xFFFFFF, GameConfiguration.DEFAULT_SCALE);
    }
}
