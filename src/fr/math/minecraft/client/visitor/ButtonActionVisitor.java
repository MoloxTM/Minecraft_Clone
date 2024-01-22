package fr.math.minecraft.client.visitor;

import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.GameState;
import fr.math.minecraft.client.audio.Sounds;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.gui.buttons.PlayButton;
import fr.math.minecraft.client.gui.menus.MainMenu;
import fr.math.minecraft.client.manager.MenuManager;
import fr.math.minecraft.client.manager.SoundManager;
import fr.math.minecraft.client.packet.ConnectionInitPacket;
import fr.math.minecraft.client.tick.TickHandler;

import static org.lwjgl.glfw.GLFW.*;

public class ButtonActionVisitor implements ButtonVisitor<Void> {

    @Override
    public Void visit(PlayButton button) {

        Game game = Game.getInstance();
        Player player = game.getPlayer();
        Camera camera = game.getCamera();
        SoundManager soundManager = game.getSoundManager();
        MenuManager menuManager = game.getMenuManager();

        game.setState(GameState.PLAYING);
        soundManager.play(Sounds.CLICK);

        player.setYaw(0.0f);
        camera.update(player);

        ConnectionInitPacket packet = new ConnectionInitPacket(player);
        packet.send();

        TickHandler tickHandler = new TickHandler();
        tickHandler.start();

        menuManager.close(MainMenu.class);
        glfwSetInputMode(game.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        return null;
    }
}
