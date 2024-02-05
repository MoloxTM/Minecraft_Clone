package fr.math.minecraft.client;

import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class MinecraftClient {

    private static final int MAX_RESPONSE_LENGTH = 16384;
    private DatagramSocket socket;
    private InetAddress address;
    private final int serverPort;
    private final static Logger logger = LoggerUtility.getClientLogger(MinecraftClient.class, LogType.TXT);
    private static final String IP_SERVER = "localhost";
    private final byte[] responseBuffer;

    public MinecraftClient(int serverPort) {
        this.serverPort = serverPort;
        this.responseBuffer = new byte[MAX_RESPONSE_LENGTH];
        this.connect();
    }

    public void connect()  {
        try {
            this.socket = new DatagramSocket();
            this.socket.setSoTimeout(10000);
            this.address = InetAddress.getByName(IP_SERVER);

        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void sendMessage(String message) throws IOException {
        byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, this.address, this.serverPort);
        synchronized (this.getSocket()) {
            this.getSocket().send(packet);
        }
    }

    public synchronized String receive() {
        DatagramPacket packet = new DatagramPacket(responseBuffer, responseBuffer.length);

        try {
            synchronized (this.getSocket()) {
                this.getSocket().receive(packet);
            }
        } catch (SocketTimeoutException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new String(packet.getData(), 0, packet.getLength()).trim();
    }

    public synchronized String sendString(String message) throws IOException {
        return this.sendBytes(message.getBytes(StandardCharsets.UTF_8));
    }

    public synchronized String sendBytes(byte[] buffer) throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length, this.address, this.serverPort);
        synchronized (this.getSocket()) {
            this.getSocket().send(packet);
            packet = new DatagramPacket(responseBuffer, responseBuffer.length);

            try {
                this.getSocket().receive(packet);
            } catch (SocketTimeoutException e) {
                logger.error(e.getMessage());
                return "TIMEOUT_REACHED";
            }
        }

        return new String(packet.getData(), 0, packet.getLength()).trim();
    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
