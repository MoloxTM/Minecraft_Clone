import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWWindowCloseCallback;

import java.nio.Buffer;
import java.nio.DoubleBuffer;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.glfw.GLFW.*;


public class Player {

    private Vector3f position;
    private Vector3f front;
    private float yaw;
    private float pitch;
    private boolean firstMouse;
    private float lastMouseX, lastMouseY;
    private float speed;

    public Player(){
        this.position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.front = new Vector3f(0.0f, 0.0f, 0.0f);
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.firstMouse = true;
        this.lastMouseX = 0.0f;
        this.lastMouseY = 0.0f;
        this.speed = 0.05f;
    }

    public void handleInputs(long window) {
        DoubleBuffer mouseX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer mouseY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, mouseX, mouseY);

        if (firstMouse) {
            lastMouseX = (float) mouseX.get(0);
            lastMouseY = (float) mouseY.get(0);
            firstMouse = false;
        }

        double mouseOffsetX = mouseX.get(0) - lastMouseX;
        double mouseOffsetY = mouseY.get(0) - lastMouseY;

        yaw += (float) mouseOffsetX * speed;
        if (yaw > 360.0f || yaw < -360.0f){
            yaw = 0.0f;
        }

        pitch -= (float) mouseOffsetY * speed;
        if (pitch > 90.0f){
            pitch = 89.0f;
        } else if (pitch < -90.0f){
            pitch = -89.0f;
        }

        front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.y = (float) Math.sin(Math.toRadians(0.0f));
        front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));

        front.normalize();
        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();

        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS){
            position = position.add(new Vector3f(front).mul(speed));
        }

        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS){
            position = position.sub(new Vector3f(right).mul(speed));
        }

        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS){
            position = position.sub(new Vector3f(front).mul(speed));
        }

        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS){
            position = position.add(new Vector3f(right).mul(speed));

        }

        lastMouseX = (float) mouseX.get(0);
        lastMouseY = (float) mouseY.get(0);
    }

    public float getSpeed() {
        return speed;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }
}
