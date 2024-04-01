package fr.math.minecraft.client.network.packet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.GameState;
import fr.math.minecraft.client.MinecraftClient;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.client.gui.menus.ConnectionMenu;
import fr.math.minecraft.client.gui.menus.Menu;
import fr.math.minecraft.client.network.FixedPacketSender;
import fr.math.minecraft.client.handler.TickHandler;
import fr.math.minecraft.client.manager.MenuManager;
import fr.math.minecraft.client.network.PacketReceiver;
import fr.math.minecraft.client.world.loader.ChunkMeshLoader;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.shared.world.World;
import fr.math.minecraft.shared.world.WorldLoader;
import org.apache.log4j.Logger;
import org.joml.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static org.lwjgl.glfw.GLFW.*;

public class ConnectionInitPacket extends ClientPacket implements Runnable {

    private final ObjectMapper mapper;
    private final Player player;
    private final static Logger logger = LoggerUtility.getClientLogger(ConnectionInitPacket.class, LogType.TXT);;
    private String giftedId;

    public ConnectionInitPacket(Player player) {
        this.mapper = new ObjectMapper();
        this.player = player;
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

            PacketReceiver packetReceiver = PacketReceiver.getInstance();
            packetReceiver.start();

            FixedPacketSender packetHandler = FixedPacketSender.getInstance();
            packetHandler.start();

            TickHandler tickHandler = new TickHandler();
            tickHandler.start();

            ChunkMeshLoader meshThread = new ChunkMeshLoader(game);
            meshThread.start();

        } catch (RuntimeException e) {
            e.printStackTrace();
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
            logger.info("Tentative de connexion...");
            String data = client.sendString(message);

            if (data.equalsIgnoreCase("TIMEOUT_REACHED")) {
                throw new RuntimeException("Impossible d'envoyer le packet, le serveur a mis trop de temps à répondre ! (timeout)");
            }

            JsonNode serverData = mapper.readTree(data);
            logger.info("Connexion initié, Réponse : " + serverData);

            if (serverData.get("uuid") == null) {
                client.connect();
                throw new RuntimeException("Impossible de se connecter au serveur, veuillez réessayez");
            }

            String uuid = serverData.get("uuid").asText();

            float spawnX = serverData.get("spawnX").floatValue();
            float spawnY = serverData.get("spawnY").floatValue();
            float spawnZ = serverData.get("spawnZ").floatValue();
            JsonNode worldData = serverData.get("worldData");

            WorldLoader worldLoader = new WorldLoader();
            World world = Game.getInstance().getWorld();

            worldLoader.load(world, worldData);
            logger.info("Données du monde fournies par le serveur chargées avec succès !");

            MenuManager menuManager = Game.getInstance().getMenuManager();
            Menu menu = menuManager.getOpenedMenu();

            if (menu instanceof ConnectionMenu) {
                ConnectionMenu connectionMenu = (ConnectionMenu) menu;
                connectionMenu.getTitle().setText("Construction du monde...");
            }
            float seed = serverData.get("seed").floatValue();
            world.setSeed(seed);
            world.buildSpawn();
            world.buildSpawnMesh();

            if (uuid.contains("USERNAME_NOT_AVAILABLE")) {
                throw new RuntimeException("Le joueur " + player.getName() + " est déjà connecté !");
            }

            player.setUuid(uuid.substring(0, 36));

            player.getPosition().x = spawnX;
            player.getPosition().y = spawnY;
            player.getPosition().z = spawnZ;

            Player dummy = new Player("dummy");
            dummy.setUuid("1");
            dummy.setPosition(new Vector3f(0, 65, 0));
            System.out.println("Dummy pos : " + dummy.getPosition());
            Game.getInstance().getPlayers().put("1", dummy);

            Game.getInstance().getCamera().update(player);

            logger.info("Connection initié, ID offert : " + player.getUuid());

            PlayersListPacket packet = new PlayersListPacket();
            packet.send();

            ObjectNode node = mapper.createObjectNode();
            node.put("type", "CONNECTION_INIT_ACK");
            node.put("uuid", player.getUuid());

            client.sendMessage(mapper.writeValueAsString(node));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Une erreur inattendue s'est produite.");
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
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getResponse() {
        return null;
    }

    public String getGiftedId() {
        return giftedId;
    }
}
