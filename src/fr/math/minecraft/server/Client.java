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

    public Client(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.front = new Vector3f(0.0f, 0.0f, 0.0f);
        this.position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.speed = 0.05f;
    }

    public String getName() {
        return name;
    }

    public void updatePosition(String direction, float yaw, float pitch) {

        this.yaw = yaw;
        this.pitch = pitch;

        front.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front.y = (float) Math.sin(Math.toRadians(0.0f));
        front.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));

        front.normalize();
        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();

        switch (direction) {
            case "FORWARD":
                position = position.add(new Vector3f(front).mul(speed));
                break;
            case "BACKWARD":
                position = position.sub(new Vector3f(front).mul(speed));
                break;
            case "LEFT":
                position = position.sub(new Vector3f(right).mul(speed));
                break;
            case "RIGHT":
                position = position.add(new Vector3f(right).mul(speed));
                break;
        }
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

        return node;
    }
}
