package fr.math.minecraft.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joml.Vector3f;

public class Client {

    private final String name;
    private final String uuid;
    private Vector3f position;
    private Vector3f front;
    private float yaw;
    private float pitch;
    private float speed;
    private String skin;
    private boolean movingLeft, movingRight, movingForward, movingBackward;

    public Client(String uuid, String name, String skin) {
        this.uuid = uuid;
        this.name = name;
        this.front = new Vector3f(0.0f, 0.0f, 0.0f);
        this.position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.speed = 0.05f;
        this.skin = skin;
    }

    public String getName() {
        return name;
    }

    public void updatePosition(JsonNode packetData) {
        boolean movingLeft = packetData.get("left").asBoolean();
        boolean movingRight = packetData.get("right").asBoolean();
        boolean movingForward = packetData.get("forward").asBoolean();
        boolean movingBackward = packetData.get("backward").asBoolean();
        boolean flying = packetData.get("flying").asBoolean();
        boolean sneaking = packetData.get("sneaking").asBoolean();

        float yaw = packetData.get("yaw").floatValue();
        float pitch = packetData.get("pitch").floatValue();

        this.yaw = yaw;
        this.pitch = pitch;

        this.movingLeft = movingLeft;
        this.movingRight = movingRight;
        this.movingForward = movingForward;
        this.movingBackward = movingBackward;

        front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.y = (float) Math.sin(Math.toRadians(0.0f));
        front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));

        front.normalize();
        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();

        if (movingForward)
            position = position.add(new Vector3f(front).mul(speed));

        if (movingBackward)
            position = position.sub(new Vector3f(front).mul(speed));

        if (movingLeft)
            position = position.sub(new Vector3f(right).mul(speed));

        if (movingRight)
            position = position.add(new Vector3f(right).mul(speed));

        if (flying)
            position = position.add(new Vector3f(0.0f, .05f, 0.0f));

        if (sneaking)
            position = position.sub(new Vector3f(0.0f, .05f, 0.0f));

    }

    public Vector3f getPosition() {
        return position;
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
        node.put("skin", this.skin);

        return node;
    }

    public String getSkin() {
        return skin;
    }
}
