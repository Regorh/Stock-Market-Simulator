package stock.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.math.PairedStats;

public class Market{
    int stability;
    Random rand;
    List<Stock> stocks;

    // flags: typically toggled by event processing
    // private boolean flag_crash;

    // timers: ensures flags un-toggle on time
    // private int timer_crash;

    public Market(){
        rand = new Random();
        stocks = new ArrayList<Stock>();
        this.stability = rand.nextInt(4);
        create_stocks(100);
    }

    public float get_average_stock_price(){
        float average_stock_price = 0;

        for(Stock stock : stocks){
            average_stock_price += stock.get_price();
        }
        return  average_stock_price/stocks.size();
    }

    public List<Stock> get_stock() { return this.stocks; }

    //creates stocks of an amount requested, with random values
    private void create_stocks(int num){
        ArrayList<String> current_stock_names = new ArrayList<>();
        for (Stock stock : this.stocks) {
            current_stock_names.add(stock.get_name());
        }
        for(int i = 0; i < num; i++){
            char letter = (char)(rand.nextInt(24) + 65);
            String stock_type = "TEST_TYPE: " + letter;
            String name = create_name();
            while (current_stock_names.contains(name)) {
                name = create_name();
            }
            current_stock_names.add(name);
            Stock stock = new Stock(name, stock_type, rand.nextInt(4), (100*rand.nextFloat()));
            this.stocks.add(stock);
        }
    }
    
    private String create_name(){
        //creates a random name when requested, 3 letters, for stock tickers
        StringBuilder name = new StringBuilder();
        for(int i = 0; i < 3; i++){
            char letter = (char)(rand.nextInt(24) + 65);
            name.append(letter);
        }
        return name.toString();
    }

    public void change_stocks(float modify) {
        for (Stock stock : this.stocks) {
            stock_changer(modify, stock);
        }
    }

    private void stock_changer(float modify, Stock stock){
        double rand_double = rand.nextDouble(1);
        float current_price = stock.get_price();
        int current_stability = stock.get_stability();
        int sign = (rand.nextFloat(0,1) <= .52)? 1 : -1;
        //will fill out with more weights as we go
        float new_price = modify + (float) ( current_price + (sign * (current_price * (rand_double))));
        if (new_price <= .5 ){
            new_price = 0.5F;
        }
        stock.set_price(new_price);
    }

    public void process_event(String event) {
        switch (event) {
            case "crash":
                // stocks lose 10% value
                for (Stock stock : this.stocks) {
                    stock.set_price(stock.get_price() * 0.9f);
                }
                break;
            case "boom":
                // stocks gain 11.11...% value
                // crash and boom cancel one another out
                for (Stock stock : this.stocks) {
                    stock.set_price(stock.get_price() / 0.9f);
                }
                break;
            case "market_shakeup":
                this.stability = rand.nextInt(4);
                break;
            case "new_stocks":
                create_stocks(15);
                break;
            case "capital_flight":
                for (Stock stock : this.stocks) {
                    stock.set_price(stock.get_price() / 10f);
                }
                break;
            case "bull_market":
                for (Stock stock : this.stocks) {
                    stock.set_price(stock.get_price() * 1.15f);
                }
                break;
            case "high_stability":
                this.stability = 4;
                break;
            case "high_uncertainty":
                this.stability = 1;
                break;
            default:
                break;
        }
    }

    public void process_end() {
        // signifies to the class that no further events for the turn
        // are queued. used to tick through timed events

        // exists for futureproofing, no current use case
    }

    public float get_market_price(String ticker){
        float price = 0;
        for(Stock stock: stocks){
            if(stock.get_name() == ticker){
                price = stock.get_price();
            }
        }
        return price;
    }

    public ArrayList<String> get_stock_names(){
        ArrayList<String> marketstocks = new ArrayList<String>();
        for( Stock stockName: stocks){
            marketstocks.add(stockName.get_name());
        }
        return marketstocks;
    }

    public ArrayList<Float> get_stock_prices(){
        ArrayList<Float> marketstocks = new ArrayList<Float>();
        for( Stock stockName: stocks){
            marketstocks.add(stockName.get_price());
        }
        return marketstocks;
    }

}
