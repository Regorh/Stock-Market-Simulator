package stock.view;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import stock.model.*;

public class gameOverUI {
    private JFrame frame;

    public gameOverUI(User player){
        frame = new JFrame("Game Over");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500 );

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        JLabel endGame = new JLabel(" GAME OVER");
        endGame.setFont(new Font("Arial", Font.BOLD, 50));
        JLabel playercapital = new JLabel("Player ended with: $" + player.getCapital());
        JLabel playerdebt = new JLabel("Debt: $" + player.getCapital());
        JLabel playerstress = new JLabel("Stress: " + player.get_stress());
        JLabel playersuspicion = new JLabel("SEC Suspicion: " + player.getsuspicionOfSEC());
        playercapital.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerdebt.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerstress.setAlignmentX(Component.CENTER_ALIGNMENT);
        playersuspicion.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(endGame,SwingConstants.CENTER);
        mainPanel.add(playercapital,SwingConstants.CENTER);
        mainPanel.add(playerdebt,SwingConstants.CENTER);
        mainPanel.add(playerstress,SwingConstants.CENTER);
        mainPanel.add(playersuspicion,SwingConstants.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);

    }
}
