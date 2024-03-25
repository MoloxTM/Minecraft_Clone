package fr.math.minecraft.shared.world;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.entity.player.Player;
import fr.math.minecraft.shared.math.MathUtils;
import org.joml.Vector3i;

import java.util.Objects;

public class Coordinates {

    private int x, y, z;

    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinates(Vector3i position) {
        this.x = position.x;
        this.y = position.y;
        this.z = position.z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public int compareTo(Coordinates o) {
        Player player = Game.getInstance().getPlayer();
        double distance1 = MathUtils.distance(player, this);
        double distance2 = MathUtils.distance(player, o);

        if (distance1 < distance2) {
            return 1;
        } else if (distance1 > distance2) {
            return -1;
        }
        return 0;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
