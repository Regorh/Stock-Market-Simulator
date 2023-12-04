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

    //public User(int debt, float suspicion,float cash){
  
    private ArrayList<String> successfulEvents;
    // private transient ArrayList<Stock> stocks = new ArrayList<Stock>(); TODO figure out where this came from
    private HashMap<String, Integer> stocks;

    public User(float avg_cost) {
        this.capital = avg_cost * 10;
        this.suspicionOfSEC = 0;
        this.currentDebt = capital*30;
        this.successfulEvents = new ArrayList<>();
    }
  
    
    public void register(GameObserver observer){
        observers.add(observer);
    }

    public void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
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
            default:
                break;

        }
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

    public void sellStock(String ticker, float price) {
        if (this.stocks.get(ticker) > 0) {
            this.stocks.replace(ticker, this.stocks.get(ticker), this.stocks.get(ticker) - 1);
            this.capital += price;
        }
    }

    public void buyStock(String ticker, float price) {
        if (this.capital >= price) {
            this.stocks.replace(ticker, this.stocks.get(ticker), this.stocks.get(ticker) + 1);
            this.capital -= price;
        }
    }
}
