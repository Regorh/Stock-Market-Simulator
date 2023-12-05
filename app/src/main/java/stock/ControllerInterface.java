package stock;

import java.util.ArrayList;

import stock.model.*;

public interface ControllerInterface {

    public void userChoose(String mode);
     
    //public void initializeState();

    public void nextday();

    public Market getMarket();

    public User getUser();



    public boolean buy(String get_name, float get_price, int value);

    public boolean sell(String get_name, float get_price, int value);

    public ArrayList<String> marketstocknames();

    public ArrayList<Float> marketstockprices();

    public ArrayList<Float> userstockprice();

    public ArrayList<String> userstocknames();

    public ArrayList<Integer> userstockamount();
    

}