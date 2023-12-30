package fr.math.minecraft.client;

import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;
public class Texture {

    private String imagePath;
    private BufferedImage image;
    private Logger logger = LoggerUtility.getClientLogger(Texture.class, LogType.TXT);
    private int id, slot;

    public Texture(String imagePath, int slot) {
        this.imagePath = imagePath;
        this.slot = slot;
    }

    public Texture(BufferedImage image, int slot) {
        this.image = image;
        this.slot = slot;
        this.imagePath = null;
    }

    public void load() {
        ByteBuffer imageBuffer;
        IntBuffer width = MemoryUtil.memAllocInt(1);
        IntBuffer height = MemoryUtil.memAllocInt(1);
        IntBuffer channels = MemoryUtil.memAllocInt(1);

        if (imagePath != null) {

            width.rewind();
            height.rewind();
            channels.rewind();

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

            for (int h = 0; h < image.getHeight(); h++) {
                for (int w = 0; w < image.getWidth(); w++) {
                    int pixel = pixels[h * image.getWidth() + w];

                    imageBuffer.put((byte) ((pixel >> 16) & 0xFF));
                    imageBuffer.put((byte) ((pixel >> 8) & 0xFF));
                    imageBuffer.put((byte) ((pixel >> 0) & 0xFF));
                    imageBuffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }
            imageBuffer.flip();
        }


        id = glGenTextures();
        glActiveTexture(GL_TEXTURE0 + this.slot);
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        if (channels.get() == 3) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(), height.get(), 0, GL_RGB, GL_UNSIGNED_BYTE, imageBuffer);
        } else {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);
        }

        logger.info("Chargement de la texture effectuée avec succès.");
        STBImage.stbi_image_free(imageBuffer);
        this.unbind();
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
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
