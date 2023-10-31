package stock.model;
public class Stock {
    String name;
    String type;
    int id;
    float price;
    float stability;

    public Stock(String name, String type, int id, float price) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.price = price;
        // TODO rand stability -- awaiting Gradle integration
    }

    float get_price() { return this.price; }

    void set_price(float price) { this.set_price(price); }
}
