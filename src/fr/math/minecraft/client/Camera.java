package fr.math.minecraft.client;

import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.meshs.model.PlayerModel;
import fr.math.minecraft.client.world.Chunk;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

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
        position = new Vector3f();
        yaw = 0.0f;
        pitch = 0.0f;
        fov = 70.0f;
        modelBuffer = BufferUtils.createFloatBuffer(16);
        viewBuffer = BufferUtils.createFloatBuffer(16);
        projectionBuffer = BufferUtils.createFloatBuffer(16);

    }

    public void update(Player player) {
        position = player.getPosition();
        yaw = player.getYaw();
        pitch = player.getPitch();
    }

    public void matrix(Shader shader, Chunk chunk) {
        this.matrix(shader, chunk.getPosition().x * Chunk.SIZE, chunk.getPosition().y * Chunk.SIZE, chunk.getPosition().z * Chunk.SIZE);
    }

    private void calculateFront(Vector3f front) {
        front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.y = (float) Math.sin(Math.toRadians(pitch));
        front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.normalize();
    }

    public void matrix(Shader shader, Player player) {

        this.calculateFront(front);

        Matrix4f projection = new Matrix4f();
        Matrix4f view = new Matrix4f();
        Matrix4f model = new Matrix4f();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f up = new Vector3f(right).cross(front).normalize();

        projection.perspective((float) Math.toRadians(fov), width / height, 0.1f ,100.0f);
        view.lookAt(position, new Vector3f(position).add(front), up);
        model.translate(player.getPosition().x, player.getPosition().y + PlayerModel.CHEST_HEIGHT, player.getPosition().z);

        shader.sendFloat("yaw", (float) Math.toRadians(player.getYaw()));
        shader.sendFloat("pitch", (float) Math.toRadians(player.getPitch()));

        shader.sendMatrix("projection", projection, projectionBuffer);
        shader.sendMatrix("view", view, viewBuffer);
        shader.sendMatrix("model", model, modelBuffer);
    }

    public void matrix(Shader shader, float x, float y, float z) {

        this.calculateFront(front);

        Matrix4f projection = new Matrix4f();
        Matrix4f view = new Matrix4f();
        Matrix4f model = new Matrix4f();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f up = new Vector3f(right).cross(front).normalize();

        projection.perspective((float) Math.toRadians(fov), width / height, 0.1f ,100.0f);
        view.lookAt(position, new Vector3f(position).add(front), up);
        model.translate(x, y, z);

        shader.sendMatrix("projection", projection, projectionBuffer);
        shader.sendMatrix("view", view, viewBuffer);
        shader.sendMatrix("model", model, modelBuffer);

    }

}
