package fr.math.minecraft.server;

import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.world.Chunk;
import fr.math.minecraft.server.world.Coordinates;
import fr.math.minecraft.server.world.ServerChunk;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Utils {

    public static double distance(Player player, Vector3i position) {

        double x = Math.pow(player.getPosition().x - position.x, 2);
        double y = Math.pow(player.getPosition().y - position.y, 2);
        double z = Math.pow(player.getPosition().z - position.z, 2);

        return Math.sqrt(x + y + z);
    }

    public static double distance(Coordinates c1, Coordinates c2) {

        double x = Math.pow(c1.getX() - c2.getX(), 2);
        double y = Math.pow(c1.getY() - c2.getY(), 2);
        double z = Math.pow(c1.getZ() - c2.getZ(), 2);

        return Math.sqrt(x + y + z);
    }

    public static double distance(Player player, Coordinates position) {
        return distance(player, new Vector3f(position.getX(), position.getY(), position.getZ()));
    }

    public static double distance(Player player, Vector3f position) {

        double x = Math.pow(player.getPosition().x - position.x, 2);
        double y = Math.pow(player.getPosition().y - position.y, 2);
        double z = Math.pow(player.getPosition().z - position.z, 2);

        return Math.sqrt(x + y + z);
    }

    public static boolean isInChunk(Coordinates coordinates, ServerChunk chunk) {

        int chunkX = chunk.getPosition().x();
        int chunkY = chunk.getPosition().y();
        int chunkZ = chunk.getPosition().z();

        int x = coordinates.getX();
        int y = coordinates.getY();
        int z = coordinates.getZ();

        boolean inX = (chunkX <= x) && (x <= (chunkX + Chunk.SIZE - 1));
        boolean inY = chunkY <= y && y <= (chunkY + (Chunk.SIZE - 1));
        boolean inZ = chunkZ <= z && z <= (chunkZ + (Chunk.SIZE - 1));

        return inX && inY && inZ;
    }
}
