package fr.math.minecraft;

import fr.math.minecraft.client.Game;

public class ClientMain {

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Veuillez renseigner un pseudo ! (--name <pseudo>)");
        }
        if (!args[0].equalsIgnoreCase("--name")) {
            throw new IllegalArgumentException("Veuillez renseigner un pseudo ! (--name <pseudo>)");
        }
        Game game = Game.getInstance();
        game.getPlayer().setName(args[1]);
        game.run();
    }

}
