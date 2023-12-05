package stock.model;

import java.util.ArrayList;

import stock.GameObserver;


import java.util.HashMap;

public class User {    

    private float capital;
    private int suspicionOfSEC;                 
    private double currentDebt;
    private int stress;
    private transient ArrayList<GameObserver> observers = new ArrayList<GameObserver>();
    
    // flags: typically toggled by event processing
    private boolean flag_can_trade;

    // timers: ensures flags un-toggle on time
    private int timer_trade_prohib;

    //public User(int debt, float suspicion,float cash){
  
    private ArrayList<String> successfulEvents;
    // private transient ArrayList<Stock> stocks = new ArrayList<Stock>(); TODO figure out where this came from
    private HashMap<String, Integer> stocks;

    public User(float avg_cost) {
        this.capital = avg_cost * 10;
        this.suspicionOfSEC = 0;
        this.currentDebt = capital*30;
        this.successfulEvents = new ArrayList<>();
        this.flag_can_trade = true;
        
        this.stocks = new HashMap<String, Integer>();
        
        
    }
  
      
    public void process_event(String event) {
        switch (event) {
            case "donated_50_dollars":
                if (this.capital >= 50) {
                    this.capital -= 50;
                    successfulEvents.add(event);
                }
                break;
            case "double_stress":
                this.stress = (this.stress <= 25) ? this.stress * 2 : this.stress;
                if (this.stress <= 25) {
                    this.stress *= 2;
                    successfulEvents.add(event);
                }
                break;
            case "got_the_other_guy":
                if (this.suspicionOfSEC >= 75) {
                    this.suspicionOfSEC -= 10;
                    successfulEvents.add(event);
                }
                break;
            case "she_took_the_kids":
                if (this.stress >= 80 && this.suspicionOfSEC >= 60 && this.successfulEvents.size() >= 3) {
                    this.capital /= 2;
                    this.stress = 90;
                    successfulEvents.add(event);
                }
                break;
            case "mysterious_benefactor":
                if (this.suspicionOfSEC <= 50) {
                    this.suspicionOfSEC += 20;
                    for (String stock : this.stocks.keySet()) {
                        this.stocks.replace(stock, this.stocks.get(stock), this.stocks.get(stock) + 1);
                    }
                }
           // case "cant_trade":
                // cannot transact for the next two turns
                // timer ticks down when process_end is called
               // this.flag_can_trade = false;
                //this.timer_trade_prohib = 2;
               // break;
            case "favor_repayment":
                this.capital *= 1.15f;
                break;
            default:
                break;

        }
    }

    public void process_end() {
        // signifies to the class that no further events for the turn
        // are queued. used to tick through timed events
        // e.g. "cant_trade"
        if (this.timer_trade_prohib > 0) { this.timer_trade_prohib -= 1; } 
        else { this.flag_can_trade = true; }
    }
    
    public float getCapital() {
        return this.capital;
    }


    public float getsuspicionOfSEC() {
        return this.suspicionOfSEC;
    }

    public double getcurrentDebt() {
        return this.currentDebt;
    }

    public void setCapital(float value) {
        this.capital = value;
    }

    public void setsuspicionOfSEC(int value){
        this.suspicionOfSEC = value;
    }

    public void setcurrentDebt(float debt){
        this.currentDebt = debt;

    }

    public void increaseStress(int value) {
        this.stress += value;
    }

    public void decreaseStress(int value) {
        this.stress -= value;
    }

    public boolean sellStock(String ticker, float price, int quantity) {
        if (this.stocks.containsKey(ticker) && this.stocks.get(ticker) > quantity && this.flag_can_trade) {
            this.stocks.replace(ticker, this.stocks.get(ticker), this.stocks.get(ticker) - quantity);
            this.capital += price * quantity;
            return true;
        }
        return false;
    }

    public boolean buyStock(String ticker, float price, int quantity) {
        if (this.capital >= price * quantity && this.flag_can_trade) {
            if (!this.stocks.containsKey(ticker)) {
                this.stocks.put(ticker, quantity);
            } else {
                this.stocks.replace(ticker, this.stocks.get(ticker), this.stocks.get(ticker) + quantity);
            }
            this.capital -= price * quantity;
            return true;
        }
        return false;
    }

    public boolean get_trade_capability() { return this.flag_can_trade; }

    public HashMap<String, Integer> get_user_stocks() { 
        return this.stocks; 
    }
}
