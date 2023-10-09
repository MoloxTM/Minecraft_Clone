import org.joml.Vector3f;

public class Vertex {

    private final Vector3f position;

    public Vertex(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
    }
    public Vertex(Vector3f position) {
        this.position = position;
    }

    public Vector3f getPosition() {
        return position;
    }
}
