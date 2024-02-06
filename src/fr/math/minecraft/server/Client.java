package fr.math.minecraft.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.server.payload.InputPayload;
import org.joml.Vector3f;
import org.joml.Vector3i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Base64;

public class Client {

    private final String name;
    private final String uuid;
    private final InetAddress address;
    private final int port;
    private Vector3f position;
    private Vector3f front;
    private float yaw;
    private float bodyYaw;
    private float pitch;
    private float speed;
    private BufferedImage skin;
    private boolean movingLeft, movingRight, movingForward, movingBackward;
    private boolean flying, sneaking;
    private boolean active;
    private final Vector3i inputVector;

    public Client(String uuid, String name, InetAddress address, int port) {
        this.address = address;
        this.port = port;
        this.uuid = uuid;
        this.name = name;
        this.front = new Vector3f(0.0f, 0.0f, 0.0f);
        this.position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.inputVector = new Vector3i(0, 0, 0);
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.speed = 0.1f;
        this.skin = null;
        this.movingLeft = false;
        this.movingRight = false;
        this.movingBackward = false;
        this.movingForward = false;
        this.flying = false;
        this.sneaking = false;
        this.active = false;
    }

    public String getName() {
        return name;
    }

    public void handleInputs(JsonNode packetData) {
        boolean movingLeft = packetData.get("left").asBoolean();
        boolean movingRight = packetData.get("right").asBoolean();
        boolean movingForward = packetData.get("forward").asBoolean();
        boolean movingBackward = packetData.get("backward").asBoolean();
        boolean flying = packetData.get("flying").asBoolean();
        boolean sneaking = packetData.get("sneaking").asBoolean();

        float yaw = packetData.get("yaw").floatValue();
        float bodyYaw = packetData.get("bodyYaw").floatValue();
        float pitch = packetData.get("pitch").floatValue();

        int inputX = packetData.get("inputX").intValue();
        int inputY = packetData.get("inputY").intValue();
        int inputZ = packetData.get("inputZ").intValue();

        this.yaw = yaw;
        this.bodyYaw = bodyYaw;
        this.pitch = pitch;

        this.movingLeft = movingLeft;
        this.movingRight = movingRight;
        this.movingForward = movingForward;
        this.movingBackward = movingBackward;

        this.inputVector.x = inputX;
        this.inputVector.y = inputY;
        this.inputVector.z = inputZ;

        this.flying = flying;
        this.sneaking = sneaking;
    }

    public void updatePosition(InputPayload payload) {
        Vector3i inputVector = payload.getInputVector();
        float yaw = payload.getYaw();
        float pitch = payload.getPitch();

        front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.y = (float) Math.sin(Math.toRadians(0.0f));
        front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));

        front.normalize();
        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();

        while (inputVector.z < 0) {
            position = position.add(new Vector3f(front).mul(speed));
            inputVector.z++;
        }

        while (inputVector.z > 0) {
            position = position.sub(new Vector3f(front).mul(speed));
            inputVector.z--;
        }

        while (inputVector.x < 0) {
            position = position.sub(new Vector3f(right).mul(speed));
            inputVector.x++;
        }

        while (inputVector.x > 0) {
            position = position.add(new Vector3f(right).mul(speed));
            inputVector.x--;
        }

        if (flying)
            position = position.add(new Vector3f(0.0f, .5f, 0.0f));

        if (sneaking)
            position = position.sub(new Vector3f(0.0f, .5f, 0.0f));
    }

    public Vector3f getPosition() {
        return position;
    }

    public ObjectNode toJSONWithSkin() {
        ObjectNode node = this.toJSON();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(skin, "png", baos);
            node.put("skin", Base64.getEncoder().encodeToString(baos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return node;
    }

    public ObjectNode toJSON() {
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("name", this.name);
        node.put("uuid", this.uuid);
        node.put("x", this.position.x);
        node.put("y", this.position.y);
        node.put("z", this.position.z);
        node.put("yaw", this.yaw);
        node.put("pitch", this.pitch);
        node.put("movingLeft", this.movingLeft);
        node.put("movingRight", this.movingRight);
        node.put("movingForward", this.movingForward);
        node.put("movingBackward", this.movingBackward);
        node.put("bodyYaw", this.bodyYaw);

        return node;
    }

    public BufferedImage getSkin() {
        return skin;
    }

    public float getBodyYaw() {
        return bodyYaw;
    }

    public int getPort() {
        return port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSkin(BufferedImage skin) {
        this.skin = skin;
    }

    public String getUuid() {
        return uuid;
    }
}
