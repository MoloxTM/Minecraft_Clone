package fr.math.minecraft.client.manager;

import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.client.fonts.CharInfo;
import fr.math.minecraft.client.meshs.FontMesh;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;

public class FontManager {

    private final static float CHARACTER_WIDTH = 0.2f;
    private final static float CHARACTER_HEIGHT = 0.3f;
    private final static float CHARACTER_SPACE = 0.2f;
    private final static Logger logger = LoggerUtility.getClientLogger(FontManager.class, LogType.TXT);
    private float currentPos;

    public FontManager() {
        this.currentPos = 0.0f;
    }

    public void addCharacter(FontMesh fontMesh, float x, float y, float z, float scale, CharInfo charInfo, int rgb, boolean onWorld) {

        if (fontMesh.getSize() >= FontMesh.BATCH_SIZE - 4) {
            fontMesh.flush();
        }

        float[] vertices = fontMesh.getVertices();

        float r = (float) ((rgb >> 16) & 0xFF) / 255.0f;
        float g = (float) ((rgb >> 8) & 0xFF) / 255.0f;
        float b = (float) ((rgb >> 0) & 0xFF) / 255.0f;

        float x0 = x;
        float y0 = y;
        float x1 = x + scale * charInfo.getWidth();
        float y1 = y + scale * charInfo.getHeight();

        float ux0 = charInfo.getTextureCoords()[0].x;
        float ux1 = charInfo.getTextureCoords()[1].x;
        float uy0 = charInfo.getTextureCoords()[0].y;
        float uy1 = charInfo.getTextureCoords()[1].y;

        int index = fontMesh.getSize() * FontMesh.VERTEX_SIZE;

        vertices[index] = onWorld ? currentPos + CHARACTER_WIDTH : x1;
        vertices[index + 1] = onWorld ? -0.5f + 0.15f / 4.0f : y0;
        vertices[index + 2] = onWorld ? 0.01f : z;
        vertices[index + 3] = r;
        vertices[index + 4] = g;
        vertices[index + 5] = b;
        vertices[index + 6] = ux1;
        vertices[index + 7] = uy0;

        index += FontMesh.VERTEX_SIZE;
        vertices[index] = onWorld ? currentPos + CHARACTER_WIDTH : x1;
        vertices[index + 1] = onWorld ? -0.5f + CHARACTER_HEIGHT + 0.15f / 4.0f : y1;
        vertices[index + 2] = onWorld ? 0.01f : z;
        vertices[index + 3] = r;
        vertices[index + 4] = g;
        vertices[index + 5] = b;
        vertices[index + 6] = ux1;
        vertices[index + 7] = uy1;

        index += FontMesh.VERTEX_SIZE;
        vertices[index] = onWorld ? currentPos : x0;
        vertices[index + 1] = onWorld ? -0.5f + CHARACTER_HEIGHT + 0.15f / 4.0f : y1;
        vertices[index + 2] = onWorld ? 0.01f : z;
        vertices[index + 3] = r;
        vertices[index + 4] = g;
        vertices[index + 5] = b;
        vertices[index + 6] = ux0;
        vertices[index + 7] = uy1;

        index += FontMesh.VERTEX_SIZE;
        vertices[index] = onWorld ? currentPos : x0;
        vertices[index + 1] = onWorld ? -0.5f + 0.15f / 4.0f : y0;
        vertices[index + 2] = onWorld ? 0.01f : z;
        vertices[index + 3] = r;
        vertices[index + 4] = g;
        vertices[index + 5] = b;
        vertices[index + 6] = ux0;
        vertices[index + 7] = uy0;

        fontMesh.setSize(fontMesh.getSize() + 4);
    }

    public void addText(FontMesh fontMesh, String text, float x, float y, float z, float scale, int rgb, boolean onWorld) {
        if (onWorld) {
            x = 0.2f;
            currentPos = -0.5f + 0.5f - text.length() * CHARACTER_SPACE / 2.0f;
        }
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            CharInfo charInfo = fontMesh.getFont().getCharacter(c);
            if (charInfo.getWidth() == 0) {
                logger.error("Impossible de charger le caractère " + c + ", ce caractère est inconnu.");
                continue;
            }

            this.addCharacter(fontMesh, x, y, z, scale, charInfo, rgb, onWorld);

            if (onWorld) {
                currentPos += CHARACTER_SPACE;
            } else {
                x += charInfo.getWidth() * scale;
            }
        }
    }

    public void addText(FontMesh fontMesh, String text, float x, float y, float z, float scale, int rgb) {
        this.addText(fontMesh, text, x, y, z, scale, rgb, false);
    }

    public float getTextWidth(FontMesh fontMesh, String text) {
        return this.getTextWidth(fontMesh, GameConfiguration.DEFAULT_SCALE, text);
    }

    public float getTextHeight(FontMesh fontMesh, String text) {
        return this.getTextHeight(fontMesh, GameConfiguration.DEFAULT_SCALE, text);
    }

    public float getTextWidth(FontMesh fontMesh, float scale, String text) {
        float width = 0;
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            CharInfo charInfo = fontMesh.getFont().getCharacter(character);
            width += charInfo.getWidth() * scale;
        }
        return width;
    }

    public float getTextHeight(FontMesh fontMesh, float scale, String text) {
        float height = 0.0f;
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            CharInfo charInfo = fontMesh.getFont().getCharacter(character);
            height = Math.max(charInfo.getWidth() * scale, height);
        }

        return height;
    }


}
