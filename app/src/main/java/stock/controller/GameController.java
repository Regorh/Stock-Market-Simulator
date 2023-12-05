package stock.controller;

import stock.ControllerInterface;
import stock.model.*;
import stock.view.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List.*;


public class GameController implements ControllerInterface{
    startUI start;
    Gui game;
    GameManager gm;
    User user;
    Market market;
    EventRoller roller;
    

    public GameController(){
        this.start = new startUI(this);
        this.gm = new GameManager();
        

    }

    public void userChoose(String mode){


        this.roller = new EventRoller();
        this.gm = new GameManager();

        this.user = new User(gm.get_avg());
        
        

        if (mode.equals("easy")) {
            user.setcurrentDebt((float) 100.00);
        }else if (mode.equals("medium")){
            user.setcurrentDebt((float) 1000.00);
        }else if (mode.equals("hard")){
            user.setcurrentDebt((float) 10000.00);
        }
        user.setCapital((float) 1000.00);
        user.setsuspicionOfSEC(50);
        this.game = new Gui(this,gm, roller);
    }

    public Market getMarket(){
        return gm.getMarket();
    }

    public User getUser(){
        return this.user;
    }

    public boolean buy(String ticker, float price, int amount){
        
        return user.buyStock(ticker, price, amount);
       
    }

    public boolean sell(String ticker, float price, int amount){
         return user.sellStock(ticker, price, amount);
    }


    public void nextday(){
        ArrayList<String> currentEvents = roller.roll_out();
        gm.next_day(currentEvents.get(0));
        user.process_event(currentEvents.get(1));
        game.update();


    }

   

    public ArrayList<String> stocknames(){
        ArrayList<String> names = new ArrayList<String>();
        HashMap<String, Integer> stocks = user.get_user_stocks();
        for (String stockName : stocks.keySet()) {
            names.add(stockName);
        }
        return names;

    }

    public ArrayList<Float> stockprice(){
        ArrayList<Float> prices = new ArrayList<Float>();
        HashMap<String, Integer> stocks = user.get_user_stocks();
        float price = 0;

        
        for (String stockName : stocks.keySet()) {
            price = market.get_market_price(stockName);
            prices.add(price);
        }
        return prices;
        
    }

    


}
