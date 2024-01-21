package fr.math.minecraft.client;

import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.fonts.CFont;
import fr.math.minecraft.client.gui.BlockButton;
import fr.math.minecraft.client.manager.FontManager;
import fr.math.minecraft.client.meshs.*;
import fr.math.minecraft.client.packet.SkinRequestPacket;
import fr.math.minecraft.client.texture.CubemapTexture;
import fr.math.minecraft.client.texture.Texture;
import fr.math.minecraft.client.world.Chunk;

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
    private final Texture terrainTexture;
    private final Texture defaultSkinTexture;
    private final Texture minecraftTitleTexture;
    private final Texture widgetsTexture;
    private final ImageMesh minecraftTitleMesh;
    private final FontManager fontManager;
    private final CFont font;
    private final Map<String, Texture> skinsMap;
    private final CubemapTexture panoramaTexture;

    public Renderer() {
        this.playerMesh = new PlayerMesh();
        this.font = new CFont(GameConfiguration.FONT_FILE_PATH, GameConfiguration.FONT_SIZE);
        this.fontMesh = new FontMesh(font);
        this.skyboxMesh = new SkyboxMesh();
        this.buttonMesh = new ButtonMesh();

        int titleWidth = (int) (1002 * .5f);
        int titleHeight = (int) (197 * .5f);

        this.minecraftTitleMesh = new ImageMesh(
                titleWidth,
                titleHeight,
                (int) (GameConfiguration.WINDOW_WIDTH - 1002 * .5f) / 2,
                (int) (GameConfiguration.WINDOW_HEIGHT - 197 * .5f - 100)
        );

        this.fontManager = new FontManager();

        this.playerShader = new Shader("res/shaders/player.vert", "res/shaders/player.frag");
        this.chunkShader = new Shader("res/shaders/chunk.vert", "res/shaders/chunk.frag");
        this.fontShader = new Shader("res/shaders/font.vert", "res/shaders/font.frag");
        this.nametagShader = new Shader("res/shaders/nametag.vert", "res/shaders/nametag.frag");
        this.nametagTextShader = new Shader("res/shaders/nametag_text.vert", "res/shaders/nametag_text.frag");
        this.skyboxShader = new Shader("res/shaders/skybox.vert", "res/shaders/skybox.frag");
        this.imageShader = new Shader("res/shaders/image.vert", "res/shaders/image.frag");

        this.terrainTexture = new Texture("res/textures/terrain.png", 1);
        this.defaultSkinTexture = new Texture("res/textures/skin.png", 2);
        this.minecraftTitleTexture = new Texture("res/textures/gui/title/minecraft_title.png", 3);
        this.widgetsTexture = new Texture("res/textures/gui/widgets.png", 5);

        String[] panoramas = new String[6];
        int[] index = new int[]{ 1, 3, 5, 4, 0, 2 };

        for (int i = 0; i < index.length; i++) {
            panoramas[i] = "res/textures/gui/title/panorama_" + index[i] + ".png";
        }

        this.panoramaTexture = new CubemapTexture(panoramas, 4);
        this.skinsMap = new HashMap<>();

        this.terrainTexture.load();
        this.defaultSkinTexture.load();
        this.minecraftTitleTexture.load();
        this.panoramaTexture.load();
        this.widgetsTexture.load();
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

        chunkShader.enable();
        chunkShader.sendInt("uTexture", terrainTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + terrainTexture.getSlot());
        terrainTexture.bind();

        camera.matrix(chunkShader, chunk);

        chunk.getChunkMesh().draw();

        terrainTexture.unbind();
    }

    public void renderText(Camera camera, String text, float x, float y, int rgb) {
        this.renderText(camera, text, x, y, -10, rgb);
    }

    public void renderText(Camera camera, String text, float x, float y, float z, int rgb) {
        this.renderString(camera, text, x, y, z, 0.0f, rgb);
        this.renderString(camera, text, x + 2, y - 2, z, 0.0f, 0x555555);
    }

    public void renderText(Camera camera, String text, float x, float y, float z, float rotationAngle, int rgb) {
        this.renderString(camera, text, x, y, z, rotationAngle, rgb);
        this.renderString(camera, text, x + 2, y - 2, z, rotationAngle, 0x555555);
    }

    private void renderString(Camera camera, String text, float x, float y, float z, float rotationAngle, int rgb) {
        Texture texture = font.getTexture();

        fontShader.enable();
        fontShader.sendInt("uTexture", texture.getSlot());

        glActiveTexture(GL_TEXTURE0 + texture.getSlot());
        texture.bind();
        camera.matrixOrtho(fontShader, rotationAngle);

        fontManager.addText(fontMesh, text, x, y, z, 0.3f, rgb);

        fontMesh.flush();

        texture.unbind();
    }

    public void renderMainMenu(Camera camera, String splash) {

        glDepthFunc(GL_LEQUAL);

        skyboxShader.enable();
        skyboxShader.sendInt("uTexture", panoramaTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + panoramaTexture.getSlot());
        panoramaTexture.bind();

        camera.matrixSkybox(skyboxShader);
        skyboxMesh.draw();

        panoramaTexture.unbind();
        glDepthFunc(GL_LESS);

        int offset = 5;

        this.renderText(camera, "Minecraft 1.0.0", offset, offset, 0xFFFFFF);
        this.renderText(camera, "Copyright Me and the hoes.", GameConfiguration.WINDOW_WIDTH - fontManager.getTextWidth(fontMesh, 0.3f, "Copyright Me and the hoes.") - offset, offset, 0xFFFFFF);
        this.renderText(camera, splash, (float)((GameConfiguration.WINDOW_WIDTH * 0.75) - (fontManager.getTextWidth(fontMesh, 0.3f, splash))), (float) (GameConfiguration.WINDOW_HEIGHT - (GameConfiguration.WINDOW_HEIGHT* 0.25)), 0xFFFF00);
        this.renderImage(camera);
        this.renderButton(
                camera,
                "Jouer",
                (GameConfiguration.WINDOW_WIDTH - ButtonMesh.BUTTON_WIDTH) / 2,
                (GameConfiguration.WINDOW_HEIGHT - ButtonMesh.BUTTON_HEIGHT) / 2
        );
    }

    public void renderImage(Camera camera) {
        imageShader.enable();
        imageShader.sendInt("uTexture", minecraftTitleTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + minecraftTitleTexture.getSlot());
        minecraftTitleTexture.bind();

        camera.matrixOrtho(imageShader);

        minecraftTitleMesh.draw();

        minecraftTitleTexture.unbind();
    }

    public void renderButton(Camera camera, String text, float x, float y) {
        imageShader.enable();
        imageShader.sendInt("uTexture", widgetsTexture.getSlot());

        glActiveTexture(GL_TEXTURE0 + widgetsTexture.getSlot());
        widgetsTexture.bind();

        camera.matrixOrtho(imageShader, x, y);

        buttonMesh.draw();

        widgetsTexture.unbind();

        int width = fontManager.getTextWidth(fontMesh,.25f, text);
        float height = fontManager.getTextHeight(fontMesh,.25f, text);

        this.renderText(camera, text, x + (ButtonMesh.BUTTON_WIDTH - width) / 2.0f, y + (ButtonMesh.BUTTON_HEIGHT - height) / 2.0f, -9, 0xFFFFFF, fontMesh);
    }
}
