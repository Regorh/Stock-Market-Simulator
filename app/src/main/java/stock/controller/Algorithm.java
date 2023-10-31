package stock.controller;
import stock.model.Market;
// testing
import java.util.Random;

public class Algorithm{
    Random rand = new Random();
    
    //these will be flags for the event logger to grab, each even can say it requires certain flags
    //if all flags are met then the event is called

    boolean isUnstable = false;
    boolean isStable = false;
    boolean isHigh = false;
    boolean isLow = false;

    float average_stability = 0;
    float average_price = 0;
    EventLog eventlogger;

    //algorithm has an event logger, event logger is part of the algorithm in the sense that it is figuring 
    //out what events to do, and is used directly in the change of prices
    public Algorithm(){
        eventlogger = new EventLog(this);
    }

    public void next_day(Market market){
        List<Stock> list_of_stock = market.get_stock();
        //need to call event roller here to decide what event soccur,
        //this will help add weights to algortihm based on events

        //here is where we roll for events

        //space above for event roller

        average_price = 0;
        average_stability = 0;
        for( Stock stock : list_of_stock){
            stock_changer(stock);
            //BELOW HERE, we can check if the price has been falling a lot, then make stability lower
        }
        int size =list_of_stock.size();

        average_price=average_price/size;
        average_stability=average_stability/size;

        //flagging for eventlogger
        this.isHigh = (average_price >= 1000) ? true : false;
        this.isLow = (average_price <= 300 ) ? true : false;
        this.isUnstable = (average_stability <= 1.5) ? true : false;
        this.isStable = (average_stability >= 3.5) ? true : false;
    }

    private void stock_changer(Stock stock){
        int rand_double = rand.nextDouble();
        this.average_price+=stock.get_price();
        this.average_stability+=stock.get_stability();
        //edit this later on to have negatives base on roll
        int sign = 1;
        //will fill out with more weights as we go
        float new_price = (stock.get_price()+((sign)*stock.get_price()*((rand_double)*stock.get_stability)));

        stock.set_price(new_price);
    }

    
    public boolean isUnstable(){return this.isUnstable;}
    public boolean isStable(){return this.isStable;}
    public boolean isHigh(){return this.isHigh;}
    public boolean isLow(){return this.isLow;}





}
