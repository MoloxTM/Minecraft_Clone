package fr.math.minecraft.server.pathfinding;

import org.joml.Vector3i;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {

    private final Map<Node, Set<Node>> nodes;
    private final Map<Node, Node> world;
    private final Set<Vector3i> loadedChunks;

    public Graph() {
        this.nodes = new HashMap<>();
        this.world = new HashMap<>();
        this.loadedChunks = new HashSet<>();
    }

    public Map<Node, Set<Node>> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        nodes.put(node, new HashSet<>());
        world.put(node, node);
    }

    public void removeNode(Node node) {
        if (nodes.containsKey(node)) {
            nodes.remove(node);
        }
    }

    public boolean isLinked(Node node1, Node node2) {
        return nodes.get(node1).contains(node2);
    }

    public void addLink(Node node1, Node node2) {
        nodes.get(node1).add(node2);
        nodes.get(node2).add(node1);
    }

    public void removeLink(Node node1, Node node2) {
        if (isLinked(node1, node2)) {
            nodes.get(node1).remove(node2);
            nodes.get(node2).remove(node1);
        }
    }

    public Set<Vector3i> getLoadedChunks() {
        return loadedChunks;
    }

    public Set<Node> getNeighbors(Node node) {
        return nodes.get(node);
    }

    @Override
    public String toString() {
        String res = "";
        for (Node node : nodes.keySet()) {
            res += node;
            Set<Node> neighbors = this.getNeighbors(node);
            if (neighbors == null || neighbors.isEmpty()) {
                res += " [ ]";
            } else {
                res += " " + neighbors;
            }
            res += "\n";
        }
        return res;
    }
}
