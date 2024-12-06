import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class MapNavigator extends Frame {

    private Graph graph = new Graph();
    private TextField nodeField, edgeField, startField, endField;
    private Button addNodeButton, addEdgeButton, findPathButton;
    private Map<String, Point> nodeLocations = new HashMap<>();

    public MapNavigator() {
        setTitle("Map Navigator");
        setSize(800, 600);
        setLayout(null);
        setVisible(true);

        Label nodeLabel = new Label("Node:");
        nodeLabel.setBounds(50, 50, 50, 30);
        add(nodeLabel);

        nodeField = new TextField();
        nodeField.setBounds(100, 50, 100, 30);
        add(nodeField);

        addNodeButton = new Button("Add Node");
        addNodeButton.setBounds(220, 50, 80, 30);
        add(addNodeButton);

        Label edgeLabel = new Label("Edge (node1-node2):");
        edgeLabel.setBounds(50, 100, 120, 30);
        add(edgeLabel);

        edgeField = new TextField();
        edgeField.setBounds(170, 100, 100, 30);
        add(edgeField);

        addEdgeButton = new Button("Add Edge");
        addEdgeButton.setBounds(280, 100, 80, 30);
        add(addEdgeButton);

        Label startLabel = new Label("Start:");
        startLabel.setBounds(50, 150, 50, 30);
        add(startLabel);

        startField = new TextField();
        startField.setBounds(100, 150, 100, 30);
        add(startField);

        Label endLabel = new Label("End:");
        endLabel.setBounds(220, 150, 50, 30);
        add(endLabel);

        endField = new TextField();
        endField.setBounds(270, 150, 100, 30);
        add(endField);

        findPathButton = new Button("Find Path");
        findPathButton.setBounds(380, 150, 100, 30);
        add(findPathButton);

        addNodeButton.addActionListener(e -> {
            String nodeName = nodeField.getText().trim();
            if (!nodeName.isEmpty() && !graph.hasNode(nodeName)) {
                graph.addNode(nodeName);
                nodeLocations.put(nodeName, new Point(100 + nodeLocations.size() * 100, 300));
                repaint();
            }
            nodeField.setText("");
        });

        addEdgeButton.addActionListener(e -> {
            String edgeText = edgeField.getText().trim();
            String[] nodes = edgeText.split("-");
            if (nodes.length == 2) {
                graph.addEdge(nodes[0].trim(), nodes[1].trim(), 1);
                repaint();
            }
            edgeField.setText("");
        });

        findPathButton.addActionListener(e -> {
            String startNode = startField.getText().trim();
            String endNode = endField.getText().trim();
            if (!startNode.isEmpty() && !endNode.isEmpty()) {
                Map<String, Integer> distances = Dijkstra.findShortestPath(graph, startNode);
                Integer distance = distances.get(endNode);
                if (distance != null && distance != Integer.MAX_VALUE) {
                    JOptionPane.showMessageDialog(this,
                            "Shortest path from " + startNode + " to " + endNode + ": " + distance);
                } else {
                    JOptionPane.showMessageDialog(this, "No path found.");
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Map.Entry<String, Point> entry : nodeLocations.entrySet()) {
            String node = entry.getKey();
            Point p = entry.getValue();
            g.setColor(Color.BLUE);
            g.fillOval(p.x - 10, p.y - 10, 20, 20);
            g.setColor(Color.WHITE);
            g.drawString(node, p.x - 5, p.y + 5);
        }

        for (String node : graph.getAdjacencyList().keySet()) {
            Point from = nodeLocations.get(node);
            for (Graph.Edge edge : graph.getAdjacencyList().get(node)) {
                Point to = nodeLocations.get(edge.destination);
                g.setColor(Color.BLACK);
                g.drawLine(from.x, from.y, to.x, to.y);
            }
        }
    }

    public static void main(String[] args) {
        new MapNavigator();
    }
}
