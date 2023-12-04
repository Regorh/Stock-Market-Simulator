package stock.model;

import java.util.ArrayList;


public class User {


    
    private float capital;
    private float suspicionOfSEC;                 
    private double currentDebt;
    private int stress;

    public User(float avg_cost){
        this.capital = (int)(avg_cost*10);
        this.suspicionOfSEC = 0;
        this.currentDebt = capital*30;
    }
    public void process_event(String event){

    }
    private transient ArrayList<Stock> stocks = new ArrayList<Stock>();
    
    public float getCapital(){
        return capital;
    }

    public float getsuspicionOfSEC(){
        return suspicionOfSEC;
    }

    public double getcurrentDebt(){
        return currentDebt;
    }

    public void setCapital(float value){
        capital = value;
    }

    public void setsuspicionOfSEC(float value){
        suspicionOfSEC = value;
    }

    public void setcurrentDebt(float value){
        currentDebt = value;
    }

    public void increaseStress(int value){
        this.stress += value;
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
