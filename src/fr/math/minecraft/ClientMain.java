package fr.math.minecraft;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.Texture;
import fr.math.minecraft.client.entity.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true");
        String skinPath = "res/textures/skin.png";
        if (args.length == 2) {
            if (!args[0].equalsIgnoreCase("--name")) {
                throw new IllegalArgumentException("Veuillez renseigner un pseudo ! (--name <pseudo>)");
            }
        } else if (args.length == 4) {
            if (!args[0].equalsIgnoreCase("--name")) {
                throw new IllegalArgumentException("Veuillez renseigner un pseudo ! (--name <pseudo>)");
            }
            if (!args[2].equalsIgnoreCase("--skin")) {
                throw new IllegalArgumentException("Veuillez renseigner un skin ! (--skin chemin/vers/skin.png)");
            }
            skinPath = args[3];
        } else {
            throw new IllegalArgumentException("Veuillez renseigner un pseudo et/ou un skin ! (--name <pseudo> --skin chemin/vers/skin.png)");
        }

        if (!new File(skinPath).exists()) {
            throw new IllegalArgumentException("Le fichier spécifié est introuvable ! " + skinPath);
        }

        System.out.println("Instance de game ClientMain :" + Game.getInstance() + "\n");
        Game game = Game.getInstance();
        game.init();
        BufferedImage skin = loadSkin(skinPath);
        game.setPlayer(new Player(args[1]));
        game.getPlayer().setSkin(skin);
        System.out.println("le jeu se lance");
        game.run();
    }

    public static BufferedImage loadSkin(String skinPath) {
        try {
            return ImageIO.read(new File(skinPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
