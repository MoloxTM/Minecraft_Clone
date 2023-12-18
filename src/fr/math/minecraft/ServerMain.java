package fr.math.minecraft;

import fr.math.minecraft.server.MinecraftServer;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) {
        MinecraftServer server = new MinecraftServer(50000);
        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
