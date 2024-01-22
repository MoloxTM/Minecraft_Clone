package fr.math.minecraft.client.packet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.GameState;
import fr.math.minecraft.client.MinecraftClient;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.gui.menus.ConnectionMenu;
import fr.math.minecraft.client.gui.menus.Menu;
import fr.math.minecraft.client.manager.MenuManager;
import fr.math.minecraft.client.tick.TickHandler;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static org.lwjgl.glfw.GLFW.*;

public class ConnectionInitPacket extends Thread implements ClientPacket {

    private final ObjectMapper mapper;
    private final Player player;
    private final Logger logger;
    private String giftedId;

    public ConnectionInitPacket(Player player) {
        this.mapper = new ObjectMapper();
        this.player = player;
        this.logger = LoggerUtility.getClientLogger(ConnectionInitPacket.class, LogType.TXT);
    }

    @Override
    public void run() {

        Game game = Game.getInstance();
        Camera camera = game.getCamera();
        MenuManager menuManager = game.getMenuManager();

        try {
            this.send();

            menuManager.close(ConnectionMenu.class);
            glfwSetInputMode(game.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
            game.setState(GameState.PLAYING);

            player.setYaw(0.0f);
            camera.update(player);

            TickHandler tickHandler = new TickHandler();
            tickHandler.start();
        } catch (RuntimeException e) {
            Menu menu = menuManager.getOpenedMenu();

            if (menu instanceof ConnectionMenu) {
                ConnectionMenu connectionMenu = (ConnectionMenu) menu;
                connectionMenu.getTitle().setText("Impossible de se connecter au serveur (timeout)");
            }
        }
    }

    @Override
    public void send() {
        MinecraftClient client = Game.getInstance().getClient();
        String message = this.toJSON();

        if (message == null)
            return;

        try {
            String id = client.sendString(message);

            if (id.contains("USERNAME_NOT_AVAILABLE")) {
                throw new RuntimeException("Le joueur " + player.getName() + " est déjà connecté !");
            }

            if (id.equalsIgnoreCase("TIMEOUT_REACHED")) {
                throw new RuntimeException("Impossible d'envoyer le packet, le serveur a mis trop de temps à répondre ! (timeout)");
            }

            player.setUuid(id.substring(0, 36));
            logger.info("Connection initié, ID offert : " + player.getUuid());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String toJSON() {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "CONNECTION_INIT");
        node.put("playerName", player.getName());
        node.put("clientVersion", "1.0.0");

        BufferedImage skin = player.getSkin();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ImageIO.write(skin, "png", baos);
            node.put("skin", Base64.getEncoder().encodeToString(baos.toByteArray()));
            baos.close();
            return mapper.writeValueAsString(node);
        } catch (IOException e) {
            return null;
        }
    }

    public String getGiftedId() {
        return giftedId;
    }
}
