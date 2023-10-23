import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;
public class Texture {

    private final String imagePath;
    private int id, slot;

    public Texture(String imagePath, int slot) {
        this.imagePath = imagePath;
        this.slot = slot;
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
        glActiveTexture(GL_TEXTURE0 + this.slot);
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

    public Vector2f[] calculateTexCoords(int x, int y, float format) {
        Vector2f texCoordsBottomLeft = new Vector2f(x/format, y/format);
        Vector2f texCoordsUpLeft = new Vector2f(x/format, (y + 1)/format);
        Vector2f texCoordsUpRight = new Vector2f((x + 1)/format, (y + 1)/format);
        Vector2f texCoordsBottomRight = new Vector2f((x + 1)/format, y/format);

        Vector2f[] texCoords = {
                texCoordsBottomLeft,
                texCoordsUpLeft,
                texCoordsUpRight,
                texCoordsBottomRight
        };

        return texCoords;
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
