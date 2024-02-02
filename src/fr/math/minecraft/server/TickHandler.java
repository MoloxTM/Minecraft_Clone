package fr.math.minecraft.server;

import static org.lwjgl.glfw.GLFW.*;

public class TickHandler extends Thread {

    private final static long TICK_PER_SECONDS = 20;
    private final static long TICK_RATE_MS = 1000 / TICK_PER_SECONDS;

    public TickHandler() {
        this.setName("TickHandler");
    }

    @Override
    public void run() {
        long tickTimer = 0;
        long lastTime = System.currentTimeMillis();
        while (true) {
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - lastTime;

            tickTimer += deltaTime;

            lastTime = currentTime;

            while (tickTimer >= TICK_RATE_MS) {
                tick();
                tickTimer -= TICK_RATE_MS;
            }
        }
    }

    private void tick() {
        MinecraftServer server = MinecraftServer.getInstance();
        synchronized (server.getClients()) {
            for (Client client : server.getClients().values()) {
                client.update();
            }
        }
    }

}
