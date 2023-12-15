/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package stock.market.simulator;

import stock.controller.*;
import stock.model.*;
import stock.view.*;



import org.junit.Test;
import stock.App;

import static org.junit.Assert.*;

public class StockMarketTest {
    @Test public void user_stock_transactions() {
        // general test to ensure that stock interactions function correctly
        User user = new User(500);
        user.setCapital(500);
        user.buyStock("abc", 1, 1);
        user.buyStock("abc", 1, 1);
        user.buyStock("abc", 1, 1);

        assertEquals(
            true, 
            user.get_user_stocks().containsKey("abc")
            && user.get_user_stocks().get("abc") == 3
            && user.getCapital() == 497
        );

        user.buyStock("def", 1, 1);
        user.buyStock("ghi", 1, 1);
        user.buyStock("jkl", 1, 1);

        assertEquals(
            true, 
            user.get_user_stocks().containsKey("def")
            && user.get_user_stocks().get("def") == 1
            && user.getCapital() == 494
        );
        assertEquals(
            true, 
            user.get_user_stocks().containsKey("ghi")
            && user.get_user_stocks().get("ghi") == 1
            && user.getCapital() == 494
        );
        assertEquals(
            true, 
            user.get_user_stocks().containsKey("jkl")
            && user.get_user_stocks().get("jkl") == 1
            && user.getCapital() == 494
        );

        user.buyStock("zzz", 1, 1);

        assertEquals(
            true, 
            user.get_user_stocks().containsKey("zzz")
            && user.get_user_stocks().get("zzz") == 1
            && user.getCapital() == 493
        );

        user.sellStock("zzz", 1, 1);

        assertEquals(
            false, 
            user.get_user_stocks().containsKey("abc")
            && user.get_user_stocks().get("abc") == 3
            && user.getCapital() == 497
        );
    }

    @Test public void market_init() {
        Market market = new Market();

        // ensure that 100 stocks are being made;
        // test necessary due to collision risk of names
        assertEquals(market.get_stock_names().size(), 100);
        
        // stability checks
        market.process_event("high_stability");
        assertEquals(market.get_stability(), 4);

        market.process_event("high_uncertainty");
        assertEquals(market.get_stability(), 1);
    }
}
