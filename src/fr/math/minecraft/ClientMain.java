package fr.math.minecraft;

import fr.math.minecraft.client.Game;

public class ClientMain {

    public static void main(String[] args) {
        Game game = Game.getInstance();
        game.run();
    }

}
