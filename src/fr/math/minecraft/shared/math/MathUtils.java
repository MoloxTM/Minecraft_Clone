package fr.math.minecraft.shared.math;

import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.shared.world.Coordinates;
import java.lang.Math;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class MathUtils {

    public static Vector2f getDirection2D(Vector3f p1, Vector3f p2) {
        Vector2f position = new Vector2f(p1.x, p1.z);
        Vector2f direction = new Vector2f(p2.x, p2.z).sub(position);

        return direction;
    }

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

    public static float fra0(float x) {
        return (float) (x - Math.floor(x));
    }

    public static float fra1(float x) {
        return (float) (1 - x + Math.floor(x));
    }

    public static int mod(int a, int b) {
        int modulo = a % b;
        if (modulo < 0) {
            modulo += b;
        }
        return modulo;
    }
}
