package fr.math.minecraft.client.network.payload;

import com.fasterxml.jackson.databind.JsonNode;
import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.handler.PacketHandler;
import fr.math.minecraft.client.network.packet.PlayerMovePacket;
import org.joml.Vector3f;

public class StatePayload {

    private final InputPayload payload;
    private JsonNode data;
    private final boolean movingLeft, movingRight, movingForward, movingBackward;
    private final boolean sneaking, flying;
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

    public void predictMovement(Player player) {

        Vector3f front = new Vector3f();
        Game game = Game.getInstance();
        Camera camera = game.getCamera();

        front.x = (float) (Math.cos(Math.toRadians(player.getYaw())) * Math.cos(Math.toRadians(player.getPitch())));
        front.y = (float) Math.sin(Math.toRadians(0.0f));
        front.z = (float) (Math.sin(Math.toRadians(player.getYaw())) * Math.cos(Math.toRadians(player.getPitch())));

        front.normalize();

        Vector3f right = new Vector3f(front).cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f position = player.getPosition();
        float speed = player.getSpeed();

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

        camera.update(player);

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
}
