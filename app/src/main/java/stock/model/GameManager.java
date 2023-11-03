package stock.model;

import java.util.List;
import java.util.Random;

public class Manager {
    Random rand;
    User user;
    Market market;
    int turn;

    public Manager() {
        this.rand = new Random();
        this.user = new User();
        this.market = new Market();
        this.turn = 0;
    }

    public TurnData next() {
        // Update market's stocks based on stability
        this.market.stock_progression();

        // Call event roller
        // Apply events as necessary

        // Generate collection of stock data to send to User and Driver (for GUI)
        List<StockData> stock_data = this.market.get_stocks();
        UserData user_data = this.user.get_data();

        return new TurnData(stock_data, user_data);
    }
}
