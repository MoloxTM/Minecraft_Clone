package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class PlayersListPacket extends ClientPacket {

    private final ObjectMapper mapper;
    private final static Logger logger = LoggerUtility.getClientLogger(PlayersListPacket.class, LogType.TXT);;

    public PlayersListPacket() {
        this.mapper = new ObjectMapper();
    }

    private BufferedImage loadSkin(String base64Skin) {
        byte[] bytes = Base64.getDecoder().decode(base64Skin);
        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            return null;
        }
    }

    /*
    @Override
    public void send() {
        Game game = Game.getInstance();
        MinecraftClient client = game.getClient();
        String message = this.toJSON();
        try {
            String players = client.sendString(message);

            System.out.println(players);

            JsonNode playersData = mapper.readTree(players);
            ArrayNode playersNode = (ArrayNode) playersData.get("players");
            game.getPlayers().clear();
            for (int i = 0; i < playersNode.size(); i++) {
                JsonNode playerNode = playersNode.get(i);

                String uuid = playerNode.get("uuid").asText();
                Player player;

                if (uuid.equalsIgnoreCase(game.getPlayer().getUuid())) continue;

                if (game.getPlayers().containsKey(uuid)) {
                    player = game.getPlayers().get(uuid);
                } else {
                    player = new Player(playerNode.get("name").asText());
                    player.setUuid(uuid);
                    SkinRequestPacket packet = new SkinRequestPacket(uuid);
                    FixedPacketSender.getInstance().enqueue(packet);
                    game.getPlayers().put(uuid, player);
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
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
     */

    @Override
    public String toJSON() {
        ObjectNode packetNode = mapper.createObjectNode();
        packetNode.put("type", "PLAYERS_LIST_REQUEST");
        packetNode.put("uuid", Game.getInstance().getPlayer().getUuid());
        packetNode.put("clientVersion", "1.0.0");
        try {
            return mapper.writeValueAsString(packetNode);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public String getResponse() {
        return null;
    }

}
