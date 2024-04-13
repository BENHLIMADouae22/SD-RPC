package org.example.client;

public class NodeImpl implements Node{
    @Override
    public int[] calculate(int rowIndex, int[][] matrix1, int[][] matrix2) throws InterruptedException {
        int[] result = new int[matrix2[0].length];
        for (int i = 0; i < matrix2[0].length; i++) {
            for (int j = 0; j < matrix1[rowIndex].length; j++) {
                result[i] += matrix1[rowIndex][j] * matrix2[j][i];
            }
        }
        return result;
    }
}
