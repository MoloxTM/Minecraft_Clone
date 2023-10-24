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
        this.speed = 0.02f;
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

        double mouseOffsetX = lastMouseX - mouseX.get(0);
        double mouseOffsetY = lastMouseY - mouseY.get(0);

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

        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS){
            position.z = position.z - 1 * speed;
        }

        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS){
            position.x = position.x - 1 * speed;
        }

        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS){
            position.z = position.z + 1 * speed;
        }

        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS){
            position.x = position.x + 1 * speed;
        }

        System.out.println("Yaw : " + yaw + " pitch : " + pitch);

        lastMouseX = (float) mouseX.get(0);
        lastMouseY = (float) mouseY.get(0);
    }

    public void updateCamera() {
        front.x = (float) (cos(this.getYaw()) * cos(this.getPitch()));
        front.y = (float) sin(this.getPitch());
        front.z = (float) (sin(this.getYaw()) * cos(this.getPitch()));
    }

    public Vector3f getFront() {
        return front;
    }

    public void setFront(Vector3f front) {
        this.front = front;
    }
    public float getSpeed() {
        return speed;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getLastMouseX() {
        return lastMouseX;
    }

    public float getLastMouseY() {
        return lastMouseY;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }
}
