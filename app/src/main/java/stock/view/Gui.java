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
    DefaultListModel<Stock> stockListModel;
    DefaultListModel<Stock> ownListModel;
    JList<String> marketList;
    JScrollPane scrollPane;
    JPanel marketPanel;
    JList<String> ownedList;
    JPanel ownedPanel;
    String stocktradedString;
    Float stocktradedPrice;
   
    JPanel familyback;
    private ArrayList<String> marketnames;
    private ArrayList<Float> marketprices;
    private ArrayList<String> usernames;
    private ArrayList<Float> userprices;
    private ArrayList<Integer> useramount;
    
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
        this.marketnames = controller.marketstocknames();
        this.marketprices = controller.marketstockprice();
        this.usernames = controller.userstocknames();
        this.userprices = controller.userstockprice();
        this.useramount = controller.userstockamount();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");


        //ArrayList<Float> marketprices = controller.marketpricenames();
        //List<St> stocksmarket = market.get_stock();
    
        //DefaultListModel<String> nameModel = new DefaultListModel<>();
        //DefaultListModel<Float> priceModel = new DefaultListModel<>();

        this.stockListModel = new DefaultListModel();
        //marketnames.setModel(stockListModel);
        this.ownListModel = new DefaultListModel<>();

        

        
        
       

        /* for(Stock stock: stocksmarket ){
            this.stockListModel.addElement(stock);
            //stockNames.add(stock.get_name());
            //stockPrices.add(stock.get_price());
           // nameModel.addElement(stock.get_name());
           // priceModel.addElement(stock.get_price());
        } */


    //Maket Stocks Panel
        
        update_market_list();
//Owned Stocks Panel
        update_owned_list();
       
        //ownedPanel.setLayout(new BoxLayout(ownedPanel,BoxLayout.Y_AXIS));
        //ownedPanel.setSize(500,300);

      

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
                if (controller.buy(stocktradedString, stocktradedPrice, (int)spinner.getValue() ) == true){
                    update();
                    ownListModel.addElement(tradedStock);
                    

                }
                    
                
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.sell(stocktradedString, stocktradedPrice, (int)spinner.getValue() )){
                    update();
                    ownListModel.removeElement(tradedStock);


                }
            }
        });
        upArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinner.setValue((int) spinner.getValue() + 1);
                costLabel.setText("Cost: " + ((int)spinner.getValue() * stocktradedPrice));
            }
        });

        downArrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((int)spinner.getValue() > 0){
                    spinner.setValue((int) spinner.getValue() - 1);
                    costLabel.setText("Cost: " + ((int)spinner.getValue() * stocktradedPrice));
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
        DefaultListModel<String> ownedListModel = new DefaultListModel<>();

        ArrayList<String> userstocks = new ArrayList<String>();
        for (int i = 0; i < usernames.size(); i++) {
            Float userStockPrice = userprices.get(i);
            NumberFormat decimalFormat = new DecimalFormat("#.##");
            Integer amount = useramount.get(i);
            Float price = Float.valueOf(decimalFormat.format(userStockPrice));
            String newStock = (usernames.get(i) + "   $" + price + "  " + amount);
            userstocks.add(newStock);
            ownedListModel.addElement(usernames.get(i) + "   $" + price + "  " + amount);
        }


        //this.ownedList = new JList<>(userstocks.toArray(new String[userstocks.size()]));
        this.ownedList= new JList<>(ownedListModel);
        this.ownedList.setModel(ownedListModel);
        /* ownedList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                Stock stock = (Stock) value;
                label.setText(stock.get_name() + " - $" + stock.get_price()); // Display name and price

                return label;
            }
        }); */

        JScrollPane ownedPane = new JScrollPane(ownedList);
        ownedPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        ownedList.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting()) {
                    
                    costLabel.setText("Cost: $0.00");
                    String stock = ownedList.getSelectedValue();
                    stocktradedString = stock.substring(0,3);
                    toBeTraded.setText("Selected Stock: " + stocktradedString);
                    //Float no = price;
                    stocktradedPrice = Float.parseFloat(stock.substring(8,12));
                    toBeCost.setText("$" + stocktradedPrice);
    
                    spinner.setValue(0); 
                }
            }
        });

        ownedPanel = new JPanel(new BorderLayout());
        ownedPanel.add(ownedPane,BorderLayout.CENTER);
        ownedPanel.setVisible(true);
    }

    public void update_market_list(){
        DefaultListModel<String> marketListModel = new DefaultListModel<>();

        ArrayList<String> market = new ArrayList<String>();
        for (int i = 0; i < marketnames.size(); i++) {
            Float newStockPrice = marketprices.get(i);
            NumberFormat decimalFormat = new DecimalFormat("#.##");
            Float price = Float.valueOf(decimalFormat.format(newStockPrice));
            String newStock = (marketnames.get(i) + "   $" + price );
    
            market.add(newStock);
            marketListModel.addElement(marketnames.get(i) + "   $" + price);
        }
        
        //this.marketList = new JList<>(market.toArray(new String[market.size()]));
        this.marketList = new JList<>(marketListModel);
        this.marketList.setModel(marketListModel);
        this.scrollPane = new JScrollPane(marketList);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        marketList.addListSelectionListener(new ListSelectionListener()  {
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting()) {
                    costLabel.setText("Cost: $0.00");
                    String stock = marketList.getSelectedValue();
                    stocktradedString = stock.substring(0,3);
                    stocktradedPrice = Float.parseFloat(stock.substring(7,stock.length()));
                    toBeTraded.setText("Selected Stock: " + stocktradedString);
                    toBeCost.setText("$" + stocktradedPrice);
                    spinner.setValue(0);
                }
            }
        });

        this.marketPanel = new JPanel(new BorderLayout());
        marketPanel.setVisible(true);
        marketPanel.add(scrollPane, BorderLayout.CENTER);



    }

    /* private JList<Stock> getStockJList() {
       Market market = controller.getMarket();

        JList<Stock> marketList = new JList<>(stockListModel);

        marketList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                Stock stock = (Stock) value;

                label.setText(stock.get_name() + " - $" + stock.get_price()); // Dis name and price

                return label;
            }
        });
        return marketList;
    } */
 

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
        //update_market_list();
        update_owned_list();
        System.out.println("We are here");


        
    }

}


