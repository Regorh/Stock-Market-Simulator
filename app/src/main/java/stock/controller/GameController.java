package stock.controller;

import stock.ControllerInterface;
import stock.model.*;
import stock.view.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class GameController implements ControllerInterface {
    startUI start;
    Gui game;
    GameManager gm;
    User user;
    Market market;
    EventRoller roller;
    int day;
    String user_event_name;
    String market_event_name;
    String illegal_event_name;
    boolean roll_illegal_flag;


    public GameController() {
        this.start = new startUI(this);
        this.gm = new GameManager();
        this.roller = new EventRoller();
        this.day = 1;
        this.roll_illegal_flag = false;
        this.illegal_event_name = "none";
    }

    public void userChoose(String mode) {
        this.user = new User(gm.get_avg());

        if (mode.equals("easy")) {
            user.setcurrentDebt((float) 10000.00);
            user.setsuspicionOfSEC(0);
            user.setPayoff(50.0f);
        } else if (mode.equals("medium")) {
            user.setcurrentDebt((float) 100000.00);
            user.set_stress(25);            
            user.setsuspicionOfSEC(25);
            user.setPayoff(10.0f);
        } else if (mode.equals("hard")) {
            user.setcurrentDebt((float) 10000000.00);
            user.setsuspicionOfSEC(50);
            user.set_stress(50);
            user.setPayoff(1.0f);
        }
        user.setCapital((float) 1000.00);
        this.game = new Gui(this, gm, roller);
    }

    public Market getMarket() {
        return gm.getMarket();
    }

    public User getUser() {
        return this.user;
    }

    public boolean buy(String ticker, float price, int amount) {

        return user.buyStock(ticker, price, amount);

    }

    public boolean sell(String ticker, float price, int amount) {
        return user.sellStock(ticker, price, amount);
    }


    public void nextday() {
        ArrayList<String> currentEvents = roller.roll_out();
        System.out.println("Turn events: " + currentEvents); // TODO debugging
        this.user_event_name = currentEvents.get(1);
        this.market_event_name = currentEvents.get(0);
        if (this.roll_illegal_flag) {
            this.illegal_event_name = roller.roll_illegal_action();
            System.out.println("NAME: " + illegal_event_name);
            user.process_event(illegal_event_name);
        }
        gm.next_day(currentEvents.get(0));
        this.user_event_name = (user.process_event(currentEvents.get(1))) ? this.user_event_name : "none";
        game.update();

        user.increaseStress(2);

        // can use this to show how far the player lasted
        this.day += 1;
        
    }


    public ArrayList<String> userstocknames() {
        ArrayList<String> names = new ArrayList<>();
        for (String stock_name : user.get_user_stocks().keySet()) {
            names.add(stock_name);
        }

        return names;
    }

    public ArrayList<Float> userstockprice() {
        ArrayList<Float> prices = new ArrayList<Float>();
        HashMap<String, Integer> stocks = user.get_user_stocks();
        float price = 0;

        if (stocks != null) {
            for (String stockName : stocks.keySet()) {
                price = gm.get_market_price(stockName);
                prices.add(price);
            }
        }
        return prices;

    }

    public ArrayList<Integer> userstockamount() {
        ArrayList<Integer> amount = new ArrayList<Integer>();
        HashMap<String, Integer> stocks = user.get_user_stocks();
        int value = 0;

        if (stocks != null) {
            for (Map.Entry<String, Integer> entry : stocks.entrySet()) {
                value = entry.getValue();
                amount.add(value);
            }
        }
        return amount;
    }


    public ArrayList<String> marketstocknames() {
        return gm.get_stock_names();

    }

    public ArrayList<Float> marketstockprices() {
        return gm.get_stock_prices();
    }
    public Float get_stock_price(String name){
        return gm.get_market_price(name);
    }

    public void set_illegal(boolean status) {
        this.roll_illegal_flag = status;
    }

    public String get_event_description(){
        String event_str = new String();
        event_str += "User: \n" + roller.get_description_for(this.user_event_name) + "\n\n";
        event_str += "Market:  \n" + roller.get_description_for(this.market_event_name) + "\n\n";
        if (this.roll_illegal_flag) {
            event_str += "Illegal: \n" + roller.get_description_for(this.illegal_event_name);
        }
        return event_str;
    }

    public void currentDebt(){
        user.payoffDebt();

    }

    public int get_user_quantity_for(String ticker) {
        return this.user.get_quantity_for(ticker);
    }

    public int get_days_played() {
        return this.day;
    }
}

