package stock.model;


public class User {


    
    private float capital;
    private float suspicionOfSEC;                 
    private double currentDebt;
    private int stress;

    private Stock[] stocks;
    
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
        stress += value;
    }

    public void decreaseStress(int value){
        stress -= value;
    }

    public void sellStock(Stock ticker){
        // TODO: commented out temporarily to facilitate testing
        // stocks.remove(ticker);
    }

    public void buyStock(Stock ticker){
        // TODO: commented out temporarily to facilitate testing
        // stocks.add(ticker);
    }
}
