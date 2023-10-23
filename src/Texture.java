import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;
public class Texture {

    private final String imagePath;
    private int id;

    public Texture(String imagePath) {
        this.imagePath = imagePath;
    }

    public void load() {
        ByteBuffer imageBuffer;
        IntBuffer width = MemoryUtil.memAllocInt(1);
        IntBuffer height = MemoryUtil.memAllocInt(1);
        IntBuffer channels = MemoryUtil.memAllocInt(1);

        width.rewind();
        height.rewind();
        channels.rewind();

        STBImage.stbi_set_flip_vertically_on_load(true);
        imageBuffer = STBImage.stbi_load(this.imagePath, width, height, channels, 0);
        if (imageBuffer == null) {
            throw new IllegalArgumentException("Impossible de charger l'image " + this.imagePath);
        }

        System.out.println("Chargement de la texture " + this.imagePath);

        id = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        if (channels.get() == 3) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(), height.get(), 0, GL_RGB, GL_UNSIGNED_BYTE, imageBuffer);
        } else {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);
        }

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
}
