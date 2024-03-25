package fr.math.minecraft.server.pathfinding;

import fr.math.minecraft.logger.LogType;
import fr.math.minecraft.logger.LoggerUtility;
import fr.math.minecraft.shared.GameConfiguration;
import fr.math.minecraft.shared.world.Chunk;
import fr.math.minecraft.shared.world.Material;
import fr.math.minecraft.shared.world.World;
import fr.math.minecraft.shared.world.generator.OverworldGenerator;
import org.apache.log4j.Logger;
import org.joml.Math;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.*;

public class AStar {

    private final static Logger logger = LoggerUtility.getServerLogger(AStar.class, LogType.TXT);

    public static void initGraph(World world, Vector3f startPosition) {
        int distance = 2;
        int startX = (int) (Math.floor(startPosition.x / (double) Chunk.SIZE) - distance);
        int startY = (int) (Math.floor(startPosition.y / (double) Chunk.SIZE) - distance);
        int startZ = (int) (Math.floor(startPosition.z / (double) Chunk.SIZE) - distance);

        int endX = (int) (Math.floor(startPosition.x / (double) Chunk.SIZE) + distance);
        int endY = (int) (Math.floor(startPosition.y / (double) Chunk.SIZE) + distance);
        int endZ = (int) (Math.floor(startPosition.z / (double) Chunk.SIZE) + distance);

        Graph graph = world.getGraph();

        logger.info("[Pathfinding] Lancement de la tâche de mis à jour du graphe.");

        for (int chunkX = startX; chunkX < endX; chunkX++) {
            for (int chunkY = startY; chunkY < endY; chunkY++) {
                for (int chunkZ = startZ; chunkZ < endZ; chunkZ++) {
                    Chunk chunk = world.getChunk(chunkX, chunkY, chunkZ);

                    if (chunk == null) {
                        chunk = new Chunk(chunkX, chunkY, chunkZ);
                        chunk.generate(world, world.getTerrainGenerator());

                        synchronized (world.getChunks()) {
                            world.addChunk(chunk);
                        }
                    }

                    if (graph.getLoadedChunks().contains(chunk.getPosition())) {
                        continue;
                    }

                    graph.getLoadedChunks().add(chunk.getPosition());

                    for (int x = 0; x < Chunk.SIZE; x++) {
                        for (int z = 0; z < Chunk.SIZE; z++) {
                            boolean solidNode = false;
                            int worldX = x + chunk.getPosition().x * Chunk.SIZE;
                            int worldZ = z + chunk.getPosition().z * Chunk.SIZE;

                            for (int y = 0; y < Chunk.SIZE; y++) {
                                Material material = Material.getMaterialById(chunk.getBlock(x, y, z));
                                if (material.isSolid()) {
                                    solidNode = true;
                                }
                            }

                            if (solidNode) {
                                continue;
                            }

                            Node node = new Node(worldX, worldZ);
                            synchronized (world.getGraph()) {
                                graph.addNode(node);
                            }
                        }
                    }
                }
            }
        }

        logger.info("[Pathfinding] Les noeuds du graphes ont été construits.");
        logger.info("[Pathfinding] Construction des connexions entre les noeuds.");
        Set<Node> graphNodes = new HashSet<>(graph.getNodes().keySet());

        for (Node node : graphNodes) {
            Vector2i position = node.getPosition();
            List<Node> adjacentsNode = getAdjacentsNode(position);

            for (Node neighbor : adjacentsNode) {

                if (graph.getNodes().get(neighbor) == null) {
                    continue;
                }

                graph.addLink(node, neighbor);
            }
        }

        logger.info("[Pathfinding] Graphe mis à jour avec succès.");
        logger.info("[Pathfinding] Nodes size : " + graph.getNodes().size());
    }

    public static List<Node> getAdjacentsNode(Vector2i position) {
        List<Node> adjacentsNode = new ArrayList<>();

        adjacentsNode.add(new Node(position.x - 1, position.y));
        adjacentsNode.add(new Node(position.x + 1, position.y));
        adjacentsNode.add(new Node(position.x, position.y - 1));
        adjacentsNode.add(new Node(position.x, position.y + 1));
        adjacentsNode.add(new Node(position.x + 1, position.y + 1));
        adjacentsNode.add(new Node(position.x - 1, position.y + 1));
        adjacentsNode.add(new Node(position.x - 1, position.y - 1));
        adjacentsNode.add(new Node(position.x + 1, position.y - 1));

        return adjacentsNode;
    }

    public static List<Node> buildPath(Node node) {
        List<Node> path = new ArrayList<>();
        Node current = node;
        while (current.getParent() != null) {
            path.add(current);
            current = current.getParent();
        }
        path.add(current);
        Collections.reverse(path);
        return path;
    }

    public static List<Node> shortestPath(World world, Node start, Node goal) {
        Graph graph = world.getGraph();

        synchronized (world.getGraph()) {
            for (Node node : graph.getNodes().keySet()) {
                node.setGCost((float) node.getPosition().distance(start.getPosition()));
                node.setHCost((float) node.getPosition().distance(goal.getPosition()));
            }
        }

        Set<Node> closedList = new HashSet<>();
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparing(Node::getFCost));
        openList.add(start);

        start.setGCost(0);
        start.setHCost((float) start.getPosition().distance(goal.getPosition()));

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.getPosition().equals(goal.getPosition())) {
                return buildPath(current);
            }

            if (graph.getNeighbors(current) != null) {
                for (Node neighbor : graph.getNeighbors(current)) {

                    if (closedList.contains(neighbor)) {
                        continue;
                    }

                    float newScore = current.getGCost() + (float) current.getPosition().distance(neighbor.getPosition());

                    if (!closedList.contains(neighbor) || newScore < neighbor.getGCost()) {
                        neighbor.setParent(current);
                        neighbor.setGCost(newScore);
                        neighbor.setFCost(neighbor.getHCost() + newScore);
                        if (!openList.contains(neighbor)) {
                            openList.add(neighbor);
                        }
                    }
                }
            }

            closedList.add(current);
        }

        return new ArrayList<>();
    }
}
