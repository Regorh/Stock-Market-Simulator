package stock.view;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import stock.ControllerInterface;


public class startUI {
    
    private JFrame mainFrame;
    public startUI(ControllerInterface controller){
        //The startUI is a pop up screen that is created and presented to the user when 
        //they run the program. There will be three buttons from which the user will select 
        //difficulty. This button selection will send a string to the game controller.
        mainFrame = new JFrame("Main Screen");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(45, 0, 0, 0));
        buttonPanel.setSize(200, 200);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(0, 0, 139));
       
        JPanel marketback = new JPanel(new BorderLayout());
        marketback.setBorder(BorderFactory.createEmptyBorder(45, 0, 0, 0));
        marketback.setSize(200, 200);
        marketback.setLayout(new BoxLayout(marketback, BoxLayout.Y_AXIS));
        marketback.setBackground(new Color(0, 0, 139));

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new BoxLayout(boardPanel, BoxLayout.Y_AXIS));
        boardPanel.setBackground(new Color(0, 0, 139));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Stock Sim", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(255, 255, 255));
        boardPanel.add(title);

        JLabel question = new JLabel("Choose a Game Mode!");
        question.setAlignmentX(Component.CENTER_ALIGNMENT);
        question.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        question.setFont(new Font("Arial", Font.BOLD, 20));
        question.setForeground(new Color(255, 255, 255));
        boardPanel.add(question);

        JButton easy = new JButton("Easy");
        easy.setAlignmentX(Component.CENTER_ALIGNMENT);
        easy.setPreferredSize(new Dimension(150, 20));
        easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.userChoose("easy");         
                mainFrame.setVisible(false);
            }
        });

        JButton medium = new JButton("Medium");
        medium.setAlignmentX(Component.CENTER_ALIGNMENT);
        medium.setPreferredSize(new Dimension(150, 20));
        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.userChoose("medium");
                mainFrame.setVisible(false);
            }
        });

        JButton hard = new JButton("Hard");
        hard.setAlignmentX(Component.CENTER_ALIGNMENT);
        hard.setPreferredSize(new Dimension(150, 20));
        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.userChoose("hard");
                mainFrame.setVisible(false);
            }
        });

        buttonPanel.add(easy);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(medium);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(hard);

        boardPanel.add(buttonPanel);

        mainPanel.add(boardPanel);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }
}

