package fr.math.minecraft.client.events.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.Renderer;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.client.events.*;
import fr.math.minecraft.client.network.FixedPacketSender;
import fr.math.minecraft.client.handler.PlayerMovementHandler;
import fr.math.minecraft.client.manager.ChunkManager;
import fr.math.minecraft.client.network.packet.ChunkACKPacket;
import fr.math.minecraft.client.network.packet.SkinRequestPacket;
import fr.math.minecraft.client.network.payload.StatePayload;
import fr.math.minecraft.client.texture.Texture;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.PlayerAction;
import fr.math.minecraft.shared.inventory.DroppedItem;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.Coordinates;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.joml.Vector3f;
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

            int rayX = playerNode.get("rx").intValue();
            int rayY = playerNode.get("ry").intValue();
            int rayZ = playerNode.get("rz").intValue();

            boolean movingLeft = playerNode.get("movingLeft").asBoolean();
            boolean movingRight = playerNode.get("movingRight").asBoolean();
            boolean movingForward = playerNode.get("movingForward").asBoolean();
            boolean movingBackward = playerNode.get("movingBackward").asBoolean();

            float pitch = playerNode.get("pitch").floatValue();
            float yaw = playerNode.get("yaw").floatValue();
            float bodyYaw = playerNode.get("bodyYaw").floatValue();

            String actionId = playerNode.get("action").asText();
            int spriteIndex = playerNode.get("spriteIndex").asInt();

            PlayerAction action = null;
            if (!actionId.equalsIgnoreCase("NONE")) {
                action = PlayerAction.valueOf(actionId);
            }

            GameConfiguration gameConfiguration = GameConfiguration.getInstance();

            if (gameConfiguration.isEntityInterpolationEnabled()) {
                EntityUpdate entityUpdate = new EntityUpdate(new Vector3f(playerX, playerY, playerZ), yaw, pitch, bodyYaw);
                player.setLastUpdate(entityUpdate);
            } else {
                player.getPosition().x = playerX;
                player.getPosition().y = playerY;
                player.getPosition().z = playerZ;
            }

            player.setMovingRight(movingRight);
            player.setMovingLeft(movingLeft);
            player.setMovingBackward(movingBackward);
            player.setMovingForward(movingForward);

            player.setYaw(yaw);
            player.setBodyYaw(bodyYaw);
            player.setPitch(pitch);

            player.getBuildRay().getBlockWorldPosition().x = rayX;
            player.getBuildRay().getBlockWorldPosition().y = rayY;
            player.getBuildRay().getBlockWorldPosition().z = rayZ;

            player.setAction(action);
            player.getSprite().setIndex(spriteIndex);
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
        Player player = game.getPlayer();

        int chunkX = chunkData.get("x").asInt();
        int chunkY = chunkData.get("y").asInt();
        int chunkZ = chunkData.get("z").asInt();

        Chunk chunk = world.getChunk(chunkX, chunkY, chunkZ);

        if (chunk == null) {
            player.getReceivedChunks().add(new Coordinates(chunkX, chunkY, chunkZ));
            game.getPacketPool().submit(() -> {
                chunkManager.loadChunkData(chunkData);
            });
        }

        ChunkACKPacket packet = new ChunkACKPacket(new Vector3i(chunkX, chunkY, chunkZ));
        FixedPacketSender.getInstance().enqueue(packet);

    }

    @Override
    public void onPlayerState(PlayerStateEvent event) {

        StatePayload payload = event.getStatePayload();
        Player player = event.getPlayer();

        player.getPosition().x = payload.getPosition().x;
        player.getPosition().y = payload.getPosition().y;
        player.getPosition().z = payload.getPosition().z;

        player.getVelocity().x = payload.getVelocity().x;
        player.getVelocity().y = payload.getVelocity().y;
        player.getVelocity().z = payload.getVelocity().z;

        player.setYaw(payload.getYaw());
        player.setPitch(payload.getPitch());

    }

    @Override
    public void onDroppedItemState(DroppedItemEvent event) {

        World world = event.getWorld();
        ArrayNode itemsData = event.getItemsData();
        synchronized (world.getDroppedItems()) {
            for (int i = 0; i < itemsData.size(); i++) {

                JsonNode itemNode = itemsData.get(i);

                String uuid = itemNode.get("uuid").asText();
                float itemX = itemNode.get("x").floatValue();
                float itemY = itemNode.get("y").floatValue();
                float itemZ = itemNode.get("z").floatValue();
                byte materialID = (byte) itemNode.get("materialID").asInt();
                Material material = Material.getMaterialById(materialID);
                float rotationAngle = itemNode.get("rotationAngle").floatValue();

                DroppedItem droppedItem = world.getDroppedItems().get(uuid);
                Vector3f position = new Vector3f(itemX, itemY, itemZ);

                if (droppedItem == null) {
                    droppedItem = new DroppedItem(uuid, position, material);
                    System.out.println("Ajout " + uuid);

                    world.getDroppedItems().put(uuid, droppedItem);
                } else {
                    droppedItem.setRotationAngle(rotationAngle);
                    droppedItem.getLastPosition().x = position.x;
                    droppedItem.getLastPosition().y = position.y;
                    droppedItem.getLastPosition().z = position.z;
                }

            }
        }
    }
}
