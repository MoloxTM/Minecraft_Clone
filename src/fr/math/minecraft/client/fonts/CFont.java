package fr.math.minecraft.client.fonts;

import java.util.HashMap;
import java.util.Map;

public class CFont {

    private final String filePath;
    private final int fontSize;
    private int width, height, lineHeight;
    private final Map<Integer, CharInfo> characterMap;

    public CFont(String filePath, int fontSize) {
        this.filePath = filePath;
        this.fontSize = fontSize;
        this.width = 0;
        this.height = 0;
        this.lineHeight = 0;
        this.characterMap = BitmapBuilder.buildBitmap(this);
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
}
