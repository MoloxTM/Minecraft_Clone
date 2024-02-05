package fr.math.minecraft.client.network.payload;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.handler.PacketHandler;
import fr.math.minecraft.client.network.packet.PlayerMovePacket;
import org.joml.Vector3f;

public class StatePayload {

    private InputPayload payload;
    private JsonNode data;
    private boolean movingLeft, movingRight, movingForward, movingBackward;
    private boolean sneaking, flying;
    private Vector3f position;

    public StatePayload(InputPayload payload) {
        this.payload = payload;
        this.movingLeft = payload.isMovingLeft();
        this.movingRight = payload.isMovingRight();
        this.movingForward = payload.isMovingForward();
        this.movingBackward = payload.isMovingBackward();
        this.flying = payload.isFlying();
        this.sneaking = payload.isSneaking();
        this.position = new Vector3f();
        this.data = null;
    }

    public StatePayload(JsonNode stateData) {
        this.position = new Vector3f();
        this.payload = new InputPayload(stateData.get("tick").asInt());
        position.x = stateData.get("x").floatValue();
        position.y = stateData.get("y").floatValue();
        position.z = stateData.get("z").floatValue();
    }

    public void predictMovement(Player player) {

        Vector3f front = new Vector3f();
        Game game = Game.getInstance();
        float yaw = player.getYaw();
        float speed = player.getSpeed();
        float pitch = player.getPitch();

        front.x = (float) (Math.cos(Math.toRadians(yaw) * Math.cos(Math.toRadians(pitch))));
        front.y = (float) Math.sin(Math.toRadians(0.0f));
        front.z = (float) (Math.sin(Math.toRadians(yaw) * Math.cos(Math.toRadians(pitch))));

        front.normalize();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        position = new Vector3f(player.getPosition());

        if (movingForward)
            position.add(new Vector3f(front).mul(speed));

        if (movingBackward)
            position.sub(new Vector3f(front).mul(speed));

        if (movingLeft)
            position.sub(new Vector3f(right).mul(speed));

        if (movingRight)
            position.add(new Vector3f(right).mul(speed));

        if (flying)
            position.add(new Vector3f(0.0f, .5f, 0.0f));

        if (sneaking)
            position.sub(new Vector3f(0.0f, .5f, 0.0f));

        this.position = new Vector3f(position);
    }

    public void send() {
        PlayerMovePacket packet = new PlayerMovePacket(this, payload);
        PacketHandler.getInstance().enqueue(packet);
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public Vector3f getPosition() {
        return position;
    }

    public InputPayload getInputPayload() {
        return payload;
    }
}
