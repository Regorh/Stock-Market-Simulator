package stock.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Market {
    int stability;
    Random rand;
    List<Stock> stocks;

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
}
