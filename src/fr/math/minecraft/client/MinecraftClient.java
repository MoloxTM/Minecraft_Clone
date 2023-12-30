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

    private static final int MAX_RESPONSE_LENGTH = 4096;
    private DatagramSocket socket;
    private InetAddress address;
    private final int serverPort;
    private final Logger logger;
    private static final String IP_SERVER = "localhost";

    public MinecraftClient(int serverPort) {
        logger = LoggerUtility.getClientLogger(MinecraftClient.class, LogType.TXT);
        this.serverPort = serverPort;
        this.connect();
    }

    public void connect()  {
        try {
            this.socket = new DatagramSocket();
            this.address = InetAddress.getByName(IP_SERVER);

        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendString(String message) throws IOException {
        return this.sendBytes(message.getBytes(StandardCharsets.UTF_8));
    }

    public String sendBytes(byte[] buffer) throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length, this.address, this.serverPort);
        socket.send(packet);
        buffer = new byte[MAX_RESPONSE_LENGTH];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        return new String(packet.getData(), 0, packet.getLength()).trim();
    }

}
