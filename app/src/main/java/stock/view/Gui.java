package stock.view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.google.common.primitives.Floats;

import java.io.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import stock.ControllerInterface;
import stock.model.*;
import stock.view.*;
import stock.GameObserver;
import stock.controller.EventRoller;

import java.util.ArrayList;

public class Gui implements GameObserver {
    private User player;
    private ControllerInterface controller;
    private Stock stocks;
    private User user;
    private GameManager manager;
    private EventRoller roller;
    private ArrayList<String> stockNames = new ArrayList<String>();
    private ArrayList<Float> stockPrices = new ArrayList<Float>();
    
    JLabel toBeTraded;
    JLabel toBeCost;
    JLabel costLabel;
    Stock tradedStock;
    JSpinner spinner;
    Market market;
    DefaultListModel<Stock> stockListModel;
    DefaultListModel<Stock> ownListModel;
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //createAndShowGUI();
        });
    }
    JPanel familyback;
    
    private static Container marketpane;
    //private static void createAndShowGUI() {
    public Gui(ControllerInterface controller, GameManager manager, EventRoller roller ) {
        this.manager = manager;
        this.controller = controller;
        this.manager.register(this);
        this.roller = roller;
      

        
        this.market = manager.getMarket();
        this.player = controller.getUser();

        JFrame frame = new JFrame("Main UI");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(screenSize.width, screenSize.height);
        

        // Create panels
        
        List<Stock> stocksmarket = market.get_stock();
        //DefaultListModel<String> nameModel = new DefaultListModel<>();
        //DefaultListModel<Float> priceModel = new DefaultListModel<>();

        this.stockListModel = new DefaultListModel<>();
        this.ownListModel = new DefaultListModel<>();
        


        for(Stock stock: stocksmarket ){
            stockListModel.addElement(stock);
            //stockNames.add(stock.get_name());
            //stockPrices.add(stock.get_price());
           // nameModel.addElement(stock.get_name());
           // priceModel.addElement(stock.get_price());
        }



        String[] stocks = {
            "Apple",  "NIO",  "Oracle",  "BLACK",   "BLUE",   "Apple",   "NIO",   "Oracle", 
             "Apple",  "NIO",  "Oracle",  "BLACK",   "BLUE",   "Apple",   "NIO",   "Oracle", 
            "Apple",  "NIO",  "Oracle",  "BLACK",   "BLUE",   "Apple",   "NIO",   "Oracle", 
            "Apple",  "NIO",  "Oracle",  "BLACK",   "BLUE",   "Apple",   "NIO",   "Oracle", 
            "Apple",  "NIO",  "Oracle",  "BLACK",   "BLUE",   "Apple",   "NIO",   "Oracle", 
            "Apple",  "NIO",  "Oracle",  "BLACK",   "BLUE",   "Apple",   "NIO",   "Oracle", 
            "Apple",  "NIO",  "Oracle",  "BLACK",   "BLUE",   "Apple",   "NIO",   "Oracle", 
            "Apple",  "NIO",  "Oracle",  "BLACK",   "BLUE",   "Apple",   "NIO",   "Oracle", 
            "Apple",  "NIO",  "Oracle",  "BLACK",   "BLUE",   "Apple",   "NIO",   "Oracle" 

        };
        

    //Maket Stocks Panel
        
       

        //String[] names = stockNames.toArray(new String[0]);

        JList<Stock> marketList = new JList<>(stockListModel);
        marketList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                Stock stock = (Stock) value;
                
                label.setText(stock.get_name() + " - $" + stock.get_price()); // Display name and price

                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(marketList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        marketList.addListSelectionListener(new ListSelectionListener()  {  
                public void valueChanged(ListSelectionEvent e)  
                {  
                    if (!e.getValueIsAdjusting()) {
                        costLabel.setText("Cost: $0.00");
                        Stock selectedStock = marketList.getSelectedValue();
                        toBeTraded.setText("Selected Stock: " + selectedStock.get_name());
                        toBeCost.setText("$" + selectedStock.get_price());
                        tradedStock = marketList.getSelectedValue(); 
                        spinner.setValue(0);
                    }
                }  
         });  
            
        
        JPanel marketPanel = new JPanel(new BorderLayout());
        marketPanel.setVisible(true);
        marketPanel.add(scrollPane, BorderLayout.CENTER);



//Owned Stocks Panel
        
       
        //ownedPanel.setLayout(new BoxLayout(ownedPanel,BoxLayout.Y_AXIS));
        //ownedPanel.setSize(500,300);

        JList<Stock> ownedList = new JList<>(ownListModel);
        ownedList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                Stock stock = (Stock) value;
                label.setText(stock.get_name() + " - $" + stock.get_price()); // Display name and price

                return label;
            }
        });
        
        JScrollPane ownedPane = new JScrollPane(ownedList);
        ownedPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        ownedList.addListSelectionListener(new ListSelectionListener()  
            {  
                public void valueChanged(ListSelectionEvent e)  
                {  
                    if (!e.getValueIsAdjusting()) {
                        costLabel.setText("Cost: $0.00");
                        Stock selectedStock = marketList.getSelectedValue();
                        toBeTraded.setText("Selected Stock: " + selectedStock.get_name());
                        toBeCost.setText("$" + selectedStock.get_price());
                        tradedStock = marketList.getSelectedValue(); 
                        spinner.setValue(0);
                    }                   
                }  
            });
        

        JPanel ownedPanel = new JPanel(new BorderLayout());
        ownedPanel.add(ownedPane,BorderLayout.CENTER);
        ownedPanel.setVisible(true);
      

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

        this.toBeTraded = new JLabel("");
        this.toBeCost= new JLabel();
        this.costLabel = new JLabel("Cost: $0.00");
        JButton upArrowButton = new JButton("\u2191"); // Up arrow
        JButton downArrowButton = new JButton("\u2193"); // Down arrow
        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");
        JButton nextDay = new JButton("Next Day");


        
        SpinnerModel spinnermodel = new SpinnerNumberModel(0, 0, 100000000,1);
        this.spinner = new JSpinner(spinnermodel);
        
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
        spinner.setEditor(editor);

        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int quantity = (int) spinner.getValue();
                
                // Assuming tradedStock is an instance of your Stock class
                Float costPerUnit = tradedStock.get_price();
        
                if (costPerUnit != null) {
                    float totalCost = quantity * costPerUnit;
                    costLabel.setText("Cost: $" + totalCost);
                } 
            }
});


        
        



        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.buy(tradedStock.get_name(), tradedStock.get_price(), (int)spinner.getValue() ) == true){
                    update();
                    ownListModel.addElement(tradedStock);


                }
                    
                
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.sell(tradedStock.get_name(), tradedStock.get_price(), (int)spinner.getValue() ) == true){
                    update();
                    ownListModel.removeElement(tradedStock);


                }
            }
        });
        upArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinner.setValue((int) spinner.getValue() + 1);
                costLabel.setText("Cost: " + ((int)spinner.getValue() * Float.parseFloat(toBeCost.getText())));
            }
        });

        downArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((int)spinner.getValue() > 0){
                    spinner.setValue((int) spinner.getValue() - 1);
                    costLabel.setText("Cost: " + ((int)spinner.getValue() * Float.parseFloat(toBeCost.getText())));
                }
            }
        });
        nextDay.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                
                controller.nextday();

            }
        });

        tradePanel.add(toBeTraded);
        tradePanel.add(spinner);
        tradePanel.add(toBeCost);
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
        frame.add(marketPanel);
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
        player = controller.getUser();
        
    }

}


