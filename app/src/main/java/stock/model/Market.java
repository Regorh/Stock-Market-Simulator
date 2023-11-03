package stock.model;

import java.util.ArrayList;
import java.util.HashMap;
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

    //creates stocks of an amount requested, with random values
    private void create_stocks(int num) {
        for(int i = 0; i < num; i++){
            char letter = (char)(rand.nextInt(24) + 65);
            String stock_type = "TEST_TYPE: " + letter;
            Stock stock = new Stock(create_name(), stock_type, rand.nextInt(4), (100*rand.nextFloat()));
            this.stocks.add(stock);
        }
    }

    //creates a random name when requested, 3 letters, for stock tickers
    private String create_name() {
        StringBuilder name = new StringBuilder();
        for(int i = 0; i < 3; i++){
            char letter = (char)(rand.nextInt(24) + 65);
            name.append(letter);
        }
        return name.toString();
    }

    /* public HashMap<Integer, Float> get_stock_info() {
        HashMap<Integer, Float> hm = new HashMap<>();
        for (Stock stock : this.stocks) {
            hm.put(stock.get_id(), stock.get_price());
        }
        return hm;
    } */

    public List<StockData> get_stocks() {
        List<StockData> sd = new ArrayList<StockData>();
        for (Stock s : this.stocks) {
            sd.add(new StockData(s.name, s.type, s.id, s.price, s.stability));
        }
        return sd;
    }

    public void stock_progression(){
        for (Stock stock : this.stocks) {
            double rand_int = rand.nextDouble(1);
            float current_price = stock.price;
            int current_stability = stock.get_stability();
    
            //will fill out with more weights as we go
            stock.set_price((float) (current_price+current_stability*(current_price*(rand_int))));
        }
    }
}
