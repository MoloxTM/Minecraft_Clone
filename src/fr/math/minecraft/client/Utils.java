package fr.math.minecraft.client;

import fr.math.minecraft.client.entity.Player;
import fr.math.minecraft.client.world.Coordinates;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class Utils {

    public static double distance(Player player, Vector3i position) {

        double x = Math.pow(player.getPosition().x - position.x, 2);
        double y = Math.pow(player.getPosition().y - position.y, 2);
        double z = Math.pow(player.getPosition().z - position.z, 2);

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

}
