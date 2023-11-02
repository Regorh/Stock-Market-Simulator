package stock.model;

import java.util.HashMap;

public class User {

    private float capital;
    private float suspicionOfSEC;                 
    private double currentDebt;
    private int stress;

    private HashMap<Integer, Float> stocks_list;
    
    public HashMap<Integer, Float> get_stocks() {
        return stocks_list;
    }
}
