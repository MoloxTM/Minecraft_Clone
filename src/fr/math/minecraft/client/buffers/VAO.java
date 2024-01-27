package fr.math.minecraft.client.buffers;

import static org.lwjgl.opengl.GL33.*;
public class VAO {

    private final int id;

    public VAO() {
        id = glGenVertexArrays();
    }

    public void linkAttrib(VBO vbo, int layout, int size, int type, int stride, long offset) {
        vbo.bind();
        glVertexAttribPointer(layout, size, type, false, stride, offset);
        glEnableVertexAttribArray(layout);
        vbo.unbind();
    }

    public void bind() {
        glBindVertexArray(id);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    public void delete() {
        glDeleteVertexArrays(id);
    }
}
