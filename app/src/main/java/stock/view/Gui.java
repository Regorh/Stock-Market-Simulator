package stock.view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import stock.model.User;
import stock.ControllerInterface;
import stock.model.*;
import stock.view.*;
import stock.GameObserver;

public class Gui implements GameObserver {
    private User player;
    private ControllerInterface controller;
    private Market market;
    private Stock stocks;
    private User user;

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //createAndShowGUI();
        });
    }
    JPanel familyback;
    
    private static Container marketpane;
    //private static void createAndShowGUI() {
    public Gui(ControllerInterface controller, User player) {
        this.controller = controller;
        this.player = player;
        this.player.register(this);

        JFrame frame = new JFrame("Main UI");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(screenSize.width, screenSize.height);
        

        // Create panels
        //JPanel panel1  = createPanel(Color.red);
        String[] listColorNames = {  
        "BLACK",  
        "BLUE",  
        "GREEN", 
        "BLACK",  
        "BLUE",  
        "GREEN",
        "BLACK",  
        "BLUE",  
        "GREEN",
        "BLACK",  
        "BLUE",  
        "GREEN",
        "BLACK",  
        "BLUE",  
        "GREEN",
        "BLACK",  
        "BLUE",  
        "GREEN",
        "BLACK",  
        "BLUE",  
        "GREEN",
        "BLACK",  
        "BLUE",  
        "GREEN", 
        "YELLOW",  
        "ORANGE"  
        };

        String[] stocks = {
            "Apple",  
            "NIO",  
            "Oracle", 
            "BLACK",  
            "BLUE",  
        };
        Color[] listColorValues = {  
            Color.BLACK,  
            Color.BLUE,  
            Color.GREEN,  
            Color.YELLOW,  
            Color.WHITE,
            Color.ORANGE
        };

    //Maket Stocks Panel
        JPanel marketPanel = new JPanel();
        marketPanel.setSize(500, 300);
        JList list = new JList(listColorNames);
        list.setSelectedIndex(0);  
        //list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
        JScrollPane scrollPane = new JScrollPane(list);
        //Container contentPane = mainPanel.getContentPane();
       // contentPane.add(scrollPane, BorderLayout.CENTER);

        list.addListSelectionListener(new ListSelectionListener()  
            {  
                public void valueChanged(ListSelectionEvent e)  
                {  
                   //contentPane.setBackground(listColorValues[list.getSelectedIndex()]);  
                }  
            });  
    
        marketPanel.add(scrollPane);
        marketPanel.setVisible(true);
        JPanel panel1 = new JPanel();
        panel1.add(marketPanel);



//Owned Stocks Panel
        JPanel ownedPanel = new JPanel();
        ownedPanel.setLayout(new BoxLayout(ownedPanel,BoxLayout.Y_AXIS));
        ownedPanel.setSize(500,300);
        JList ownedList = new JList(stocks);
        ownedList.setSelectedIndex(0);
        JScrollPane ownedPane = new JScrollPane(ownedList);
        list.addListSelectionListener(new ListSelectionListener()  
            {  
                public void valueChanged(ListSelectionEvent e)  
                {  
                   //contentPane.setBackground(listColorValues[list.getSelectedIndex()]);  
                }  
            });
        
        
        ownedPanel.add(ownedPane);
        ownedPanel.setVisible(true);
        JPanel panel3 = new JPanel();
      //  panel3.add(ownedPanel);

//Player Stats
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel,BoxLayout.Y_AXIS));
        //JPanel familyback = new JPanel();
        JLabel family = new JLabel("FAMILY",SwingConstants.LEFT);
        family.setFont(new Font("Arial", Font.BOLD, 10));
        family.setAlignmentX(Component.LEFT_ALIGNMENT);
        family.setForeground(Color.BLACK);
        JProgressBar familyBar = new JProgressBar(0,100);
        familyBar.setValue(50);
        familyBar.setStringPainted(true);
        familyBar.setForeground(Color.yellow);
        //familyback.setBackground(new Color(0, 0, 139));
        JLabel sec = new JLabel("SEC",SwingConstants.LEFT);
        sec.setFont(new Font("Arial", Font.BOLD, 10));
        sec.setAlignmentX(Component.LEFT_ALIGNMENT);
        sec.setForeground(Color.BLACK);
        JProgressBar secBar = new JProgressBar(0,100);
        secBar.setValue((int) player.getsuspicionOfSEC());
        secBar.setStringPainted(true);
        secBar.setForeground(Color.yellow);
        //familyback.setVisible(true);
       // Dimension size = family.getPreferredSize();
        //family.setBounds(50,50,size.width,size.height);
        JLabel debt = new JLabel("Debt: " + player.getcurrentDebt(),SwingConstants.LEFT);
        debt.setFont(new Font("Arial", Font.BOLD, 10));
        debt.setAlignmentX(Component.LEFT_ALIGNMENT);
        debt.setForeground(Color.BLACK);
        JButton payOff = new JButton("Payoff");
        payOff.setBackground(Color.GREEN);
        payOff.setOpaque(true);
        payOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //open up new gui. 
            }
        });

        JLabel totalPortfolio = new JLabel("Total cash: " + player.getCapital());
        totalPortfolio.setHorizontalAlignment(SwingConstants.CENTER);
        totalPortfolio.setFont(new Font("Arial", Font.BOLD, 10));
        totalPortfolio.setAlignmentX(Component.LEFT_ALIGNMENT);
        totalPortfolio.setForeground(Color.BLACK);

     
       // familyBar.set
        //familyback.add(familyBar);
        //familyback.add(family);

        
        statsPanel.add(family);
        statsPanel.add(familyBar);
        statsPanel.add(Box.createVerticalStrut(10));
        statsPanel.add(sec);
        statsPanel.add(secBar);
        statsPanel.add(Box.createVerticalStrut(10));
        statsPanel.add(debt);
        statsPanel.add(payOff);
        statsPanel.add(totalPortfolio);

        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));

        statsPanel.setVisible(true);
        JPanel panel4 = new JPanel();
        panel4.add(statsPanel);
        

        JPanel panel2 = createPanel(Color.BLUE);
        
// Trade Panel
        JPanel tradePanel = new JPanel(new GridLayout(3,3));
        //JPanel tradePanel = new JPanel(new FlowLayout());
        JTextField textField = new JTextField(10);

        JLabel label1 = new JLabel("Label 1");
        JLabel label2 = new JLabel("Label 2");
        JLabel costLabel = new JLabel("Cost: $0.00");
        JButton upArrowButton = new JButton("\u2191"); // Up arrow
        JButton downArrowButton = new JButton("\u2193"); // Down arrow
        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");
        JButton nextDay = new JButton("Next Day");


        JSpinner spinner;
        SpinnerModel spinnermodel = new SpinnerNumberModel(0, 0, 100000000,1);
        spinner = new JSpinner(spinnermodel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
        spinner.setEditor(editor);



        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle sell button click
            }
        });
        upArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinner.setValue((int) spinner.getValue() + 1);
            }
        });
        downArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((int)spinner.getValue() > 0){
                    spinner.setValue((int) spinner.getValue() - 1);
                }
            }
        });
        nextDay.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {

            }
        });

        tradePanel.add(label1);
        tradePanel.add(spinner);
        tradePanel.add(label2);
        tradePanel.add(upArrowButton);
        tradePanel.add(costLabel);
        tradePanel.add(downArrowButton);
        tradePanel.add(buyButton);
        tradePanel.add(nextDay);
        tradePanel.add(sellButton);

         JPanel panel5 = new JPanel(new BorderLayout());
        panel5.add(tradePanel,BorderLayout.CENTER);

// Events display
        JPanel events = new JPanel(new FlowLayout());
        JTextField eventsField = new JTextField(10);
        eventsField.setEditable(false);
        events.add(eventsField);
        JPanel panel6 = new JPanel();
        panel6.add(events);

        // Set layout manager
        frame.setLayout(new GridLayout(2, 3));

        // Add panels to the frame
        frame.add(list);
        frame.add(panel2);
        frame.add(ownedList);
        frame.add(panel4);
        frame.add(panel5);
        frame.add(panel6);

        // Display the frame
        frame.setVisible(true);
    }







    private static JPanel createPanel(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panel;
    }

    @Override
    public void update(){
        player.setcurrentDebt((float)2000.00);

    }

}


