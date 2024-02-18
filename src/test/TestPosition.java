package test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.network.packet.PlayerMovePacket;
import fr.math.minecraft.server.Client;
import fr.math.minecraft.server.payload.InputPayload;
import fr.math.minecraft.shared.network.PlayerInputData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestPosition {

    private Player player;
    private Client client;

    @Before
    public void prepareEntities() {
        this.player = new Player("Dummy");
        this.client = new Client("1", "Dummy", null, -1);
    }

    @Test
    public void testPosition() {
        
        float yaw = 0.0f;
        float pitch = 0.0f;
        
        player.setYaw(yaw);
        player.setPitch(pitch);

        PlayerInputData inputData = new PlayerInputData(false, false, true, false, false, false, yaw, pitch);
        List<PlayerInputData> inputs = new ArrayList<>();
        inputs.add(inputData);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        ArrayNode inputsArray = mapper.createArrayNode();

        for (PlayerInputData input : inputs) {
            System.out.println(input.toJSON());
            inputsArray.add(input.toJSON());
        }

        node.put("tick", 0);
        node.put("uuid", "1");
        node.set("inputs", inputsArray);

        InputPayload inputPayload = new InputPayload(node);


        for (int i = 0; i < 1; i++) {
            client.updatePosition(inputPayload);
        }

        player.setMovingForward(true);
        for (int i = 0; i < 1; i++) {
            player.updatePosition();
        }

        System.out.println(player.getPosition());
        System.out.println(client.getPosition());

        Assert.assertEquals(player.getPosition(), client.getPosition());
    }
}
