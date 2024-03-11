package fr.math.minecraft.client.texture;

import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;
public class Texture {

    protected String imagePath;
    private BufferedImage image;
    private final static Logger logger = LoggerUtility.getClientLogger(Texture.class, LogType.TXT);
    protected int id, slot;
    protected boolean loaded;

    public Texture(String imagePath, int slot) {
        this.imagePath = imagePath;
        this.slot = slot;
        this.loaded = false;
    }

    public Texture(BufferedImage image, int slot) {
        this.image = image;
        this.slot = slot;
        this.imagePath = null;
        this.loaded = false;
    }

    public void load() {
        ByteBuffer imageBuffer;
        IntBuffer width = MemoryUtil.memAllocInt(1);
        IntBuffer height = MemoryUtil.memAllocInt(1);
        IntBuffer channels = MemoryUtil.memAllocInt(1);

        if (imagePath != null) {

            STBImage.stbi_set_flip_vertically_on_load(true);
            imageBuffer = STBImage.stbi_load(this.imagePath, width, height, channels, 0);
            if (imageBuffer == null) {
                throw new IllegalArgumentException("Impossible de charger l'image " + this.imagePath);
            }
        } else {
            int[] pixels = new int[image.getWidth() * image.getHeight()];
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
            imageBuffer = ByteBuffer.allocateDirect(image.getWidth() * image.getHeight() * 4);

            width.put(image.getWidth());
            height.put(image.getHeight());
            channels.put(4);

            // Flip pixels vertically on load (cref stbi_flip_vertically_on_load)
            for (int y = 0; y < image.getHeight() / 2; y++) {
                int oppositeY = image.getHeight() - y - 1;
                for (int x = 0; x < image.getWidth(); x++) {
                    int index1 = y * image.getWidth() + x;
                    int index2 = oppositeY * image.getWidth() + x;

                    int temp = pixels[index1];
                    pixels[index1] = pixels[index2];
                    pixels[index2] = temp;
                }
            }

            for (int pixel : pixels) {
                imageBuffer.put((byte) ((pixel >> 16) & 0xFF));
                imageBuffer.put((byte) ((pixel >> 8) & 0xFF));
                imageBuffer.put((byte) ((pixel >> 0) & 0xFF));
                imageBuffer.put((byte) ((pixel >> 24) & 0xFF));
            }
            imageBuffer.flip();
        }

        width.rewind();
        height.rewind();
        channels.rewind();

        id = glGenTextures();
        glActiveTexture(GL_TEXTURE0 + this.slot);
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);

        logger.info("Chargement de la texture " + imagePath + " effectuée avec succès.");

        if (imagePath != null) {
            STBImage.stbi_image_free(imageBuffer);
        }

        this.unbind();
        this.loaded = true;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void activeSlot() {
        glActiveTexture(GL_TEXTURE0 + slot);
    }

    public void delete() {
        glDeleteTextures(id);
    }

    public int getId() {
        return id;
    }

    public int getSlot() {
        return slot;
    }
    public void setSlot(int slot) {
        this.slot = slot;
    }
}
