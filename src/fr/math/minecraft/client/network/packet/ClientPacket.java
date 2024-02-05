package fr.math.minecraft.client.network.packet;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.MinecraftClient;

import java.io.IOException;

public abstract class ClientPacket {

    public synchronized void send() {
        Game game = Game.getInstance();
        MinecraftClient client = game.getClient();
        String message = this.toJSON();
        try {
            client.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String toJSON();

    public abstract String getResponse();


}
