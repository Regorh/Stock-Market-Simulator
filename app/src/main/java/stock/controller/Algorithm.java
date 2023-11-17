package stock.controller;
import stock.model.Market;
import stock.model.Stock;
import stock.model.*;
import stock.view.*;


// testing
import java.util.List;
import java.util.Random;

public class Algorithm{

    startUI start;

    Random rand = new Random();

    public Algorithm(){
        this.start = new startUI();
    }

    public void userchoose(String mode){

    }

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
        double rand_double = rand.nextDouble(1);
        float current_price = stock.get_price();
        int current_stability = stock.get_stability();
        int sign = (rand.nextFloat(0,1) <= .52)? 1 : -1;
        //will fill out with more weights as we go
        float new_price = (float) (sign * current_price + current_stability * (current_price * (rand_double)));

        stock.set_price(new_price);
    }



}
