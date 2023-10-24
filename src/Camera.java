import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL33.*;

import java.nio.FloatBuffer;

public class Camera {

    private Vector3f position;
    private Vector3f front;
    private float width, height;
    private float yaw, pitch;
    private float fov;
    private final FloatBuffer modelBuffer, projectionBuffer, viewBuffer;

    public Camera(float width, float height) {
        this.width = width;
        this.height = height;
        front = new Vector3f();
        yaw = 0.0f;
        pitch = 0.0f;
        fov = 70.0f;
        modelBuffer = BufferUtils.createFloatBuffer(16);
        viewBuffer = BufferUtils.createFloatBuffer(16);
        projectionBuffer = BufferUtils.createFloatBuffer(16);

    }

    public void update(Player player, Shader shader, int x, int y, int z) {

        front.x = (float) (Math.cos(Math.toRadians(player.getYaw())) * Math.cos(Math.toRadians(player.getPitch())));
        front.y = (float) Math.sin(Math.toRadians(player.getPitch()));
        front.z = (float) (Math.sin(Math.toRadians(player.getYaw())) * Math.cos(Math.toRadians(player.getPitch())));

        Matrix4f projection = new Matrix4f();
        Matrix4f view = new Matrix4f();
        Matrix4f model = new Matrix4f();

        projection.perspective((float) Math.toRadians(fov), width / height, 0.1f ,100.0f);
        view.lookAt(player.getPosition(), new Vector3f(player.getPosition()).add(front), new Vector3f(0.0f, 1.0f, 0.0f));
        model.translate(x, y, z);

        model.get(modelBuffer);
        view.get(viewBuffer);
        projection.get(projectionBuffer);

        glUniformMatrix4fv(glGetUniformLocation(shader.getId(), "projection"), false, projectionBuffer);
        glUniformMatrix4fv(glGetUniformLocation(shader.getId(), "view"), false, viewBuffer);
        glUniformMatrix4fv(glGetUniformLocation(shader.getId(), "model"), false, modelBuffer);

    }

}
