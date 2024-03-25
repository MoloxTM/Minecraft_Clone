package fr.math.minecraft.server.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.MinecraftServer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SkinRequestHandler extends PacketHandler implements Runnable{
    public SkinRequestHandler(JsonNode packetData, InetAddress address, int clientPort) {
        super(packetData, address, clientPort);
    }

    @Override
    public void run() {
        String uuid = packetData.get("uuid").asText();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        File file = new File("skins/" + uuid + ".png");
        MinecraftServer server = MinecraftServer.getInstance();

        if (!file.exists()) {
            file = new File("skins/skin.png");
        }

        try {
            BufferedImage skin = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(skin, "png", baos);
            String base64Skin = Base64.getEncoder().encodeToString(baos.toByteArray());

            node.put("type", "SKIN_PACKET");
            node.put("skin", base64Skin);
            node.put("uuid", uuid);

            String skinData = mapper.writeValueAsString(node);

            byte[] buffer = skinData.getBytes(StandardCharsets.UTF_8);

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, clientPort);
            server.sendPacket(packet);
        } catch (IOException e) {
            byte[] buffer = "ERROR".getBytes(StandardCharsets.UTF_8);
            e.printStackTrace();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, clientPort);
            server.sendPacket(packet);
        }
    }
}
