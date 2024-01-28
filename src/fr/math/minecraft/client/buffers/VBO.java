package fr.math.minecraft.client.buffers;

import fr.math.minecraft.client.vertex.PlayerVertex;
import fr.math.minecraft.client.vertex.Vertex;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.*;
public class VBO {

    private final int id;

    public VBO(Vertex[] vertices) {
        id = glGenBuffers();
        float[] data = new float[vertices.length * 7];
        int bufferPosition = 0;
        for (Vertex vertex : vertices) {
            data[bufferPosition++] = vertex.getPosition().x;
            data[bufferPosition++] = vertex.getPosition().y;
            data[bufferPosition++] = vertex.getPosition().z;
            data[bufferPosition++] = vertex.getTextureCoords().x;
            data[bufferPosition++] = vertex.getTextureCoords().y;
            data[bufferPosition++] = vertex.getBlockID();
            data[bufferPosition++] = vertex.getBlockFace();
        }
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * 7);
        buffer.put(data).flip();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public VBO(PlayerVertex[] vertices) {
        id = glGenBuffers();
        float[] data = new float[vertices.length * 6];
        int bufferPosition = 0;
        for (PlayerVertex vertex : vertices) {
            data[bufferPosition++] = vertex.getPosition().x;
            data[bufferPosition++] = vertex.getPosition().y;
            data[bufferPosition++] = vertex.getPosition().z;
            data[bufferPosition++] = vertex.getTextureCoords().x;
            data[bufferPosition++] = vertex.getTextureCoords().y;
            data[bufferPosition++] = vertex.getPartId();
        }
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * 6);
        buffer.put(data).flip();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public VBO(float[] vertices) {
        id = glGenBuffers();
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices).flip();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public VBO(int bufferSize, int drawMethod) {
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(bufferSize);
        glBufferData(GL_ARRAY_BUFFER, buffer, drawMethod);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void bufferFloat(FloatBuffer buffer, int drawMethod) {
        this.bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, drawMethod);
    }

    public int getId() {
        return id;
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void delete() {
        glDeleteBuffers(id);
    }

}
