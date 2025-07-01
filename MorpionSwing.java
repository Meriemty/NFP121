import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MorpionSwing extends JFrame {
    private char currentPlayer = 'X';
    private JButton[][] grid = new JButton[3][3];
    private JLabel statusLabel = new JLabel("Joueur X, à vous de jouer !");
    
    public MorpionSwing() {
        setTitle("Jeu de Morpion");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 60));
                button.setFocusPainted(false);
                button.addActionListener(new ButtonClickListener(i, j));
                grid[i][j] = button;
                boardPanel.add(button);
            }
        }
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Jeu");
        JMenuItem newGame = new JMenuItem("Nouvelle partie");
        JMenuItem exitGame = new JMenuItem("Quitter");
        
        newGame.addActionListener(e -> resetGame());
        exitGame.addActionListener(e -> System.exit(0));
        
        menu.add(newGame);
        menu.add(exitGame);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        
        add(statusLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
    }
    
    private class ButtonClickListener implements ActionListener {
        int row, col;
        
        ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        public void actionPerformed(ActionEvent e) {
            if (grid[row][col].getText().isEmpty()) {
                grid[row][col].setText(String.valueOf(currentPlayer));
                if (checkWin()) {
                    JOptionPane.showMessageDialog(null, "Le joueur " + currentPlayer + " a gagné !");
                    resetGame();
                    return;
                } else if (isDraw()) {
                    JOptionPane.showMessageDialog(null, "Match nul !");
                    resetGame();
                    return;
                }
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                statusLabel.setText("Joueur " + currentPlayer + ", à vous de jouer !");
            }
        }
    }
    
    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (grid[i][0].getText().equals(grid[i][1].getText()) && grid[i][1].getText().equals(grid[i][2].getText()) && !grid[i][0].getText().isEmpty())
                return true;
            if (grid[0][i].getText().equals(grid[1][i].getText()) && grid[1][i].getText().equals(grid[2][i].getText()) && !grid[0][i].getText().isEmpty())
                return true;
        }
        if (grid[0][0].getText().equals(grid[1][1].getText()) && grid[1][1].getText().equals(grid[2][2].getText()) && !grid[0][0].getText().isEmpty())
            return true;
        if (grid[0][2].getText().equals(grid[1][1].getText()) && grid[1][1].getText().equals(grid[2][0].getText()) && !grid[0][2].getText().isEmpty())
            return true;
        return false;
    }
    
    private boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void resetGame() {
        currentPlayer = 'X';
        statusLabel.setText("Joueur X, à vous de jouer !");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setText("");
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MorpionSwing().setVisible(true);
        });
    }
}

