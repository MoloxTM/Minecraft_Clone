package fr.math.minecraft.server.pathfinding;

import java.util.List;

public class Pattern {

    private List<Node> path;
    private int currentIndex;

    public Pattern(List<Node> path) {
        this.path = path;
        this.currentIndex = 0;
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
}
