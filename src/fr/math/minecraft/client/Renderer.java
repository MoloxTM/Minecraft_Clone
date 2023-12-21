package fr.math.minecraft.client;

import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.meshs.PlayerMesh;
import fr.math.minecraft.client.world.Chunk;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    private final PlayerMesh playerMesh;
    private final Shader playerShader;
    private final Shader chunkShader;
    private final Texture skinTexture;
    private final Texture terrainTexture;

    public Renderer() {
        this.playerMesh = new PlayerMesh();

        this.playerShader = new Shader("res/shaders/player.vert", "res/shaders/player.frag");
        this.chunkShader = new Shader("res/shaders/chunk.vert", "res/shaders/chunk.frag");

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

}
