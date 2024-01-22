package fr.math.minecraft.client.fonts;

import fr.math.minecraft.client.builder.BitmapBuilder;
import fr.math.minecraft.client.texture.Texture;

import java.util.Map;

public class CFont {

    private final String filePath;
    private final int fontSize;
    private int width, height, lineHeight;
    private final Map<Integer, CharInfo> characterMap;
    private Texture texture;

    public CFont(String filePath, int fontSize) {
        this.filePath = filePath;
        this.fontSize = fontSize;
        this.width = 0;
        this.height = 0;
        this.lineHeight = 0;
        this.texture = null;
        this.characterMap = BitmapBuilder.buildBitmap(this);
    }

    public CharInfo getCharacter(int code) {
        return characterMap.getOrDefault(code, new CharInfo(0, 0, 0, 0));
    }

    public String getFilePath() {
        return filePath;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    public Map<Integer, CharInfo> getCharacterMap() {
        return characterMap;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
