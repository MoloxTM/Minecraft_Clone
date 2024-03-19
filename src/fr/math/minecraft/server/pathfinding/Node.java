package fr.math.minecraft.server.pathfinding;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.Objects;

public class Node {

    private float cost;
    private float heuristic;
    private boolean solid;
    private final Vector2i position;
    private Node parent;

    public Node(int x, int y, boolean solid) {
        this(new Vector2i(x, y), solid);
    }

    public Node(Vector3i position, boolean solid) {
        this(new Vector2i(position.x, 0, position.z), solid);
    }

    public Node(Vector3f position, boolean solid) {
        this(new Vector2i((int) position.x, (int) position.z), solid);
    }

    public Node(Vector2i position, boolean solid) {
        this.position = position;
        this.cost = -1;
        this.heuristic = -1;
        this.solid = solid;
        this.parent = null;
    }

    public Vector2i getPosition() {
        return position;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public void setHeuristic(float heuristic) {
        this.heuristic = heuristic;
    }

    public float getHeuristic() {
        return heuristic;
    }

    public boolean isSolid() {
        return solid;
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
                "cost=" + cost +
                ", heuristic=" + heuristic +
                ", solid=" + solid +
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
