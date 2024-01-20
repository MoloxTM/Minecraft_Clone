package fr.math.minecraft.client;

import fr.math.minecraft.client.animations.Animation;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.world.Chunk;
import org.joml.Matrix3f;
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
    private float nearPlane, farPlane;
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
        this.nearPlane = 0.1f;
        this.farPlane = 999.0f;
    }

    public void update(Player player) {
        position = player.getPosition();
        yaw = player.getYaw();
        pitch = player.getPitch();
    }

    private void calculateFront(Vector3f front) {
        front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.y = (float) Math.sin(Math.toRadians(pitch));
        front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.normalize();
    }

    public void matrix(Shader shader, Chunk chunk) {
        this.calculateFront(front);

        Matrix4f projection = new Matrix4f();
        Matrix4f view = new Matrix4f();
        Matrix4f model = new Matrix4f();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f up = new Vector3f(right).cross(front).normalize();

        Vector3f newPosition = new Vector3f(position).add(0, 1 + 0.25f, 0);

        projection.perspective((float) Math.toRadians(fov), width / height, nearPlane ,farPlane);
        view.lookAt(newPosition, new Vector3f(newPosition).add(front), up);
        model.translate(chunk.getPosition().x * Chunk.SIZE, chunk.getPosition().y * Chunk.SIZE, chunk.getPosition().z * Chunk.SIZE);

        shader.sendMatrix("projection", projection, projectionBuffer);
        shader.sendMatrix("view", view, viewBuffer);
        shader.sendMatrix("model", model, modelBuffer);
    }

    public void matrix(Shader shader, Player player) {

        this.calculateFront(front);

        Matrix4f projection = new Matrix4f();
        Matrix4f view = new Matrix4f();
        Matrix4f model = new Matrix4f();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f up = new Vector3f(right).cross(front).normalize();

        projection.perspective((float) Math.toRadians(fov), width / height, nearPlane ,farPlane);
        view.lookAt(position, new Vector3f(position).add(front), up);
        model.translate(player.getPosition().x, player.getPosition().y, player.getPosition().z);
        model.rotate((float) Math.toRadians(90.0f), new Vector3f(0.0f, 1.0f, 0.0f), model);

        shader.sendFloat("bodyYaw", (float) Math.toRadians(player.getBodyYaw()));
        shader.sendFloat("yaw", (float) Math.toRadians(player.getYaw()));
        shader.sendFloat("pitch", (float) Math.toRadians(player.getPitch()));
        shader.sendFloat("time", Game.getInstance().getTime());

        for (Animation animation : player.getAnimations()) {
            animation.sendUniforms(shader);
        }

        shader.sendMatrix("projection", projection, projectionBuffer);
        shader.sendMatrix("view", view, viewBuffer);
        shader.sendMatrix("model", model, modelBuffer);
    }

    public void matrixNametag(Shader shader, Player player) {

        this.calculateFront(front);

        Matrix4f projection = new Matrix4f();
        Matrix4f view = new Matrix4f();
        Matrix4f model = new Matrix4f();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f up = new Vector3f(right).cross(front).normalize();

        projection.perspective((float) Math.toRadians(fov), width / height, nearPlane ,farPlane);
        view.lookAt(position, new Vector3f(position).add(front), up);
        model.translate(player.getPosition().x, player.getPosition().y + 0.8f, player.getPosition().z);
        model.rotate((float) Math.toRadians(90.0f), new Vector3f(0.0f, 1.0f, 0.0f), model);

        shader.sendFloat("yaw", (float) Math.toRadians(player.getYaw()));

        shader.sendMatrix("projection", projection, projectionBuffer);
        shader.sendMatrix("view", view, viewBuffer);
        shader.sendMatrix("model", model, modelBuffer);
    }

    public void matrix(Shader shader, String text) {

        Matrix4f projection = new Matrix4f();
        Matrix4f view = new Matrix4f();
        Matrix4f model = new Matrix4f();

        projection.ortho(0, GameConfiguration.WINDOW_WIDTH, 0, GameConfiguration.WINDOW_HEIGHT, nearPlane ,farPlane);

        shader.sendMatrix("projection", projection, projectionBuffer);
        shader.sendMatrix("view", view, viewBuffer);
        shader.sendMatrix("model", model, modelBuffer);
    }

    public void matrixSkybox(Shader shader) {

        this.calculateFront(front);

        Matrix4f projection = new Matrix4f();
        Matrix4f view = new Matrix4f();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f up = new Vector3f(right).cross(front).normalize();

        projection.perspective((float) Math.toRadians(fov), width / height, nearPlane ,farPlane);
        view.lookAt(position, new Vector3f(position).add(front), up);
        view = new Matrix4f(new Matrix3f(view));

        shader.sendMatrix("projection", projection, projectionBuffer);
        shader.sendMatrix("view", view, viewBuffer);
    }

    public Vector3f getPosition() {
        return position;
    }
}
