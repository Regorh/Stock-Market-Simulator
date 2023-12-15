package stock.view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.checkerframework.common.value.qual.StringVal;

import com.google.common.primitives.Floats;

import java.io.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import stock.ControllerInterface;
import stock.model.*;
import stock.view.*;
import stock.GameObserver;
import stock.controller.EventRoller;

import java.util.ArrayList;
import java.util.Enumeration;

public class Gui implements GameObserver {
    private User player;
    private ControllerInterface controller;
    private GameManager manager;
    private EventRoller roller;
    
    gameOver end;

    private EventRoller roller;
    boolean isMarket;
    JLabel toBeTraded;
    JLabel toBeCost;
    JLabel costLabel;
    Stock tradedStock;
    JSpinner spinner;
    Market market;
    DefaultListModel<String> stockListModel;
    DefaultListModel<String> ownListModel;
    JList<String> marketList;
    JScrollPane scrollPane;
    JPanel marketPanel;
    JList<String> ownedList;
    JPanel ownedPanel;
    JPanel debtPanel;
    JScrollPane ownedPane;
    String stocktradedString;
    Float stocktradedPrice;
    int quantity;
    JPanel familyBack;
    private ArrayList<String> marketNames;
    private ArrayList<Float> marketPrices;
    private ArrayList<String> userNames;
    private ArrayList<Float> userPrices;
    private ArrayList<Integer> userAmount;
    static DecimalFormat decimalFormat;
    JFrame frame;
    JLabel totalPortfolio;
    JPanel statsPanel;
    String chosenStock;
    String chosenstockSell;
    String stocktradedStringsell;
    Float stocktradedPricesell;
    JProgressBar stressBar;
    JProgressBar secBar;
    JTextArea eventsField;
    JLabel debtLabel;
    JLabel walletLabel;
    JLabel transferLabel;
    JLabel debt;


    //private static void createAndShowGUI() {
    public Gui(ControllerInterface controller, GameManager manager, EventRoller roller ) {
        decimalFormat = new DecimalFormat("#.##");
        this.manager = manager;
        this.controller = controller;
        this.manager.register(this);
        this.roller = roller;
        this.marketPanel = new JPanel(new BorderLayout());
        isMarket = true;
        
        this.market = manager.getMarket();
        this.player = controller.getUser();

        this.frame = new JFrame("Main UI");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(screenSize.width, screenSize.height);
        

        // Create panels
        this.marketNames = controller.marketstocknames();
        this.marketPrices = controller.marketstockprices();
        this.userNames = controller.userstocknames();
        this.userPrices = controller.userstockprice();
        this.userAmount = controller.userstockamount();



        this.stockListModel = new DefaultListModel<String>();
        this.ownListModel = new DefaultListModel<String>();

        

        
        
       


        //creating market panel, populating
        for (int i = 0; i < marketNames.size(); i++) {
            Float newStockPrice = marketPrices.get(i);
            Float price = Float.valueOf(decimalFormat.format(newStockPrice));
            String newStock = (marketNames.get(i) + "   $" + price );
            System.out.println(marketNames.get(i) + "   $" + price);
            stockListModel.addElement(marketNames.get(i) + "   $" + price);
        }

        //setting up all of the market to load
        this.marketList = new JList<>(stockListModel);
        this.marketList.setModel(stockListModel);
        this.scrollPane = new JScrollPane(marketList);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        marketPanel.add(scrollPane, BorderLayout.CENTER);

        //creating stock panel, populating with info
        ArrayList<String> userstocks = new ArrayList<String>();
        this.userAmount = controller.userstockamount();
        for (int i = 0; i < userNames.size(); i++) {
            Float userStockPrice = userPrices.get(i);
            Integer amount = userAmount.get(i);
            Float price = Float.valueOf(decimalFormat.format(userStockPrice));
            String newStock = (userNames.get(i) + "   $" + price + "  " + amount);
            userstocks.add(newStock);
            ownListModel.addElement(userNames.get(i) + "   $" + price + "  " + amount);
        }

        //setting up everything in the gui to load
        this.ownedList= new JList<>(ownListModel);
        this.ownedList.setModel(ownListModel);
        this.ownedPane = new JScrollPane(ownedList);
        ownedPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ownedPanel = new JPanel(new BorderLayout());
        ownedPanel.add(ownedPane,BorderLayout.CENTER);


        update_market_list();
//Owned Stocks Panel
        update_owned_list();

//Debt Payoff Panel

       /*  this.debtPanel = new JPanel();
        debtPanel.setLayout(new GridLayout(3,2));
        this.debtLabel = new JLabel("Debt: $" + player.getcurrentDebt());
        this.walletLabel = new JLabel("Wallet: $" + player.getCapital());
        this.transferLabel = new JLabel("<html>Transfer Amount:<br/>  0.00  </html>", SwingConstants.CENTER);
        JTextField transferAmount = new JTextField();
        JLabel inputCheck = new JLabel();

        //JButton incrementButton = new JButton("Increase");
        //JButton decrementButton = new JButton("Decrease");
        JButton acceptButton = new JButton("Accept");
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    float f = Float.parseFloat(transferAmount.getText());
                    if (player.getCapital() - f >= 0) {
                        controller.currentDebt(player.getCapital() - f);
                        inputCheck.setText("It worked" + player.getcurrentDebt());
                        update();
                    } else {
                        inputCheck.setText("Not enough cash");
                        update();
                    }
                } catch (NumberFormatException a) {
                    inputCheck.setText("Invalid Input. Enter as 0.00");
                    update();
                }
            }
        }); */

/* 
        debtPanel.add(walletLabel);
        debtPanel.add(debtLabel);
        debtPanel.add(transferLabel);
        debtPanel.add(transferAmount);
        debtPanel.add(inputCheck);
        debtPanel.add(acceptButton);
        debtPanel.setVisible(true);*/
        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel title = new JLabel("Stock Market Game");
        title.setFont(new Font("Comic Sans", Font.BOLD, 50));
        titlePanel.add(title);
        //panel2.add(debtPanel); 

        





//Player Stats
        this.statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel,BoxLayout.Y_AXIS));
        JLabel family = new JLabel("STRESS",SwingConstants.CENTER);
        family.setFont(new Font("Arial", Font.BOLD, 30));
        family.setAlignmentX(Component.CENTER_ALIGNMENT);
        family.setForeground(Color.BLACK);
        this.stressBar = new JProgressBar(0,100);
        this.stressBar.setValue(player.get_stress());
        this.stressBar.setStringPainted(true);
        this.stressBar.setForeground(Color.yellow);
        JLabel sec = new JLabel("SEC'S SUSPICION",SwingConstants.CENTER);
        sec.setFont(new Font("Arial", Font.BOLD, 30));
        sec.setAlignmentX(Component.CENTER_ALIGNMENT);
        sec.setForeground(Color.BLACK);
        this.secBar = new JProgressBar(0,100);
        this.secBar.setValue(player.getsuspicionOfSEC());
        this.secBar.setStringPainted(true);
        this.secBar.setForeground(Color.yellow);

        this.debt = new JLabel("Debt: " + player.getcurrentDebt(),SwingConstants.CENTER);
        this.debt.setFont(new Font("Arial", Font.BOLD, 25));
        this.debt.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.debt.setForeground(Color.BLACK);
        JButton payOff = new JButton("Payoff");
        
        payOff.setOpaque(true);
        payOff.setAlignmentY(Component.CENTER_ALIGNMENT);
        payOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                controller.currentDebt();
                update();
            }
        });

        JButton illegal = new JButton("Illegal Action");
        illegal.setOpaque(true);
        illegal.setAlignmentY(Component.CENTER_ALIGNMENT);
        illegal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                
                update();
            }
        });     

        this.totalPortfolio = new JLabel("Total cash: " + player.getCapital());
        this.totalPortfolio.setHorizontalAlignment(SwingConstants.CENTER);
        this.totalPortfolio.setFont(new Font("Arial", Font.BOLD, 30));
        this.totalPortfolio.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.totalPortfolio.setForeground(Color.BLACK);

        
        statsPanel.add(family);
        statsPanel.add(stressBar);
        statsPanel.add(Box.createVerticalStrut(10));
        statsPanel.add(sec);
        statsPanel.add(secBar);
        statsPanel.add(Box.createVerticalStrut(10));
        statsPanel.add(debt);
        statsPanel.add(totalPortfolio);
        statsPanel.add(payOff);
        statsPanel.add(illegal);

        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));

        statsPanel.setVisible(true);
        JPanel statPanel = new JPanel();
        statPanel.add(statsPanel);
        

        
// Trade Panel
        JPanel tradePanel = new JPanel(new GridLayout(3,3));
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
                Float costPerUnit = stocktradedPrice;
        
                if (costPerUnit != null) {
                    float totalCost = quantity * costPerUnit;
                    costLabel.setText("Cost: $" + totalCost);
                } 
            }
        });

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int spinner_value = (int)spinner.getValue();
                if (spinner_value >= 1) {
                    // spinner_value = (int)spinner.getValue();
                    if (controller.buy(stocktradedString, controller.get_stock_price(stocktradedString), (int) spinner.getValue())){
                        // ownListModel.addElement(stocktradedString + "   $" + controller.get_stock_price(stocktradedString) + "  " + a);
                        Enumeration<String> ticker_enumeration = ownListModel.elements();
                        boolean owns_ticker = false;
                        int ticker_idx = 0;
                        int enumeration_counter = 0;
                        while (ticker_enumeration.hasMoreElements()) {
                            String ticker_full = ticker_enumeration.nextElement();
                            String ticker_substr = ticker_full.substring(0, 3);
                            System.out.println("SUBSTR: \"" + ticker_substr + "\" VS. \"" + stocktradedString + "\"");
                            if (stocktradedString.equals(ticker_substr)) {
                                System.out.println("HIT");
                                owns_ticker = true;
                                ticker_idx = enumeration_counter;
                            }
                            enumeration_counter += 1;
                        }
                        if (!owns_ticker) {
                            ownListModel.addElement(stocktradedString + "   $" + controller.get_stock_price(stocktradedString) + "  " + spinner_value);
                        } else {
                            ownListModel.set(ticker_idx, stocktradedString + "   $" + controller.get_stock_price(stocktradedString) + "  " + controller.get_user_quantity_for(stocktradedString));
                        }
                        update();
                    }
                } 
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO debug
                // System.out.println("===\nSpinner Val: " + (Integer)spinner.getValue());
                // System.out.println("STSS:" + stocktradedStringsell);
                // System.out.println("Stock Price: " + controller.get_stock_price(stocktradedStringsell) + "\n===");
                //

                int spinner_value = (int) spinner.getValue();
                stocktradedStringsell = marketList.getSelectedValue().substring(0,3);
                int user_quantity_before_change = controller.get_user_quantity_for(stocktradedString);
                if(spinner_value > 0) {
                    if (controller.sell(stocktradedStringsell,  controller.get_stock_price((stocktradedStringsell)), (Integer) spinner.getValue())) {
                        Enumeration<String> ticker_enumeration = ownListModel.elements();
                        boolean owns_ticker = false;
                        int ticker_idx = 0;
                        int enumeration_counter = 0;
                        while (ticker_enumeration.hasMoreElements()) {
                            String ticker_full = ticker_enumeration.nextElement();
                            String ticker_substr = ticker_full.substring(0, 3);
                            System.out.println("SUBSTR: \"" + ticker_substr + "\" VS. \"" + stocktradedString + "\"");
                            if (stocktradedString.equals(ticker_substr)) {
                                System.out.println("HIT");
                                owns_ticker = true;
                                ticker_idx = enumeration_counter;
                            }
                            enumeration_counter += 1;
                        }
                        if (!owns_ticker || user_quantity_before_change - spinner_value == 0) {
                            ownListModel.remove(ticker_idx);
                        } else {
                            ownListModel.set(ticker_idx, stocktradedString + "   $" + controller.get_stock_price(stocktradedString) + "  " + controller.get_user_quantity_for(stocktradedString));
                        }

                        //
                        
                        update();
                        // update();
                        //make sure the gui had the correct stocks to be loaded, if not have them reload, dont just remove the elemeents int he list
                    }
                }
            }
        });
        upArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinner.setValue((int) spinner.getValue() + 1);
                float price;
                if(isMarket){
                    price = stocktradedPrice;
                }else{
                    price = stocktradedPricesell;
                }
                costLabel.setText("Cost: " + decimalFormat.format(((int)spinner.getValue() * price)));
            }
        });

        downArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((int)spinner.getValue() > 0){
                    spinner.setValue((int) spinner.getValue() - 1);
                    float price;
                    if(isMarket){
                        price = stocktradedPrice;
                    }else{
                        price = stocktradedPricesell;
                    }
                    costLabel.setText("Cost: " + decimalFormat.format( ((int)spinner.getValue() * price)));
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

        JPanel tradingPanel = new JPanel(new BorderLayout());
        tradingPanel.add(tradePanel,BorderLayout.CENTER);

// Events display
        JPanel events = new JPanel(new FlowLayout(FlowLayout.LEADING));
        this.eventsField = new JTextArea();
        this.eventsField.setEditable(false);
        this.eventsField.setLineWrap(true);
        this.eventsField.setRows(20);
        this.eventsField.setColumns(30);
        JScrollPane eventScroll = new JScrollPane(eventsField);
        eventScroll.setViewportView(eventsField);
        eventScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        events.add(eventScroll);
        JLabel eventTitle = new JLabel("User and Market Events");
        
        JPanel currentEvents = new JPanel(new FlowLayout());
        currentEvents.add(eventTitle);
        currentEvents.add(events);



        // Set layout manager
        frame.setLayout(new GridLayout(2, 3));

        // Add panels to the frame
        frame.add(marketPanel);
        frame.add(titlePanel);
        frame.add(ownedList);
        frame.add(statPanel);
        frame.add(tradingPanel);
        frame.add(currentEvents);

        // Display the frame
        frame.setVisible(true);
    }


    private void update_owned_list(){

        isMarket=false;
        this.userNames = controller.userstocknames();
        this.userAmount = controller.userstockamount();
        
        ownedPanel.revalidate();
        ownedPanel.repaint();
        ownedPane.revalidate();
        ownedPane.repaint();

        ownedList.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting()) {
                    if(ownedList.isSelectionEmpty())
                        chosenstockSell = ownListModel.get(0);

                    if (ownedList.getSelectedValue() != null) {
                        chosenstockSell = ownedList.getSelectedValue();
                    }else{
                        chosenstockSell = ownListModel.get(0);
                    }
                    costLabel.setText("Cost: $0.00");

                    stocktradedStringsell = marketList.getSelectedValue().substring(0,3);
                    toBeTraded.setText("Selected Stock: " + stocktradedStringsell);
                    //Float no = price;
                    stocktradedPricesell = controller.get_stock_price(stocktradedStringsell);
                    toBeCost.setText("$" + decimalFormat.format(stocktradedPricesell));

                    spinner.setValue(0);
                }
            }
        });

        ownedPanel.setVisible(true);
        ownedPanel.revalidate();
        ownedPanel.repaint();

    }


    public void update_market_list(){
        marketPanel.setVisible(false);

        isMarket=true;
        System.out.println(marketPrices.get(0));

        for (int i = 0; i < stockListModel.size(); i++) {
            Float newStockPrice = marketPrices.get(i);
            float price = Float.parseFloat(decimalFormat.format(newStockPrice));
            String newStock = (marketNames.get(i) + "   $" + price );
            //System.out.println(marketnames.get(i) + "   $" + price);
            stockListModel.set(i,(marketNames.get(i) + "   $" + price));
        }

        System.out.println("Doing it");


        marketList.addListSelectionListener(new ListSelectionListener()  {
            public void valueChanged(ListSelectionEvent e)
            {
                if(marketList.isVisible() && stockListModel.size()>1) {
                    if (!e.getValueIsAdjusting()) {

                        if (marketList.isSelectionEmpty())
                            marketList.setSelectedValue(stockListModel.get(0), true);

                        costLabel.setText("Cost: $0.00");
                        if (marketList.getSelectedValue() != null) {
                            chosenStock = marketList.getSelectedValue();
                        } else {
                            chosenStock = stockListModel.get(0);
                        }
                        stocktradedString = chosenStock.substring(0, 3);
                        stocktradedPrice = controller.get_stock_price(stocktradedString);
                        System.out.println(stocktradedPrice +"work");
                        toBeTraded.setText("Selected Stock: " + stocktradedString);
                        toBeCost.setText("$" + decimalFormat.format(stocktradedPrice));

                    }
                }
            }
        });

        marketPanel.setVisible(true);
        marketPanel.revalidate();
        marketPanel.repaint();


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
        market = controller.getMarket();

        if (player.reached_fail_state()){
            frame.setVisible(false);
            this.end = new gameOver();

        }
        this.marketPrices = controller.marketstockprices();
        this.userNames = controller.userstocknames();
        this.userPrices = controller.userstockprice();
        this.userAmount = controller.userstockamount();
        update_market_list();
        update_owned_list();

        System.out.println("We are here");
      
        this.secBar.setValue(player.getsuspicionOfSEC());
        this.secBar.revalidate();
        this.secBar.repaint();
        this.stressBar.setValue(player.get_stress());
        this.stressBar.revalidate();
        this.stressBar.repaint();

        this.eventsField.setText(controller.get_event_description());
        this.eventsField.revalidate();
        this.eventsField.repaint();

        this.debt.setText("Debt: " + player.getcurrentDebt());

        /* this.debtLabel = new JLabel("Debt: $" + player.getcurrentDebt());
        this.debtLabel.revalidate();
        this.debtLabel.repaint();
    
        this.walletLabel = new JLabel("Wallet: $" + player.getCapital());
        this.walletLabel.revalidate();
        this.walletLabel.repaint(); */


        this.totalPortfolio.setText("Total cash: " + player.getCapital());
        this.totalPortfolio.revalidate();
        this.totalPortfolio.repaint();

        /* this.debtPanel.revalidate();
        this.debtPanel.repaint(); */

        this.statsPanel.revalidate();
        this.statsPanel.repaint();

       

        spinner.setValue(0);
        //System.out.println(player.getCapital());
        stocktradedPrice = controller.get_stock_price(stocktradedString);
        stocktradedPricesell = controller.get_stock_price(stocktradedStringsell);
        //this.totalPortfolio.repaint();
        frame.revalidate();
        frame.repaint();




        
    }

}


