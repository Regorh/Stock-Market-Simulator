package stock.model;
import stock.GameObserver;
import stock.controller.EventRoller;
import stock.model.*;
import stock.view.*;

import java.util.List;
import java.util.ArrayList;


// testing

import java.util.Random;


public class GameManager {
    Random rand = new Random();
    Market market;
    EventRoller roller;
    User user;
    private ArrayList<GameObserver> observers = new ArrayList<GameObserver>();

    
    public GameManager(){
        market = new Market();
    }

    public void register(GameObserver observer){
        observers.add(observer);
    }

    public void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }

    public float get_avg(){
        return market.get_average_stock_price();
    }

    
    public void next_day(String event){

        //need to call event roller here to decide what event occur,
        //this will help add weights to algorithm based on events
        
        float modify = 0;

        switch(event) {
            case "crash":
                // code block
                modify = -market.get_average_stock_price()/10;
                break;
            case "boom":
                // code block
                modify = market.get_average_stock_price()/10;
                break;
            default:
                // code block
        }
        market.change_stocks(modify);
    }


    /* private void stock_changer(float modify, Stock stock){
        double rand_double = rand.nextDouble(1);
        float current_price = stock.get_price();
        int current_stability = stock.get_stability();
        int sign = (rand.nextFloat(0,1) <= .52)? 1 : -1;
        //will fill out with more weights as we go
        float new_price = modify + (float) ( current_price + (sign *current_stability * (current_price * (rand_double))));

        stock.set_price(new_price);
    } */

    public Market getMarket() {
        return this.market;
    }

   


}
