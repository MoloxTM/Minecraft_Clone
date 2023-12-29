package fr.math.minecraft;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.entity.Player;

public class ClientMain {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true");

        if (args.length != 2) {
            throw new IllegalArgumentException("Veuillez renseigner un pseudo ! (--name <pseudo>)");
        }
        if (!args[0].equalsIgnoreCase("--name")) {
            throw new IllegalArgumentException("Veuillez renseigner un pseudo ! (--name <pseudo>)");
        }
        Game game = Game.getInstance();
        game.setPlayer(new Player(args[1]));
        game.run();
    }

}
