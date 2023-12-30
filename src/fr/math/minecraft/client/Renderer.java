package fr.math.minecraft.client;

import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.fonts.CFont;
import fr.math.minecraft.client.manager.FontManager;
import fr.math.minecraft.client.meshs.FontMesh;
import fr.math.minecraft.client.meshs.NametagMesh;
import fr.math.minecraft.client.meshs.PlayerMesh;
import fr.math.minecraft.client.world.Chunk;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    private final PlayerMesh playerMesh;
    private final FontMesh fontMesh;
    private final Shader playerShader;
    private final Shader chunkShader;
    private final Shader fontShader;
    private final Shader nametagTextShader;
    private final Shader nametagShader;
    private final Texture skinTexture;
    private final Texture terrainTexture;
    private final FontManager fontManager;
    private final CFont font;

    public Renderer() {
        this.playerMesh = new PlayerMesh();
        this.font = new CFont(GameConfiguration.FONT_FILE_PATH, GameConfiguration.FONT_SIZE);
        this.fontMesh = new FontMesh(font);

        this.fontManager = new FontManager();

        this.playerShader = new Shader("res/shaders/player.vert", "res/shaders/player.frag");
        this.chunkShader = new Shader("res/shaders/chunk.vert", "res/shaders/chunk.frag");
        this.fontShader = new Shader("res/shaders/font.vert", "res/shaders/font.frag");
        this.nametagShader = new Shader("res/shaders/nametag.vert", "res/shaders/nametag.frag");
        this.nametagTextShader = new Shader("res/shaders/nametag_text.vert", "res/shaders/nametag_text.frag");

        this.terrainTexture = new Texture("res/textures/terrain.png", 1);
        this.skinTexture = new Texture("res/textures/skin.png", 2);

        this.skinTexture.load();
        this.terrainTexture.load();
    }

    public void render(Camera camera, Player player) {
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

        fontManager.addText(fontMesh, player.getName(), 0, 0, 0, 1.0f, 0xFFFFFF, true);

        nametagTextShader.enable();
        nametagTextShader.sendInt("uTexture", texture.getSlot());

        glActiveTexture(GL_TEXTURE0 + texture.getSlot());
        texture.bind();
        camera.matrixNametag(nametagTextShader, player);

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
        this.renderString(camera, text, x, y, z, rgb);
        this.renderString(camera, text, x + 2, y - 2, z, 0x555555);
    }

    private void renderString(Camera camera, String text, float x, float y, float z, int rgb) {
        Texture texture = font.getTexture();

        fontManager.addText(fontMesh, text, x, y, z, 0.25f, rgb);

        fontShader.enable();
        fontShader.sendInt("uTexture", texture.getSlot());

        glActiveTexture(GL_TEXTURE0 + texture.getSlot());
        texture.bind();

        fontMesh.flush();
        camera.matrix(fontShader, text);

        texture.unbind();
    }

}
