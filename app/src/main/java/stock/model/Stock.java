package stock.model;

import java.util.Random;

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

        // TODO -- improve/specify random behavior
        Random rnd = new Random();
        rnd.setSeed(1234567890);
        this.stability = rnd.nextInt();
    }

    float get_price() { return this.price; }

    void set_price(float price) { this.set_price(price); }

    float get_stability() { return this.stability; }

    String get_type() { return this.type; }

    int get_id() { return this.id; }

    String get_name() { return this.name; }

}
