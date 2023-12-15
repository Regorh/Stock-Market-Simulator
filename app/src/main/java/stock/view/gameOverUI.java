package stock.view;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import stock.ControllerInterface;
import stock.model.*;

public class gameOverUI {
    private JFrame frame;

    public gameOverUI(User player, ControllerInterface controller){
        frame = new JFrame("Game Over");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500 );

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        JLabel endGame = new JLabel(" GAME OVER");
        endGame.setFont(new Font("Arial", Font.BOLD, 50));
        JLabel playercapital = new JLabel("Player ended with: $" + player.getCapital());
        playercapital.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel playerdebt = new JLabel("Debt: $" + player.getCapital());
        playerdebt.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel playerstress = new JLabel("Stress: " + player.get_stress());
        playerstress.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel playersuspicion = new JLabel("SEC Suspicion: " + player.getsuspicionOfSEC());
        playersuspicion.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel playerdays = new JLabel("Your survived " + controller.get_days_played() + " days.");
        playerdays.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(playerdays, SwingConstants.CENTER);
        mainPanel.add(playersuspicion,SwingConstants.CENTER);
        mainPanel.add(playerstress,SwingConstants.CENTER);
        mainPanel.add(playerdebt,SwingConstants.CENTER);
        mainPanel.add(playercapital,SwingConstants.CENTER);
        mainPanel.add(endGame,SwingConstants.CENTER);
       





        frame.add(mainPanel);
        frame.setVisible(true);

    }
}
