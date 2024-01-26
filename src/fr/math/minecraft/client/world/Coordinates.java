package fr.math.minecraft.client.world;

import fr.math.minecraft.client.Game;
import fr.math.minecraft.client.Utils;
import fr.math.minecraft.client.entity.Player;

import java.util.Objects;

public class Coordinates {

    private int x, y, z;

    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
        double distance1 = Utils.distance(player, this);
        double distance2 = Utils.distance(player, o);

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
}
