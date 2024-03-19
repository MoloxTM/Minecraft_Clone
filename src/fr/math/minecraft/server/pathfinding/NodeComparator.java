package fr.math.minecraft.server.pathfinding;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node n1, Node n2) {
        return (int) (n2.getHeuristic() - n1.getHeuristic());
    }

}
