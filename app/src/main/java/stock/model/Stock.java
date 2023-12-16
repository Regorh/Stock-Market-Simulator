package stock.model;

import java.util.Random;

public class Stock {
    String name;
    String type;
    int id;
    float price;
    int stability;

    public Stock(String name, String type, int id, float price) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.price = price;
        Random rnd = new Random();
        rnd.setSeed(1234567890);
        this.stability = rnd.nextInt();
    }

    public float get_price() { return this.price; }

    public void set_price(float price) { this.price = price; }

    public int get_stability() { return this.stability; }

    public String get_type() { return this.type; }

    public int get_id() { return this.id; }

    public String get_name() { return this.name; }

}
