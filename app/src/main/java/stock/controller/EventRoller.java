package stock.controller;

import java.util.ArrayList;
import java.util.Random;
public class EventRoller {

    private ArrayList<ArrayList<String>> events;
    private ArrayList<String> previous_events;
    private Random rand;

    public EventRoller(ArrayList<ArrayList<String>> events){
        this.events = events;
        this.previous_events = new ArrayList<String>();
        this.rand = new Random();
        //ADD EVENTS TO A NEW LINE IN THE EVENTS FILE
        
    }







    public ArrayList<String> roll_out(){
        //roll for all available events, we can have this roll multiple times/ check if the event is a user/market/stock
        ArrayList<String> current_events = new ArrayList<String>();

        for(ArrayList<String> eventList : events) {
            int randint = rand.nextInt(0, eventList.size() - 1);
            String event = eventList.get(randint);
            this.previous_events.add(event);
            current_events.add(event);

        }
        //saving a list of previous events
        return  current_events;
    }


    public ArrayList<String> get_previous_events(){return this.previous_events;}
}
