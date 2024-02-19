package fr.math.minecraft.client.handler;

import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.shared.network.PlayerInputData;
import fr.math.minecraft.client.network.payload.InputPayload;
import fr.math.minecraft.client.network.payload.StatePayload;
import org.joml.Vector3f;

import java.util.List;

public class PlayerMovementHandler {

    private final InputPayload[] inputBuffer;
    private final StatePayload[] stateBuffer;
    private StatePayload lastServerState;
    private int currentTick;
    private final static int BUFFER_SIZE = 1024;

    public PlayerMovementHandler() {
        this.currentTick = 0;
        this.lastServerState = null;
        this.inputBuffer = new InputPayload[BUFFER_SIZE];
        this.stateBuffer = new StatePayload[BUFFER_SIZE];
    }

    public void handle(Player player, Vector3f playerPosition, List<PlayerInputData> inputData) {

        if (lastServerState != null) {
            this.reconcile(player);
        }

        int bufferIndex = currentTick % BUFFER_SIZE;

        InputPayload inputPayload = new InputPayload(currentTick, inputData);
        inputBuffer[bufferIndex] = inputPayload;

        StatePayload statePayload = new StatePayload(inputPayload);
        // statePayload.predictMovement(player, playerPosition);
        statePayload.setPosition(playerPosition);
        statePayload.send(player);

        // System.out.println("Tick " + currentTick + " InputVector: " + inputVector + " Calculated position : " + playerPosition);
        stateBuffer[bufferIndex] = statePayload;

        currentTick++;
    }

    public void reconcile(Player player) {

        int serverTick = lastServerState.getInputPayload().getTick();
        Vector3f serverPosition = lastServerState.getPosition();
        Vector3f serverVelocity = lastServerState.getPosition();

        StatePayload payload = stateBuffer[serverTick % BUFFER_SIZE];

        float positionError = serverPosition.distance(payload.getPosition());

        if (positionError > 0.001f) {
            Camera camera = Game.getInstance().getCamera();
            System.out.println("[Reconciliation] ServerTick : " + serverTick + " ClientTick : " + currentTick + " Error : " + positionError + " ServerPosition " + serverPosition + " PayloadPosition " + payload.getPosition());
            System.out.println("[Reconciliation] Server Yaw : " + lastServerState.getYaw() + " Server Pitch : " + lastServerState.getPitch() + " Payload Yaw : " + payload.getInputPayload().getInputData().get(payload.getInputPayload().getInputData().size() - 1).getYaw() + " Payload Pitch : " + payload.getInputPayload().getInputData().get(payload.getInputPayload().getInputData().size() - 1).getPitch());
            stateBuffer[serverTick % BUFFER_SIZE] = lastServerState;

            int tickToProcess = serverTick + 1;

            player.getPosition().x = serverPosition.x;
            player.getPosition().y = serverPosition.y;
            player.getPosition().z = serverPosition.z;

            player.getVelocity().x = serverVelocity.x;
            player.getVelocity().y = serverVelocity.y;
            player.getVelocity().z = serverVelocity.z;

            player.setYaw(lastServerState.getYaw());
            player.setPitch(lastServerState.getPitch());

            camera.update(player);

            while (tickToProcess < currentTick) {

                InputPayload inputPayload = inputBuffer[tickToProcess % BUFFER_SIZE];
                StatePayload statePayload = new StatePayload(inputPayload);
                statePayload.predictMovement(player, player.getPosition(), player.getVelocity());

                camera.update(player);

                int bufferIndex = tickToProcess % BUFFER_SIZE;
                stateBuffer[bufferIndex] = statePayload;

                tickToProcess++;
            }
        }
    }

    public StatePayload getLastServerState() {
        return lastServerState;
    }

    public void setLastServerState(StatePayload lastServerState) {
        this.lastServerState = lastServerState;
    }
}
