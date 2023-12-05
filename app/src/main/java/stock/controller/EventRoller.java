package stock.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
public class EventRoller {

    private ArrayList<ArrayList<String>> events;
    private ArrayList<String> previous_events;
    private Random rand;

    public EventRoller(){
        this.previous_events = new ArrayList<String>();
        this.rand = new Random();

        //ADD EVENTS TO A NEW LINE IN THE EVENTS FILE

        File file = new File("src/main/java/stock/controller/events.txt");
        ArrayList<ArrayList<String>> events = new ArrayList<ArrayList<String>>();

        //creating the 3 lists, for the 3 kinds of event types
        for(int i = 0 ; i <2 ;i++ ){
            ArrayList<String> a = new ArrayList<String>();
            events.add(a);
        }
        try {

            //read the file and parse each line, checking if it should be added to events
            int y = -1;
            BufferedReader br = new BufferedReader(new FileReader(file));
            String event;
            while((event = br.readLine()) != null){
                int x;
                switch (event) {

                    case("#MarketEvents"):
                        x = 0;
                        y = 0;
                    break;

                    case("#UserEvents"):
                        y = 1;
                        x = 1;
                        break;

                    default:
                        x = -1;
                };

                System.out.println(event+x);
                //adds events to their proper list, only reads in if it is longer than length 2
                if(y>=0) {
                    events.get(y).add(event);
                }
            }
        }
        catch (Exception e){
            System.out.println("FILE DOES NOT EXIST, EVENTS CANNOT LOAD");
        }
        System.out.println(events);
        this.events = events;

    }







    public ArrayList<String> roll_out(){
        //roll for all available events, we can have this roll multiple times/ check if the event is a user/market/stock
        ArrayList<String> current_events = new ArrayList<String>();

        for(ArrayList<String> eventList : events) {
            System.out.println(eventList);
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
