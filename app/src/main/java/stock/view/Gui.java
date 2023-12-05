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
    DefaultListModel<String> stockListModel;
    DefaultListModel<String> ownListModel;
    JList<String> marketList;
    JScrollPane scrollPane;
    JPanel marketPanel;
    JList<String> ownedList;
    JPanel ownedPanel;
    JScrollPane ownedPane;
    String stocktradedString;
    Float stocktradedPrice;
    int quantity;
    JPanel familyback;
    private ArrayList<String> marketnames;
    private ArrayList<Float> marketprices;
    private ArrayList<String> usernames;
    private ArrayList<Float> userprices;
    private ArrayList<Integer> useramount;
    static DecimalFormat decimalFormat;
    JFrame frame;
    JLabel totalPortfolio;
    JPanel statsPanel;
    String chosen_stock;
    String chosen_stock_sell;
    String stocktradedStringsell;
    Float stocktradedPricesell;
    private static Container marketpane;
    //private static void createAndShowGUI() {
    public Gui(ControllerInterface controller, GameManager manager, EventRoller roller ) {
        decimalFormat = new DecimalFormat("#.##");
        this.manager = manager;
        this.controller = controller;
        this.manager.register(this);
        this.roller = roller;
        this.marketPanel = new JPanel(new BorderLayout());

        
        this.market = manager.getMarket();
        this.player = controller.getUser();

        this.frame = new JFrame("Main UI");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(screenSize.width, screenSize.height);
        

        // Create panels
        this.marketnames = controller.marketstocknames();
        this.marketprices = controller.marketstockprices();
        this.usernames = controller.userstocknames();
        this.userprices = controller.userstockprice();
        this.useramount = controller.userstockamount();



        this.stockListModel = new DefaultListModel<String>();
        this.ownListModel = new DefaultListModel<String>();

        

        
        
       


        //creating market panel, populating
        for (int i = 0; i < marketnames.size(); i++) {
            Float newStockPrice = marketprices.get(i);
            Float price = Float.valueOf(decimalFormat.format(newStockPrice));
            String newStock = (marketnames.get(i) + "   $" + price );
            System.out.println(marketnames.get(i) + "   $" + price);
            stockListModel.addElement(marketnames.get(i) + "   $" + price);
        }

        //setting up all of the market to load
        this.marketList = new JList<>(stockListModel);
        this.marketList.setModel(stockListModel);
        this.scrollPane = new JScrollPane(marketList);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        marketPanel.add(scrollPane, BorderLayout.CENTER);

        //creating stock panel, populating with info
        ArrayList<String> userstocks = new ArrayList<String>();
        for (int i = 0; i < usernames.size(); i++) {
            Float userStockPrice = userprices.get(i);
            Integer amount = useramount.get(i);
            Float price = Float.valueOf(decimalFormat.format(userStockPrice));
            String newStock = (usernames.get(i) + "   $" + price + "  " + amount);
            userstocks.add(newStock);
            ownListModel.addElement(usernames.get(i) + "   $" + price + "  " + amount);
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


//Player Stats
        this.statsPanel = new JPanel();
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

        this.totalPortfolio = new JLabel("Total cash: " + player.getCapital());
        this.totalPortfolio.setHorizontalAlignment(SwingConstants.CENTER);
        this.totalPortfolio.setFont(new Font("Arial", Font.BOLD, 10));
        this.totalPortfolio.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.totalPortfolio.setForeground(Color.BLACK);

     
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
                int a  = 0;
                if((int) spinner.getValue() >= 1) {

                    a = (int)spinner.getValue();
                    if (controller.buy(stocktradedString, controller.get_stock_price(stocktradedString), (int) spinner.getValue())){
                        ownListModel.addElement(stocktradedString + "   $" + controller.get_stock_price(stocktradedString) + "  " + a);
                        update();

                    }
                }

                    
                
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((int) spinner.getValue() > 0) {
                    if (controller.sell(stocktradedStringsell,  controller.get_stock_price((stocktradedStringsell)), (int) spinner.getValue())) {
                        //System.out.println();
                        update();
                        //make sure the gui had the correct stocks to be loaded, if not have them reload, dont just remove the elemeents int he list


                    }
                }
            }
        });
        upArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinner.setValue((int) spinner.getValue() + 1);
                costLabel.setText("Cost: " + decimalFormat.format(((int)spinner.getValue() * stocktradedPrice)));
            }
        });

        downArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((int)spinner.getValue() > 0){
                    spinner.setValue((int) spinner.getValue() - 1);
                    costLabel.setText("Cost: " + decimalFormat.format( ((int)spinner.getValue() * stocktradedPrice)));
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


    private void update_owned_list(){


        for (int i = 0; i < ownListModel.size(); i++) {
             stocktradedStringsell = usernames.get(i);
            Integer amount = useramount.get(i);
            if (amount > 0) {
                float price = Float.parseFloat(decimalFormat.format(controller.get_stock_price(stocktradedStringsell)));
                String newStock = (usernames.get(i) + "   $" + price + "  " + amount);
                ownListModel.set(i, (newStock));
            }else{
                ownListModel.removeElement(i);
            }

        }

        ownedList.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting()) {
                    if(ownedList.isSelectionEmpty())
                        chosen_stock_sell = ownListModel.get(0);

                    if (ownedList.getSelectedValue() != null) {
                        chosen_stock_sell = ownedList.getSelectedValue();
                    }else{
                        chosen_stock_sell = ownListModel.get(0);
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


        System.out.println(marketprices.get(0));

        for (int i = 0; i < stockListModel.size(); i++) {
            Float newStockPrice = marketprices.get(i);
            float price = Float.parseFloat(decimalFormat.format(newStockPrice));
            String newStock = (marketnames.get(i) + "   $" + price );
            //System.out.println(marketnames.get(i) + "   $" + price);
            stockListModel.set(i,(marketnames.get(i) + "   $" + price));
        }

        System.out.println("Doing it");
        //this.marketList = new JList<>(market.toArray(new String[market.size()]));
        //this.marketList = new JList<>(stockListModel);
        //this.marketList.setModel(stockListModel);
        //this.scrollPane = new JScrollPane(marketList);
        //this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        marketList.addListSelectionListener(new ListSelectionListener()  {
            public void valueChanged(ListSelectionEvent e)
            {
                if(marketList.isVisible() && stockListModel.size()>1) {
                    if (!e.getValueIsAdjusting()) {

                        if (marketList.isSelectionEmpty())
                            marketList.setSelectedValue(stockListModel.get(0), true);

                        costLabel.setText("Cost: $0.00");
                        if (marketList.getSelectedValue() != null) {
                            chosen_stock = marketList.getSelectedValue();
                        } else {
                            chosen_stock = stockListModel.get(0);
                        }
                        stocktradedString = chosen_stock.substring(0, 3);
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
        this.marketprices = controller.marketstockprices();
        this.usernames = controller.userstocknames();
        this.userprices = controller.userstockprice();
        this.useramount = controller.userstockamount();
        update_market_list();
        update_owned_list();

        System.out.println("We are here");
        this.totalPortfolio.setText("Total cash: " + player.getCapital());
        this.totalPortfolio.revalidate();
        this.totalPortfolio.repaint();
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


