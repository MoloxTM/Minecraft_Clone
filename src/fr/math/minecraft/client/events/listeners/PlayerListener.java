package fr.math.minecraft.client.events.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.events.PlayerJoinEvent;
import fr.math.minecraft.client.events.PlayerMoveEvent;
import fr.math.minecraft.client.manager.WorldManager;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class PlayerListener implements EventListener {

    private final WorldManager worldManager;
    private static final Logger logger = LoggerUtility.getClientLogger(PlayerListener.class, LogType.TXT);

    public PlayerListener() {
        this.worldManager = new WorldManager();
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
        // worldManager.loadChunks();
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {

        JsonNode playerData = event.getPlayerData().get("playerData");
        String playerName = playerData.get("name").asText();
        String uuid = playerData.get("uuid").asText();
        String base64Skin = playerData.get("skin").asText();
        Game game = Game.getInstance();

        logger.info(playerName + " a rejoint le serveur !");

        Player player = new Player(playerName);
        player.setUuid(uuid);

        byte[] skinBytes = Base64.getDecoder().decode(base64Skin);
        try {
            BufferedImage skin = ImageIO.read(new ByteArrayInputStream(skinBytes));
            player.setSkin(skin);
            synchronized (game.getPlayers()) {
                game.getPlayers().put(uuid, player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
