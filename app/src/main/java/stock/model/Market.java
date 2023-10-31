package stock.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Market {
    int stability;
    Random rand = new Random();
    List<Stock> stocks;

    boolean isUnstable = false;
    boolean isStable = false;
    boolean isHigh = false;
    boolean isLow = false;


    public Market(){
        Random rand = new Random();

        stocks = new ArrayList<Stock>();
        this.stability = rand.nextInt(4);
        create_stocks(100);
    }

    public List<Stock> get_stock() { return this.stocks; }

    //creates stocks of an amount requested, with random values
    private void create_stocks(int num){
        for(int i = 0; i < num; i++){
            char letter = (char)(rand.nextInt(24) + 65);
            String stocktype = "TEST_TYPE: " + letter;
            Stock stock = new Stock(create_name(), stocktype, rand.nextInt(4), (100*rand.nextFloat()));
            this.stocks.add(stock);
        }
    }

    //creates a random name when requested, 3 letters, for stock tickers
    private String create_name(){
        String name = "";
        for(int i = 0; i < 3; i++){
            char letter = (char)(rand.nextInt(24) + 65);
            name = name + letter;
        }
        return name;
    }

    public boolean isUnstable(){return this.isUnstable;}
    public boolean isStable(){return this.isStable;}
    public boolean isHigh(){return this.isHigh;}
    public boolean isLow(){return this.isLow;}

    public boolean set_isUnstable(boolean bool){ this.isUnstable = bool;}
    public boolean set_isStable(boolean bool){this.isStable = bool;}
    public boolean set_isHigh(boolean bool){this.isHigh = bool;}
    public boolean set_isLow(boolean bool){this.isLow = bool;}

}
