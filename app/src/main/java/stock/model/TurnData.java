package stock.model;

import java.util.List;

public record TurnData(List<StockData> stock_data, UserData user_data) {
    
}
