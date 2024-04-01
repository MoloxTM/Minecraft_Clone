package fr.math.minecraft.client.handler;

import fr.math.minecraft.client.Camera;
import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.audio.Sounds;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.client.manager.SoundManager;
import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.shared.network.PlayerInputData;
import fr.math.minecraft.client.network.payload.InputPayload;
import fr.math.minecraft.client.network.payload.StatePayload;
import fr.math.minecraft.shared.world.BreakedBlock;
import fr.math.minecraft.shared.world.PlacedBlock;
import fr.math.minecraft.shared.world.World;
import org.apache.log4j.Logger;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.Arrays;
import java.util.List;

public class PlayerMovementHandler {

    private final InputPayload[] inputBuffer;
    private final StatePayload[] stateBuffer;
    private StatePayload lastServerState;
    private int currentTick;
    private final static int BUFFER_SIZE = 1024;
    private final static Logger logger = LoggerUtility.getClientLogger(PlayerMovementHandler.class, LogType.TXT);

    public PlayerMovementHandler() {
        this.currentTick = 0;
        this.lastServerState = null;
        this.inputBuffer = new InputPayload[BUFFER_SIZE];
        this.stateBuffer = new StatePayload[BUFFER_SIZE];
    }

    public void handle(World world, Player player, Vector3f playerPosition, Vector3f playerVelocity, List<PlayerInputData> inputData, List<PlacedBlock> placedBlocks, List<BreakedBlock> brokenBlockData) {

        int bufferIndex = currentTick % BUFFER_SIZE;

        InputPayload inputPayload = new InputPayload(currentTick, inputData);
        inputBuffer[bufferIndex] = inputPayload;

        StatePayload statePayload = new StatePayload(inputPayload);
        // statePayload.predictMovement(player, playerPosition);
        statePayload.setPosition(playerPosition);
        statePayload.setVelocity(playerVelocity);
        statePayload.setBreakedBlocksData(brokenBlockData);
        statePayload.setPlacedBlocksData(placedBlocks);
        statePayload.send(player);

        //player.setLastPosition(new Vector3f(playerPosition));

        // System.out.println("Tick " + currentTick + " InputVector: " + inputVector + " Calculated position : " + playerPosition);
        stateBuffer[bufferIndex] = statePayload;

        if (lastServerState != null) {
            this.reconcile(world, player);
        }

        currentTick++;
    }

    public void reconcile(World world, Player player) {

        int serverTick = lastServerState.getInputPayload().getTick();

        Vector3f serverPosition = lastServerState.getPosition();
        Vector3f serverVelocity = lastServerState.getVelocity();

        StatePayload payload = stateBuffer[serverTick % BUFFER_SIZE];

        float positionError = serverPosition.distance(payload.getPosition());

        lastServerState.verifyPlacedBlocks(world, payload.getPlacedBlocks());
        lastServerState.verifyBrokenBlocks(world, payload.getBreakedBlockData());
        lastServerState.reconcileInventory(player);

        if (lastServerState.getHealth() < player.getHealth()) {
            SoundManager soundManager = Game.getInstance().getSoundManager();
            soundManager.play(Sounds.HIT);
        }

        player.setHealth(lastServerState.getHealth());
        player.setMaxHealth(lastServerState.getMaxHealth());

        player.setHunger(lastServerState.getHunger());
        player.setMaxHunger(lastServerState.getMaxHunger());

        if (lastServerState.getMaxSpeed() != player.getMaxSpeed()) {
            player.setMaxSpeed(lastServerState.getMaxSpeed());
        }

        if (positionError > 0.001f) {
            Camera camera = Game.getInstance().getCamera();
            logger.debug("[Reconciliation] ServerTick : " + serverTick + " ClientTick : " + currentTick + " Error : " + positionError + " ServerPosition " + serverPosition + " PayloadPosition " + payload.getPosition());
            logger.debug("[Reconciliation] Server Yaw : " + lastServerState.getYaw() + " Server Pitch : " + lastServerState.getPitch() + " Payload Yaw : " + payload.getInputPayload().getInputData().get(payload.getInputPayload().getInputData().size() - 1).getYaw() + " Payload Pitch : " + payload.getInputPayload().getInputData().get(payload.getInputPayload().getInputData().size() - 1).getPitch());
            stateBuffer[serverTick % BUFFER_SIZE] = lastServerState;

            int tickToProcess = serverTick + 1;

            player.getPosition().x = serverPosition.x;
            player.getPosition().y = serverPosition.y;
            player.getPosition().z = serverPosition.z;

            player.getVelocity().x = serverVelocity.x;
            player.getVelocity().y = serverVelocity.y;
            player.getVelocity().z = serverVelocity.z;

            //player.setYaw(lastServerState.getYaw());
            //player.setPitch(lastServerState.getPitch());

            while (tickToProcess <= currentTick) {

                InputPayload inputPayload = inputBuffer[tickToProcess % BUFFER_SIZE];
                StatePayload statePayload = new StatePayload(inputPayload);
                statePayload.reconcileMovement(world, player, player.getPosition(), player.getVelocity());

                int bufferIndex = tickToProcess % BUFFER_SIZE;
                stateBuffer[bufferIndex] = statePayload;

                tickToProcess++;
            }

            camera.update(player);
        }
    }

    public StatePayload getLastServerState() {
        return lastServerState;
    }

    public void setLastServerState(StatePayload lastServerState) {
        this.lastServerState = lastServerState;
    }
}
