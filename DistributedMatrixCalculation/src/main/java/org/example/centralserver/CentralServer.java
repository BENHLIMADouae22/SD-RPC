package org.example.centralserver;

import org.example.client.Node;
import org.example.client.NodeImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CentralServer {
    private ExecutorService executor;
    private List<Node> nodes;

    public CentralServer(int numNodes) {
        executor = Executors.newFixedThreadPool(numNodes);
        nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            nodes.add(new NodeImpl());
        }
    }

    public int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) throws InterruptedException, ExecutionException {
        int[][] result = new int[matrix1.length][matrix2[0].length];

        // Create a list to hold the Future object associated with Callable
        List<Future<int[]>> futures = new ArrayList<>();

        for (int i = 0; i < matrix1.length; i++) {
            Node node = nodes.get(i % nodes.size());
            int rowIndex = i;
            Callable<int[]> callable = () -> node.calculate(rowIndex, matrix1, matrix2);
            // Submit Callable tasks to be executed by thread pool
            Future<int[]> future = executor.submit(callable);
            // Add Future to the list, we can get return value using Future
            futures.add(future);
        }

        for (int i = 0; i < matrix1.length; i++) {
            result[i] = futures.get(i).get();
        }

        return result;
    }

    public void shutdown() {
        executor.shutdown();
    }
}
