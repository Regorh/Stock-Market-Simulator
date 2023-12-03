package stock.model;

import java.util.ArrayList;
import stock.GameObserver;

public class User {


    
    private float capital;
    private int suspicionOfSEC;                 
    private double currentDebt;
    private int stress;
    private transient ArrayList<Stock> stocks = new ArrayList<Stock>();
    private transient ArrayList<GameObserver> observers = new ArrayList<GameObserver>();

    //public User(int debt, float suspicion,float cash){
    public User(){
        this.currentDebt = 0;
        this.suspicionOfSEC = 50;
        this.capital = 0;
        

    }

    public void register(GameObserver observer){
        observers.add(observer);
    }

    public void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }
    
    public float getCapital(){
        return capital;
    }

    public int getsuspicionOfSEC(){
        return suspicionOfSEC;
    }

    public double getcurrentDebt(){
        return currentDebt;
    }

    public void setCapital(float value){
        capital = value;
    }

    public void setsuspicionOfSEC(int value){
        suspicionOfSEC = value;
    }

    public void setcurrentDebt(float debt){
        currentDebt = debt;
    }

    public void increaseStress(int value){
        stress += value;
    }

    public void decreaseStress(int value){
        stress -= value;
    }

    public void sellStock(Stock ticker){
        stocks.remove(ticker);
    }

    public void buyStock(Stock ticker){
        stocks.add(ticker);
    }
}
