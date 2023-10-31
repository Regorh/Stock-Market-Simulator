package stock.model;

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

    public ArrayList get_stock() { return this.stocks; }

    //creates stocks of an amount requested, with random values
    private void create_stocks(int num){
        for( i = 0; i < num; i++){

            Stock stock = new Stock(create_name(),"testing",rand.nextInt(4),(100*rand.nextDouble()));
            this.stocks.add(stock);
        }
    }

    //creates a random name when requested, 3 letters, for stock tickers
    private String create_name(){
        String name = "";
        for(i = 0; i < 3; i++){
            Character letter = Character.toString(rand.nextInt(24)+65);
            name = name + letter;
        }
        return name;
    }
}
