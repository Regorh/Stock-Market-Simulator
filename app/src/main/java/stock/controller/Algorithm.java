package stock.controller;
import stock.model.Market;
import stock.model.Stock;
// testing
import java.util.List;
import java.util.Random;

public class Algorithm{
    Random rand = new Random();

    public void next_day(Market market){
        List<Stock> list_of_stock = market.get_stock();
        //need to call event roller here to decide what event occur,
        //this will help add weights to algorithm based on events
        for( Stock stock : list_of_stock){
            stock_changer(stock);
            //BELOW HERE, we can check if the price has been falling a lot, then make stability lower
        }

    }

    private void stock_changer(Stock stock){
        double rand_int = rand.nextDouble(1);
        float current_price = stock.get_price();
        int current_stability = stock.get_stability();

        //will fill out with more weights as we go
        float new_price = (float) (current_price+current_stability*(current_price*(rand_int)));

        stock.set_price(new_price);
    }



}
