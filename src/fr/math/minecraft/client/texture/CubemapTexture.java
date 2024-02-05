package fr.math.minecraft.client.texture;

import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import org.apache.log4j.Logger;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;

public class CubemapTexture extends Texture {

    private final String[] filePaths;
    private final static Logger logger = LoggerUtility.getClientLogger(CubemapTexture.class, LogType.TXT);

    public CubemapTexture(String[] filePaths, int slot) {
        super("", slot);
        this.filePaths = filePaths;
        this.loaded = false;
    }

    @Override
    public void load() {

        id = glGenTextures();
        glActiveTexture(GL_TEXTURE0 + this.slot);
        this.bind();

        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

        for (int i = 0; i < 6; i++) {

            IntBuffer width = MemoryUtil.memAllocInt(1);
            IntBuffer height = MemoryUtil.memAllocInt(1);
            IntBuffer channels = MemoryUtil.memAllocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(true);
            ByteBuffer imageBuffer = STBImage.stbi_load(this.filePaths[i], width, height, channels, 0);

            if (imageBuffer == null) {
                throw new RuntimeException("Impossible de charger la texture " + this.filePaths[i]);
            }

            int format;
            if (channels.get() == 3) {
                format = GL_RGB;
            } else {
                format = GL_RGBA;
            }

            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, format, width.get(), height.get(), 0, format, GL_UNSIGNED_BYTE, imageBuffer);

            STBImage.stbi_image_free(imageBuffer);

            MemoryUtil.memFree(width);
            MemoryUtil.memFree(height);
            MemoryUtil.memFree(channels);

            logger.info("Texture " + this.filePaths[i] + " chargée avec succès");
            this.loaded = true;
        }

    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_CUBE_MAP, id);
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }

}
