package fr.math.minecraft.client;

import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.fonts.CFont;
import fr.math.minecraft.client.manager.FontManager;
import fr.math.minecraft.client.meshs.FontMesh;
import fr.math.minecraft.client.meshs.PlayerMesh;
import fr.math.minecraft.client.world.Chunk;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    private final PlayerMesh playerMesh;
    private final FontMesh fontMesh;
    private final Shader playerShader;
    private final Shader chunkShader;
    private final Shader fontShader;
    private final Texture skinTexture;
    private final Texture terrainTexture;
    private final FontManager fontManager;
    private final CFont font;

    public Renderer() {
        this.playerMesh = new PlayerMesh();
        this.font = new CFont("res/fonts/Minecraft.ttf", 16);
        this.fontMesh = new FontMesh(font);

        this.fontManager = new FontManager();

        this.playerShader = new Shader("res/shaders/player.vert", "res/shaders/player.frag");
        this.chunkShader = new Shader("res/shaders/chunk.vert", "res/shaders/chunk.frag");
        this.fontShader = new Shader("res/shaders/font.vert", "res/shaders/font.frag");

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

    public void renderText(Camera camera, String text, int x, int y, int rgb) {
        this.renderText(camera, text, x, y, -10, rgb);
    }

    public void renderText(Camera camera, String text, int x, int y, int z, int rgb) {
        this.renderString(camera, text, x, y, z, rgb);
        this.renderString(camera, text, x + 2, y - 2, z, 0x555555);
    }

    private void renderString(Camera camera, String text, int x, int y, int z, int rgb) {
        Texture texture = font.getTexture();

        fontManager.addText(fontMesh, text, x, y, z, 1.0f, rgb);

        fontShader.enable();
        fontShader.sendInt("uTexture", texture.getSlot());

        glActiveTexture(GL_TEXTURE0 + texture.getSlot());
        texture.bind();

        fontMesh.flush();
        camera.matrix(fontShader, text);

        texture.unbind();
    }

}
