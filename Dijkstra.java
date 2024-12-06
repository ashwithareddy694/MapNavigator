import java.util.*;

public class Dijkstra {

    public static Map<String, Integer> findShortestPath(Graph graph, String startNode) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));

        for (String node : graph.getAdjacencyList().keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(startNode, 0);
        priorityQueue.add(new Node(startNode, 0));

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            String currentNodeName = currentNode.name;

            for (Graph.Edge edge : graph.getAdjacencyList().get(currentNodeName)) {
                String neighbor = edge.destination;
                int newDist = distances.get(currentNodeName) + edge.weight;

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    priorityQueue.add(new Node(neighbor, newDist));
                }
            }
        }
        return distances;
    }

    static class Node {
        String name;
        int distance;

        Node(String name, int distance) {
            this.name = name;
            this.distance = distance;
        }
    }
}
