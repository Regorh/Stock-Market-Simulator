package stock.model;

import java.util.ArrayList;

import stock.GameObserver;


import java.util.HashMap;

public class User {    

    private float capital;
    private int suspicionOfSEC;                 
    private float currentDebt;
    private int stress;
    private float payoffvalue;
    
    // flags: typically toggled by event processing
    private boolean flag_can_trade;

    // timers: ensures flags un-toggle on time
    private int timer_trade_prohib;
    private ArrayList<String> successfulEvents;
    private HashMap<String, Integer> stocks;

    public User(float avg_cost) {
        this.capital = avg_cost * 10;
        this.suspicionOfSEC = 0;
        this.currentDebt = capital*30;
        this.successfulEvents = new ArrayList<>();
        this.flag_can_trade = true;
        this.payoffvalue = 0.00f;
        this.stocks = new HashMap<String, Integer>();
    } 
      
    public boolean process_event(String event) {
        boolean event_fired = true;
        switch (event) {
            // User Events
            case "donated_50_dollars":
                if (this.capital >= 50) {
                    this.capital -= 50;
                    successfulEvents.add(event);
                } else {
                    event_fired = false;
                }
                break;
            case "double_stress":
                if (this.stress <= 25) {
                    this.stress *= 2;
                    successfulEvents.add(event);
                } else {
                    event_fired = false;
                }
                break;
            case "got_the_other_guy":
                if (this.suspicionOfSEC >= 75) {
                    this.suspicionOfSEC -= 10;
                    successfulEvents.add(event);
                } else {
                    event_fired = false;
                }
                break;
            case "she_took_the_kids":
                if (this.stress >= 80 && this.suspicionOfSEC >= 60) {
                    this.capital /= 2;
                    this.stress = 90;
                    successfulEvents.add(event);
                } else {
                    event_fired = false;
                }
                break;
            case "mysterious_benefactor":
                if (this.suspicionOfSEC <= 50) {
                    this.suspicionOfSEC += 20;
                    for (String stock : this.stocks.keySet()) {
                        this.stocks.replace(stock, this.stocks.get(stock), this.stocks.get(stock) + 1);
                    }
                } else {
                    event_fired = false;
                }
            case "favor_repayment":
                this.capital *= 1.15f;
                break;

            // Illegal Events
            case "steal_500":
                this.capital += 500f;
                this.suspicionOfSEC += 5;
                break;
            case "embezzlement_for_vacation":
                if (this.stress >= 30) {
                    this.stress -= 30;
                } else {
                    this.stress -= this.stress;
                }
                this.suspicionOfSEC += 15;
            default:
                break;
        }
        return event_fired;
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


    public int getsuspicionOfSEC() {
        return this.suspicionOfSEC;
    }

    public int get_stress() {
        return this.stress;
    }

    public void set_stress(int stress) {
        this.stress = stress;
    }

    public float getcurrentDebt() {
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

    public void setPayoff(float value){
        this.payoffvalue = value;
    }

    public boolean sellStock(String ticker, float price, int quantity) {
        if (this.stocks.containsKey(ticker) && this.flag_can_trade) {
            if (this.stocks.get(ticker) == quantity) {
                this.stocks.remove(ticker);
            } else if (this.stocks.get(ticker) > quantity) {
                this.stocks.put(ticker, this.stocks.get(ticker) - quantity);
            } else {
                return false;
            }
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
                this.stocks.put(ticker, quantity + this.stocks.get(ticker));
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

    public boolean reached_fail_state() {
        if (
            this.stress > 100 ||
            this.capital < 0 ||
            this.suspicionOfSEC > 100
        ) {
            return true;
        }
        return false;
    }

    public void payoffDebt() {
        if((this.getcurrentDebt() - payoffvalue)> 0 & ((this.getCapital() - payoffvalue) > 0)){
            this.setcurrentDebt(this.getcurrentDebt()- payoffvalue);
            this.setCapital(this.getCapital() - payoffvalue);
        }   
    }

    public int get_quantity_for(String ticker) {
        // return the quantity of shares the user owns of a given stock
        return this.stocks.get(ticker);
    }
}
