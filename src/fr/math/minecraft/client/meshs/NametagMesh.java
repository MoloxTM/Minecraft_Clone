package fr.math.minecraft.client.meshs;

import fr.math.minecraft.client.buffers.EBO;
import fr.math.minecraft.client.buffers.VAO;
import fr.math.minecraft.client.buffers.VBO;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL33.*;

public class NametagMesh extends Mesh {

    private float width, height;
    private float[] vertices;

    public NametagMesh(String text, Font font) {
        this.width = calculateWidth(text, font);

        this.vertices = new float[] {
            -0.5f, -0.5f, 0.0f,
            -0.5f, -0.1f, 0.0f,
            -0.5f + text.length() * 0.5f, -0.1f, 0.0f,
            -0.5f + text.length() * 0.5f, -0.5f, 0.0f,
        };

        this.indices = new int[] {
            0, 1, 2,
            2, 3, 0
        };

        System.out.println("Width : "  + width + " Height : " + height);

        this.init();
    }

    private int calculateWidth(String text, Font font) {
        int width = 0;
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = image.createGraphics();

        g2D.setFont(font);
        FontMetrics fontMetrics = g2D.getFontMetrics();

        for (int i = 0; i < text.length(); i++) {
            width += fontMetrics.charWidth(text.charAt(i));
        }
        g2D.dispose();
        this.height = fontMetrics.getHeight();
        return width;
    }

    @Override
    public void init() {
        this.vao = new VAO();
        vao.bind();

        vbo = new VBO(vertices);
        ebo = new EBO(indices);

        vao.linkAttrib(vbo, 0, 3, GL_FLOAT, 3 * Float.BYTES, 0);

        vao.unbind();
        vbo.unbind();
        ebo.unbind();
    }

    @Override
    public void draw() {
        vao.bind();
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        vao.unbind();
    }
}
