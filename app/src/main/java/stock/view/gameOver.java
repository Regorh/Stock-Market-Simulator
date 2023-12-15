package stock.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class gameOver {
    private JFrame frame;

    public gameOver(){
        frame = new JFrame("Game Over");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JLabel endGame = new JLabel(" Game IS OVER");
        endGame.setFont(new Font("Arial", Font.BOLD, 50));

        mainPanel.add(endGame);
        frame.add(mainPanel);
        frame.setVisible(true);

    }
}
