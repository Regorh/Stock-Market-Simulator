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
                modify = -market.get_average_stock_price()/10;
                break;
            case "boom":
                modify = market.get_average_stock_price()/10;
                break;
            default:
        }
        market.change_stocks(modify);
    }

    public Market getMarket() {
        return this.market;
    }

    public ArrayList<String> get_stock_names(){
        return market.get_stock_names();

    }
    public ArrayList<Float> get_stock_prices(){
        return market.get_stock_prices();
    }
    public Float  get_market_price(String name){
        return market.get_stock_price(name);
    }

   


}
