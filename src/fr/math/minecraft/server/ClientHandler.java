package fr.math.minecraft.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientHandler extends Thread {

    private final ObjectMapper mapper;
    private final static Logger logger = LoggerUtility.getServerLogger(ClientHandler.class, LogType.TXT);;

    public ClientHandler() {
        this.mapper = new ObjectMapper();
    }

    public JsonNode parsePacket(DatagramPacket packet) {
        try {
            byte[] buffer = packet.getData();
            String message = new String(buffer, 0, buffer.length).trim();

            return mapper.readTree(message);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public void run() {

    }
}
