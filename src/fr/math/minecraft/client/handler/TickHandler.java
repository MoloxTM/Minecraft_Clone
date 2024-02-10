package fr.math.minecraft.client.handler;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.GameConfiguration;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.manager.WorldManager;
import fr.math.minecraft.client.world.World;
import org.joml.Vector3f;
import org.joml.Vector3i;

import static org.lwjgl.glfw.GLFW.*;

public class TickHandler extends Thread {

    public TickHandler() {
        this.setName("TickHandler");
    }

    @Override
    public void run() {

        Game game = Game.getInstance();
        WorldManager worldManager = game.getWorldManager();
        World world = game.getWorld();
        double lastTime = glfwGetTime();
        double tickTimer = 0;

        while (!glfwWindowShouldClose(game.getWindow())) {

            double currentTime = glfwGetTime();
            double deltaTime = currentTime - lastTime;

            tickTimer += deltaTime;

            lastTime = currentTime;

            while (tickTimer >= GameConfiguration.TICK_RATE) {
                Player player = game.getPlayer();
                Vector3f playerPosition = new Vector3f(player.getPosition());
                Vector3i inputVector = new Vector3i(player.getInputVector());
                float yaw = player.getYaw();
                float pitch = player.getPitch();

                synchronized (player.getInputVector()) {
                    player.getInputVector().x = 0;
                    player.getInputVector().y = 0;
                    player.getInputVector().z = 0;
                    player.resetMoving();
                }

                game.getPlayerMovementHandler().handle(player, playerPosition, inputVector, yaw, pitch);
                tickTimer -= GameConfiguration.TICK_RATE;
            }

        }

    }

}
