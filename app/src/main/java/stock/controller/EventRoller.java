package stock.controller;

import java.util.ArrayList;
import java.util.Random;
public class EventRoller {

    private ArrayList<String> events;
    private ArrayList<String> previous_events;
    private Random rand;

    public EventRoller(ArrayList<String> events){
        this.events = events;
        this.previous_events = new ArrayList<String>();
        this.rand = new Random();
        //ADD EVENTS TO A NEW LINE IN THE EVENTS FILE
    }

    public String roll_out(){
        //roll for all available events, we can have this roll multiple times/ check if the event is a user/market/stock
        int i = rand.nextInt(0,events.size()-1);
        //saving a list of previous events
        String event = this.events.get(i);
        this.previous_events.add(event);
        return event;
    }

    public ArrayList<String> get_previous_events(){return this.previous_events;}
}
