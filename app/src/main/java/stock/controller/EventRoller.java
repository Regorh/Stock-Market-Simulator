package stock.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventRoller {
    private record Event(String name, String description) {}

    HashMap<String, ArrayList<Event>> events;
    private ArrayList<String> previous_events;
    private Random rand;

    public EventRoller() {
        this.previous_events = new ArrayList<String>();
        this.rand = new Random();
        this.events = new HashMap<>();

        //ADD EVENTS TO A NEW LINE IN THE EVENTS FILE
        File file = new File("src/main/java/stock/controller/events.txt");

        String events_txt = new String();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = new String();
            while((line = br.readLine()) != null) {
                // Read in all lines of the file into a single string, preserving newlines.
                events_txt += line + "\n";
            }
            br.close();
        } catch (Exception e) {
            System.out.println("FILE DOES NOT EXIST, EVENTS CANNOT LOAD");
        }
        
        // Collecting all capture groups & matches against the above regex pattern.
        // This preliminary step first separates blocks by event type.
        String pattern_str = "(?:\\[(\\w+)Events\\]\\n" + 
                ")(?:([\\w\\n" + 
                "\\{\\}\\s\\:\\\"\\!\\?\\'\\.\\;\\,\\-\\$]+))+";
        Pattern p = Pattern.compile(pattern_str, Pattern.MULTILINE);
        Matcher m = p.matcher(events_txt);
        while (m.find()) {
            // event_block refers to the body of text after the event category tag (e.g. [MarketEvent])
            String event_category = m.group(1);
            String event_block = m.group(2);

            // This pattern extracts each event name and description pair from the event block
            Matcher block_matches = Pattern.compile("\\{\\s*(\\w+)\\s{0,}:\\s*\\\"(.*)\\\"\\s*\\}").matcher(event_block);
            ArrayList<Event> event_contents = new ArrayList<>();
            while (block_matches.find()) {
                Event event = new Event(block_matches.group(1), block_matches.group(2));
                event_contents.add(event);
            }
            this.events.put(event_category, event_contents);
        }
        // Output diagnostic info related to event loading to standard output
        System.out.println("Event parsing successful:");
        System.out.println("  Category Count: " + this.events.size());
        for (String cat : this.events.keySet()) {
            System.out.println("    " + cat + " Event Count: " + this.events.get(cat).size());
        }
    }

    public ArrayList<String> roll_out() {
        // Current rolls once for each specified event type (event_categories).
        // Special event types, such as IllegalEvents, are not rolled here. If a new category of rollable action
        // needs to be rolled, add its category name to event_categories.
        ArrayList<String> current_events = new ArrayList<String>();
        
        String[] event_categories = { "Market", "User" };
        for (String category : event_categories) {
            // This variable decides the percentage chance that an event is NOT rolled.
            // By default, it sits at 50%
            int chance_of_none = 50;
            boolean should_roll = rand.nextInt(0, 100) > (chance_of_none - 1);
            if (should_roll) {
                int rand_int = rand.nextInt(0, this.events.get(category).size() - 1);
                Event rolled_ev = this.events.get(category).get(rand_int);
                this.previous_events.add(rolled_ev.name);
                current_events.add(rolled_ev.name);
            } else {
                this.previous_events.add("none");
                current_events.add("none");
            }
        }

        return current_events;
    }

    public ArrayList<String> get_previous_events(){return this.previous_events;}

    public String get_description_for(String event_name) {
        if (event_name == "none") return "Nothing happened.";
        String desc = new String();
        for (ArrayList<Event> coll : this.events.values()) {
            for (Event ev : coll) {
                if (ev.name == event_name) desc = ev.description;
            }
        }
        return desc;
    }

    public String roll_illegal_action() {
        int rand_int = rand.nextInt(0, this.events.get("Illegal").size());
        return this.events.get("Illegal").get(rand_int).name;
    }
}
