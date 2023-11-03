package stock.model;

import java.util.HashMap;
import java.util.List;

public class User {

    private float capital;
    private float suspicion;                 
    private double currentDebt;
    private int stress;

    private HashMap<Integer, Float> stocks_list;
    
    public HashMap<Integer, Float> get_stocks() {
        return stocks_list;
    }

    public UserData get_data() {
        return new UserData(this.capital, this.suspicion, this.currentDebt, this.stress);
    }
}
