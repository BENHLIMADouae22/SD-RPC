package org.example.interfaceUser;

import org.example.centralserver.CentralServer;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MatrixInput extends JFrame {
    private JTextField[][] matrix1Fields;
    private JTextField[][] matrix2Fields;
    private JTextArea resultArea;
    private CentralServer server;

    public MatrixInput() {
        server = new CentralServer(4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        matrix1Fields = createMatrixPanel("Matrice 1");
        matrix2Fields = createMatrixPanel("Matrice 2");

        JButton button = new JButton("Calculer la multiplication");
        button.addActionListener(e -> calculateMultiplication());
        add(button);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));

        // Bouton Réinitialiser
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.addActionListener(e -> resetInputs());

        // Panel pour le bouton Réinitialiser
        JPanel resetPanel = new JPanel();
        resetPanel.add(resetButton);
        add(resetPanel);



        setSize(400, 300); // Définir la taille de la fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        pack();
        setVisible(true);
    }
    private void resetInputs() {
        // Réinitialiser les valeurs des champs de texte
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrix1Fields[i][j].setText("");
                matrix2Fields[i][j].setText("");
            }
        }
        resultArea.setText("");
    }

    private JTextField[][] createMatrixPanel(String title) {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        add(panel);

        JTextField[][] fields = new JTextField[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                fields[i][j] = new JTextField();
                panel.add(fields[i][j]);
            }
        }
        return fields;
    }

    private void calculateMultiplication() {
        System.out.println("calculateMultiplication: start");

        int[][] matrix1 = getMatrixFromFields(matrix1Fields);
        int[][] matrix2 = getMatrixFromFields(matrix2Fields);

        try {
            int[][] result = server.multiplyMatrices(matrix1, matrix2);
            System.out.println("calculateMultiplication: result = " + Arrays.deepToString(result));
            resultArea.setText(Arrays.deepToString(result));
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("calculateMultiplication: exception = " + e.getMessage());
            resultArea.setText("Erreur lors du calcul de la multiplication: " + e.getMessage());
        }

        System.out.println("calculateMultiplication: end");
    }

    private int[][] getMatrixFromFields(JTextField[][] fields) {
        int[][] matrix = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrix[i][j] = Integer.parseInt(fields[i][j].getText());
            }
        }
        return matrix;
    }

    @Override
    public void dispose() {
        server.shutdown();
        super.dispose();
    }
}
