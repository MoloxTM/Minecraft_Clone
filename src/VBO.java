
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.*;
public class VBO {

    private final int id;

    public VBO(Vertex[] vertices) {
        id = glGenBuffers();
        float[] data = new float[vertices.length * 5];
        int bufferPosition = 0;
        for (Vertex vertex : vertices) {
            data[bufferPosition++] = vertex.getPosition().x;
            data[bufferPosition++] = vertex.getPosition().y;
            data[bufferPosition++] = vertex.getPosition().z;
            data[bufferPosition++] = vertex.getTextureCoords().x;
            data[bufferPosition++] = vertex.getTextureCoords().y;
        }
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * 5);
        buffer.put(data).flip();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
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
}