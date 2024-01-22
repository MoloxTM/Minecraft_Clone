package fr.math.minecraft.client.builder;

import fr.math.minecraft.client.fonts.CFont;
import fr.math.minecraft.client.fonts.CharInfo;
import fr.math.minecraft.client.texture.Texture;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BitmapBuilder {


    private final static Logger logger = LoggerUtility.getClientLogger(BitmapBuilder.class, LogType.TXT);

    public static Map<Integer, CharInfo> buildBitmap(CFont cfont) {
        HashMap<Integer, CharInfo> characterMap = new HashMap<>();


        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(cfont.getFilePath())).deriveFont((float) cfont.getFontSize());
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        // Création d'une image fictive pour récupérer les informations de la police (largeur, hauteur...)

        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setFont(font);
        FontMetrics fontMetrics = g2d.getFontMetrics();

        int estimatedWidth = (int) Math.sqrt(font.getNumGlyphs() * font.getSize()) + 1;
        int width = 0;
        int height = fontMetrics.getHeight();
        int lineHeight = fontMetrics.getHeight();

        cfont.setWidth(width);
        cfont.setHeight(height);
        cfont.setLineHeight(lineHeight);

        int x = 0;
        int y = (int) (fontMetrics.getHeight() * 1.4f);

        for (int i = 0; i < Character.MAX_VALUE; i++) {
            if (!font.canDisplay(i))
                continue;

            CharInfo charInfo = new CharInfo(x, y, fontMetrics.charWidth(i), fontMetrics.getHeight());
            characterMap.put(i, charInfo);
            width = Math.max(x + fontMetrics.charWidth(i), width);
            cfont.setWidth(width);

            x += charInfo.getWidth();

            if (x > estimatedWidth) {
                x = 0;
                y += fontMetrics.getHeight() * 1.4f;
                height += fontMetrics.getHeight() * 1.4f;
                cfont.setHeight(height);
            }
        }

        g2d.dispose();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);

        for (int i = 0; i < Character.MAX_VALUE; i++) {
            if (!font.canDisplay(i))
                continue;
            CharInfo charInfo = characterMap.get(i);
            charInfo.calculateTextureCoordinates(width, height);

            g2d.drawString("" + (char) i, charInfo.getSourceX(), charInfo.getSourceY());
        }

        g2d.dispose();
        File file = new File("res/bitmap.png");

        try {
            ImageIO.write(image, "png", file);
            logger.info("Génération de la bitmap pour la font " + file.getPath() + " effectuée avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Texture texture = new Texture(file.getPath(), 5);
        texture.load();

        cfont.setTexture(texture);

        return characterMap;
    }

}
