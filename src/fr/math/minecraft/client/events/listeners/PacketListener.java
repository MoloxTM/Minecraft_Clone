package fr.math.minecraft.client.events.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.Renderer;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.events.ChunkPacketEvent;
import fr.math.minecraft.client.events.PlayerListPacketEvent;
import fr.math.minecraft.client.events.ServerStateEvent;
import fr.math.minecraft.client.events.SkinPacketEvent;
import fr.math.minecraft.client.network.FixedPacketSender;
import fr.math.minecraft.client.handler.PlayerMovementHandler;
import fr.math.minecraft.client.manager.ChunkManager;
import fr.math.minecraft.client.network.packet.ChunkACKPacket;
import fr.math.minecraft.client.network.packet.SkinRequestPacket;
import fr.math.minecraft.client.network.payload.StatePayload;
import fr.math.minecraft.client.texture.Texture;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.client.world.World;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.joml.Vector3i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class PacketListener implements PacketEventListener {

    private final Game game;
    private final static Logger logger = LoggerUtility.getClientLogger(PacketListener.class, LogType.TXT);

    public PacketListener() {
        this.game = Game.getInstance();
    }

    @Override
    public void onServerState(ServerStateEvent event) {
        StatePayload payload = event.getStatePayload();
        PlayerMovementHandler handler = game.getPlayerMovementHandler();

        handler.setLastServerState(payload);
    }

    @Override
    public void onPlayerListPacket(PlayerListPacketEvent event) {

        ArrayNode playersNode = event.getPlayers();

        for (int i = 0; i < playersNode.size(); i++) {
            JsonNode playerNode = playersNode.get(i);

            String uuid = playerNode.get("uuid").asText();
            Player player;

            if (uuid.equalsIgnoreCase(game.getPlayer().getUuid())) continue;

            synchronized (game.getPlayers()) {
                if (game.getPlayers().containsKey(uuid)) {
                    player = game.getPlayers().get(uuid);
                } else {
                    player = new Player(playerNode.get("name").asText());
                    player.setUuid(uuid);
                    SkinRequestPacket packet = new SkinRequestPacket(uuid);
                    FixedPacketSender.getInstance().enqueue(packet);
                    game.getPlayers().put(uuid, player);
                }
            }

            float playerX = playerNode.get("x").floatValue();
            float playerY = playerNode.get("y").floatValue();
            float playerZ = playerNode.get("z").floatValue();
            float pitch = playerNode.get("pitch").floatValue();
            float yaw = playerNode.get("yaw").floatValue();
            float bodyYaw = playerNode.get("bodyYaw").floatValue();

            boolean movingLeft = playerNode.get("movingLeft").asBoolean();
            boolean movingRight = playerNode.get("movingRight").asBoolean();
            boolean movingForward = playerNode.get("movingForward").asBoolean();
            boolean movingBackward = playerNode.get("movingBackward").asBoolean();

            player.getPosition().x = playerX;
            player.getPosition().y = playerY;
            player.getPosition().z = playerZ;

            player.setYaw(yaw);
            player.setBodyYaw(bodyYaw);
            player.setPitch(pitch);

            player.setMovingLeft(movingLeft);
            player.setMovingRight(movingRight);
            player.setMovingForward(movingForward);
            player.setMovingBackward(movingBackward);

        }
    }

    @Override
    public void onSkinPacket(SkinPacketEvent event) {
        String base64Skin = event.getSkin();

        if (base64Skin.contains("PLAYER_DOESNT_EXISTS") || base64Skin.contains("ERROR") || base64Skin.equalsIgnoreCase("TIMEOUT_REACHED")) {
            logger.error("Impossible d'envoyer le packet, le serveur a mis trop de temps à répondre ! (timeout)");
            return;
        }

        byte[] skinBytes = Base64.getDecoder().decode(base64Skin);
        try {
            BufferedImage skin = ImageIO.read(new ByteArrayInputStream(skinBytes));
            Renderer renderer = game.getRenderer();
            synchronized (renderer.getSkinsMap()) {
                renderer.getSkinsMap().put(event.getPlayerUuid(), new Texture(skin, 2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChunkPacket(ChunkPacketEvent event) {
        ChunkManager chunkManager = new ChunkManager();
        JsonNode chunkData = event.getChunkData();
        World world = game.getWorld();

        int chunkX = chunkData.get("x").asInt();
        int chunkY = chunkData.get("y").asInt();
        int chunkZ = chunkData.get("z").asInt();

        Chunk chunk = world.getChunk(chunkX, chunkY, chunkZ);

        if (chunk == null) {
            game.getChunkLoadingQueue().submit(() -> {
                chunkManager.loadChunkData(chunkData);
            });
        }

        ChunkACKPacket packet = new ChunkACKPacket(new Vector3i(chunkX, chunkY, chunkZ));
        FixedPacketSender.getInstance().enqueue(packet);

    }

}
