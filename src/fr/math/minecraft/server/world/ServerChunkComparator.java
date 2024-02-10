package fr.math.minecraft.server.world;

import fr.math.minecraft.server.Client;
import org.joml.Vector3fc;
import org.joml.Vector3ic;

import java.util.Comparator;

public class ServerChunkComparator implements Comparator<ServerChunk> {

    private final Client client;

    public ServerChunkComparator(Client client) {
        this.client = client;
    }

    @Override
    public int compare(ServerChunk o1, ServerChunk o2) {

        float distance1 = client.getPosition().distance(o1.getPosition().x, o1.getPosition().y, o1.getPosition().z);
        float distance2 = client.getPosition().distance(o2.getPosition().x, o2.getPosition().y, o2.getPosition().z);

        if (distance1 < distance2) {
            if (o1.isEmpty()) {
                return -1;
            }
            return 1;
        } else if (distance2 < distance1) {
            if (o2.isEmpty()) {
                return 1;
            }
            return -1;
        }

        return 0;
    }
}
