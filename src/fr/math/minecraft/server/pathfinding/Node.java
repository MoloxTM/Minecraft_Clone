package fr.math.minecraft.server.pathfinding;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.Objects;

public class Node {

    private float gCost, fCost, hCost;
    private float heuristic;
    private final Vector2i position;
    private Node parent;

    public Node(int x, int y) {
        this(new Vector2i(x, y));
    }

    public Node(Vector3i position) {
        this(new Vector2i(position.x, 0, position.z));
    }

    public Node(Vector3f position) {
        this(new Vector2i((int) position.x, (int) position.z));
    }

    public Node(Vector2i position) {
        this.position = position;
        this.gCost = -1;
        this.hCost = -1;
        this.fCost = -1;
        this.heuristic = -1;
        this.parent = null;
    }

    public Vector2i getPosition() {
        return position;
    }

    public float getGCost() {
        return gCost;
    }

    public void setGCost(float gCost) {
        this.gCost = gCost;
    }

    public float getFCost() {
        return fCost;
    }

    public void setFCost(float fCost) {
        this.fCost = fCost;
    }

    public float getHCost() {
        return hCost;
    }

    public void setHCost(float hCost) {
        this.hCost = hCost;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Node{" +
                ", position=" + position +
                ", heuristic=" + heuristic +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(position, node.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
