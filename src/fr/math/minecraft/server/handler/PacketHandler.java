package fr.math.minecraft.server.handler;

import com.fasterxml.jackson.databind.JsonNode;

import java.net.InetAddress;

public abstract class PacketHandler {

    protected JsonNode packetData;
    protected InetAddress address;
    protected int clientPort;

    public PacketHandler(JsonNode packetData, InetAddress address, int clientPort) {
        this.packetData = packetData;
        this.address = address;
        this.clientPort = clientPort;
    }

}
