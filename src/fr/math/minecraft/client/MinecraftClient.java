package fr.math.minecraft.client;

import fr.math.minecraft.client.packet.ConnectionInitPacket;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.joml.Vector3f;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class MinecraftClient {

    private static final int MAX_RESPONSE_LENGTH = 2048;
    private DatagramSocket socket;
    private InetAddress address;
    private final int serverPort;
    private final Logger logger;

    public MinecraftClient(int serverPort) {
        logger = LoggerUtility.getClientLogger(MinecraftClient.class, LogType.TXT);
        this.serverPort = serverPort;
        this.connect();
    }

    public void connect()  {
        try {
            this.socket = new DatagramSocket();
            this.address = InetAddress.getByName("localhost");

        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }
        logger.info("Connexion établie avec le serveur");
    }

    public String sendString(String message) throws IOException {
        byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length, this.address, this.serverPort);
        socket.send(packet);
        buffer = new byte[MAX_RESPONSE_LENGTH];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        return new String(packet.getData(), 0, buffer.length);
    }

}
