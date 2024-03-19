package test;

import fr.math.minecraft.server.pathfinding.AStar;
import fr.math.minecraft.server.pathfinding.Graph;
import fr.math.minecraft.server.pathfinding.Node;
import fr.math.minecraft.shared.entity.Entity;
import fr.math.minecraft.shared.entity.EntityFactory;
import fr.math.minecraft.shared.entity.EntityType;
import fr.math.minecraft.shared.entity.mob.Zombie;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.World;
import org.joml.Vector3f;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestPathfinding {

    @Test
    public void testPathFinding() {
        /*
        World world = new World();
        world.buildSpawn();
        world.calculateSpawnPosition();
        Entity zombie = EntityFactory.createEntity(EntityType.ZOMBIE);
        zombie.setPosition(new Vector3f(world.getSpawnPosition()).add(0, 5, 0));
        world.addEntity(zombie);
        AStar.initGraph(world, zombie.getPosition());
        Node start = new Node(zombie.getPosition(), false);
        Node end = new Node(new Vector3f(zombie.getPosition()).add(10, 0, 0), false);
        List<Node> path = AStar.shortestPath(world.getGraph(), start, end);

        System.out.println(Arrays.toString(path.toArray()));
         */

        Vector3f position = new Vector3f(10, 0, -1);
        Node currentNode = new Node(0, 0, false);

        Vector3f nodePosition = new Vector3f(currentNode.getPosition().x, 0, currentNode.getPosition().y);
        Vector3f direction = new Vector3f(position.x, 0, position.z).sub(nodePosition);
        System.out.println(direction);
    }

    @Test
    public void testGraph() {
        Graph graph = new Graph();
        graph.addNode(new Node(0, 0, false));
        graph.addNode(new Node(1, 0, false));
        graph.addNode(new Node(2, 0, false));
        graph.addNode(new Node(3, 0, false));

        graph.addLink(new Node(0, 0, true), new Node(1, 0, false));
        graph.addLink(new Node(0, 0, true), new Node(2, 0, false));

        System.out.println(graph);

    }
}
