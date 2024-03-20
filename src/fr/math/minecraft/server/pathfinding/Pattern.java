package fr.math.minecraft.server.pathfinding;

import java.util.List;

public class Pattern {

    private List<Node> path;
    private int currentIndex;
    private final Node start, end;

    public Pattern(List<Node> path, Node start, Node end) {
        this.path = path;
        this.currentIndex = 0;
        this.start = start;
        this.end = end;
    }

    public void setPath(List<Node> path) {
        this.path = path;
    }

    public List<Node> getPath() {
        return path;
    }

    public Pattern next() {
        if (!path.isEmpty()) {
            path.remove(0);
            return this;
        }
        return null;
    }

    public Node getCurrentNode() {
        if (currentIndex >= path.size()) {
            return null;
        }
        return path.get(currentIndex);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }
}
