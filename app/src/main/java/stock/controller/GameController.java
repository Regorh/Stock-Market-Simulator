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
    HashMap<String, Integer> userstocks;


    public GameController() {
        this.start = new startUI(this);
        this.gm = new GameManager();


    }

    public void userChoose(String mode) {


        this.roller = new EventRoller();
        this.gm = new GameManager();

        this.user = new User(gm.get_avg());


        if (mode.equals("easy")) {
            user.setcurrentDebt((float) 100.00);
        } else if (mode.equals("medium")) {
            user.setcurrentDebt((float) 1000.00);
        } else if (mode.equals("hard")) {
            user.setcurrentDebt((float) 10000.00);
        }
        user.setCapital((float) 1000.00);
        user.setsuspicionOfSEC(50);
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
        gm.next_day(currentEvents.get(0));
        user.process_event(currentEvents.get(1));
        game.update();


    }


    public ArrayList<String> userstocknames() {
        ArrayList<String> names = new ArrayList<String>();
        this.userstocks = user.get_user_stocks();
        if (this.userstocks != null) {
            for (String stockName : this.userstocks.keySet()) {
                names.add(stockName);
            }
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
}

