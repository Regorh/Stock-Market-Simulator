package stock.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public void create_stocks(int num){
        for(int i = 0; i < num; i++){
            char letter = (char)(rand.nextInt(24) + 65);
            String stock_type = "TEST_TYPE: " + letter;
            Stock stock = new Stock(create_name(), stock_type, rand.nextInt(4), (100*rand.nextFloat()));
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
            default:
                break;
        }
    }

    public void process_end() {
        // signifies to the class that no further events for the turn
        // are queued. used to tick through timed events

        // exists for futureproofing, no current use case
    }
}
